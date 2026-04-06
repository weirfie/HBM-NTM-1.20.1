package net.StrayBead.hbm_ntm.entity.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

public class SubmunitionProjectileEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.URANIUM_DUST.get());

    public SubmunitionProjectileEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
        this.setNoGravity(false);
        this.setPierceLevel((byte) 127);
    }

    public SubmunitionProjectileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.SUBMUNITION_PROJECTILE.get(), world);
    }

    public SubmunitionProjectileEntity(EntityType<? extends SubmunitionProjectileEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
        isNoGravity();
        this.setPierceLevel((byte) 127);
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    @Override
    protected float getWaterInertia() {
        return 0.7F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    private int tickCounter = 0;
    private int waitTickCounter = 0;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            double offsetX = random.nextGaussian() * 0.4;
            double offsetY = random.nextGaussian() * 0.4;
            double offsetZ = random.nextGaussian() * 0.4;
            float baseR = 255f / 255f;
            float baseG = 150f / 255f;
            float baseB = 40f / 255f;
            float brightness = 0.8f + (random.nextFloat() * 0.3f);
            float r = Math.min(1.0f, baseR * brightness);
            float g = Math.min(1.0f, baseG * brightness);
            float b = Math.min(1.0f, baseB * brightness);

            ParticleManager.addParticle((float) (this.getX() + offsetX), (float) (this.getY() + offsetY), (float) (this.getZ() + offsetZ), 0.9f, r, g, b, 1.0f, 0, 0, 0, 100, 0.04f);
        }

        if (!this.level().isClientSide) {
            if (this.level() instanceof ServerLevel _level) {
                _level.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME,
                        this.getX(), this.getY(), this.getZ(), 1, 0.05, 0.05, 0.05, 0.02);
            }

            if (this.tickCount > 200 || this.inGround) {
                this.applyImpactEffects();
            }
        }
    }

    private void applyImpactEffects() {
        if (!this.level().isClientSide) {
            BlockPos pos = this.blockPosition();

            tryPlaceFire(pos);
            tryPlaceFire(pos.east());
            tryPlaceFire(pos.west());
            tryPlaceFire(pos.north());
            tryPlaceFire(pos.south());

            this.discard();
        }
    }

    private void tryPlaceFire(BlockPos pos) {
        if (this.level().isEmptyBlock(pos) && this.level().getBlockState(pos.below()).isSolidRender(this.level(), pos.below())) {
            this.level().setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
        } else if (this.level().isEmptyBlock(pos.above()) && this.level().getBlockState(pos).isSolidRender(this.level(), pos)) {
            this.level().setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 3);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        hitResult.getEntity().setSecondsOnFire(1);
    }

    @Override
    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    public static SubmunitionProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        SubmunitionProjectileEntity entityarrow = new SubmunitionProjectileEntity(ModEntities.SUBMUNITION_PROJECTILE.get(), entity, entity.level());
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
}
