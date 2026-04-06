package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.ItemStackSyncS2CPacket;
import net.StrayBead.hbm_ntm.recipe.AssemblyRecipe;
import net.StrayBead.hbm_ntm.recipe.ModRecipes;
import net.StrayBead.hbm_ntm.screen.AssemblyMachineMenu;
import net.StrayBead.hbm_ntm.screen.BatterySocketMenu;
import net.StrayBead.hbm_ntm.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AssemblyMachineBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(15) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;

    public AssemblyMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.ASSEMBLY_MACHINE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AssemblyMachineBlockEntity.this.progress;
                    case 1 -> AssemblyMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AssemblyMachineBlockEntity.this.progress = value;
                    case 1 -> AssemblyMachineBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(200000, 50000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private static final int ENERGY_REQ = 32;

    @Override
    public Component getDisplayName() {
        return Component.literal("Assembly Machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AssemblyMachineMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("assembly_machine.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putInt("assembly_machine.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(nbt.getInt("assembly_machine.energy"));
        progress = nbt.getInt("assembly_machine.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AssemblyMachineBlockEntity pEntity) {
        if (level.isClientSide()) return;

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<AssemblyRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.ASSEMBLY_TYPE.get(), inventory, level);

        if (recipe.isPresent() && hasEnoughEnergy(pEntity) && canInsertIntoOutput(pEntity, recipe.get().getResultItem(level.registryAccess()).getItem())) {
            pEntity.progress++;
            extractEnergy(pEntity);

            if (pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity, recipe.get());
                pEntity.progress = 0;
            }
        } else {
            pEntity.progress = 0;
        }
    }

    private static void craftItem(AssemblyMachineBlockEntity pEntity, AssemblyRecipe recipe) {
        // Change this line to use getRecipeStacks() instead of getIngredients()
        for (ItemStack requirement : recipe.getRecipeStacks()) {
            int needed = requirement.getCount();
            for (int i = 0; i < 13; i++) {
                ItemStack stackInSlot = pEntity.itemHandler.getStackInSlot(i);
                if (ItemStack.isSameItem(stackInSlot, requirement)) {
                    int toExtract = Math.min(stackInSlot.getCount(), needed);
                    pEntity.itemHandler.extractItem(i, toExtract, false);
                    needed -= toExtract;
                }
                if (needed <= 0) break;
            }
        }
        pEntity.itemHandler.insertItem(14, recipe.getResultItem(pEntity.getLevel().registryAccess()), false);
    }

    private static boolean hasIngredients(AssemblyMachineBlockEntity pEntity, ItemStack[] requirements) {
        for (ItemStack requirement : requirements) {
            int count = 0;
            for (int i = 0; i < 13; i++) {
                ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
                if (stack.is(requirement.getItem())) {
                    count += stack.getCount();
                }
            }
            if (count < requirement.getCount()) return false;
        }
        return true;
    }

    private static void craftGeneric(AssemblyMachineBlockEntity pEntity, ItemStack[] requirements, ItemStack output) {
        for (ItemStack requirement : requirements) {
            int needed = requirement.getCount();
            for (int i = 0; i < 13; i++) {
                if (needed <= 0) break;
                ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
                if (stack.is(requirement.getItem())) {
                    int toExtract = Math.min(stack.getCount(), needed);
                    pEntity.itemHandler.extractItem(i, toExtract, false);
                    needed -= toExtract;
                }
            }
        }
        pEntity.itemHandler.insertItem(14, output.copy(), false);
    }

    private static int removeItem(AssemblyMachineBlockEntity pEntity, int slot, int amount) {
        ItemStack extracted = pEntity.itemHandler.extractItem(slot, amount, false);
        return extracted.getCount();
    }

    private static boolean canInsertIntoOutput(AssemblyMachineBlockEntity pEntity, net.minecraft.world.item.Item result) {
        ItemStack outputSlot = pEntity.itemHandler.getStackInSlot(14);
        return outputSlot.isEmpty() || (outputSlot.is(result) && outputSlot.getCount() < outputSlot.getMaxStackSize());
    }

    private static void extractEnergy(AssemblyMachineBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(AssemblyMachineBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * pEntity.maxProgress;
    }

    private static boolean hasBatteryInFirstSlot(AssemblyMachineBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(12).getItem() == ModItems.INFINITE_BATTERY.get();
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(0).isEmpty()) {
            stack = itemHandler.getStackInSlot(0);
        } else {
            stack = itemHandler.getStackInSlot(0);
        }

        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
}
