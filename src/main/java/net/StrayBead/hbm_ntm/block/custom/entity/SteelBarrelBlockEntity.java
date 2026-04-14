package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.item.custom.FluidIdentifierItem;
import net.StrayBead.hbm_ntm.item.custom.FluidTankItem;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BigAssTankGuiMenu;
import net.StrayBead.hbm_ntm.screen.SteelBarrelGuiMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class SteelBarrelBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    public boolean IS_OUTPUTTING = false;
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);
    private String allowedFluid = "";
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    protected final ContainerData data;

    public SteelBarrelBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntites.STEEL_BARREL.get(), position, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 2 -> IS_OUTPUTTING ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 2 -> IS_OUTPUTTING = value != 0;
                }
            }

            @Override
            public int getCount() { return 1; }
        };
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public String getAllowedFluid() {
        return this.allowedFluid;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        IS_OUTPUTTING = compound.getBoolean("is_outputting");
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        this.allowedFluid = compound.getString("allowedFluid");
        ContainerHelper.loadAllItems(compound, this.stacks);
        if (compound.get("fluidTank") instanceof CompoundTag compoundTag)
            fluidTank.readFromNBT(compoundTag);
    }

    public boolean isOutputting() {
        return this.IS_OUTPUTTING;
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
        compound.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
        compound.putString("allowedFluid", this.allowedFluid);
        compound.putBoolean("is_outputting", IS_OUTPUTTING);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SteelBarrelBlockEntity entity) {
        if (level.isClientSide) return;

        ItemStack slot0 = entity.getItem(0);

        if (!slot0.isEmpty() && slot0.getItem() instanceof FluidIdentifierItem identifier) {
            ItemStack slot1 = entity.getItem(1);

            if (slot1.isEmpty() || (ItemStack.isSameItem(slot0, slot1) && slot1.getCount() < slot1.getMaxStackSize())) {

                String fluidName = identifier.getFluidName();

                entity.updateTankFilter(fluidName);

                if (slot1.isEmpty()) {
                    entity.setItem(1, slot0.split(1));
                } else {
                    slot0.shrink(1);
                    slot1.grow(1);
                }

                entity.setChanged();
            }
        }

        if (entity.IS_OUTPUTTING && !entity.fluidTank.getFluid().isEmpty()) {
            System.out.println("OUTPUTTING!");
            entity.pushFluidToNeighbors();
        }

        ItemStack slot2 = entity.getItem(2);
        if (!slot2.isEmpty() && slot2.is(ModItems.INFINITE_WATER_TANK.get())) {

            if (entity.allowedFluid.isEmpty() || entity.allowedFluid.equals("water")) {

                entity.fluidTank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
            }
        }

        if (!slot2.isEmpty() && slot2.getItem() instanceof FluidTankItem tankItem) {
            String itemFluidId = FluidTankItem.getFluidType(slot2);
            int amountInItem = FluidTankItem.getFluidAmount(slot2);

            if (amountInItem > 0 && (entity.allowedFluid.isEmpty() || entity.allowedFluid.equals(tankItem.getFluidName()))) {
                int spaceInBlock = entity.fluidTank.getCapacity() - entity.fluidTank.getFluidAmount();
                int transfer = Math.min(amountInItem, Math.min(spaceInBlock, 1000));

                if (transfer > 0) {
                    String fluidPath = tankItem.getFluidName().contains(":") ? tankItem.getFluidName() : "hbm_ntm:" + tankItem.getFluidName();
                    var fluid = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidPath));

                    if (fluid != null && fluid != Fluids.EMPTY) {
                        entity.fluidTank.fill(new FluidStack(fluid, transfer), IFluidHandler.FluidAction.EXECUTE);

                        int newAmount = amountInItem - transfer;
                        FluidTankItem.setFluidData(slot2, newAmount, newAmount > 0 ? itemFluidId : "");
                        entity.setChanged();
                    }
                }
            }
        }
        if (!slot2.isEmpty() && slot2.is(ModItems.INFINITE_FLUID_BARREL.get())) {
            FluidStack currentInTank = entity.fluidTank.getFluid();

            if (!currentInTank.isEmpty()) {
                entity.fluidTank.fill(new FluidStack(currentInTank.getFluid(), 1000), IFluidHandler.FluidAction.EXECUTE);
            }
            else if (!entity.allowedFluid.isEmpty()) {
                ResourceLocation res = ResourceLocation.tryParse(entity.allowedFluid);

                if (res != null && !entity.allowedFluid.contains(":")) {
                    res = new ResourceLocation("hbm_ntm", entity.allowedFluid);
                }

                if (res != null) {
                    var fluid = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getValue(res);
                    if (fluid != null && fluid != Fluids.EMPTY) {
                        entity.fluidTank.fill(new FluidStack(fluid, 1000), IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }
        }

        ItemStack slot3 = entity.getItem(3);
        if (!slot3.isEmpty() && slot3.getItem() instanceof FluidTankItem tankItem) {
            FluidStack stackInBlock = entity.fluidTank.getFluid();

            if (!stackInBlock.isEmpty()) {
                String blockFluidId = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stackInBlock.getFluid()).toString();

                String tankItemIdentity = tankItem.getFluidName();

                String fullItemIdentity = tankItemIdentity.contains(":") ? tankItemIdentity : "hbm_ntm:" + tankItemIdentity;

                if (blockFluidId.equals(fullItemIdentity)) {

                    String itemFluidTypeNBT = FluidTankItem.getFluidType(slot3);
                    int amountInItem = FluidTankItem.getFluidAmount(slot3);

                    if (itemFluidTypeNBT.isEmpty() || itemFluidTypeNBT.equals(blockFluidId)) {

                        int itemMaxCapacity = FluidTankItem.TANK_CAPACITY;

                        if (amountInItem < itemMaxCapacity) {
                            int spaceInItem = itemMaxCapacity - amountInItem;
                            int transferAmount = Math.min(stackInBlock.getAmount(), Math.min(spaceInItem, 1000));

                            if (transferAmount > 0) {
                                entity.fluidTank.drain(transferAmount, IFluidHandler.FluidAction.EXECUTE);
                                FluidTankItem.setFluidData(slot3, amountInItem + transferAmount, blockFluidId);
                                entity.setChanged();
                            }
                        }
                    }
                }
            }
        }
    }

    private void pushFluidToNeighbors() {
        FluidStack stackInTank = fluidTank.getFluid();
        if (stackInTank.isEmpty()) return;

        int pushAmount = 1000;

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = worldPosition.relative(dir);
            BlockEntity neighborBE = level.getBlockEntity(neighborPos);

            if (neighborBE != null) {
                if (neighborBE instanceof FluidBlockEntity ductBE) {
                    if (!ductBE.getAllowedFluid().equals(this.allowedFluid)) {
                        continue;
                    }
                }
                else if (level.getBlockState(neighborPos).is(ModBlocks.WATER_DUCT.get())) {
                    if (!stackInTank.getFluid().isSame(Fluids.WATER)) {
                        continue;
                    }
                }

                neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(handler -> {
                    FluidStack toPush = new FluidStack(stackInTank.getFluid(), Math.min(stackInTank.getAmount(), pushAmount));
                    int accepted = handler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                    if (accepted > 0) {
                        this.fluidTank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                    }
                });
            }
        }
    }

    private void fillNeighbor(BlockPos pos, FluidStack stack, int amount) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null) {
            be.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(handler -> {
                int accepted = handler.fill(new FluidStack(stack.getFluid(), amount), IFluidHandler.FluidAction.EXECUTE);
                if (accepted > 0) {
                    this.fluidTank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                }
            });
        }
    }

    private void updateTankFilter(String fluidName) {
        this.allowedFluid = fluidName;
        this.fluidTank.setFluid(FluidStack.EMPTY);
        this.setChanged();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
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
        return Component.literal("steel_barrel");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        assert this.level != null;
        return new SteelBarrelGuiMenu(id, inventory, this, ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Steel Barrel");
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

    private final FluidTank fluidTank = new FluidTank(16000) {
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

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
            return handlers[facing.ordinal()].cast();
        if (!this.remove && capability == ForgeCapabilities.FLUID_HANDLER)
            return LazyOptional.of(() -> fluidTank).cast();
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
}
