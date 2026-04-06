package net.StrayBead.hbm_ntm.block.custom.rbmk;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.entity.custom.NeutronProjectileEntity;
import net.StrayBead.hbm_ntm.init.ModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

public class GraphiteModeratorBlock extends Block {

    public GraphiteModeratorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void entityInside(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity entity) {
        super.entityInside(p_60495_, p_60496_, p_60497_, entity);
        if (entity instanceof NeutronProjectileEntity neutronProjectileEntity) {
            if (neutronProjectileEntity.getEntityData().get(neutronProjectileEntity.DATA_isFastNeutron)) {
                neutronProjectileEntity.getEntityData().set(neutronProjectileEntity.DATA_isFastNeutron, false);
                Vec3 currentVelocity = neutronProjectileEntity.getDeltaMovement();
                double speedReductionFactor = -0.8;
                Vec3 newVelocity = new Vec3(-1, 0, -1);
                neutronProjectileEntity.setDeltaMovement(newVelocity);

                neutronProjectileEntity.setNoGravity(true);

                neutronProjectileEntity.setDeltaMovement(newVelocity);
            }
        }
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        boolean found = false;
        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -1;
        found = false;
        for (int index0 = 0; index0 < 2; index0++) {
            sy = -1;
            for (int index1 = 0; index1 < 2; index1++) {
                sz = -1;
                for (int index2 = 0; index2 < 2; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.FUEL_ROD.get()) {
                        {
                            BlockEntity _ent = world.getBlockEntity(BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy), Math.floor(z + sz)));
                            int _amount = 40;
                            if (_ent != null)
                                _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> capability.receiveEnergy(_amount, false));
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
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
                world.setBlock(BlockPos.containing(x, y + up, z), ModBlocks.GRAPHITE_MODERATOR.get().defaultBlockState(), 3);
                up = up + 1;
            }
        }
    }
}
