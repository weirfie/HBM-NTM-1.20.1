package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.custom.entity.BoilerBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.FatManBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.StrayBead.hbm_ntm.block.custom.entity.ThePrototypeBlockEntity;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.render.custom.FolkvangrFieldRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ThePrototypeBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 4, 16);

    public ThePrototypeBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.scheduleTick(pos, this, 1);
    }

    public static void detonate(ServerLevel world, BlockPos pos) {
        if (world.isClientSide()) return;

        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ThePrototypeBlockEntity prototype) {
            if (hasRequiredItems(prototype)) {
                FolkvangrFieldRenderer.addField(pos.getCenter(), 500.0f, 100);

                prototype.startDetonation(pos, 150);
            }
        }
    }

    private static boolean hasRequiredItems(ThePrototypeBlockEntity prototypeBlockEntity) {
        return prototypeBlockEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.SCHRABIDIUM_TRISULFIDE_CELL.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(1).getItem() == ModItems.SCHRABIDIUM_TRISULFIDE_CELL.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(12).getItem() == ModItems.SCHRABIDIUM_TRISULFIDE_CELL.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(13).getItem() == ModItems.SCHRABIDIUM_TRISULFIDE_CELL.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(2).getItem() == ModItems.URANIUM_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(7).getItem() == ModItems.URANIUM_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(6).getItem() == ModItems.URANIUM_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(11).getItem() == ModItems.URANIUM_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(3).getItem() == ModItems.LEAD_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(8).getItem() == ModItems.LEAD_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(10).getItem() == ModItems.LEAD_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(5).getItem() == ModItems.LEAD_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(4).getItem() == ModItems.NEPTUNIUM_237_QUAD_ROD.get() &&
                prototypeBlockEntity.itemHandler.getStackInSlot(9).getItem() == ModItems.NEPTUNIUM_237_QUAD_ROD.get();
    }

    private static void carveSphere(Level world, BlockPos center, int radius) {
        if (!(world instanceof ServerLevel serverLevel)) return;

        int slices = 20;
        int sliceHeight = (radius * 2) / slices;

        for (int i = 0; i < slices; i++) {
            final int sliceIndex = i;

            HBMNTM.queueServerWork(sliceIndex * 2, () -> {
                int startY = (center.getY() - radius) + (sliceIndex * sliceHeight);
                int endY = Math.min(center.getY() + radius, startY + sliceHeight);
                int rSquared = radius * radius;

                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for (int y = startY; y < endY; y++) {
                    int dy = y - center.getY();
                    for (int x = center.getX() - radius; x <= center.getX() + radius; x++) {
                        int dx = x - center.getX();
                        for (int z = center.getZ() - radius; z <= center.getZ() + radius; z++) {
                            int dz = z - center.getZ();

                            if (dx * dx + dy * dy + dz * dz <= rSquared) {
                                mutablePos.set(x, y, z);

                                serverLevel.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 2 | 16 | 128);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ThePrototypeBlockEntity) {
                ((ThePrototypeBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof ThePrototypeBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (ThePrototypeBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ThePrototypeBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntites.THE_PROTOTYPE.get(), ThePrototypeBlockEntity::tick);
    }
}
