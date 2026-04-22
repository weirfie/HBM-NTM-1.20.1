package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.BedrockOreProcessorBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.PyrolysisOvenBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;

import java.util.HashMap;

public class PyrolysisOvenMenu extends AbstractContainerMenu {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final PyrolysisOvenBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluidStack;

    public PyrolysisOvenMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public PyrolysisOvenMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(NTMModMenus.PYROLYSIS_OVEN_ENTITY.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (PyrolysisOvenBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 8, 71));
            this.addSlot(new SlotItemHandler(handler, 1, 35, 44));
            this.addSlot(new SlotItemHandler(handler, 2, 89, 44));
            this.addSlot(new SlotItemHandler(handler, 3, 71, 71));
            this.addSlot(new SlotItemHandler(handler, 4, 89, 71));
            this.addSlot(new SlotItemHandler(handler, 5, 152, 71));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 30;

        return (maxProgress != 0 && progress != 0) ? (progress * progressArrowSize / maxProgress) : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 6;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.PYROLYSIS_OVEN.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 37 + 84 + si * 18));
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 8 + si * 18, 37 + 142));
    }

    public PyrolysisOvenBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

    public FluidStack getTank1() {
        return blockEntity.getOutputTank1().getFluid();
    }

}
