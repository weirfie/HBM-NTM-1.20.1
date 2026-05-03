package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;
import java.util.Objects;

public class GenericBoundingBoxBlock extends BaseEntityBlock {
    public interface BoundingBoxOwner {
        BlockPos getCorePos();
    }

    public GenericBoundingBoxBlock(Properties props) {
        super(props.noOcclusion().noLootTable());
    }

    @Override
    public int getLightBlock(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
        return 0;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public void onPlace(BlockState p_60566_, Level world, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, world, pos, p_60569_, p_60570_);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    public boolean canConnect(BlockGetter level, BlockPos pos, Direction direction) {
        BlockPos corePos = getCorePos(level, pos);
        if (corePos != null) {
            BlockEntity coreBE = level.getBlockEntity(corePos);
            if (coreBE != null) {
                return coreBE.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).isPresent();
            }
        }
        return false;
    }

    @Nullable
    private BlockPos getCorePos(BlockGetter level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof GenericBoundingBoxBE be) {
            return be.getCorePos();
        }
        return null;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        BlockPos core = getCorePos(level, pos);
        if (core != null) {
            BlockState coreState = level.getBlockState(core);
            if (!coreState.isAir()) {
                return new ItemStack(coreState.getBlock());
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos core = getCorePos(level, pos);
        if (core != null) {
            BlockState coreState = level.getBlockState(core);
            return coreState.use(level, player, hand, new BlockHitResult(hit.getLocation(), hit.getDirection(), core, hit.isInside()));
        }
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof GenericBoundingBoxBE boundingBE) {
            BlockPos corePos = boundingBE.getCorePos();
            if (corePos != null && !corePos.equals(BlockPos.ZERO)) {
                BlockState coreState = level.getBlockState(corePos);
                return coreState.use(level, player, hand, hit.withPosition(corePos));
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource randomSource) {
        super.tick(state, world, pos, randomSource);
        if (!world.isClientSide()) {
            if (getCorePos(world, pos) == null) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            } else {
                if (world.getBlockState(getCorePos(world, pos)).getBlock() == Blocks.AIR) {
                    world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        BlockPos corePos = getCorePos(level, pos);
        if (corePos != null) {
            BlockState coreState = level.getBlockState(corePos);
            if (!coreState.isAir()) {
                return coreState.getDestroyProgress(player, level, corePos);
            }
        }
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockPos core = getCorePos(level, pos);
            if (core != null) {
                BlockState coreState = level.getBlockState(core);
                if (!coreState.isAir()) {
                    if (!player.isCreative()) {
                        Block.dropResources(coreState, level, pos, level.getBlockEntity(core));
                    }
                    level.destroyBlock(core, false);
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override public RenderShape getRenderShape(BlockState s) { return RenderShape.INVISIBLE; }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GenericBoundingBoxBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type,
                ModBlockEntites.GENERIC_BOUNDING_BOX.get(), GenericBoundingBoxBE::tick);
    }
}
