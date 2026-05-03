package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.StrayBead.hbm_ntm.item.custom.FluidIdentifierItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class TankBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<TankTextureType> TEXTURE = EnumProperty.create("texture", TankTextureType.class);

    public TankBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(TEXTURE, TankTextureType.NONE));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        Direction facing = state.getValue(FACING);

        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {

                    BlockPos offset = rotateOffset(x, y, z, facing);
                    BlockPos target = pos.offset(offset);

                    if (target.equals(pos)) continue;

                    level.setBlock(target, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);

                    BlockEntity be = level.getBlockEntity(target);
                    if (be instanceof GenericBoundingBoxBE boundingBE) {
                        boundingBE.setCorePos(pos);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof FluidIdentifierItem identifier) {
            if (!level.isClientSide) {
                BlockEntity entity = level.getBlockEntity(pos);
                if (entity instanceof TankBlockEntity tank) {
                    String fluidName = identifier.getFluidName();

                    tank.setAllowedFluid(fluidName);

                    TankTextureType mappedTexture = mapFluidToTexture(fluidName);
                    level.setBlock(pos, state.setValue(TEXTURE, mappedTexture), 3);

                    player.displayClientMessage(Component.literal("Tank set to: " + fluidName), true);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof TankBlockEntity tank) {
                NetworkHooks.openScreen((ServerPlayer) player, tank, pos);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private TankTextureType mapFluidToTexture(String fluidName) {
        return switch (fluidName) {
            case "water", "heavy_water" -> TankTextureType.WATER;
            case "carbon_dioxide" -> TankTextureType.CARBON_DIOXIDE;
            case "diesel", "cracked_diesel" -> TankTextureType.DIESEL;
            case "alumina", "bauxite_solution" -> TankTextureType.ALUMINA;
            case "deuterium" -> TankTextureType.DEUTERIUM;
            case "liquid_oxygen" -> TankTextureType.LIQUID_OXYGEN;
            case "reformate_gas" -> TankTextureType.REFORMATE_GAS;
            case "petroleum_gas" -> TankTextureType.PETROLEUM_GAS;
            case "liquid_hydrogen" -> TankTextureType.LIQUID_HYDROGEN;
            case "heavy_oil" -> TankTextureType.HEAVY_OIL;
            case "light_oil" -> TankTextureType.LIGHT_OIL;
            case "sulfuric_acid" -> TankTextureType.SULFURIC_ACID;
            case "crude_oil" -> TankTextureType.CRUDE_OIL;
            case "syngas" -> TankTextureType.SYNGAS;
            default -> TankTextureType.NONE;
        };
    }

    private BlockPos rotateOffset(int x, int y, int z, Direction facing) {
        return switch (facing) {
            case NORTH -> new BlockPos(x, y, z);
            case SOUTH -> new BlockPos(-x, y, -z);
            case WEST  -> new BlockPos(z, y, -x);
            case EAST  -> new BlockPos(-z, y, x);
            default -> new BlockPos(x, y, z);
        };
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, TEXTURE);
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
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TankBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TankBlockEntity be) {
                Containers.dropContents(world, pos, be);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof TankBlockEntity be)
            return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
        else
            return 0;
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (type != ModBlockEntites.TANK.get()) {
            return null;
        }

        return (lvl, pos, st, be) -> {
            if (be instanceof TankBlockEntity tank) {
                TankBlockEntity.tick(lvl, pos, st, tank);
            }
        };
    }
}
