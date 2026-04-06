package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.custom.render.ShockwaveRenderer;
import net.StrayBead.hbm_ntm.client.ClientExplosionEffects;
import net.StrayBead.hbm_ntm.client.ShockwaveData;
import net.StrayBead.hbm_ntm.client.screens.FlashOverlay;
import net.StrayBead.hbm_ntm.render.custom.FlashParticleManager;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TsarBombaBlock extends Block {
    private boolean isActive = false;
    private int counter = 0;
    public static boolean hasDetonated = false;
    public static BlockPos lastExplosionCenter = null;
    public static int lastX, lastY, lastZ;
    public static boolean isSet = false;
    public int growthAmount = 2;

    public TsarBombaBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.literal("MY PC!"));
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

    private static final int MAX_RADIUS = 600;
    private static final int TICK_INTERVAL = 1;

    private static final Map<BlockPos, Integer> activeExplosions = new HashMap<>();

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
        if (!p_60567_.isClientSide) {
            lastX = p_60568_.getX();
            lastY = p_60568_.getY();
            lastZ = p_60568_.getZ();
            isSet = true;
        }
        p_60567_.scheduleTick(p_60568_, this, TICK_INTERVAL);
    }

    public static void detonate(Level world, BlockPos pos) {
        if (world.isClientSide || hasDetonated) return;
        RandomSource random = RandomSource.create();

        FlashParticleManager.addParticle(
                (float) (pos.getX()),
                (float) (pos.getY() + 10),
                (float) (pos.getZ()),
                500f,
                1, 1, 1,
                1.0f,
                0.0f, 0.0f, 0.0f,
                70, 0f
        );

        FlashOverlay.triggerFlash();

        boolean doRandomizeTerrain = random.nextFloat() < 0.5F;

        if (world instanceof ServerLevel level) {
            spawnCustomParticles(pos.getX(), pos.getY() + 25, pos.getZ(), 100, 100, 100, 30000);
            spawnBaseParticles(pos.getX(), pos.getY() + 3, pos.getZ(), 120, 3, 120, 8000, level);
            spawnCustomParticles(pos.getX(), pos.getY() - 400, pos.getZ(), 15, 150, 15, 5000);
        }

        ShockwaveRenderer.addShockwave(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 2000f, 1000);

        hasDetonated = true;
        activeExplosions.put(pos.immutable(), 50);
        lastExplosionCenter = pos;

        carveSphere(world, pos, 90);

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
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1000 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                entityiterator.setSecondsOnFire(200);
            }
        }

        {
            final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(400 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 200);
            }
        }

        if (world instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(pos, serverLevel.getBlockState(pos).getBlock(), TICK_INTERVAL);
        }

        if (world instanceof ServerLevel level) {
            BlockPos center = pos;

            for (int r = 100; r <= 600; r += 3) {
                final int radius = r;
                int delay = r;
                HBMNTM.queueServerWork(delay, () -> {
                    for (double a = 0; a < 360; a += 0.5) {
                        double radians = Math.toRadians(a);
                        double x = center.getX() + Math.cos(radians) * radius;
                        double z = center.getZ() + Math.sin(radians) * radius;
                        double y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) x, (int) z);
                        if (Math.random() < 0.1) {
                            spawnShockwaveParticles(x, y + 3, z, center.getX(), center.getZ(), 6, 6, 6, 3);
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

                        int radiusReplace = 3;
                        BlockPos origin = new BlockPos((int) x, (int) y, (int) z);

                        for (int dx = -radiusReplace; dx <= radiusReplace; dx++) {
                            for (int dy = -1; dy <= 1; dy++) {
                                for (int dz = -radiusReplace; dz <= radiusReplace; dz++) {
                                    BlockPos targetPos = origin.offset(dx, dy, dz);
                                    if (!world.isLoaded(targetPos)) continue;

                                    BlockState state = world.getBlockState(targetPos);
                                    if (state.getBlock() == Blocks.SNOW) {
                                        world.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
                                    }
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

                    if (radius == 600) {
                        ClientExplosionEffects.explosionCenter = pos.immutable();
                        ClientExplosionEffects.effectStartTime = world.getGameTime();
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
        int pick = random.nextInt(8);
        return switch (pick) {
            case 0 -> Blocks.COBBLED_DEEPSLATE;
            case 1 -> Blocks.TUFF;
            case 2 -> Blocks.DIRT;
            case 3 -> Blocks.COARSE_DIRT;
            default -> Blocks.AIR;
        };
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

            int maxAge = 3000 + random.nextInt(400);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    25.0f,
                    r, g, b,
                    1.0f,
                    0.0f, 0.3f, 0.0f,
                    maxAge, 0.005f
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
                    (float) (level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) (x + offsetX), (int) (z + offsetZ))) + 5,
                    (float) (z + offsetZ),
                    20.0f,
                    r, g, b,
                    1.0f,
                    0.0f, 0.0f, 0.0f,
                    maxAge, 0.1f
            );
        }
    }

    public static void spawnShockwaveParticles(double x, double y, double z, double centerX, double centerZ, double dx, double dy, double dz, int count) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffZ = z - centerZ;
        double distance = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float vx = distance > 0 ? (float) ((diffX / distance) * 0.6f) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * 0.6f) : 0.0f;

        for (int i = 0; i < count; i++) {
            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float r = 1.0f; float g = 0.6f; float b = 0.2f;
            int maxAge = 500 + random.nextInt(400);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (y + offsetY),
                    (float) (z + offsetZ),
                    6f,
                    r, g, b,
                    0.8f,
                    vx, 0.0f, vz,
                    maxAge, 0.1f, true
            );
        }
    }
}
