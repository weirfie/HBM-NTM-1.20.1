package net.StrayBead.hbm_ntm.block.custom.rbmk;

import io.netty.buffer.Unpooled;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.FuelRodEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.SteamChannelBlockEntity;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.init.ModGameRules;
import net.StrayBead.hbm_ntm.screen.SteamChannelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SteamChannelBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public SteamChannelBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        UltradenseSteamPipeUpdateTickProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());

        BlockEntity entity = world.getBlockEntity(pos);
        if (entity != null) {
            entity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(sourceHandler -> {
                for (int i = 0; i < sourceHandler.getTanks(); i++) {
                    FluidStack fluidInTank = sourceHandler.getFluidInTank(i);

                    if (fluidInTank.getFluid().isSame(Fluids.WATER) && fluidInTank.getAmount() > 5) {
                        for (int sx = -2; sx <= 1; sx++) {
                            for (int sy = -2; sy <= 1; sy++) {
                                for (int sz = -2; sz <= 1; sz++) {

                                    BlockPos targetPos = pos.offset(sx, sy, sz);
                                    if (world.getBlockState(targetPos).getBlock() == ModBlocks.FUEL_ROD.get()) {
                                        BlockEntity _ent = world.getBlockEntity(targetPos);

                                        if (_ent != null) {
                                            _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(cap ->
                                                    cap.extractEnergy(fluidInTank.getAmount() / 10, false)
                                            );
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                BlockPos belowPos = pos.below();
                if (world.getBlockState(belowPos).getBlock() == ModBlocks.WATER_DUCT.get()) {
                    BlockEntity belowEntity = world.getBlockEntity(belowPos);
                    if (belowEntity != null) {
                        belowEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP).ifPresent(destHandler -> {

                            FluidStack steamInSource = sourceHandler.getFluidInTank(1);

                            if (!steamInSource.isEmpty() && steamInSource.getFluid().isSame(ModFluids.STEAM.get())) {
                                int transferAmount = 100;
                                FluidStack toMove = new FluidStack(ModFluids.STEAM.get(), Math.min(steamInSource.getAmount(), transferAmount));

                                int accepted = destHandler.fill(toMove, IFluidHandler.FluidAction.SIMULATE);

                                if (accepted > 0) {
                                    FluidStack actuallyDrained = sourceHandler.drain(new FluidStack(ModFluids.STEAM.get(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                    destHandler.fill(actuallyDrained, IFluidHandler.FluidAction.EXECUTE);
                                }
                            }
                        });
                    }
                }

                BlockPos neighborBelow = pos.below();
                if (world.getBlockState(neighborBelow).getBlock() == ModBlocks.STEAM_CHANNEL.get()) {
                    BlockEntity neighborEnt = world.getBlockEntity(neighborBelow);
                    if (neighborEnt != null) {
                        neighborEnt.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP).ifPresent(neighborHandler -> {

                            FluidStack mySteam = sourceHandler.getFluidInTank(1);
                            int myAmount = mySteam.getAmount();

                            if (myAmount > 0 && mySteam.getFluid().isSame(ModFluids.STEAM.get())) {
                                int transferLimit = 500;
                                int toMove = Math.min(myAmount, transferLimit);

                                FluidStack moveStack = new FluidStack(ModFluids.STEAM.get(), toMove);
                                int accepted = neighborHandler.fill(moveStack, IFluidHandler.FluidAction.SIMULATE);

                                if (accepted > 0) {
                                    FluidStack drained = sourceHandler.drain(new FluidStack(ModFluids.STEAM.get(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                    neighborHandler.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                }
                            }
                        });
                    }
                }
            });
        }
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
                world.setBlock(BlockPos.containing(x, y + up, z), ModBlocks.STEAM_CHANNEL.get().defaultBlockState(), 3);
                up = up + 1;
            }
        }
        world.setBlock(BlockPos.containing(x, y + up, z), ModBlocks.STEAM_CHANNEL_TOP.get().defaultBlockState(), 3);
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        if (entity instanceof ServerPlayer player) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Steam Channel");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new SteamChannelMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SteamChannelBlockEntity(blockPos, blockState);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof FuelRodEntity be)
            return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
        else
            return 0;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return super.getTicker(p_153212_, p_153213_, p_153214_);
    }
}
