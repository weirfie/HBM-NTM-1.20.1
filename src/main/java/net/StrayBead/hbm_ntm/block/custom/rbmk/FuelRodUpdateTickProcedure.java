package net.StrayBead.hbm_ntm.block.custom.rbmk;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.Explosion;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.entity.custom.NeutronProjectileEntity;
import net.StrayBead.hbm_ntm.network.NTMModVariables;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FuelRodUpdateTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, double energy) {
        if (world instanceof ServerLevel projectileLevel) {
            if (Mth.nextInt(RandomSource.create(), 1, 10) <= 3) {
                Projectile _entityToSpawn = new Object() {
                    public Projectile getArrow(Level level, float damage, int knockback) {
                        AbstractArrow entityToSpawn = new NeutronProjectileEntity(ModEntities.NEUTRON_PROJECTILE.get(), level);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);
                        return entityToSpawn;
                    }
                }.getArrow(projectileLevel, 5, 1);
                _entityToSpawn.setPos(x - 0.5, y, z - 0.5);
                _entityToSpawn.shoot((Mth.nextInt(RandomSource.create(), -10, 10)), 0, (Mth.nextInt(RandomSource.create(), -10, 10)), 0.5f, 0);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }
        }

        if (new Object() {
            public int getEnergyStored(LevelAccessor level, BlockPos pos) {
                AtomicInteger _retval = new AtomicInteger(0);
                BlockEntity _ent = level.getBlockEntity(pos);
                if (_ent != null)
                    _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> _retval.set(capability.getEnergyStored()));
                return _retval.get();
            }
        }.getEnergyStored(world, BlockPos.containing(x, y, z)) >= 32000) {
            double sx = 0;
            double sy = 0;
            double sz = 0;
            sx = -10;
            for (int index0 = 0; index0 < 20; index0++) {
                sy = -10;
                for (int index1 = 0; index1 < 20; index1++) {
                    sz = -10;
                    for (int index2 = 0; index2 < 20; index2++) {
                        if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.FUEL_ROD_NORMAL.get()) {
                            world.setBlock(BlockPos.containing(x + sx, y + sy, z + sz), ModBlocks.CORIUM.get().defaultBlockState(), 3);
                            if (Mth.nextInt(RandomSource.create(), 1, 10) == 1) {
                                world.setBlock(BlockPos.containing(x + sx, y + sy, z + sz), ModBlocks.TITANIUM_ORE_BLOCK.get().defaultBlockState(), 3);
                            }
                        }
                        sz = sz + 1;
                    }
                    sy = sy + 1;
                }
                sx = sx + 1;
            }
            NTMModVariables.WorldVariables worldVars = NTMModVariables.WorldVariables.get(world);
            worldVars.hasReactorExploded = true;
            worldVars.syncData(world);
            if (world instanceof ServerLevel level && !level.isClientSide()) {
                Explosion.create(level, x, y, z, 4.0f, 500);
            }
            if (world instanceof Level _level && !_level.isClientSide())
                _level.explode(null, x, y + 30, z, 60, Level.ExplosionInteraction.BLOCK);
            if (world instanceof Level _level && !_level.isClientSide())
                _level.explode(null, x, y + 40, z, 60, Level.ExplosionInteraction.BLOCK);
            if (world instanceof Level _level && !_level.isClientSide())
                _level.explode(null, x, y + 45, z, 70, Level.ExplosionInteraction.BLOCK);
            if (world instanceof Level _level && !_level.isClientSide())
                _level.explode(null, x, y - 20, z, 70, Level.ExplosionInteraction.BLOCK);
            {
                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = ModBlocks.RADIATED_GRAPHITE.get().defaultBlockState();
                BlockState _bso = world.getBlockState(_bp);
                for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                    Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                    if (_property != null && _bs.getValue(_property) != null)
                        try {
                            _bs = _bs.setValue(_property, (Comparable) entry.getValue());
                        } catch (Exception e) {
                        }
                }
                world.setBlock(_bp, _bs, 3);
            }

            if (!world.isClientSide()) {
                for (Entity entityiterator : new ArrayList<>(world.players())) {
                    world.playSound(null, entityiterator.blockPosition(), ModSounds.RBMK_EXPLOSION.get(), SoundSource.NEUTRAL, 20f, 1f);
                }
            }

            if (!world.isClientSide() && world instanceof Level level) {
                int debrisCount = 20 + level.random.nextInt(10);

                for (int i = 0; i < debrisCount; i++) {
                    FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -4, 4), y + 3, z + Mth.nextInt(RandomSource.create(), -4, 4)), ModBlocks.FUEL_ROD_NORMAL.get().defaultBlockState());

                    double mx = (level.random.nextDouble() - 0.5D) * 1.5D;
                    double my = 0.7D + level.random.nextDouble() * 1.2D;
                    double mz = (level.random.nextDouble() - 0.5D) * 1.5D;

                    fallingBlock.setPos(x + 0.5, y + 1.1, z + 0.5);

                    fallingBlock.setDeltaMovement(mx, my, mz);
                    fallingBlock.time = 1;

                    fallingBlock.hasImpulse = true;
                    fallingBlock.hurtMarked = true;

                    level.addFreshEntity(fallingBlock);
                }

                for (int i = 0; i < debrisCount; i++) {
                    FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -4, 4), y + 3, z + Mth.nextInt(RandomSource.create(), -4, 4)), ModBlocks.BLOCK_OF_GRAPHITE.get().defaultBlockState());

                    double mx = (level.random.nextDouble() - 0.5D) * 1.5D;
                    double my = 0.7D + level.random.nextDouble() * 1.2D;
                    double mz = (level.random.nextDouble() - 0.5D) * 1.5D;

                    fallingBlock.setPos(x + 0.5, y + 1.1, z + 0.5);

                    fallingBlock.setDeltaMovement(mx, my, mz);
                    fallingBlock.time = 1;

                    fallingBlock.hasImpulse = true;
                    fallingBlock.hurtMarked = true;

                    level.addFreshEntity(fallingBlock);
                }
            }
        }
    }
}
