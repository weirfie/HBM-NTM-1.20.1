package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.List;

public class ConveyorInserter extends Block {
    public ConveyorInserter(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level level, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, level, pos, p_60569_, p_60570_);
        level.scheduleTick(pos, this, 6);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction dir : directions) {
            BlockPos checkPos = pos.relative(dir);

            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(checkPos));

            if (!items.isEmpty()) {
                ItemEntity itemEntity = items.get(0);
                for (Direction searchDir : Direction.values()) {
                    BlockPos targetPos = pos.relative(searchDir);
                    BlockEntity targetBE = level.getBlockEntity(targetPos);

                    if (targetBE instanceof CentrifugeBlockEntity centrifuge) {
                        centrifuge.getCapability(ForgeCapabilities.ITEM_HANDLER, searchDir.getOpposite()).ifPresent(handler -> {
                            ItemStack toInsert = itemEntity.getItem();

                            ItemStack remaining = handler.insertItem(3, toInsert, false);

                            if (remaining.getCount() < toInsert.getCount()) {
                                if (remaining.isEmpty()) {
                                    itemEntity.discard();
                                } else {
                                    itemEntity.setItem(remaining);
                                }
                                centrifuge.setChanged();
                            }
                        });
                    } else if (targetBE instanceof OreAcidizerBlockEntity ore_acidizer) {
                        ore_acidizer.getCapability(ForgeCapabilities.ITEM_HANDLER, searchDir.getOpposite()).ifPresent(handler -> {
                            ItemStack toInsert = itemEntity.getItem();

                            ItemStack remaining = handler.insertItem(0, toInsert, false);

                            if (remaining.getCount() < toInsert.getCount()) {
                                if (remaining.isEmpty()) {
                                    itemEntity.discard();
                                } else {
                                    itemEntity.setItem(remaining);
                                }
                                ore_acidizer.setChanged();
                            }
                        });
                    } else if (targetBE instanceof SilexBlockEntity silexBlock) {
                        silexBlock.getCapability(ForgeCapabilities.ITEM_HANDLER, searchDir.getOpposite()).ifPresent(handler -> {
                            ItemStack toInsert = itemEntity.getItem();

                            ItemStack remaining = handler.insertItem(1, toInsert, false);

                            if (remaining.getCount() < toInsert.getCount()) {
                                if (remaining.isEmpty()) {
                                    itemEntity.discard();
                                } else {
                                    itemEntity.setItem(remaining);
                                }
                                silexBlock.setChanged();
                            }
                        });
                    } else if (targetBE instanceof ShredderBlockEntity shredderBlock) {
                        shredderBlock.getCapability(ForgeCapabilities.ITEM_HANDLER, searchDir.getOpposite()).ifPresent(handler -> {
                            ItemStack toInsert = itemEntity.getItem();

                            ItemStack remaining = handler.insertItem(0, toInsert, false);

                            if (remaining.getCount() < toInsert.getCount()) {
                                if (remaining.isEmpty()) {
                                    itemEntity.discard();
                                } else {
                                    itemEntity.setItem(remaining);
                                }
                                shredderBlock.setChanged();
                            }
                        });
                    } else if (targetBE instanceof BoilerBlockEntity boiler) {
                        boiler.getCapability(ForgeCapabilities.ITEM_HANDLER, searchDir.getOpposite()).ifPresent(handler -> {
                            ItemStack toInsert = itemEntity.getItem();
                            ItemStack remaining = ItemStack.EMPTY;

                            remaining = handler.insertItem(0, toInsert, false);

                            if (remaining.getCount() == toInsert.getCount()) {
                                remaining = handler.insertItem(1, toInsert, false);
                            }

                            if (remaining.getCount() < toInsert.getCount()) {
                                if (remaining.isEmpty()) {
                                    itemEntity.discard();
                                } else {
                                    itemEntity.setItem(remaining);
                                }
                                boiler.setChanged();
                            }
                        });
                    }
                }
            }
        }
        level.scheduleTick(pos, this, 6);
    }
}
