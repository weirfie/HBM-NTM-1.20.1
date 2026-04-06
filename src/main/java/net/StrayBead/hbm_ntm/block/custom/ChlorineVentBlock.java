package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class ChlorineVentBlock extends Block {
    public static final BooleanProperty ISPUMPINGGAS = BooleanProperty.create("ispumpinggas");

    public ChlorineVentBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(ISPUMPINGGAS, false));
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ISPUMPINGGAS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(ISPUMPINGGAS, false);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level world, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, world, pos, p_60569_, p_60570_);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource p_222948_) {
        super.tick(blockstate, world, pos, p_222948_);
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        if (blockstate.getBlock().getStateDefinition().getProperty("ispumpinggas") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
            world.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, world, 4, "", Component.literal(""), world.getServer(), null).withSuppressedOutput(),
                    "particle minecraft:dust 0.45 0.55 0.35 20 ~ ~ ~ 4 2 4 0.05 50 force");
            {
                final Vec3 _center = new Vec3(x, y, z);
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 2, false, false));
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 3, false, false));
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 3, false, false));
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2, false, false));
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 2, false, false));
                }
            }
        }
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
        if (world.getBestNeighborSignal(pos) > 0) {
            {
                BlockState _bs = world.getBlockState(pos);
                if (_bs.getBlock().getStateDefinition().getProperty("ispumpinggas") instanceof BooleanProperty _booleanProp)
                    world.setBlock(pos, _bs.setValue(_booleanProp, true), 3);
            }
        } else {
            {
                BlockState _bs = world.getBlockState(pos);
                if (_bs.getBlock().getStateDefinition().getProperty("ispumpinggas") instanceof BooleanProperty _booleanProp)
                    world.setBlock(pos, _bs.setValue(_booleanProp, false), 3);
            }
        }
    }
}
