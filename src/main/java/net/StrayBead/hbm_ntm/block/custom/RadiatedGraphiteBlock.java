package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.network.NTMModVariables;
import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.Collections;
import java.util.List;

public class RadiatedGraphiteBlock extends Block {
    boolean canChange = true;
    int ticks = 0;
    public static final IntegerProperty STARTTICK = IntegerProperty.create("starttick", 0, 500);

    public RadiatedGraphiteBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STARTTICK, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STARTTICK);
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
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            super.onRemove(state, world, pos, newState, isMoving);

            NTMModVariables.WorldVariables worldVars = NTMModVariables.WorldVariables.get(world);

            worldVars.hasReactorExploded = false;
            worldVars.syncData(world);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(STARTTICK, 0);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        double maxRadius = 50.0;
        int startTick = blockstate.getValue(STARTTICK);
        {
            int _value = (int) ((blockstate.getBlock().getStateDefinition().getProperty("starttick") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1) + 1);
            BlockPos _pos = BlockPos.containing(x, y, z);
            BlockState _bs = world.getBlockState(_pos);
            if (_bs.getBlock().getStateDefinition().getProperty("starttick") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
        }

//        List<LivingEntity> entities = world.getEntitiesOfClass(
//                LivingEntity.class,
//                new AABB(pos).inflate(maxRadius)
//        );
//
//        for (LivingEntity entity : entities) {
//            double distance = Math.sqrt(entity.distanceToSqr(x, y, z));
//
//            if (distance <= maxRadius) {
//                double pct = 1.0 - (distance / maxRadius);
//
//                int dynamicAmplifier = (int) (pct * 5);
//
//                applyRadiationEffect(entity, dynamicAmplifier);
//            }
//        }

        RadiatedGraphiteUpdateTick.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate.getBlock().getStateDefinition().getProperty("starttick") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1);
        world.scheduleTick(pos, this, 1);
    }

    public static void applyRadiationEffect(LivingEntity entity, int amplifier) {
        MobEffectInstance current = entity.getEffect(ModMobEffects.RADIATION_POISONING.get());

        if (current == null || current.getAmplifier() <= amplifier) {
            entity.addEffect(new MobEffectInstance(
                    ModMobEffects.RADIATION_POISONING.get(),
                    200,
                    amplifier,
                    false,
                    true
            ));
        }
    }
}
