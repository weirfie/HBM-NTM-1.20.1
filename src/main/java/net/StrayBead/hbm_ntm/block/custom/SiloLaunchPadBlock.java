package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.BoilerBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.StrayBead.hbm_ntm.block.custom.entity.SiloLaunchPadBlockEntity;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.entity.custom.AntiBallisticMissileEntity;
import net.StrayBead.hbm_ntm.entity.custom.HighExplosiveMissileEntity;
import net.StrayBead.hbm_ntm.entity.custom.IncendiaryMissileEntity;
import net.StrayBead.hbm_ntm.entity.custom.NuclearMissileEntity;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.item.custom.LongRangeTargetDesignatorItem;
import net.StrayBead.hbm_ntm.item.custom.ShortRangeTargetDesignatorItem;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SiloLaunchPadBlock extends BaseEntityBlock {
    public SiloLaunchPadBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SiloLaunchPadBlockEntity silo) {
                silo.drops();
                clearExistingMissiles(pLevel, pPos);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    private void clearExistingMissiles(Level pLevel, BlockPos pPos) {
        BlockPos abovePos = pPos.above(3);
        AABB scanArea = new AABB(abovePos).inflate(0.3D);

        List<Entity> missiles = pLevel.getEntitiesOfClass(Entity.class, scanArea,
                e -> e instanceof HighExplosiveMissileEntity || e instanceof IncendiaryMissileEntity || e instanceof AntiBallisticMissileEntity || e instanceof NuclearMissileEntity);

        for (Entity missile : missiles) {
            missile.discard();
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof SiloLaunchPadBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (SiloLaunchPadBlockEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos,
                                Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide()) {
            if (pLevel.hasNeighborSignal(pPos)) {
                BlockEntity entity = pLevel.getBlockEntity(pPos);
                if (entity instanceof SiloLaunchPadBlockEntity launchPad) {
                    attemptLaunch(pLevel, pPos, launchPad);
                }
            }
        }
    }

    private void attemptLaunch(Level pLevel, BlockPos pPos, SiloLaunchPadBlockEntity launchPad) {
        if (launchPad.ENERGY_STORAGE.getEnergyStored() <= 0) return;

        ItemStack missileStack = launchPad.itemHandler.getStackInSlot(0);
        ItemStack designatorStack = launchPad.itemHandler.getStackInSlot(1);

        BlockPos target = null;
        if (designatorStack.getItem() instanceof ShortRangeTargetDesignatorItem designator) {
            target = designator.getTargetPos(designatorStack);
        } else if (designatorStack.getItem() instanceof LongRangeTargetDesignatorItem designator) {
            target = designator.getTargetPos(designatorStack);
        }

        if (target == null) {
            return;
        }

        boolean isHE = missileStack.is(ModItems.HIGH_EXPLOSIVE_MISSILE.get());
        boolean isIncendiary = missileStack.is(ModItems.INCENDIARY_MISSILE.get());
        boolean isAntiBallistic = missileStack.is(ModItems.ANTI_BALLISTIC_MISSILE.get());
        boolean isNuclear = missileStack.is(ModItems.NUCLEAR_MISSILE.get());

        if (isHE || isIncendiary || isAntiBallistic|| isNuclear) {
            if (isAntiBallistic) {
                List<Entity> potentialTargets = pLevel.getEntitiesOfClass(Entity.class, new AABB(pPos).inflate(400.0D),
                        e -> e instanceof HighExplosiveMissileEntity || e instanceof IncendiaryMissileEntity || e instanceof NuclearMissileEntity);

                if (potentialTargets.isEmpty()) {
                    return;
                }
            }

            clearExistingMissiles(pLevel, pPos);

            if (isHE) {
                HighExplosiveMissileEntity missile = new HighExplosiveMissileEntity(ModEntities.HIGH_EXPLOSIVE_MISSILE_ENTITY.get(), pLevel);
                missile.setPos(pPos.getX() + 0.5, pPos.getY() + 1.2, pPos.getZ() + 0.5);
                missile.setTargetPos(target);
                missile.setActivated(true);
                pLevel.addFreshEntity(missile);
            } else if (isIncendiary) {
                IncendiaryMissileEntity missile = new IncendiaryMissileEntity(ModEntities.INCENDIARY_MISSILE_ENTITY.get(), pLevel);
                missile.setPos(pPos.getX() + 0.5, pPos.getY() + 1.2, pPos.getZ() + 0.5);
                missile.setTargetPos(target);
                missile.setActivated(true);
                pLevel.addFreshEntity(missile);
            } else if (isAntiBallistic) {
                AntiBallisticMissileEntity missile = new AntiBallisticMissileEntity(ModEntities.ANTI_BALLISTIC_MISSILE_ENTITY.get(), pLevel);
                missile.setPos(pPos.getX() + 0.5, pPos.getY() + 1.2, pPos.getZ() + 0.5);
                missile.setTargetPos(target);
                missile.setActivated(true);
                pLevel.addFreshEntity(missile);
            } else if (isNuclear) {
                NuclearMissileEntity missile = new NuclearMissileEntity(ModEntities.NUCLEAR_MISSILE_ENTITY.get(), pLevel);
                missile.setPos(pPos.getX() + 0.5, pPos.getY() + 1.2, pPos.getZ() + 0.5);
                missile.setTargetPos(target);
                missile.setActivated(true);
                pLevel.addFreshEntity(missile);
            }

            missileStack.shrink(1);
            pLevel.playSound(null, pPos, ModSounds.MISSILE_LAUNCH.get(), SoundSource.BLOCKS, 2.0f, 1.0f);
            System.out.println("Launch Pad Target Found: " + target);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SiloLaunchPadBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntites.SILO_LAUNCH_PAD.get(), SiloLaunchPadBlockEntity::tick);
    }
}
