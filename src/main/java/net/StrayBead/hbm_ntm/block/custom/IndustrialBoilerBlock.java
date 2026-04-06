package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class IndustrialBoilerBlock extends Block {

    public IndustrialBoilerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        if (!level.isClientSide) {
            for (int x = -1; x <= 1; x++) {
                for (int y = 0; y < 5; y++) {
                    for (int z = -1; z <= 1; z++) {
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
