package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class RadiatedGraphiteUpdateTick {
    public static void execute(LevelAccessor world, double x, double y, double z, int startTick) {
        double sx = 0;
        double sy = 0;
        double sz = 0;
        if (world instanceof ServerLevel _level) {
            RandomSource random = RandomSource.create();
            for (int i = 0; i < 10; i++) {
                double offsetX = random.nextGaussian() * 5;
                double offsetY = random.nextGaussian() * 5;
                double offsetZ = random.nextGaussian() * 5;

                float baseR = 255f / 255f;
                float baseG = 150f / 255f;
                float baseB = 40f / 255f;

                float brightness = 0.8f + (random.nextFloat() * 0.3f);

                float r = Math.min(1.0f, baseR * brightness);
                float g = Math.min(1.0f, baseG * brightness);
                float b = Math.min(1.0f, baseB * brightness);

                int maxAge = 400 + random.nextInt(400);

                ParticleManager.addParticle(
                        (float) (x + offsetX),
                        (float) (y + offsetY),
                        (float) (z + offsetZ),
                        4.0f,
                        r, g, b,
                        1.0f,
                        Mth.nextFloat(RandomSource.create(), -0.06f, 0.06f), 0.4f, Mth.nextFloat(RandomSource.create(), -0.06f, 0.06f),
                        maxAge, 0.01f
                );
            }
        }
        sx = -5;
        for (int index0 = 0; index0 < 10; index0++) {
            sy = -5;
            for (int index1 = 0; index1 < 10; index1++) {
                sz = -5;
                for (int index2 = 0; index2 < 10; index2++) {
                    if (world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz)).canOcclude()) {
                        if ((world.getBlockState(BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy) + 1, Math.floor(z + sz)))).getBlock() == Blocks.AIR) {
                            world.setBlock(BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy) + 1, Math.floor(z + sz)), Blocks.FIRE.defaultBlockState(), 3);
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
//
//        {
//            final Vec3 _center = new Vec3(x, y, z);
//            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(100 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
//            for (Entity entityiterator : _entfound) {
//                if (entityiterator instanceof Player) {
//                    if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.GEIGER_COUNTER.get()
//                            || (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == ModItems.GEIGER_COUNTER.get()) {
//                        if (world instanceof Level _level) {
//                            if (!_level.isClientSide()) {
//                                _level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), ModSounds.GEIGER_CLICK.get(), SoundSource.MASTER, 3,
//                                        1);
//                            } else {
//                                _level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), ModSounds.GEIGER_CLICK.get(), SoundSource.MASTER, 3, 1, false);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        {
//            final Vec3 _center = new Vec3(x, y, z);
//            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
//            for (Entity entityiterator : _entfound) {
//                if (entityiterator instanceof Player) {
//                    if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.GEIGER_COUNTER.get()
//                            || (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == ModItems.GEIGER_COUNTER.get()) {
//                        if (world instanceof Level _level) {
//                            if (!_level.isClientSide()) {
//                                for (int i = 0; i < 20; i++) {
//                                    if (Math.random() < 0.5) {
//                                        _level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), ModSounds.GEIGER_CLICK.get(), SoundSource.MASTER, 50,
//                                                5);
//                                    }
//                                }
//                            } else {
//                                _level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), ModSounds.GEIGER_CLICK.get(), SoundSource.MASTER, 50, 5, false);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (!(world instanceof ServerLevel level)) return;
//
//        Vec3 _center = new Vec3(x, y, z);
//        double currentRadius = startTick;
//        System.out.println(currentRadius);
//        for (Entity entity : level.getAllEntities()) {
//            if (entity instanceof LivingEntity living) {
//                double dist = living.position().distanceTo(_center);
//
//                if (!(new Object() {
//                    public boolean checkGamemode(Entity _ent) {
//                        if (_ent instanceof ServerPlayer _serverPlayer) {
//                            return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
//                        } else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
//                            return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
//                        }
//                        return false;
//                    }
//                }.checkGamemode(entity))) {
//                    if (dist <= currentRadius) {
//                        int amplifier = 0;
//                        if (dist < currentRadius / 40) amplifier = 10;
//                        else if (dist < currentRadius / 8) amplifier = 2;
//                        else if (dist < currentRadius / 2) amplifier = 1;
//
//                        living.addEffect(new MobEffectInstance(ModMobEffects.RADIATION_POISONING.get(), 2000, amplifier, false, true));
//
//                        if (living instanceof Player player && hasGeigerCounter(player)) {
//                            int clickDelay = (int) Math.max(1, (dist / 10.0));
//                            if (level.getGameTime() % clickDelay == 0) {
//                                float volume = (float) Math.max(0.1, 1.0 - (dist / currentRadius));
//                                level.playSound(null, player.blockPosition(), ModSounds.GEIGER_CLICK.get(), SoundSource.MASTER, 1.0f, 1.0f);
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    private static boolean hasGeigerCounter(Player player) {
        return player.getMainHandItem().getItem() == ModItems.GEIGER_COUNTER.get() ||
                player.getOffhandItem().getItem() == ModItems.GEIGER_COUNTER.get();
    }
}
