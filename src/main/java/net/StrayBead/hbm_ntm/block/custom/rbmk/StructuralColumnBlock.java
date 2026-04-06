package net.StrayBead.hbm_ntm.block.custom.rbmk;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.init.ModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StructuralColumnBlock extends Block {

    public StructuralColumnBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public int getLightBlock(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
        return 0;
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState blockstate, @Nullable LivingEntity entity, ItemStack itemStack) {
        super.setPlacedBy(world, pos, blockstate, entity, itemStack);
        double up = 0;
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        up = 1;
        for (int index0 = 0; index0 < world.getLevelData().getGameRules().getInt(ModGameRules.RBMK_REACTOR_HEIGHT) - 1; index0++) {
            if ((world.getBlockState(BlockPos.containing(x, y + up, z)).getBlock() == Blocks.AIR)) {
                world.setBlock(BlockPos.containing(x, y + up, z), ModBlocks.STRUCTURAL_COLUMN.get().defaultBlockState(), 3);
                up = up + 1;
            }
        }
    }
}
