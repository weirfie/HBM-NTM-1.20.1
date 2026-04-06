package net.StrayBead.hbm_ntm.entity.custom;

import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ShockwaveProjectileEntity extends AbstractArrow implements ItemSupplier {
    private float expansionRate = 0.012f; // How fast the projectile expands
    private float size = 0.0f; // Current size of the projectile
    private static final int LIFETIME_TICKS = 400;
    private int age = 0;

    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.URANIUM_DUST.get());

    public ShockwaveProjectileEntity(EntityType<? extends AbstractArrow> type, Level world) {
        super(type, world);
    }

    public ShockwaveProjectileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.SHOCKWAVE_PROJECTILE.get(), world);
    }

    public ShockwaveProjectileEntity(EntityType<? extends ShockwaveProjectileEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.noPhysics = true;
        this.setNoGravity(true);
        this.setInvulnerable(true);

        this.age++;

        if (this.age >= LIFETIME_TICKS) {
            this.discard();
            if (this.level() instanceof ServerLevel _level) {
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(this.getX(), this.getY(), this.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                        "kill @e[type=hbm_ntm:shockwave_projectile]");
            }
            return;
        }

        if (this.level() instanceof ServerLevel _level) {
            for (int i = 0; i <= this.getY() + 100; i++) {
                if (_level.getBlockState(BlockPos.containing(this.getX(), this.getY() - i, this.getZ())).canOcclude() || (_level.getBlockState(BlockPos.containing(this.getX(), this.getY() - i, this.getZ()))).getBlock() == Blocks.WATER || (_level.getBlockState(BlockPos.containing(this.getX(), this.getY() - i, this.getZ()))).getBlock() == Blocks.LAVA) {
                    if((_level.getBlockState(BlockPos.containing(this.getX(), (this.getY() - i) + 1, this.getZ()))).getBlock() == Blocks.AIR) {
                        if(!this.level().isClientSide()) {
                            if (Mth.nextInt(RandomSource.create(), 1, 15) < 3) {
                                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(this.getX(), (this.getY() - i) + 1, this.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                                        "particle minecraft:explosion_emitter ~ ~ ~ 1 1 1 0 1 force @a");
                            }
                            if (Mth.nextInt(RandomSource.create(), 1, 20) < 2) {
                                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(this.getX(), (this.getY() - i) + 1, this.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                                        "particle hbm_ntm:shockwave_particle ~ ~-2 ~ 4 5 4 0 1 force @a");
                            }
                            if (!_level.isClientSide()) {
                                _level.playSound(null, BlockPos.containing(this.getX(), (this.getY() - i) + 1, this.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 1, 1);
                            } else {
                                _level.playLocalSound(this.getX(), (this.getY() - i) + 1, this.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 1, 1, false);
                            }

                            {
                                if (!((_level.getBlockState(BlockPos.containing(this.getX(), this.getY() - i, this.getZ()))).getBlock() == Blocks.WATER)) {
                                    BlockPos _bp = BlockPos.containing(this.getX(), this.getY() - i, this.getZ());
                                    BlockState _bs = Blocks.DEEPSLATE.defaultBlockState();
                                    BlockState _bso = _level.getBlockState(_bp);
                                    for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                                        Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                                        if (_property != null && _bs.getValue(_property) != null)
                                            try {
                                                _bs = _bs.setValue(_property, (Comparable) entry.getValue());
                                            } catch (Exception e) {
                                            }
                                    }
                                    _level.setBlock(_bp, _bs, 3);
                                }
                            }

                            if (Mth.nextInt(RandomSource.create(), 1, 10) < 5) {
                                BlockPos _bp = BlockPos.containing(this.getX(), this.getY() - i, this.getZ());
                                BlockState _bs = Blocks.AIR.defaultBlockState();
                                BlockState _bso = _level.getBlockState(_bp);
                                for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                                    Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                                    if (_property != null && _bs.getValue(_property) != null)
                                        try {
                                            _bs = _bs.setValue(_property, (Comparable) entry.getValue());
                                        } catch (Exception e) {
                                        }
                                }
                                _level.setBlock(_bp, _bs, 3);
                            }

                            {
                                if(Math.random() < 0.5) {
                                    BlockPos _bp = BlockPos.containing(this.getX(), (this.getY() - i) + 1, this.getZ());
                                    BlockState _bs = Blocks.FIRE.defaultBlockState();
                                    BlockState _bso = _level.getBlockState(_bp);
                                    for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                                        Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                                        if (_property != null && _bs.getValue(_property) != null)
                                            try {
                                                _bs = _bs.setValue(_property, (Comparable) entry.getValue());
                                            } catch (Exception e) {
                                            }
                                    }
                                    _level.setBlock(_bp, _bs, 3);
                                }
                            }

                            final Vec3 _center = new Vec3(this.getX(), this.getY() - i, this.getZ());
                            List<Entity> _entfound = _level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                            for (Entity entityiterator : _entfound) {
                                entityiterator.hurt(new DamageSource(_level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 30);
                            }
                        }
                        break;
                    }
                }
            }
        }

        // Apply outward velocity expansion to simulate shockwave
        Vec3 velocity = getDeltaMovement();
        setDeltaMovement(velocity.x() * (1 + expansionRate), velocity.y(), velocity.z() * (1 + expansionRate));

        // Increase the size of the projectile as it moves (could be used for visual effects)
        size += expansionRate;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_36726_) {
        return false;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return false;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
    }

    @Override
    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    public static ShockwaveProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        ShockwaveProjectileEntity entityarrow = new ShockwaveProjectileEntity(ModEntities.SHOCKWAVE_PROJECTILE.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 3f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(5);
        entityarrow.setKnockback(5);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }

    // Method to summon projectiles in a circle
    public static void summonProjectilesInCircle(Level level, double x, double y, double z, int count, double radius) {
        for (int i = 0; i < count; i++) {
            double angle = Math.PI * 2 * i / count;
            double offsetX = x + Math.cos(angle) * radius;
            double offsetZ = z + Math.sin(angle) * radius;

            // Create a new projectile at the calculated position
            ShockwaveProjectileEntity projectile = new ShockwaveProjectileEntity(ModEntities.SHOCKWAVE_PROJECTILE.get(), level);

            // Set the exact position where you want the projectile to spawn
            projectile.setPos(offsetX, y, offsetZ);

            double velocityX = Math.cos(angle) * 0.5;
            double velocityZ = Math.sin(angle) * 0.5;

            // Set the projectile's velocity to move in the desired direction
            projectile.setDeltaMovement(velocityX, 0.0, velocityZ); // Keeping Y at 0 to not affect upward/downward movement

            // Spawn the projectile in the world
            level.addFreshEntity(projectile);
        }
    }
}
