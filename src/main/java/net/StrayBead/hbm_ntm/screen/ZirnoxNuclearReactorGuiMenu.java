package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.custom.entity.ZirnoxNuclearReactorBlockEntity;
import net.minecraft.world.inventory.*;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

public class ZirnoxNuclearReactorGuiMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public final ZirnoxNuclearReactorBlockEntity blockEntity;
    private final ContainerData data;
    private FluidStack fluidStack;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;

    public ZirnoxNuclearReactorGuiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public ZirnoxNuclearReactorGuiMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(NTMModMenus.ZIRNOX_NUCLEAR_REACTOR_GUI.get(), id);
        this.entity = inv.player;
        checkContainerSize(inv, 3);
        blockEntity = (ZirnoxNuclearReactorBlockEntity) entity;
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();
        this.world = inv.player.level();
        this.internal = new ItemStackHandler(27);
        BlockPos pos = null;
        if (entity != null) {
            entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                this.internal = capability;
                this.bound = true;
            });
            this.x = entity.getBlockPos().getX();
            this.y = entity.getBlockPos().getY();
            this.z = entity.getBlockPos().getZ();
        }

        this.boundBlockEntity = entity;

        if (this.internal == null) {
            this.internal = new ItemStackHandler(6);
        }

        this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 32, 28) {
            private final int slot = 0;
        }));
        this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 68, 28) {
            private final int slot = 1;
        }));
        this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 104, 28) {
            private final int slot = 2;
        }));
        this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, 86, 45) {
            private final int slot = 3;
        }));
        this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 50, 45) {
            private final int slot = 4;
        }));
        this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 15, 46) {
            private final int slot = 5;
        }));
        this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 32, 63) {
            private final int slot = 6;
        }));
        this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 68, 63) {
            private final int slot = 7;
        }));
        this.customSlots.put(8, this.addSlot(new SlotItemHandler(internal, 8, 104, 63) {
            private final int slot = 8;
        }));
        this.customSlots.put(9, this.addSlot(new SlotItemHandler(internal, 9, 122, 45) {
            private final int slot = 9;
        }));
        this.customSlots.put(10, this.addSlot(new SlotItemHandler(internal, 10, 15, 81) {
            private final int slot = 10;
        }));
        this.customSlots.put(11, this.addSlot(new SlotItemHandler(internal, 11, 50, 81) {
            private final int slot = 11;
        }));
        this.customSlots.put(12, this.addSlot(new SlotItemHandler(internal, 12, 86, 81) {
            private final int slot = 12;
        }));
        this.customSlots.put(13, this.addSlot(new SlotItemHandler(internal, 13, 122, 81) {
            private final int slot = 13;
        }));
        this.customSlots.put(14, this.addSlot(new SlotItemHandler(internal, 14, 33, 99) {
            private final int slot = 14;
        }));
        this.customSlots.put(15, this.addSlot(new SlotItemHandler(internal, 15, 68, 99) {
            private final int slot = 15;
        }));
        this.customSlots.put(16, this.addSlot(new SlotItemHandler(internal, 16, 104, 99) {
            private final int slot = 16;
        }));
        this.customSlots.put(17, this.addSlot(new SlotItemHandler(internal, 17, 15, 117) {
            private final int slot = 17;
        }));
        this.customSlots.put(18, this.addSlot(new SlotItemHandler(internal, 18, 51, 117) {
            private final int slot = 18;
        }));
        this.customSlots.put(19, this.addSlot(new SlotItemHandler(internal, 19, 86, 117) {
            private final int slot = 19;
        }));
        this.customSlots.put(20, this.addSlot(new SlotItemHandler(internal, 20, 122, 117) {
            private final int slot = 20;
        }));
        this.customSlots.put(21, this.addSlot(new SlotItemHandler(internal, 21, 33, 135) {
            private final int slot = 21;
        }));
        this.customSlots.put(22, this.addSlot(new SlotItemHandler(internal, 22, 68, 135) {
            private final int slot = 22;
        }));
        this.customSlots.put(23, this.addSlot(new SlotItemHandler(internal, 23, 104, 135) {
            private final int slot = 23;
        }));
        this.customSlots.put(24, this.addSlot(new SlotItemHandler(internal, 24, 148, 117) {
            private final int slot = 24;
        }));
        this.customSlots.put(25, this.addSlot(new SlotItemHandler(internal, 25, 148, 135) {
            private final int slot = 25;

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        }));
        this.customSlots.put(26, this.addSlot(new SlotItemHandler(internal, 26, 167, 117) {
            private final int slot = 26;
        }));
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 6 + 8 + sj * 18, 77 + 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 6 + 8 + si * 18, 77 + 142));
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null)
                return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null)
                return this.boundEntity.isAlive();
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 27) {
                if (!this.moveItemStackTo(itemstack1, 27, this.slots.size(), true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (!this.moveItemStackTo(itemstack1, 0, 27, false)) {
                if (index < 27 + 27) {
                    if (!this.moveItemStackTo(itemstack1, 27 + 27, this.slots.size(), true))
                        return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(itemstack1, 27, 27 + 27, false))
                        return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0)
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
        boolean flag = false;
        int i = p_38905_;
        if (p_38907_) {
            i = p_38906_ - 1;
        }
        if (p_38904_.isStackable()) {
            while (!p_38904_.isEmpty()) {
                if (p_38907_) {
                    if (i < p_38905_) {
                        break;
                    }
                } else if (i >= p_38906_) {
                    break;
                }
                Slot slot = this.slots.get(i);
                ItemStack itemstack = slot.getItem();
                if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameTags(p_38904_, itemstack)) {
                    int j = itemstack.getCount() + p_38904_.getCount();
                    int maxSize = Math.min(slot.getMaxStackSize(), p_38904_.getMaxStackSize());
                    if (j <= maxSize) {
                        p_38904_.setCount(0);
                        itemstack.setCount(j);
                        slot.set(itemstack);
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        p_38904_.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.set(itemstack);
                        flag = true;
                    }
                }
                if (p_38907_) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        if (!p_38904_.isEmpty()) {
            if (p_38907_) {
                i = p_38906_ - 1;
            } else {
                i = p_38905_;
            }
            while (true) {
                if (p_38907_) {
                    if (i < p_38905_) {
                        break;
                    }
                } else if (i >= p_38906_) {
                    break;
                }
                Slot slot1 = this.slots.get(i);
                ItemStack itemstack1 = slot1.getItem();
                if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
                    if (p_38904_.getCount() > slot1.getMaxStackSize()) {
                        slot1.setByPlayer(p_38904_.split(slot1.getMaxStackSize()));
                    } else {
                        slot1.setByPlayer(p_38904_.split(p_38904_.getCount()));
                    }
                    slot1.setChanged();
                    flag = true;
                    break;
                }
                if (p_38907_) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        return flag;
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
                for (int j = 0; j < internal.getSlots(); ++j) {
                    playerIn.drop(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
                }
            } else {
                for (int i = 0; i < internal.getSlots(); ++i) {
                    playerIn.getInventory().placeItemBackInInventory(internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
                }
            }
        }
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }

    public ZirnoxNuclearReactorBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }
}
