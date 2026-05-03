package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.FuelRodEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.PWRFuelRodBlockEntity;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.entity.custom.NeutronProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PWRFuelRodBlock extends BaseEntityBlock {
    public static final BooleanProperty ISXENON = BooleanProperty.create("isxenon");
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public PWRFuelRodBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(ISXENON, false));
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ISXENON);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(ISXENON, false);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 2);
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
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (!state.getValue(ISXENON)) {
            if (world instanceof ServerLevel projectileLevel) {
                if (entity instanceof NeutronProjectileEntity projectileEntity) {
                    if (!projectileEntity.getPersistentData().getBoolean("isFastNeutron")) {
                        Projectile _entityToSpawn = new Object() {
                            public Projectile getArrow(Level level, float damage, int knockback) {
                                AbstractArrow entityToSpawn = new NeutronProjectileEntity(ModEntities.NEUTRON_PROJECTILE.get(), level);
                                entityToSpawn.setBaseDamage(damage);
                                entityToSpawn.setKnockback(knockback);
                                entityToSpawn.setSilent(true);
                                return entityToSpawn;
                            }
                        }.getArrow(projectileLevel, 5, 1);
                        _entityToSpawn.getEntityData().set(projectileEntity.DATA_isFastNeutron, true);
                        _entityToSpawn.setPos(pos.getX(), pos.getY(), pos.getZ());
                        _entityToSpawn.shoot((Mth.nextInt(RandomSource.create(), -10, 10)), 0, (Mth.nextInt(RandomSource.create(), -10, 10)), 1.5f, 0);
                        projectileLevel.addFreshEntity(_entityToSpawn);
                    }
                }
            }
            if (world instanceof ServerLevel projectileLevel) {
                if (entity instanceof NeutronProjectileEntity projectileEntity) {
                    if (!projectileEntity.getPersistentData().getBoolean("isFastNeutron")) {
                        Projectile _entityToSpawn = new Object() {
                            public Projectile getArrow(Level level, float damage, int knockback) {
                                AbstractArrow entityToSpawn = new NeutronProjectileEntity(ModEntities.NEUTRON_PROJECTILE.get(), level);
                                entityToSpawn.setBaseDamage(damage);
                                entityToSpawn.setKnockback(knockback);
                                entityToSpawn.setSilent(true);
                                return entityToSpawn;
                            }
                        }.getArrow(projectileLevel, 5, 1);
                        _entityToSpawn.getEntityData().set(projectileEntity.DATA_isFastNeutron, true);
                        _entityToSpawn.setPos(pos.getX(), pos.getY(), pos.getZ());
                        _entityToSpawn.shoot((Mth.nextInt(RandomSource.create(), -10, 10)), 0, (Mth.nextInt(RandomSource.create(), -10, 10)), 2, 0);
                        projectileLevel.addFreshEntity(_entityToSpawn);
                    }
                }
            }
            if (entity instanceof NeutronProjectileEntity neutronProjectileEntity) {
                if (Math.random() < 0.2) {
                    world.setBlock(pos, state.setValue(ISXENON, true), 3);
                }
            }
        }
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        if (blockstate.getValue(ISXENON)) {
            {
                final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    if (entityiterator instanceof NeutronProjectileEntity neutronProjectileEntity) {
                        neutronProjectileEntity.discard();
                        world.setBlock(pos, blockstate.setValue(ISXENON, false), 3);
                    }
                }
            }
        }
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        double sx = 0;
        double sy = 0;
        double sz = 0;
        if (new Object() {
            public int getEnergyStored(LevelAccessor level, BlockPos pos) {
                AtomicInteger _retval = new AtomicInteger(0);
                BlockEntity _ent = level.getBlockEntity(pos);
                if (_ent != null)
                    _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> _retval.set(capability.getEnergyStored()));
                return _retval.get();
            }
        }.getEnergyStored(world, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ())) > 100000) {
            sx = -6;
            for (int index0 = 0; index0 < 12; index0++) {
                sy = -6;
                for (int index1 = 0; index1 < 12; index1++) {
                    sz = -6;
                    for (int index2 = 0; index2 < 12; index2++) {
                        if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.PWR_NEUTRON_SOURCE.get()) {
                            if (Mth.nextInt(RandomSource.create(), 1, 10) < 3) {
                                if (!world.isClientSide())
                                    world.explode(null, (x + sx), (y + sy), (z + sz), 2, Level.ExplosionInteraction.BLOCK);
                                world.setBlock(BlockPos.containing(x + sx, y + sy, z + sz), ModBlocks.TITANIUM_ORE_BLOCK.get().defaultBlockState(), 3);
                            }
                        }
                        sz = sz + 1;
                    }
                    sy = sy + 1;
                }
                sx = sx + 1;
            }
            world.setBlock(BlockPos.containing(x, y, z), ModBlocks.CORIUM.get().defaultBlockState(), 3);
        }
        world.scheduleTick(pos, this, 2);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PWRFuelRodBlockEntity(pos, state);
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
}
