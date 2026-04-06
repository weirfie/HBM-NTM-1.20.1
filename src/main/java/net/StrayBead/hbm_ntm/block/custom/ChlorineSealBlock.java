package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class ChlorineSealBlock extends Block {
    public static final BooleanProperty ISPUMPINGGAS = BooleanProperty.create("ispumpinggas");

    public ChlorineSealBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(ISPUMPINGGAS, false));
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ISPUMPINGGAS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(ISPUMPINGGAS, false);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level world, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, world, pos, p_60569_, p_60570_);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        if (blockstate.getValue(ISPUMPINGGAS)) {

            if ((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x + 1, y, z), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
            if ((world.getBlockState(BlockPos.containing(x + 2, y, z))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x + 2, y, z), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
            if ((world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x - 1, y, z), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
            if ((world.getBlockState(BlockPos.containing(x - 2, y, z))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x - 2, y, z), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
            if ((world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x, y, z - 1), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
            if ((world.getBlockState(BlockPos.containing(x, y, z - 2))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x, y, z - 2), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
            if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x, y + 1, z), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
            if ((world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Blocks.AIR) {
                world.setBlock(BlockPos.containing(x, y, z + 1), ModBlocks.CHLORINE_GAS.get().defaultBlockState(), 3);
            }
        }

        world.scheduleTick(pos, this, 10);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(state, world, pos, neighborBlock, fromPos, moving);

        boolean isPowered = world.hasNeighborSignal(pos);
        boolean isCurrentlyPumping = state.getValue(ISPUMPINGGAS);

        if (isPowered != isCurrentlyPumping) {
            world.setBlock(pos, state.setValue(ISPUMPINGGAS, isPowered), 3);

            if (!isPowered) {
                for (Direction direction : Direction.values()) {
                    BlockPos sidePos = pos.relative(direction);
                    BlockState sideState = world.getBlockState(sidePos);

                    if (sideState.getBlock() instanceof ChlorineGasBlock) {
                        world.setBlock(sidePos, sideState.setValue(ChlorineGasBlock.CAN_SPREAD, false), 3);
                    }
                }
            }
        }
    }
}
