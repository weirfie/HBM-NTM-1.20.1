package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class AntiPersonellMineBlock extends Block {
    public static final BooleanProperty PRESSED = BooleanProperty.create("pressed");
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 2, 15);

    public AntiPersonellMineBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(PRESSED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PRESSED);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide) return;

        if (entity instanceof LivingEntity) {
            if (!state.getValue(PRESSED)) {
                level.setBlock(pos, state.setValue(PRESSED, true), 3);
                level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.BLOCKS, 1.0f, 2.0f);
            }
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(PRESSED)) {
            List<Entity> list = level.getEntities(null, new AABB(pos).inflate(0.1D));
            boolean entityPresent = false;
            for (Entity e : list) {
                if (e instanceof LivingEntity) {
                    entityPresent = true;
                    break;
                }
            }

            if (!entityPresent) {
                level.destroyBlock(pos, false);
                Explosion.create(level, pos.getX(), pos.getY(), pos.getZ(), 1f, 300);
            } else {
                level.scheduleTick(pos, this, 1);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {

            if (level instanceof ServerLevel serverLevel && !level.isClientSide()) {
                if (!state.getValue(PRESSED)) {
                    Explosion.create(serverLevel, pos.getX(), pos.getY(), pos.getZ(), 1f, 300);
                }
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
