package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ConveyorEjector extends Block {
    public ConveyorEjector(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        level.scheduleTick(pos, this, 6);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        for (Direction searchDir : Direction.values()) {
            BlockPos targetPos = pos.relative(searchDir);
            BlockEntity targetBE = level.getBlockEntity(targetPos);

            if (targetBE != null) {
                targetBE.getCapability(ForgeCapabilities.ITEM_HANDLER, searchDir.getOpposite()).ifPresent(handler -> {
                    int[] outputSlots = getOutputSlotsFor(targetBE);

                    for (int slot : outputSlots) {
                        ItemStack stackInSlot = handler.getStackInSlot(slot);
                        if (stackInSlot.isEmpty()) continue;

                        for (Direction beltDir : Direction.values()) {
                            BlockPos beltPos = pos.relative(beltDir);
                            BlockState beltState = level.getBlockState(beltPos);

                            if (isAnyBelt(beltState)) {
                                ItemStack extracted = handler.extractItem(slot, stackInSlot.getCount(), false);

                                if (!extracted.isEmpty()) {
                                    double yOffset = (beltDir == Direction.DOWN) ? 0.1 : 0.7;

                                    ItemEntity entity = new ItemEntity(level,
                                            beltPos.getX() + 0.5,
                                            beltPos.getY() + yOffset,
                                            beltPos.getZ() + 0.5,
                                            extracted);

                                    entity.setDeltaMovement(0, 0, 0);
                                    level.addFreshEntity(entity);

                                    targetBE.setChanged();
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        }
        level.scheduleTick(pos, this, 6);
    }

    private boolean isAnyBelt(BlockState state) {
        return state.is(ModBlocks.CONVEYOR_BELT.get()) ||
                state.is(ModBlocks.CONVEYOR_LIFT.get()) ||
                state.is(ModBlocks.CONVEYOR_CHAIN_LIFT.get());
    }

    private int[] getOutputSlotsFor(BlockEntity be) {
        if (be instanceof CentrifugeBlockEntity) return new int[]{4, 5, 6, 7};
        if (be instanceof OreAcidizerBlockEntity) return new int[]{1};
        if (be instanceof SilexBlockEntity) return new int[]{6};
        if (be instanceof ShredderBlockEntity) return new int[]{9, 10, 11, 12, 13, 14, 15};
        if (be instanceof BoilerBlockEntity) return new int[]{2};
        return new int[0];
    }
}
