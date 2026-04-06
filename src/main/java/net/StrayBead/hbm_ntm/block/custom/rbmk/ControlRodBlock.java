package net.StrayBead.hbm_ntm.block.custom.rbmk;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.ControlRodTopBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.ControlRodTopBlockEntity;
import net.StrayBead.hbm_ntm.entity.custom.NeutronProjectileEntity;
import net.StrayBead.hbm_ntm.init.ModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ControlRodBlock extends Block {

    public ControlRodBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        ControlRodUpdateTickProcedure.execute(world, x, y, z);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void entityInside(BlockState p_60495_, Level world, BlockPos pos, Entity entity) {
        super.entityInside(p_60495_, world, pos, entity);
        if (entity instanceof NeutronProjectileEntity neutronProjectileEntity) {
            double efficiency = 1.0;
            int maxHeight = 74;

            for (int i = 0; i < maxHeight; i++) {
                BlockPos checkPos = BlockPos.containing(pos.getX(), pos.getY() + i, pos.getZ());
                BlockEntity be = world.getBlockEntity(checkPos);

                if (be instanceof ControlRodTopBlockEntity top) {
                    float currentTick = top.animationTick;
                    float maxTick = 40.0f;
                    efficiency = Math.max(0, (maxTick - currentTick) / maxTick);
                    break;
                }

                if (!(world.getBlockState(checkPos).getBlock() instanceof ControlRodBlock)) {
                    if (!(world.getBlockState(checkPos).getBlock() instanceof ControlRodTopBlock)) break;
                }
            }

            int baseAmount = 100;
            int finalAmount = (int) (baseAmount * efficiency);

            if (finalAmount <= 0) return;

            int maxThreshold = 15000;
            int absorptionThreshold = (int) (maxThreshold * efficiency);

            if (Mth.nextInt(RandomSource.create(), 1, maxThreshold) <= absorptionThreshold) {
                neutronProjectileEntity.discard();
            }
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            int debrisCount = 5 + level.random.nextInt(5);

            for (int i = 0; i < debrisCount; i++) {
                FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, pos, state);

                double mx = (level.random.nextDouble() - 0.5D) * 1.5D;
                double my = 0.7D + level.random.nextDouble() * 1.2D;
                double mz = (level.random.nextDouble() - 0.5D) * 1.5D;

                fallingBlock.setPos(pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5);

                fallingBlock.setDeltaMovement(mx, my, mz);
                fallingBlock.time = 1;

                fallingBlock.hasImpulse = true;
                fallingBlock.hurtMarked = true;

                level.addFreshEntity(fallingBlock);
            }
        }
        super.onBlockExploded(state, level, pos, explosion);
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
                world.setBlock(BlockPos.containing(x, y + up, z), ModBlocks.CONTROL_ROD.get().defaultBlockState(), 3);
                up = up + 1;
            }
        }
        world.setBlock(BlockPos.containing(x, y + up, z), ModBlocks.CONTROL_ROD_TOP.get().defaultBlockState(), 3);
    }
}
