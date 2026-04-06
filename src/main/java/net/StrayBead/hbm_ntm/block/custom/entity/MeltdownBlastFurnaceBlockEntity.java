package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.recipe.BlastFurnaceRecipe;
import net.StrayBead.hbm_ntm.recipe.ModRecipes;
import net.StrayBead.hbm_ntm.screen.MeltdownBlastFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MeltdownBlastFurnaceBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(6400) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    private void syncBlock() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public MeltdownBlastFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.BLAST_FURNACE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MeltdownBlastFurnaceBlockEntity.this.progress;
                    case 1 -> MeltdownBlastFurnaceBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MeltdownBlastFurnaceBlockEntity.this.progress = value;
                    case 1 -> MeltdownBlastFurnaceBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Blast Furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MeltdownBlastFurnaceMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER)
            return lazyFluidHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("blast_furnace.progress", this.progress);
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("blast_furnace.progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MeltdownBlastFurnaceBlockEntity pEntity) {
        if (level.isClientSide()) return;

        // --- DEBUG: TICK HEARTBEAT ---
        // If you don't see this every second, your Block class is missing the getTicker method!
        if (level.getGameTime() % 20 == 0) {
            System.out.println("DEBUG: [Tick] Heartbeat at " + pos + " | Fluid: " + pEntity.FLUID_TANK.getFluidAmount() + "mB");
        }

        System.out.println("Slot 1 Item: " + pEntity.itemHandler.getStackInSlot(1).getItem());
        System.out.println("Slot 2 Item: " + pEntity.itemHandler.getStackInSlot(2).getItem());
        System.out.println("Fluid: " + pEntity.FLUID_TANK.getFluid().getFluid() + " Fluid amount: " + pEntity.FLUID_TANK.getFluidAmount());

        // 1. FUEL CHECK
        ItemStack fuelSlot = pEntity.itemHandler.getStackInSlot(0);
        if (fuelSlot.is(Items.COAL) || fuelSlot.is(Items.CHARCOAL) || fuelSlot.is(ModBlocks.LIGNITE_ORE.get().asItem())) {
            if (pEntity.FLUID_TANK.getFluidAmount() <= pEntity.FLUID_TANK.getCapacity() - 100) {
                // Only consume/print fuel once per second to avoid spam
                System.out.println("DEBUG: [Fuel] Consuming " + fuelSlot.getItem().toString() + ". Tank now: " + (pEntity.FLUID_TANK.getFluidAmount() + 100));
                fuelSlot.shrink(1);
                pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.MOLTEN_FUEL.get(), 100), IFluidHandler.FluidAction.EXECUTE);
            }
        }

        // 2. RECIPE PREP
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        // 3. RECIPE MATCHING
        java.util.Optional<BlastFurnaceRecipe> match = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BLAST_FURNACE_TYPE.get(), inventory, level);

        if (match.isPresent()) {
            BlastFurnaceRecipe recipe = match.get();

            // --- DEBUG: RECIPE FOUND ---
            if (level.getGameTime() % 20 == 0) {
                System.out.println("DEBUG: [Recipe] Found Match: " + recipe.getId());
            }

            boolean hasEnoughFluid = pEntity.FLUID_TANK.getFluidAmount() >= 10;
            boolean canInsert = canInsertAmountIntoOutputSlot(inventory, recipe.getResultItem(level.registryAccess()));

            if (hasEnoughFluid && canInsert) {
                pEntity.progress++;

                // --- DEBUG: PROGRESS ---
                if (pEntity.progress % 10 == 0) { // Print every 10 ticks
                    System.out.println("DEBUG: [Progress] " + pEntity.progress + " / " + pEntity.maxProgress);
                }

                setChanged(level, pos, state);

                if (pEntity.progress >= pEntity.maxProgress) {
                    System.out.println("DEBUG: [Crafting] Completing recipe!");
                    craftItem(pEntity, recipe, level.registryAccess());
                }
            } else {
                // --- DEBUG: FAILURE REASON ---
                if (level.getGameTime() % 20 == 0) {
                    if (!hasEnoughFluid) System.out.println("DEBUG: [Wait] Not enough fluid (Needed: 10, Have: " + pEntity.FLUID_TANK.getFluidAmount() + ")");
                    if (!canInsert) System.out.println("DEBUG: [Wait] Output slot full or invalid item!");
                }
                coolDown(pEntity, level, pos, state);
            }
        } else {
            // --- DEBUG: NO MATCH ---
            // Uncomment this if you suspect the ingredients are wrong
            // if (level.getGameTime() % 20 == 0) System.out.println("DEBUG: [Recipe] No recipe matches items in slots 1 & 2");
            coolDown(pEntity, level, pos, state);
        }
    }

    private static void craftItem(MeltdownBlastFurnaceBlockEntity entity, BlastFurnaceRecipe recipe, RegistryAccess registryAccess) {
        entity.itemHandler.extractItem(1, 1, false);
        entity.itemHandler.extractItem(2, 1, false);

        ItemStack result = recipe.getResultItem(registryAccess);
        ItemStack currentOutput = entity.itemHandler.getStackInSlot(3);

        if (currentOutput.isEmpty()) {
            entity.itemHandler.setStackInSlot(3, result.copy());
        } else {
            currentOutput.grow(result.getCount());
        }

        entity.FLUID_TANK.drain(10, IFluidHandler.FluidAction.EXECUTE);

        entity.progress = 0;
        setChanged(entity.getLevel(), entity.getBlockPos(), entity.getBlockState());
    }

    private static void coolDown(MeltdownBlastFurnaceBlockEntity entity, Level level, BlockPos pos, BlockState state) {
        if (entity.progress > 0) {
            entity.progress--;
            setChanged(level, pos, state);
        }
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        ItemStack currentOutput = inventory.getItem(3);
        return currentOutput.isEmpty() || (currentOutput.getItem() == output.getItem() && currentOutput.getCount() + output.getCount() <= currentOutput.getMaxStackSize());
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
