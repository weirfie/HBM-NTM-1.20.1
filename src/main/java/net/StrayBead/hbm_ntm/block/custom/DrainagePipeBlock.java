package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class DrainagePipeBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public DrainagePipeBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);

        BlockEntity ent = world.getBlockEntity(pos);
        if (ent == null) return;

        ent.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(capability -> {
            FluidStack fluidStack = capability.getFluidInTank(0);

            if (fluidStack.getAmount() > 0) {
                capability.drain(30, IFluidHandler.FluidAction.EXECUTE);

                int color = IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack);

                float r = ((color >> 16) & 0xFF) / 255f;
                float g = ((color >> 8) & 0xFF) / 255f;
                float b = (color & 0xFF) / 255f;

                if (blockstate.getValue(FACING) == Direction.NORTH) {
                    ParticleManager.addParticle(((pos.getX() + 0.5f) + 2.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), pos.getY() + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), (pos.getZ() + 0.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f),
                            0.4f, r, g, b, 0.9f, 0, -0.08f, 0, 40, 0.0f);
                } else if (blockstate.getValue(FACING) == Direction.SOUTH) {
                    ParticleManager.addParticle(((pos.getX() + 0.5f) - 2.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), pos.getY() + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), (pos.getZ() + 0.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f),
                            0.4f, r, g, b, 0.9f, 0, -0.08f, 0, 40, 0.0f);
                } else if (blockstate.getValue(FACING) == Direction.EAST) {
                    ParticleManager.addParticle((pos.getX() + 0.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), pos.getY() + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), ((pos.getZ() + 0.5f) + 2.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f),
                            0.4f, r, g, b, 0.9f, 0, -0.08f, 0, 40, 0.0f);
                } else if (blockstate.getValue(FACING) == Direction.WEST) {
                    ParticleManager.addParticle((pos.getX() + 0.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), pos.getY() + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f), ((pos.getZ() + 0.5f) - 2.5f) + Mth.nextFloat(RandomSource.create(), -0.1f, 0.1f),
                            0.4f, r, g, b, 0.9f, 0, -0.08f, 0, 40, 0.0f);
                }
            }
        });

        world.scheduleTick(pos, this, 1);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DrainagePipeBlockEntity(pos, state);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
}
