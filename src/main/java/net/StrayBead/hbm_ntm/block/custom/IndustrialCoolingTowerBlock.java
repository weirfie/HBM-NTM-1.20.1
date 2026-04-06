package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class IndustrialCoolingTowerBlock extends Block {
    public IndustrialCoolingTowerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);

        if (level.isClientSide) return;

        BlockPos corePos = pos.offset(5, 0, 0);

        if (stack != null && stack.getItem() == this.asItem()) {

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            level.setBlock(corePos, ModBlocks.INDUSTRIAL_COOLING_TOWER.get().defaultBlockState(), 3);

            for (int x = -5; x <= 5; x++) {
                for (int y = 0; y < 18; y++) {
                    for (int z = -5; z <= 5; z++) {
                        BlockPos target = corePos.offset(x, y, z);

                        if (target.equals(corePos)) continue;

                        level.setBlock(target, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);

                        BlockEntity be = level.getBlockEntity(target);
                        if (be instanceof GenericBoundingBoxBE boundingBE) {
                            boundingBE.setCorePos(corePos);
                        }
                    }
                }
            }
        }
    }
}
