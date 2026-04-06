package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.ChemicalPlantBlockEntity;
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

public class ChemicalPlantMenu extends AbstractContainerMenu {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final ChemicalPlantBlockEntity blockEntity;
    public final Level level;
    public final ContainerData data;
    private FluidStack fluidStack;

    public ChemicalPlantMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public ChemicalPlantMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(NTMModMenus.CHEMICAL_PLANT_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (ChemicalPlantBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 11, 56));
            this.addSlot(new SlotItemHandler(handler, 1, 29, 56));
            this.addSlot(new SlotItemHandler(handler, 2, 47, 56));
            this.addSlot(new SlotItemHandler(handler, 3, 11, 74));
            this.addSlot(new SlotItemHandler(handler, 4, 29, 74));
            this.addSlot(new SlotItemHandler(handler, 5, 47, 74));
            this.addSlot(new SlotItemHandler(handler, 6, 11, 98));
            this.addSlot(new SlotItemHandler(handler, 7, 29, 98));
            this.addSlot(new SlotItemHandler(handler, 8, 47, 98));
            this.addSlot(new SlotItemHandler(handler, 9, 89, 56));
            this.addSlot(new SlotItemHandler(handler, 10, 107, 56));
            this.addSlot(new SlotItemHandler(handler, 11, 125, 56));
            this.addSlot(new SlotItemHandler(handler, 12, 89, 74));
            this.addSlot(new SlotItemHandler(handler, 13, 107, 74));
            this.addSlot(new SlotItemHandler(handler, 14, 125, 74));
            this.addSlot(new SlotItemHandler(handler, 15, 89, 98));
            this.addSlot(new SlotItemHandler(handler, 16, 107, 98));
            this.addSlot(new SlotItemHandler(handler, 17, 125, 98));
            this.addSlot(new SlotItemHandler(handler, 18, 153, 79));
            this.addSlot(new SlotItemHandler(handler, 19, 43, 118));
            this.addSlot(new SlotItemHandler(handler, 20, 153, 120));
            this.addSlot(new SlotItemHandler(handler, 21, 153, 101));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowWidth = 16;

        if (maxProgress == 0 || progress == 0) return 0;

        return Math.min((progress * arrowWidth) / maxProgress, arrowWidth);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 22;

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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.CHEMICAL_PLANT.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 2 + 8 + sj * 18, 55 + 84 + si * 18));
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 2 + 8 + si * 18, 55 + 142));
    }

    public ChemicalPlantBlockEntity getBlockEntity() {
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

    public FluidStack getTank3() {
        return blockEntity.getOutputTank3().getFluid();
    }

    public FluidStack getInputTank2() {
        return blockEntity.getInputTank2().getFluid();
    }

    public FluidStack getInputTank3() {
        return blockEntity.getInputTank3().getFluid();
    }
}
