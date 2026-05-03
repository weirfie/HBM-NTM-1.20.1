package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.BoilerBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ElectricArcFurnaceBlockEntity;
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
import java.util.Map;

public class ElectricArcFurnaceMenu extends AbstractContainerMenu {
    public final ElectricArcFurnaceBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluidStack;
    private final Map<Integer, Slot> customSlots = new HashMap<>();

    public ElectricArcFurnaceMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public ElectricArcFurnaceMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(NTMModMenus.ELECTRIC_ARC_FURNACE_MENU.get(), id);
        checkContainerSize(inv, 30);
        blockEntity = (ElectricArcFurnaceBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 8, 100));
            this.addSlot(new SlotItemHandler(handler, 1, 44, 46));
            this.addSlot(new SlotItemHandler(handler, 2, 62, 46));
            this.addSlot(new SlotItemHandler(handler, 3, 80, 46));
            this.addSlot(new SlotItemHandler(handler, 4, 98, 46));
            this.addSlot(new SlotItemHandler(handler, 5, 116, 46));
            this.addSlot(new SlotItemHandler(handler, 6, 44, 64));
            this.addSlot(new SlotItemHandler(handler, 7, 62, 64));
            this.addSlot(new SlotItemHandler(handler, 8, 80, 64));
            this.addSlot(new SlotItemHandler(handler, 9, 98, 64));
            this.addSlot(new SlotItemHandler(handler, 10, 116, 64));
            this.addSlot(new SlotItemHandler(handler, 11, 44, 82));
            this.addSlot(new SlotItemHandler(handler, 12, 62, 82));
            this.addSlot(new SlotItemHandler(handler, 13, 80, 82));
            this.addSlot(new SlotItemHandler(handler, 14, 98, 82));
            this.addSlot(new SlotItemHandler(handler, 15, 116, 82));
            this.addSlot(new SlotItemHandler(handler, 16, 44, 100));
            this.addSlot(new SlotItemHandler(handler, 17, 62, 100));
            this.addSlot(new SlotItemHandler(handler, 18, 80, 100));
            this.addSlot(new SlotItemHandler(handler, 19, 98, 100));
            this.addSlot(new SlotItemHandler(handler, 20, 116, 100));
            this.addSlot(new SlotItemHandler(handler, 21, 44, 121));
            this.addSlot(new SlotItemHandler(handler, 22, 62, 121));
            this.addSlot(new SlotItemHandler(handler, 23, 80, 121));
            this.addSlot(new SlotItemHandler(handler, 24, 98, 121));
            this.addSlot(new SlotItemHandler(handler, 25, 116, 121));
            this.addSlot(new SlotItemHandler(handler, 26, 152, 100));
            this.addSlot(new SlotItemHandler(handler, 27, 62, 14));
            this.addSlot(new SlotItemHandler(handler, 28, 80, 14));
            this.addSlot(new SlotItemHandler(handler, 29, 98, 14));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the height in pixels of the arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 30;

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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.ELECTRIC_ARC_FURNACE.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 82 + 84 + si * 18));
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 8 + si * 18, 82 + 142));
    }

    public ElectricArcFurnaceBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }
}
