package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.TurbineBlockEntity;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

public class CoolingTowerBlockOne extends Block {

    public CoolingTowerBlockOne(Properties p_49795_) {
        super(p_49795_);
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
        world.scheduleTick(pos, this, 2);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);

        BlockPos targetPos = pos.below(15);

        if (world.getBlockState(targetPos).getBlock() == ModBlocks.COOLING_TOWER.get()) {
            BlockEntity be = world.getBlockEntity(targetPos);
            if (be != null) {
                be.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(handler -> {
                    FluidStack stack = handler.getFluidInTank(1);

                    if (stack.getFluid().isSame(ModFluids.STEAM.get()) && stack.getAmount() > 1) {
                        String cmd = "particle minecraft:campfire_cosy_smoke ~ ~ ~ 0.01 0.01 0.01 0.1 3 force @a";
                        world.getServer().getCommands().performPrefixedCommand(
                                new CommandSourceStack(CommandSource.NULL, new Vec3(pos.getX(), pos.getY() + 2, pos.getZ()),
                                        Vec2.ZERO, world, 4, "", Component.literal(""), world.getServer(), null),
                                cmd
                        );
                    }
                });
            }
        }
        world.scheduleTick(pos, this, 2);
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
        if (tileentity instanceof TurbineBlockEntity be)
            return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
        else
            return 0;
    }
}
