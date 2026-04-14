package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CyclotronBlock extends Block {
    public CyclotronBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        if (!level.isClientSide) {
            for (int x = -2; x <= 2; x++) {
                for (int y = 0; y < 3; y++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos target = pos.offset(x, y, z);

                        if (target.equals(pos)) continue;

                        level.setBlock(target, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);

                        BlockEntity be = level.getBlockEntity(target);
                        if (be instanceof GenericBoundingBoxBE boundingBE) {
                            boundingBE.setCorePos(pos);
                        }
                    }
                }
            }
        }
    }
}
