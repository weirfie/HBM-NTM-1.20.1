package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.BoilerBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.IronCrateBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class IronCrateBlock extends BaseEntityBlock {
    public IronCrateBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (stack.hasTag()) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof IronCrateBlockEntity) {
                CompoundTag tag = stack.getTagElement("BlockEntityTag");
                if (tag != null) {
                    blockentity.load(tag);
                }
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity blockentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof IronCrateBlockEntity ironCrate) {
            ItemStack stack = new ItemStack(this);

            blockentity.saveToItem(stack);

            return Collections.singletonList(stack);
        }
        return super.getDrops(state, builder);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof IronCrateBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (IronCrateBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IronCrateBlockEntity(blockPos, blockState);
    }
}
