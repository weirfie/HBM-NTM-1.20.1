package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.AssemblyMachineBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class AssemblyMachineMenu extends AbstractContainerMenu {
    public final AssemblyMachineBlockEntity blockEntity;
    public final Level level;
    public final ContainerData data;

    public AssemblyMachineMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public AssemblyMachineMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(NTMModMenus.ASSEMBLY_MACHINE_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (AssemblyMachineBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 9, 21));
            this.addSlot(new SlotItemHandler(handler, 1, 9, 39));
            this.addSlot(new SlotItemHandler(handler, 2, 9, 57));
            this.addSlot(new SlotItemHandler(handler, 3, 9, 75));
            this.addSlot(new SlotItemHandler(handler, 4, 9, 93));
            this.addSlot(new SlotItemHandler(handler, 5, 9, 111));
            this.addSlot(new SlotItemHandler(handler, 6, 27, 21));
            this.addSlot(new SlotItemHandler(handler, 7, 27, 39));
            this.addSlot(new SlotItemHandler(handler, 8, 27, 57));
            this.addSlot(new SlotItemHandler(handler, 9, 27, 75));
            this.addSlot(new SlotItemHandler(handler, 10, 27, 93));
            this.addSlot(new SlotItemHandler(handler, 11, 27, 111));
            this.addSlot(new SlotItemHandler(handler, 12, 81, 21));
            this.addSlot(new SlotItemHandler(handler, 13, 81, 68));
            this.addSlot(new SlotItemHandler(handler, 14, 137, 98) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.ASSEMBLY_MACHINE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int si = 0; si < 3; ++si) {
            for (int sj = 0; sj < 9; ++sj) {
                this.addSlot(new Slot(playerInventory, sj + (si + 1) * 9, 1 + 8 + sj * 18, 57 + 84 + si * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int si = 0; si < 9; ++si) {
            this.addSlot(new Slot(playerInventory, si, 1 + 8 + si * 18, 57 + 142));
        }
    }

    public AssemblyMachineBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}
