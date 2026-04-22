package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.custom.entity.LittleBoyBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.render.ExplosionShakeHandler;
import net.StrayBead.hbm_ntm.block.custom.render.LittleBoyShockwaveRenderer;
import net.StrayBead.hbm_ntm.client.ClientExplosionEffects;
import net.StrayBead.hbm_ntm.client.screens.FlashOverlay;
import net.StrayBead.hbm_ntm.render.custom.FlashParticleManager;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class NuclearChargeBlock extends Block {
    public NuclearChargeBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(blockState, world, pos, neighborBlock, fromPos, moving);
        if (world.getBestNeighborSignal(pos) > 0) {
            if (world instanceof ServerLevel level) {
                detonate(level, pos);
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private static final int TICK_INTERVAL = 2;

    private void detonate(Level world, BlockPos pos) {
        RandomSource random = RandomSource.create();
        FlashParticleManager.addParticle(
                (float) (pos.getX()),
                (float) (pos.getY() + 10),
                (float) (pos.getZ()),
                350f,
                1, 1, 1,
                1.0f,
                0.0f, 0.0f, 0.0f,
                70, 0f
        );
        spawnFireballFlashParticles(pos.getX(), pos.getY() + 30, pos.getZ(), 20, 30, 30, 30, 10000, 40 + random.nextInt(20));
        FlashOverlay.triggerFlash();

        boolean doRandomizeTerrain = random.nextFloat() < 0.5F;

        if (world instanceof ServerLevel level) {
            ParticleManager.setClientHeatSource(new Vec3(pos.getX(), pos.getY() + 20, pos.getZ()));
            spawnCustomParticles(pos.getX(), pos.getY() + 20, pos.getZ(), 20, 20, 20, 10000, true);
            spawnBaseParticles(pos.getX(), pos.getY() + 3, pos.getZ(), 35, 2, 35, 3000, level);
            spawnCustomParticles(pos.getX(), pos.getY() - 160, pos.getZ(), 5, 60, 5, 3000, false);

            spawnFireRing(pos.getX(), pos.getY() + 3, pos.getZ(), 10.0, 3000);
        }

        LittleBoyShockwaveRenderer.addShockwave(new Vec3(pos.getX(), pos.getY() + 30, pos.getZ()), 600f, 800);

        carveSphere(world, pos, 35);

        {
            final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(700 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                if (entityiterator instanceof Player) {
                    HBMNTM.queueServerWork((int) entityiterator.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) / 5, () -> {
                        world.playSound(null, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), ModSounds.NUCLEAR_EXPLOSION.get(), SoundSource.MASTER, 20f, 1f);
                    });
                }
            }
        }

        {
            final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(500 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                entityiterator.setSecondsOnFire(40);
            }
        }

        if (world instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(pos, serverLevel.getBlockState(pos).getBlock(), TICK_INTERVAL);
        }

        if (world instanceof ServerLevel level) {
            BlockPos center = pos;

            for (double r = 0; r <= 200; r += 1) {
                final double radius = r;
                double delay = r;
                HBMNTM.queueServerWork((int) delay, () -> {
                    for (int a = 0; a < 360; a += 1) {
                        double radians = Math.toRadians(a);
                        double x = center.getX() + Math.cos(radians) * radius;
                        double z = center.getZ() + Math.sin(radians) * radius;
                        double y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) x, (int) z);
                        if (Math.random() < 0.1) {
                            if (Mth.nextInt(RandomSource.create(), 1, 10) == 1) {
                                spawnShockwaveParticles(x, y + 3, z, center.getX(), center.getZ(), 1, 1, 1, 1);
                            }
                            level.getServer().getCommands().performPrefixedCommand(
                                    new CommandSourceStack(CommandSource.NULL,
                                            new Vec3(x, y, z),
                                            Vec2.ZERO, level, 4, "", Component.literal(""), level.getServer(), null
                                    ).withSuppressedOutput(),
                                    "particle minecraft:explosion_emitter ~ ~2 ~ 0.01 0.01 0.01 0 1 force @a"
                            );
                            level.getServer().getCommands().performPrefixedCommand(
                                    new CommandSourceStack(CommandSource.NULL,
                                            new Vec3(x, pos.getY() + 50, z),
                                            Vec2.ZERO, level, 4, "", Component.literal(""), level.getServer(), null
                                    ).withSuppressedOutput(),
                                    "particle oilcraft:white_cloud ~ ~2 ~ 0.01 0.01 0.01 0 1 force @a"
                            );
                            level.getServer().getCommands().performPrefixedCommand(
                                    new CommandSourceStack(CommandSource.NULL,
                                            new Vec3(x * 0.8, pos.getY() + 80, z * 0.8),
                                            Vec2.ZERO, level, 4, "", Component.literal(""), level.getServer(), null
                                    ).withSuppressedOutput(),
                                    "particle oilcraft:white_cloud ~ ~2 ~ 0.01 0.01 0.01 0 1 force @a"
                            );
                        }

                        level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 1f, 1f);

                        final Vec3 _center = new Vec3(x, y, z);
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true)
                                .stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                .toList();

                        for (Entity entityiterator : _entfound) {
                            entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.EXPLOSION)), 100);
                        }

                        int radiusReplace = 2;
                        BlockPos origin = new BlockPos((int) x, (int) y, (int) z);

                        for (int dx = -radiusReplace; dx <= radiusReplace; dx++) {
                            for (int dy = -1; dy <= 1; dy++) {
                                for (int dz = -radiusReplace; dz <= radiusReplace; dz++) {
                                    BlockPos targetPos = origin.offset(dx, dy, dz);
                                    if (!world.isLoaded(targetPos)) continue;

                                    BlockState state = world.getBlockState(targetPos);
                                    if (state.isAir() || state.getCollisionShape(world, targetPos).isEmpty()) continue;

                                    if (random.nextFloat() < 0.1F) {
                                        Block newBlock = getRandomReplacement(random);
                                        world.setBlock(targetPos, newBlock.defaultBlockState(), 3);

                                        BlockPos firePos = targetPos.above();
                                        if (world.getBlockState(firePos).isAir() && random.nextFloat() < 0.6F) {
                                            world.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (radius == 200) {
                        ClientExplosionEffects.explosionCenter = pos.immutable();
                        ClientExplosionEffects.effectStartTime = world.getGameTime();
                        ExplosionShakeHandler.explosionCenter = null;
                        ExplosionShakeHandler.currentRadius = 0;
                        world.setBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Blocks.AIR.defaultBlockState(), 3);
                    }
                });
            }
        }
    }

    public static void spawnFireRing(double x, double y, double z, double radius, int count) {
        RandomSource random = RandomSource.create();

        float expansionSpeed = 0.03f;

        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI * i) / count;

            double dx = Math.cos(angle);
            double dz = Math.sin(angle);

            double currentRadius = radius + (random.nextFloat() - 0.5) * 15.0;

            double offsetX = dx * currentRadius;
            double offsetZ = dz * currentRadius;

            double offsetY = (random.nextFloat() - 0.5) * 2.0;

            float r = 1.0f;
            float g = 0.8f + (random.nextFloat() * 0.2f);
            float b = 0.2f;

            int maxAge = 2000 + random.nextInt(400);

            float vx = (float) (dx * expansionSpeed);
            float vy = 0.1f;
            float vz = (float) (dz * expansionSpeed);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    2f + random.nextFloat() * 2f,
                    r, g, b,
                    1.0f,
                    vx, vy, vz,
                    maxAge,
                    0.001f,
                    false,
                    true,
                    false,
                    false,
                    false
            );
        }
    }

    public static void spawnBaseParticles(double x, double y, double z, double dx, double dy, double dz, int count, Level level) {
        RandomSource random = RandomSource.create();
        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float baseR = 255f / 255f;
            float baseG = 150f / 255f;
            float baseB = 40f / 255f;

            float brightness = 0.8f + (random.nextFloat() * 0.3f);

            float r = Math.min(1.0f, baseR * brightness);
            float g = Math.min(1.0f, baseG * brightness);
            float b = Math.min(1.0f, baseB * brightness);

            int maxAge = 2000 + random.nextInt(400);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) (x + offsetX), (int) (z + offsetZ)) + 3),
                    (float) (z + offsetZ),
                    15.0f,
                    r, g, b,
                    1.0f,
                    0.0f, 0.0f, 0.0f,
                    maxAge, 0.1f
            );
        }
    }

    private static void carveSphere(Level world, BlockPos center, int radius) {
        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();
        int rSquared = radius * radius;

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int y = cy - radius; y <= cy + radius; y++) {
                for (int z = cz - radius; z <= cz + radius; z++) {
                    int dx = x - cx;
                    int dy = y - cy;
                    int dz = z - cz;
                    if (dx*dx + dy*dy + dz*dz <= rSquared) {
                        BlockPos pos = new BlockPos(x, y, z);
                        if (!pos.equals(center)) {
                            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2 | 16);
                        }
                    }
                }
            }
        }
    }

    public static void spawnCustomParticles(double x, double y, double z, double dx, double dy, double dz, int count, boolean convectionBehave) {
        RandomSource random = RandomSource.create();
        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float baseR = 255f / 255f;
            float baseG = 247f / 255f;
            float baseB = 94f / 255f;

            float brightness = 0.8f + (random.nextFloat() * 0.8f);

            float r = Math.min(1.0f, baseR * brightness);
            float g = Math.min(1.0f, baseG * brightness);
            float b = Math.min(1.0f, baseB * brightness);

            int maxAge = 2000 + random.nextInt(400);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    8f,
                    r, g, b,
                    1.0f,
                    0.0f, 0.2f, 0.0f,
                    maxAge, 0.005f, false, false, convectionBehave, false,
                    20f
            );
        }
    }

    public static void spawnFireballFlashParticles(double x, double y, double z, int size, double dx, double dy, double dz, int count, int maxAge) {
        RandomSource random = RandomSource.create();
        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float baseR = 1.0f;
            float baseG = 1.0f;
            float baseB = 1.0f;

            float brightness = 1.0f;

            float r = 1.0f;
            float g = 1.0f;
            float b = 1.0f;

            spawnFireballShockwaveParticles(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    size,
                    x, y, z, r, g, b, 1,
                    1, 1,
                    1, maxAge
            );
        }
    }

    public static void spawnFireballShockwaveParticles(double x, double y, double z, int size, double centerX, double centerY, double centerZ, float r, float g, float b, double dx, double dy, double dz, int count, int maxAge) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffZ = z - centerZ;
        double diffY = y - centerY;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

        float vx = distance > 0 ? (float) ((diffX / distance) * 0.8f) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * 0.8f) : 0.0f;
        float vy = distance > 0 ? (float) ((diffY / distance) * 0.8f) : 0.0f;

        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;
            int age = maxAge + random.nextInt(40);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    size,
                    r, g, b,
                    1.0f,
                    vx, vy, vz,
                    age, 0f, true, true
            );
        }
    }

    public static void spawnShockwaveParticles(double x, double y, double z, double centerX, double centerZ, double dx, double dy, double dz, int count) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffZ = z - centerZ;
        double distance = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float vx = distance > 0 ? (float) ((diffX / distance) * 0.7f) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * 0.7f) : 0.0f;

        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float r = 0.3f; float g = 0.3f; float b = 0.3f;
            int maxAge = 40 + random.nextInt(30);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    Mth.nextFloat(RandomSource.create(), 3, 10),
                    r, g, b,
                    0.8f,
                    vx, 0.0f, vz,
                    maxAge, 0f, true
            );
        }
    }

    private static Block getRandomReplacement(RandomSource random) {
        int pick = random.nextInt(7);
        return switch (pick) {
            case 0 -> Blocks.COBBLED_DEEPSLATE;
            case 1 -> Blocks.TUFF;
            case 2 -> Blocks.DIRT;
            case 3 -> Blocks.COARSE_DIRT;
            case 4, 5 -> Blocks.AIR;
            default -> Blocks.COBBLED_DEEPSLATE;
        };
    }
}
