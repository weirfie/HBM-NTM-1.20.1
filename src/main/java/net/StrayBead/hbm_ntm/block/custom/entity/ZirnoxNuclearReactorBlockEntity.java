package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.ZirnoxNuclearReactorBlock;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BigAssTankGuiMenu;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
import net.StrayBead.hbm_ntm.screen.ZirnoxNuclearReactorGuiMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZirnoxNuclearReactorBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
    private String allowedFluid = "";
    protected final ContainerData data;
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public ZirnoxNuclearReactorBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntites.ZIRNOX_NUCLEAR_REACTOR.get(), position, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int getCount() {
                return 27;
            }
        };
    }

    public String getAllowedFluid() {
        return this.allowedFluid;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        this.allowedFluid = compound.getString("allowedFluid");
        ContainerHelper.loadAllItems(compound, this.stacks);
        if (compound.get("energyStorage") instanceof IntTag intTag)
            energyStorage.deserializeNBT(intTag);
        if (compound.get("fluidTank") instanceof CompoundTag compoundTag)
            fluidTank.readFromNBT(compoundTag);
        if (compound.contains("co2Tank"))
            co2Tank.readFromNBT(compound.getCompound("co2Tank"));
        if (compound.contains("co2Tank"))
            co2Tank.readFromNBT(compound.getCompound("co2Tank"));
        if (compound.contains("pressuretank"))
            pressuretank.readFromNBT(compound.getCompound("pressuretank"));
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.stacks = nonNullList;
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        compound.put("co2Tank", co2Tank.writeToNBT(new CompoundTag()));
        compound.put("pressuretank", pressuretank.writeToNBT(new CompoundTag()));
    }

    private final EnergyStorage energyStorage = new EnergyStorage(400000, 3000, 3000, 0) {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int retval = super.receiveEnergy(maxReceive, simulate);
            if (!simulate) {
                setChanged();
                level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 2);
            }
            return retval;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int retval = super.extractEnergy(maxExtract, simulate);
            if (!simulate) {
                setChanged();
                level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 2);
            }
            return retval;
        }
    };
    private final FluidTank fluidTank = new FluidTank(100000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return super.isFluidValid(stack);
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };
    private final FluidTank pressuretank = new FluidTank(50000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return super.isFluidValid(stack);
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
//            if (!level.isClientSide()) {
//                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
//            }
        }
    };

    private final FluidTank co2Tank = new FluidTank(100000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid().isSame(ModFluids.CARBON_DIOXIDE.get());
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
//            if (level != null && !level.isClientSide()) {
//                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
//            }
        }
    };

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @javax.annotation.Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
            return handlers[facing.ordinal()].cast();
        if (!this.remove && capability == ForgeCapabilities.ENERGY)
            return LazyOptional.of(() -> energyStorage).cast();
        if (capability == ForgeCapabilities.FLUID_HANDLER) {
            return LazyOptional.of(() -> new IFluidHandler() {
                @Override public int getTanks() { return 3; }

                @NotNull
                @Override
                public FluidStack getFluidInTank(int tank) {
                    return switch (tank) {
                        case 0 -> fluidTank.getFluid();
                        case 1 -> co2Tank.getFluid();
                        case 2 -> pressuretank.getFluid();
                        default -> FluidStack.EMPTY;
                    };
                }

                @Override
                public int getTankCapacity(int tank) {
                    return switch (tank) {
                        case 0 -> fluidTank.getCapacity();
                        case 1 -> co2Tank.getCapacity();
                        case 2 -> pressuretank.getCapacity();
                        default -> 0;
                    };
                }

                @Override
                public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
                    return switch (tank) {
                        case 0 -> fluidTank.isFluidValid(stack);
                        case 1 -> co2Tank.isFluidValid(stack);
                        case 2 -> pressuretank.isFluidValid(stack);
                        default -> false;
                    };
                }

                @Override
                public int fill(FluidStack resource, FluidAction action) {
                    if (resource.isEmpty()) return 0;

                    if (resource.getFluid().isSame(ModFluids.CARBON_DIOXIDE.get())) {
                        return co2Tank.fill(resource, action);
                    }

                    if (resource.getFluid().isSame(net.minecraft.world.level.material.Fluids.WATER)) {
                        return fluidTank.fill(resource, action);
                    }

                    if (pressuretank.isFluidValid(resource)) {
                        return pressuretank.fill(resource, action);
                    }

                    return 0;
                }

                @NotNull
                @Override
                public FluidStack drain(FluidStack resource, FluidAction action) {
                    if (resource.isFluidEqual(fluidTank.getFluid())) return fluidTank.drain(resource, action);
                    if (resource.isFluidEqual(co2Tank.getFluid())) return co2Tank.drain(resource, action);
                    if (resource.isFluidEqual(pressuretank.getFluid())) return pressuretank.drain(resource, action);
                    return FluidStack.EMPTY;
                }

                @NotNull
                @Override
                public FluidStack drain(int maxDrain, FluidAction action) {
                    FluidStack s = fluidTank.drain(maxDrain, action);
                    if (s.isEmpty()) s = co2Tank.drain(maxDrain, action);
                    if (s.isEmpty()) s = pressuretank.drain(maxDrain, action);
                    return s;
                }
            }).cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    public void setFluid(FluidStack stack) {
        this.fluidTank.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.fluidTank.getFluid();
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("zirnox");
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Zirnox");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new ZirnoxNuclearReactorGuiMenu(i, inventory, this, this.data);
    }

    @Override
    public int getContainerSize() {
        return 64;
    }

    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, ZirnoxNuclearReactorBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        if (pEntity.pressuretank.getFluidAmount() >= pEntity.pressuretank.getCapacity()) {
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 20f, Level.ExplosionInteraction.BLOCK);
            level.removeBlock(pos, false);
            return;
        }

        FluidStack currentFluid = pEntity.fluidTank.getFluid();
        FluidStack currentFluidCarbonDioxide = pEntity.co2Tank.getFluid();
        boolean hasEnoughWater = !currentFluid.isEmpty() &&
                currentFluid.getFluid().isSame(Fluids.WATER) &&
                currentFluid.getAmount() > 100;
        boolean hasEnoughCarbonDioxide = !currentFluidCarbonDioxide.isEmpty() &&
                currentFluidCarbonDioxide.getFluid().isSame(ModFluids.CARBON_DIOXIDE.get()) &&
                currentFluidCarbonDioxide.getAmount() > 100;

        if (hasEnoughWater && hasEnoughCarbonDioxide) {
            boolean steamHandled = false;
            FluidStack steamToOutput = new FluidStack(ModFluids.STEAM.get(), 100);

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);

                if (level.getBlockState(neighborPos).is(ModBlocks.STEAM_PIPE.get())) {
                    BlockEntity neighborBE = level.getBlockEntity(neighborPos);
                    if (neighborBE != null) {
                        var cap = neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite());
                        if (cap.isPresent()) {
                            IFluidHandler handler = cap.orElse(null);
                            int accepted = handler.fill(steamToOutput, IFluidHandler.FluidAction.EXECUTE);

                            if (accepted > 0) {
                                pEntity.fluidTank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                steamHandled = true;
                                break;
                            }
                        }
                    }
                }
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                if (neighborState.is(ModBlocks.STEAM_PIPE.get())) {
                    if (pEntity.fluidTank.getFluidAmount() > 100) {
                        pEntity.fluidTank.drain(100, IFluidHandler.FluidAction.EXECUTE);
                        {
                            BlockEntity _ent = level.getBlockEntity(neighborPos);
                            int _amount = 100;
                            if (_ent != null)
                                _ent.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(capability -> capability.fill(new FluidStack(ModFluids.STEAM.get(), _amount), IFluidHandler.FluidAction.EXECUTE));
                        }
                    }
                }
            }

            if (!steamHandled) {
                int acceptedIntoPressure = pEntity.pressuretank.fill(steamToOutput, IFluidHandler.FluidAction.EXECUTE);
                if (acceptedIntoPressure > 0) {
                    pEntity.fluidTank.drain(acceptedIntoPressure, IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
    }
}
