package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.entity.custom.NeutronProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class PWRControlRodBlock extends Block {
    public static final IntegerProperty EXTRACTION_AMOUNT = IntegerProperty.create("extraction_amount", 0, 20);

    public PWRControlRodBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(EXTRACTION_AMOUNT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EXTRACTION_AMOUNT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(EXTRACTION_AMOUNT, 0);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level world, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, world, pos, p_60569_, p_60570_);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource p_222948_) {
        super.tick(state, world, pos, p_222948_);
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        {
            final Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                if (entityiterator instanceof NeutronProjectileEntity neutronProjectileEntity) {
                    BlockPos hitPos = BlockPos.containing(pos.getX(), pos.getY(), pos.getZ());
                    BlockState blockstate = world.getBlockState(hitPos);

                    int extraction = blockstate.getBlock().getStateDefinition().getProperty("extraction_amount") instanceof IntegerProperty _getip1
                            ? blockstate.getValue(_getip1) : 0;

                    float maxExtraction = 20.0f;

                    float absorptionChance = 1.0f - (extraction / maxExtraction);

                    if (neutronProjectileEntity.level().random.nextFloat() < absorptionChance) {
                        entityiterator.discard();
                    }
                }
            }
        }
        world.scheduleTick(pos, this, 1);
    }
}
