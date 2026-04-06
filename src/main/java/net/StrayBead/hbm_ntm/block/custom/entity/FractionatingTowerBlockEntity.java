package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class FractionatingTowerBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
    private int filterColor = 0xFFFFFF;
    private String allowedFluid = "";
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public FractionatingTowerBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntites.FRACTIONATING_TOWER_BLOCK_ENTITY.get(), position, state);
    }

    public void setFilterColor(int color) {
        this.filterColor = color;
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());

        if (level != null && level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public String getAllowedFluid() {
        return this.allowedFluid;
    }

    public void setAllowedFluid(String fluidName) {
        this.allowedFluid = fluidName;
        this.setChanged();
    }

    public void setFilterAndFluid(int color, String fluidName) {
        this.setFilterColor(color);
        this.allowedFluid = fluidName;
    }

    public int getFilterColor() {
        return this.filterColor;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
        if (compound.get("fluidTank") instanceof CompoundTag compoundTag)
            fluidTank.readFromNBT(compoundTag);
        if (compound.contains("filterColor")) {
            this.filterColor = compound.getInt("filterColor");
        }
        if (compound.contains("outputTank"))
            outputTank.readFromNBT(compound.getCompound("outputTank"));
        if (compound.contains("outputTank2"))
            outputTank2.readFromNBT(compound.getCompound("outputTank2"));
        this.allowedFluid = compound.getString("allowedFluid");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
        compound.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
        compound.putString("allowedFluid", this.allowedFluid);
        compound.putInt("filterColor", this.filterColor);
        compound.put("outputTank", outputTank.writeToNBT(new CompoundTag()));
        compound.put("outputTank2", outputTank2.writeToNBT(new CompoundTag()));
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("fractionating_tower");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fractionating Tower");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }
    private final FluidTank fluidTank = new FluidTank(20000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            if (allowedFluid == null || allowedFluid.isEmpty()) return true;

            String incomingFullName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString();

            String incomingPath = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

            return incomingFullName.equals(allowedFluid) || incomingPath.equals(allowedFluid);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!isFluidValid(resource)) return 0;
            return super.fill(resource, action);
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
        }
    };

    private final FluidTank outputTank = new FluidTank(20000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
        }
    };

    private final FluidTank outputTank2 = new FluidTank(20000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
        }
    };

    private final IFluidHandler combinedFluidHandler = new IFluidHandler() {
        @Override
        public int getTanks() {
            return 3;
        }

        @NotNull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return switch (tank) {
                case 1 -> outputTank.getFluid();
                case 2 -> outputTank2.getFluid();
                default -> fluidTank.getFluid();
            };
        }

        @Override
        public int getTankCapacity(int tank) {
            return switch (tank) {
                case 1 -> outputTank.getCapacity();
                case 2 -> outputTank2.getCapacity();
                default -> fluidTank.getCapacity();
            };
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return switch (tank) {
                case 1 -> outputTank.isFluidValid(stack);
                case 2 -> outputTank2.isFluidValid(stack);
                default -> fluidTank.isFluidValid(stack);
            };
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return fluidTank.fill(resource, action);
        }

        @NotNull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            FluidStack drain = outputTank.drain(resource, action);
            if (drain.isEmpty()) drain = outputTank2.drain(resource, action);
            if (drain.isEmpty()) drain = fluidTank.drain(resource, action);
            return drain;
        }

        @NotNull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            FluidStack drain = outputTank.drain(maxDrain, action);
            if (drain.isEmpty()) drain = outputTank2.drain(maxDrain, action);
            if (drain.isEmpty()) drain = fluidTank.drain(maxDrain, action);
            return drain;
        }
    };

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
            return handlers[facing.ordinal()].cast();
        if (!this.remove && capability == ForgeCapabilities.FLUID_HANDLER)
            return LazyOptional.of(() -> combinedFluidHandler).cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FractionatingTowerBlockEntity entity) {
        if (level.isClientSide) return;

        entity.processRecipes();

        if (entity.fluidTank.getFluidAmount() > 0 || entity.outputTank.getFluidAmount() > 0 || entity.outputTank2.getFluidAmount() > 0) {
            entity.distributeFluid();
        }
    }

    public FluidTank getOutputTank() { return this.outputTank; }
    public FluidTank getOutputTank2() { return this.outputTank2; }
    public FluidTank getInputTank() { return this.fluidTank; }

    private void processRecipes() {
        int inputAmount = 40;
        int outputAmount = inputAmount / 2;

        FluidStack inputStack = fluidTank.getFluid();
        if (inputStack.isEmpty() || inputStack.getAmount() < inputAmount) return;

        FluidStack result1 = FluidStack.EMPTY;
        FluidStack result2 = FluidStack.EMPTY;

        if (inputStack.getFluid() == ModFluids.HEAVY_OIL.get()) {
            result1 = new FluidStack(ModFluids.BITUMEN.get(), outputAmount);
            result2 = new FluidStack(ModFluids.INDUSTRIAL_OIL.get(), outputAmount);
        }
        else if (inputStack.getFluid() == ModFluids.LIGHT_OIL.get()) {
            result1 = new FluidStack(ModFluids.DIESEL.get(), outputAmount);
            result2 = new FluidStack(ModFluids.KEROSENE.get(), outputAmount);
        }
        else if (inputStack.getFluid() == ModFluids.VACUUM_LIGHT_OIL.get()) {
            result1 = new FluidStack(ModFluids.KEROSENE.get(), outputAmount);
            result2 = new FluidStack(ModFluids.REFORMATE_GAS.get(), outputAmount);
        }
        else if (inputStack.getFluid() == ModFluids.NAPHTHA.get()) {
            result1 = new FluidStack(ModFluids.HEATING_OIL.get(), outputAmount);
            result2 = new FluidStack(ModFluids.DIESEL.get(), outputAmount);
        }
        else if (inputStack.getFluid() == ModFluids.VACUUM_HEAVY_OIL.get()) {
            result1 = new FluidStack(ModFluids.INDUSTRIAL_OIL.get(), outputAmount);
            result2 = new FluidStack(ModFluids.HEAVY_HEATING_OIL.get(), outputAmount);
        }

        if (!result1.isEmpty() && !result2.isEmpty()) {
            int accepted1 = outputTank.fill(result1, IFluidHandler.FluidAction.SIMULATE);
            int accepted2 = outputTank2.fill(result2, IFluidHandler.FluidAction.SIMULATE);

            if (accepted1 == outputAmount && accepted2 == outputAmount) {
                fluidTank.drain(inputAmount, IFluidHandler.FluidAction.EXECUTE);
                outputTank.fill(result1, IFluidHandler.FluidAction.EXECUTE);
                outputTank2.fill(result2, IFluidHandler.FluidAction.EXECUTE);
                this.setChanged();
            }
        }
    }

    private void distributeFluid() {
        int transferSpeed = 1000;
        FluidTank[] tanksToDistribute = {outputTank, outputTank2, fluidTank};

        for (FluidTank tank : tanksToDistribute) {
            if (tank.getFluidAmount() <= 0) continue;

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = worldPosition.relative(direction);
                BlockEntity neighborBE = level.getBlockEntity(neighborPos);

                if (neighborBE != null) {
                    neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite()).ifPresent(handler -> {
                        if (neighborBE instanceof FluidBlockEntity neighborDuct) {
                            if (!neighborDuct.getAllowedFluid().isEmpty() &&
                                    !neighborDuct.getAllowedFluid().equals(net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(tank.getFluid().getFluid()).getPath())) {
                                return;
                            }
                        }

                        FluidStack toPush = new FluidStack(tank.getFluid(), Math.min(tank.getFluidAmount(), transferSpeed));
                        int filled = handler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                        if (filled > 0) {
                            tank.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                        }
                    });
                }
            }
        }
    }
}
