package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.BoilerBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.StrayBead.hbm_ntm.block.custom.entity.ShallowFoundryBasinBlockEntity;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

public class ShallowFoundryBasinBlock extends BaseEntityBlock {
    private boolean isValidMold(ItemStack stack) {
        return stack.is(ModItems.NUGGET_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.BILLET_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.INGOT_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.PLATE_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.WIRE_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.CAST_PLATE_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.DENSE_WIRE_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.BLADE_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.SHREDDER_BLADE_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.SHELL_FOUNDRY_MOLD.get()) ||
                stack.is(ModItems.PIPE_FOUNDRY_MOLD.get());
    }

    public ShallowFoundryBasinBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(0, 0, 0, 16, 8, 16);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ShallowFoundryBasinBlockEntity basinEntity) {
                if (!basinEntity.getMold().isEmpty()) {
                    Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), basinEntity.getMold());
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ShallowFoundryBasinBlockEntity basinEntity) {

                ItemStack stackInHand = player.getItemInHand(hand);

                if (stackInHand.isEmpty() && player.isShiftKeyDown() && !basinEntity.getMold().isEmpty()) {
                    player.setItemInHand(hand, basinEntity.getMold());
                    basinEntity.setMold(ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }

                if (isValidMold(stackInHand) && basinEntity.getMold().isEmpty()) {
                    basinEntity.setMold(stackInHand.copyWithCount(1));
                    if (!player.isCreative()) {
                        stackInHand.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }

                if (FluidUtil.interactWithFluidHandler(player, hand, level, pos, hit.getDirection())) {
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ShallowFoundryBasinBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntites.SHALLOW_FOUNDRY_BASIN.get(), ShallowFoundryBasinBlockEntity::tick);
    }
}
