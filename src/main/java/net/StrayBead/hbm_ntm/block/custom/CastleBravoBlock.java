package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.custom.render.ExplosionShakeHandler;
import net.StrayBead.hbm_ntm.block.custom.render.LittleBoyShockwaveRenderer;
import net.StrayBead.hbm_ntm.block.custom.render.ShockwaveRenderer;
import net.StrayBead.hbm_ntm.client.ClientExplosionEffects;
import net.StrayBead.hbm_ntm.client.ShockwaveData;
import net.StrayBead.hbm_ntm.render.custom.FlashParticleManager;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CastleBravoBlock extends Block {
    private boolean isActive = false;
    private int counter = 0;
    public static boolean hasDetonated = false;
    public static BlockPos lastExplosionCenter = null;
    public int growthAmount = 2;
    public static int lastX, lastY, lastZ;
    public static boolean isSet = false;
    public Vec3 position;

    public CastleBravoBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.literal("Death Ash"));
    }

    public static BlockPos getStoredPos() {
        return new BlockPos(lastX, lastY, lastZ);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return 15;
    }

    private static final int MAX_RADIUS = 300;
    private static final int TICK_INTERVAL = 1;

    private static final Map<BlockPos, Integer> activeExplosions = new HashMap<>();

    @Override
    public void onPlace(BlockState p_60566_, Level world, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, world, pos, p_60569_, p_60570_);
        {
            int _value = pos.getX();
            BlockPos _pos = BlockPos.containing(pos.getX(), pos.getY(), pos.getZ());
            BlockState _bs = world.getBlockState(_pos);
            if (_bs.getBlock().getStateDefinition().getProperty("positionx") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
        }
        {
            int _value = pos.getY();
            BlockPos _pos = BlockPos.containing(pos.getX(), pos.getY(), pos.getZ());
            BlockState _bs = world.getBlockState(_pos);
            if (_bs.getBlock().getStateDefinition().getProperty("positiony") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
        }
        {
            int _value = pos.getZ();
            BlockPos _pos = BlockPos.containing(pos.getX(), pos.getY(), pos.getZ());
            BlockState _bs = world.getBlockState(_pos);
            if (_bs.getBlock().getStateDefinition().getProperty("positionz") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
        }
        if (!world.isClientSide) {
            lastX = pos.getX();
            lastY = pos.getY();
            lastZ = pos.getZ();
            isSet = true;
        }
        world.scheduleTick(pos, this, TICK_INTERVAL);
    }

    public static void detonate(Level world, BlockPos pos) {
        if (world.isClientSide || hasDetonated) return;
        RandomSource random = RandomSource.create();

        {
            final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("hbm_ntm:vaporization")))), 200);
            }
        }

        for (double a = 0; a < 360; a += 0.5) {
            double radians = Math.toRadians(a);
            double x = pos.getX() + Math.cos(radians) * 75;
            double z = pos.getZ() + Math.sin(radians) * 75;
            double y = pos.getY() + 160;
            spawnCondensationClouds(x, y, z, pos.getX(), pos.getZ(), 0.01, 7, 0.01, 30);
        }

        for (double a = 0; a < 360; a += 0.5) {
            double radians = Math.toRadians(a);
            double x = pos.getX() + Math.cos(radians) * 70;
            double z = pos.getZ() + Math.sin(radians) * 70;
            double y = pos.getY() + 200;
            spawnCondensationClouds(x, y, z, pos.getX(), pos.getZ(), 0.01, 10, 0.01, 14);
        }

        for (double a = 0; a < 360; a += 0.5) {
            double radians = Math.toRadians(a);
            double x = pos.getX() + Math.cos(radians) * 5;
            double z = pos.getZ() + Math.sin(radians) * 5;
            double y = pos.getY() + 270;
            spawnCondensationClouds(x, y, z, pos.getX(), pos.getZ(), 0.01, 3, 0.01, 3);
        }

        FlashParticleManager.addParticle(
                (float) (pos.getX()),
                (float) (pos.getY() + 10),
                (float) (pos.getZ()),
                300f,
                1, 1, 1,
                1.0f,
                0.0f, 0.0f, 0.0f,
                70, 0f
        );
        spawnFireballFlashParticles(pos.getX(), pos.getY() + 30, pos.getZ(), 30, 30, 30, 10000, 40 + random.nextInt(20));

        boolean doRandomizeTerrain = random.nextFloat() < 0.5F;

        if (world instanceof ServerLevel level) {
            spawnFireballParticles(pos.getX(), pos.getY() + 30, pos.getZ(), 70, 70, 70, 20000, 1000 + random.nextInt(400));
            spawnBaseParticles(pos.getX(), pos.getY() + 3, pos.getZ(), 100, 5, 100, 7000, level);
            spawnCustomParticles(pos.getX(), pos.getY() - 180, pos.getZ(), 8, 80, 8, 6000);
        }

        ShockwaveRenderer.addShockwave(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 600f, 800);

        hasDetonated = true;
        activeExplosions.put(pos.immutable(), 50);
        lastExplosionCenter = pos;

        carveSphere(world, pos, 80);

        HBMNTM.queueServerWork(100, () -> {
            LittleBoyShockwaveRenderer.addShockwave(new Vec3(pos.getX(), pos.getY() + 100, pos.getZ()), 1000f, 500);
        });

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
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(700 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                entityiterator.setSecondsOnFire(50);
            }
        }

        if (world instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(pos, serverLevel.getBlockState(pos).getBlock(), TICK_INTERVAL);
        }

        if (world instanceof ServerLevel level) {
            BlockPos center = pos;

            for (int r = 0; r <= 600; r += 3) {
                final int radius = r;
                int delay = r;
                HBMNTM.queueServerWork(delay, () -> {
                    ServerLevel serverLevel = (ServerLevel) world;
                    int chunkXStart = (center.getX() - radius) >> 4;
                    int chunkXEnd = (center.getX() + radius) >> 4;
                    int chunkZStart = (center.getZ() - radius) >> 4;
                    int chunkZEnd = (center.getZ() + radius) >> 4;

                    for (int cx = chunkXStart; cx <= chunkXEnd; cx++) {
                        for (int cz = chunkZStart; cz <= chunkZEnd; cz++) {
                            serverLevel.setChunkForced(cx, cz, true);

                            int finalCx = cx;
                            int finalCz = cz;
                            HBMNTM.queueServerWork(10, () -> {
                                serverLevel.setChunkForced(finalCx, finalCz, false);
                            });
                        }
                    }
                    for (double a = 0; a < 360; a += Mth.nextDouble(RandomSource.create(), 1, 4)) {
                        double radians = Math.toRadians(a);
                        double x = center.getX() + Math.cos(radians) * radius;
                        double z = center.getZ() + Math.sin(radians) * radius;
                        double y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) x, (int) z);
                        if (Math.random() < 0.8) {
                            spawnShockwaveParticles(x, y + 3, z, center.getX(), center.getY(), center.getZ(), 1, 1, 1, 1, 40);
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

                        final Vec3 _center = new Vec3(x, y, z);
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true)
                                .stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                .toList();

                        for (Entity entityiterator : _entfound) {
                            entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.EXPLOSION)), 10);
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
                                        if (world.getBlockState(firePos).isAir() && random.nextFloat() < 0.9F) {
                                            world.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (radius == 500) {
                        ClientExplosionEffects.explosionCenter = pos.immutable();
                        ClientExplosionEffects.effectStartTime = world.getGameTime();
                        ExplosionShakeHandler.isShaking = false;
                        ExplosionShakeHandler.explosionCenter = null;
                        ExplosionShakeHandler.currentRadius = 0;
                        world.setBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Blocks.AIR.defaultBlockState(), 3);
                    }
                });
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (ShockwaveData.hasHitPlayer) {
            final Vec3 center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            for (Entity playeriterator : new ArrayList<>(world.players())) {
                {
                    final Vec3 _center = new Vec3(playeriterator.getX(), playeriterator.getY(), playeriterator.getZ());
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        Vec3 pushDir = playeriterator.position().subtract(center).normalize();
                        float power = 1.6f;
                        entityiterator.setDeltaMovement(pushDir.x * power, 0.4f, pushDir.z * power);
                    }
                }
            }
            HBMNTM.queueServerWork(20, () -> {
                ShockwaveData.hasHitPlayer = false;
            });
        }
        world.scheduleTick(pos, this, TICK_INTERVAL);
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
        hasDetonated = false;
        if (!p_60515_.is(p_60518_.getBlock())) {
            if (p_60517_.getX() == lastX && p_60517_.getY() == lastY && p_60517_.getZ() == lastZ) {
                isSet = false;
            }
        }
    }

    private static Block getRandomReplacement(RandomSource random) {
        int pick = random.nextInt(7);
        return switch (pick) {
            case 0 -> Blocks.DIRT;
            case 1 -> Blocks.COARSE_DIRT;
            default -> Blocks.AIR;
        };
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

    public static void spawnCustomParticles(double x, double y, double z, double dx, double dy, double dz, int count) {
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

            int maxAge = 1000 + random.nextInt(400);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    15.0f,
                    r, g, b,
                    1.0f,
                    0.0f, 0.2f, 0.0f,
                    maxAge, 0.005f
            );
        }
    }

    public static void spawnFireballParticles(double x, double y, double z, double dx, double dy, double dz, int count, int maxAge) {
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

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    15.0f,
                    r, g, b,
                    1.0f,
                    0.0f, 0.2f, 0.0f,
                    maxAge, 0.005f
            );
        }
    }

    public static void spawnFireballFlashParticles(double x, double y, double z, double dx, double dy, double dz, int count, int maxAge) {
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

            spawnFlashFireballShockwaveParticles(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    15,
                    x, y, z, r, g, b, 2,
                    2, 2,
                    1, maxAge
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

            int maxAge = 1000 + random.nextInt(400);

            spawnShockwaveParticles(
                    (float) (x + offsetX),
                    (float) (level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) (x + offsetX), (int) (z + offsetZ))) + 5,
                    (float) (z + offsetZ),
                    x, y, z,
                    1, 1, 1,
                    1, 1000
            );
        }
    }

    public static void spawnShockwaveParticles(double x, double y, double z, double centerX, double centerY, double centerZ, double dx, double dy, double dz, int count, int maxAge) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffY = y - centerY;
        double diffZ = z - centerZ;

        double distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

        float vx = distance > 0 ? (float) ((diffX / distance) * 0.7f) : 0.0f;
        float vy = distance > 0 ? (float) ((diffY / distance) * 0.7f) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * 0.7f) : 0.0f;

        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float r = 0.3f; float g = 0.3f; float b = 0.3f;
            int age = maxAge + random.nextInt(40);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    6f,
                    r, g, b,
                    0.8f,
                    vx, vy, vz,
                    age, 0.1f, true
            );
        }
    }

    public static void spawnFlashFireballShockwaveParticles(double x, double y, double z, double centerX, double centerY, double centerZ, double dx, double dy, double dz, int count, int maxAge) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffY = y - centerY;
        double diffZ = z - centerZ;

        double distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

        float vx = distance > 0 ? (float) ((diffX / distance) * 0.9f) : 0.0f;
        float vy = distance > 0 ? (float) ((diffY / distance) * 0.9f) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * 0.9f) : 0.0f;

        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float r = 1.0f; float g = 1.0f; float b = 1.0f;
            int age = maxAge + random.nextInt(200);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    20f,
                    r, g, b,
                    1f,
                    vx, vy, vz,
                    age, 0.0f, false, false, true
            );
        }
    }

    public static void spawnFireballShockwaveParticles(double x, double y, double z, int size, double centerX, double centerY, double centerZ, float r, float g, float b, double dx, double dy, double dz, int count, int maxAge) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffY = y - centerY;
        double diffZ = z - centerZ;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

        float expansionSpeed = 0.15f;

        float vx = distance > 0 ? (float) ((diffX / distance) * expansionSpeed) : 0.0f;
        float vy = distance > 0 ? (float) ((diffY / distance) * expansionSpeed) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * expansionSpeed) : 0.0f;

        for (int i = 0; i < count; i++) {
            float turbulence = 0.05f;
            float finalVX = vx + (random.nextFloat() - 0.5f) * turbulence;
            float finalVY = vy + (random.nextFloat() - 0.5f) * turbulence;
            float finalVZ = vz + (random.nextFloat() - 0.5f) * turbulence;

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
                    0.8f,
                    finalVX, 0.3f, finalVZ,
                    age, 0.005f, true, true
            );
        }
    }

    public static void spawnFlashFireballShockwaveParticles(double x, double y, double z, int size, double centerX, double centerY, double centerZ, float r, float g, float b, double dx, double dy, double dz, int count, int maxAge) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffY = y - centerY;
        double diffZ = z - centerZ;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

        float expansionSpeed = 0.15f;

        float vx = distance > 0 ? (float) ((diffX / distance) * expansionSpeed) : 0.0f;
        float vy = distance > 0 ? (float) ((diffY / distance) * expansionSpeed) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * expansionSpeed) : 0.0f;

        for (int i = 0; i < count; i++) {
            float turbulence = 0.05f;
            float finalVX = vx + (random.nextFloat() - 0.5f) * turbulence;
            float finalVY = vy + (random.nextFloat() - 0.5f) * turbulence;
            float finalVZ = vz + (random.nextFloat() - 0.5f) * turbulence;

            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;
            int age = maxAge + random.nextInt(40);

            spawnFlashFireballShockwaveParticles(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    centerX, centerY, centerZ,
                    1, 1, 1,
                    1, age
            );
        }
    }

    public static void spawnCondensationClouds(double x, double y, double z, double centerX, double centerZ, double dx, double dy, double dz, int count) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffZ = z - centerZ;
        double distance = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float vx = distance > 0 ? (float) ((diffX / distance) * 2f) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * 2f) : 0.0f;

        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float r = 255f / 255f; float g = 255f / 255f; float b = 255f / 255f;
            int maxAge = 500 + random.nextInt(400);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    10f,
                    r, g, b,
                    0.4f,
                    vx, 0.0f, vz,
                    maxAge, 0f, false
            );
        }
    }
}
