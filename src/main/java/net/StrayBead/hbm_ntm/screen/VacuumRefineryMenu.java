package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.VacuumRefineryBlockEntity;
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

public class VacuumRefineryMenu extends AbstractContainerMenu {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final VacuumRefineryBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluidStack;

    public VacuumRefineryMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(0));
    }

    public VacuumRefineryMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(NTMModMenus.VACUUM_REFINERY_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (VacuumRefineryBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 25, 83));
            this.addSlot(new SlotItemHandler(handler, 1, 80, 88));
            this.addSlot(new SlotItemHandler(handler, 2, 98, 88));
            this.addSlot(new SlotItemHandler(handler, 3, 116, 88));
            this.addSlot(new SlotItemHandler(handler, 4, 134, 88));
            this.addSlot(new SlotItemHandler(handler, 5, 80, 106));
            this.addSlot(new SlotItemHandler(handler, 6, 98, 106));
            this.addSlot(new SlotItemHandler(handler, 7, 116, 106));
            this.addSlot(new SlotItemHandler(handler, 8, 134, 106));
            this.addSlot(new SlotItemHandler(handler, 9, 25, 102));
        });

        addDataSlots(data);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 10;

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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.VACUUM_REFINERY.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, -1 + 8 + sj * 18, 43 + 84 + si * 18));

    }

    private void addPlayerHotbar(Inventory inv) {
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, -1 + 8 + si * 18, 43 + 142));
    }

    public VacuumRefineryBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return blockEntity.getFluidStack();
    }

    public FluidStack getVacuumHeavyOilStack() {
        return blockEntity.getVacuumHeavyOilTank().getFluid();
    }

    public FluidStack getReformateStack() {
        return blockEntity.getReformateTank().getFluid();
    }

    public FluidStack getVacuumLightOilStack() {
        return blockEntity.getVacuumLightOilTank().getFluid();
    }

    public FluidStack getSourGasStack() {
        return blockEntity.getSourGasTank().getFluid();
    }
}
