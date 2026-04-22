package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.ArcWelderBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ElectrolysisMachineBlockEntity;
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

public class ElectrolysisMachineMenu extends AbstractContainerMenu {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final ElectrolysisMachineBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluidStack;

    public ElectrolysisMachineMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public ElectrolysisMachineMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(NTMModMenus.ELECTROLYSIS_MACHINE_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (ElectrolysisMachineBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 6, 3));
            this.addSlot(new SlotItemHandler(handler, 1, 24, 3));
            this.addSlot(new SlotItemHandler(handler, 2, 6, 39));
            this.addSlot(new SlotItemHandler(handler, 3, 24, 39));
            this.addSlot(new SlotItemHandler(handler, 4, 78, 3));
            this.addSlot(new SlotItemHandler(handler, 5, 78, 39));
            this.addSlot(new SlotItemHandler(handler, 6, 134, 3));
            this.addSlot(new SlotItemHandler(handler, 7, 134, 39));
            this.addSlot(new SlotItemHandler(handler, 8, 154, 3));
            this.addSlot(new SlotItemHandler(handler, 9, 154, 21));
            this.addSlot(new SlotItemHandler(handler, 10, 154, 39));
            this.addSlot(new SlotItemHandler(handler, 11, 189, 94));
            this.addSlot(new SlotItemHandler(handler, 12, 186, 125));
            this.addSlot(new SlotItemHandler(handler, 13, 186, 143));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 32;

        return (maxProgress != 0 && progress != 0) ? (progress * progressArrowSize / maxProgress) : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 14;

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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.ELECTROLYSIS_MACHINE.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 23 + 84 + si * 18));
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 8 + si * 18, 23 + 142));
    }

    public ElectrolysisMachineBlockEntity getBlockEntity() {
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

    public FluidStack getTank2() {
        return blockEntity.getOutputTank2().getFluid();
    }

}
