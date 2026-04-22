package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.custom.BedrockOreProcessorBlock;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BedrockOreProcessorMenu;
import net.StrayBead.hbm_ntm.screen.ElectrolysisMachineMenu;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
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

public class BedrockOreProcessorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 10000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    public final ContainerData data;
    public int progress = 0;
    private int maxProgress = 78;

    public BedrockOreProcessorBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntites.BEDROCK_ORE_PROCESSOR_ENTITY.get(), p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BedrockOreProcessorBlockEntity.this.progress;
                    case 1 -> BedrockOreProcessorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BedrockOreProcessorBlockEntity.this.progress = value;
                    case 1 -> BedrockOreProcessorBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public final FluidTank FLUID_TANK = new FluidTank(64000) {

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    public final FluidTank OUTPUT_TANK = new FluidTank(64000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            String name = stack.getFluid().getFluidType().toString();
            return !name.contains("steam");
        }

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

    public FluidTank getOutputTank1() {
        return this.OUTPUT_TANK;
    }

    public void setOutputTank1(FluidStack stack) { this.OUTPUT_TANK.setFluid(stack); }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Bedrock Ore Processor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BedrockOreProcessorMenu(id, inventory, this, this.data);
    }

    private static class CombinedFluidHandler implements IFluidHandler {
        private final FluidTank[] tanks;

        public CombinedFluidHandler(FluidTank... tanks) {
            this.tanks = tanks;
        }

        @Override public int getTanks() { return tanks.length; }
        @Override public FluidStack getFluidInTank(int tank) { return tanks[tank].getFluid(); }
        @Override public int getTankCapacity(int tank) { return tanks[tank].getCapacity(); }
        @Override public boolean isFluidValid(int tank, FluidStack stack) { return tanks[tank].isFluidValid(stack); }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (tank.isFluidValid(resource)) return tank.fill(resource, action);
            }
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (tank.isFluidValid(resource)) return tank.drain(resource, action);
            }
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (!tank.getFluid().isEmpty()) return tank.drain(maxDrain, action);
            }
            return FluidStack.EMPTY;
        }
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, OUTPUT_TANK);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER)
            return fluidCapability.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
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
        super.saveAdditional(nbt);
        nbt.putInt("hbm.progress", this.progress);
        nbt.putInt("hbm.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.put("OutputTank", OUTPUT_TANK.writeToNBT(new CompoundTag()));
        FLUID_TANK.writeToNBT(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("hbm.progress");
        this.ENERGY_STORAGE.setEnergy(nbt.getInt("hbm.energy"));
        this.itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.FLUID_TANK.readFromNBT(nbt);
        if (nbt.contains("OutputTank")) {
            this.OUTPUT_TANK.readFromNBT(nbt.getCompound("OutputTank"));
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag()); // Client receives the packet and updates its local variables
    }

    public int animationTick = 0;
    public static void tick(Level level, BlockPos pos, BlockState state, BedrockOreProcessorBlockEntity pEntity) {
        if (level.isClientSide()) {
            if (pEntity.progress > 0) {
                pEntity.animationTick++;
            } else {
                pEntity.animationTick = 0;
            }
            return;
        }

        if (level.isClientSide()) return;

        if (pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.INFINITE_BATTERY.get()) {
            pEntity.ENERGY_STORAGE.receiveEnergy(10000, false);
        }

        boolean wasProcessing = pEntity.progress > 0;

        if (hasEnoughEnergy(pEntity) && pEntity.FLUID_TANK.getFluidAmount() >= 50) {
            if (pEntity.FLUID_TANK.getFluid().getFluid() == Fluids.WATER) {
                ItemStack inputStack = pEntity.itemHandler.getStackInSlot(2);

                if (inputStack.getItem() == ModItems.RAW_BEDROCK_ORE.get()) {
                    pEntity.progress++;
                    if (!wasProcessing) pEntity.syncBlock();

                    if (pEntity.progress >= pEntity.maxProgress) {
                        craftTheOre(pEntity);
                        pEntity.progress = 0;
                        pEntity.syncBlock();
                    }
                } else {
                    if (pEntity.progress != 0) {
                        pEntity.progress = 0;
                        pEntity.syncBlock();
                    }
                }
            }
        } else {
            pEntity.progress = 0;
        }

        if (!level.isClientSide) {
            boolean currentlyWorking = pEntity.progress > 0;

            if (state.getValue(BedrockOreProcessorBlock.WORKING) != currentlyWorking) {
                level.setBlock(pos, state.setValue(BedrockOreProcessorBlock.WORKING, currentlyWorking), 3);
            }
        }
    }

    private static void craftTheOre(BedrockOreProcessorBlockEntity pEntity) {
        pEntity.FLUID_TANK.drain(50, IFluidHandler.FluidAction.EXECUTE);
        pEntity.itemHandler.extractItem(2, 1, false);
        pEntity.ENERGY_STORAGE.extractEnergy(1000, false);

        java.util.Map<String, Double> chances = java.util.Map.of(
                "light_metal_bedrock_ore", 0.3,
                "heavy_metal_bedrock_ore", 0.53,
                "rare_earth_bedrock_ore", 0.58,
                "actinide_bedrock_ore", 0.58,
                "non_metal_bedrock_ore", 0.01,
                "crystalline_bedrock_ore", 0.52
        );

        boolean producedAnything = false;
        java.util.Random rand = new java.util.Random();

        for (java.util.Map.Entry<String, Double> entry : chances.entrySet()) {
            if (rand.nextDouble() < entry.getValue()) {
                var itemRegistryObject = ModItems.NUCLEAR_COMPONENTS.get(entry.getKey());

                if (itemRegistryObject != null) {
                    for (int i = 3; i < 11; i++) {
                        ItemStack remainder = pEntity.itemHandler.insertItem(i, new ItemStack(itemRegistryObject.get()), false);
                        if (remainder.isEmpty()) {
                            producedAnything = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!producedAnything) {
            pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.ORE_SLOP.get(), 50), IFluidHandler.FluidAction.EXECUTE);
        }

        pEntity.setChanged();
    }

    private static boolean hasEnoughEnergy(BedrockOreProcessorBlockEntity pEntity) {
        return (pEntity.ENERGY_STORAGE.getEnergyStored() > 200);
    }
}
