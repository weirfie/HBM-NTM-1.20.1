package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.FluidBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class PWRCoolantChannelBlock extends BaseEntityBlock {
    public PWRCoolantChannelBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onPlace(BlockState p_60566_, Level level, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, level, pos, p_60569_, p_60570_);
        level.scheduleTick(pos, this, 2);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);

        for (int i = 1; i < 10; i++) {
            if (level.getBlockState(BlockPos.containing(pos.getX(), pos.getY() + i, pos.getZ())).getBlock() == ModBlocks.PWR_FUEL_ROD.get()) {
                if (new Object() {
                    public int getFluidTankLevel(LevelAccessor level, BlockPos pos, int tank) {
                        AtomicInteger _retval = new AtomicInteger(0);
                        BlockEntity _ent = level.getBlockEntity(pos);
                        if (_ent != null)
                            _ent.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(capability -> _retval.set(capability.getFluidInTank(tank).getAmount()));
                        return _retval.get();
                    }
                }.getFluidTankLevel(level, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), 1) > 50) {
                    {
                        BlockEntity _ent = level.getBlockEntity(BlockPos.containing(pos.getX(), pos.getY() + 1, pos.getZ()));
                        int _amount = (int) (new Object() {
                            public int getFluidTankLevel(LevelAccessor level, BlockPos pos, int tank) {
                                AtomicInteger _retval = new AtomicInteger(0);
                                BlockEntity _ent = level.getBlockEntity(pos);
                                if (_ent != null)
                                    _ent.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(capability -> _retval.set(capability.getFluidInTank(tank).getAmount()));
                                return _retval.get();
                            }
                        }.getFluidTankLevel(level, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), 1) * 0.004);
                        if (_ent != null)
                            _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> capability.extractEnergy(_amount, false));
                    }
                }
            }
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof FluidBlockEntity sourceEntity) {
            sourceEntity.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.FLUID_HANDLER, null).ifPresent(sourceHandler -> {

                int maxTransfer = 500;
                for (net.minecraft.core.Direction dir : net.minecraft.core.Direction.values()) {
                    BlockPos neighborPos = pos.relative(dir);
                    BlockEntity neighborBE = level.getBlockEntity(neighborPos);

                    if (neighborBE != null) {
                        neighborBE.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(targetHandler -> {

                            net.minecraftforge.fluids.FluidStack sourceFluid = sourceHandler.getFluidInTank(0);
                            net.minecraftforge.fluids.FluidStack targetFluid = targetHandler.getFluidInTank(0);

                            if (!sourceFluid.isEmpty() && (targetFluid.isEmpty() || targetFluid.isFluidEqual(sourceFluid))) {
                                int sourceAmount = sourceFluid.getAmount();
                                int targetAmount = targetFluid.getAmount();

                                if (sourceAmount > targetAmount) {
                                    int difference = sourceAmount - targetAmount;
                                    int amountToMove = Math.min(difference / 2, maxTransfer);

                                    if (amountToMove > 0) {
                                        net.minecraftforge.fluids.FluidStack toMove = new net.minecraftforge.fluids.FluidStack(sourceFluid.getFluid(), amountToMove);

                                        int accepted = targetHandler.fill(toMove, net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE);
                                        if (accepted > 0) {
                                            net.minecraftforge.fluids.FluidStack drained = sourceHandler.drain(new net.minecraftforge.fluids.FluidStack(sourceFluid.getFluid(), accepted), net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE);
                                            targetHandler.fill(drained, net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE);
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }

        level.scheduleTick(pos, this, 2);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FluidBlockEntity(blockPos, blockState);
    }
}
