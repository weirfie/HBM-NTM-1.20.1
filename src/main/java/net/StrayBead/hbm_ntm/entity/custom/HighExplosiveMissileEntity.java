package net.StrayBead.hbm_ntm.entity.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.Explosion;
import net.StrayBead.hbm_ntm.block.custom.TheGadgetBlock;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

public class HighExplosiveMissileEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(HighExplosiveMissileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> TARGET_POS = SynchedEntityData.defineId(HighExplosiveMissileEntity.class, EntityDataSerializers.BLOCK_POS);

    private double currentSpeed = 0.0D;
    private int flightTicks = 0;
    private Vec3 startPos = null;
    private double totalDistance = -1;
    private float targetXRot;
    private float targetYRot;

    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.URANIUM_DUST.get());

    public HighExplosiveMissileEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public HighExplosiveMissileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.INCENDIARY_MISSILE_ENTITY.get(), world);
    }

    public HighExplosiveMissileEntity(EntityType<? extends HighExplosiveMissileEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    protected float getWaterInertia() {
        return 0.7F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACTIVATED, false);
        this.entityData.define(TARGET_POS, BlockPos.ZERO);
    }

    public void setActivated(boolean activated) { this.entityData.set(ACTIVATED, activated); }
    public boolean isActivated() { return this.entityData.get(ACTIVATED); }
    public void setTargetPos(BlockPos pos) { this.entityData.set(TARGET_POS, pos); }

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
        if (isActivated()) {
            this.setNoGravity(true);

            handleMissileLogic();

            Vec3 move = this.getDeltaMovement();
            Vec3 nextPos = this.position().add(move);

            if (!this.level().isClientSide()) {
                HitResult hit = this.level().clip(new ClipContext(this.position(), nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

                if (hit.getType() != HitResult.Type.MISS) {
                    this.onHitBlock((BlockHitResult) hit);
                } else {
                    this.setPos(nextPos.x, nextPos.y, nextPos.z);
                }
                this.hasImpulse = true;
            } else {
                this.setPos(nextPos.x, nextPos.y, nextPos.z);
            }

            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
        } else {
            this.setDeltaMovement(Vec3.ZERO);
            float lockedX = -90.0F;

            this.setXRot(lockedX);
            super.tick();
            this.setXRot(lockedX);

            this.xRotO = lockedX;
            this.yRotO = this.getYRot();
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPos(x, y, z);
        this.setRot(yaw, pitch);
    }

    private void handleMissileLogic() {
        BlockPos targetPos = this.entityData.get(TARGET_POS);
        if (targetPos.equals(BlockPos.ZERO)) return;

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

            ParticleManager.addParticle((float) (this.getX() + offsetX), (float) (this.getY() + offsetY), (float) (this.getZ() + offsetZ), 1.2f, r, g, b, 1.0f, 0, 0, 0, 100, 0.04f);
        }

        Vec3 targetVec = new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
        if (startPos == null) {
            startPos = this.position();
            totalDistance = startPos.distanceTo(targetVec);
            this.setXRot(-90.0F);
            this.xRotO = -90.0F;
        }

        flightTicks++;

        double maxSpeed = 4.0D;
        if (currentSpeed < maxSpeed) {
            currentSpeed += 0.1D;
        }

        double distToTargetTotal = this.position().distanceTo(targetVec);
        if (distToTargetTotal < currentSpeed) {
            currentSpeed = Math.max(0.01D, distToTargetTotal);
        }

        double dx = targetVec.x - startPos.x;
        double dy = targetVec.y - startPos.y;
        double dz = targetVec.z - startPos.z;
        double totalHorizontal = Math.sqrt(dx * dx + dz * dz);

        double currentDx = targetVec.x - this.getX();
        double currentDz = targetVec.z - this.getZ();
        double distRemainingHorizontal = Math.sqrt(currentDx * currentDx + currentDz * currentDz);

        double progress = Mth.clamp(1.0 - (distRemainingHorizontal / totalHorizontal), 0, 1);
        double peakHeight = Math.min(120, totalHorizontal * 0.6);

        double tinyStep = 0.005;
        double nextP = Mth.clamp(progress + tinyStep, 0, 1);

        double currentArcY = 4 * peakHeight * progress * (1 - progress);
        double nextArcY = 4 * peakHeight * nextP * (1 - nextP);

        Vec3 currentPosOnCurve = new Vec3(startPos.x + (dx * progress), startPos.y + (dy * progress) + currentArcY, startPos.z + (dz * progress));
        Vec3 nextPosOnCurve = new Vec3(startPos.x + (dx * nextP), startPos.y + (dy * nextP) + nextArcY, startPos.z + (dz * nextP));

        Vec3 tangentDir = nextPosOnCurve.subtract(currentPosOnCurve).normalize();

        float goalYRot = (float) (Mth.atan2(tangentDir.x, tangentDir.z) * (180D / Math.PI));
        float goalXRot = (float) (Mth.atan2(-tangentDir.y, Math.sqrt(tangentDir.x * tangentDir.x + tangentDir.z * tangentDir.z)) * (180D / Math.PI));

        if (flightTicks < 10) {
            this.setXRot(-90.0F);
            this.setYRot(goalYRot);
        } else {
            this.setYRot(approachDegrees(this.getYRot(), goalYRot, 15.0F));
            this.setXRot(approachDegrees(this.getXRot(), goalXRot, 15.0F));
        }

        float fYaw = this.getYRot() * ((float)Math.PI / 180F);
        float fPit = this.getXRot() * ((float)Math.PI / 180F);

        this.setDeltaMovement(
                Mth.sin(fYaw) * Mth.cos(fPit) * currentSpeed,
                -Mth.sin(fPit) * currentSpeed,
                Mth.cos(fYaw) * Mth.cos(fPit) * currentSpeed
        );
    }

    private float approachDegrees(float current, float target, float maxStep) {
        float f = Mth.wrapDegrees(target - current);
        return current + Mth.clamp(f, -maxStep, maxStep);
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        if (!this.level().isClientSide()) {
            Explosion.create((ServerLevel)this.level(), this.getX(), this.getY(), this.getZ(), 5f, 500);
            this.discard();
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

    public static HighExplosiveMissileEntity shoot(LivingEntity entity, LivingEntity target) {
        HighExplosiveMissileEntity missile = new HighExplosiveMissileEntity(ModEntities.HIGH_EXPLOSIVE_MISSILE_ENTITY.get(), entity, entity.level());

        missile.setTargetPos(target.blockPosition());
        missile.setActivated(true);

        missile.moveTo(entity.getX(), entity.getY() + 1.5, entity.getZ(), entity.getYRot(), -90.0F);

        missile.setXRot(-90.0F);
        missile.xRotO = -90.0F;
        missile.setYRot(entity.getYRot());
        missile.yRotO = entity.getYRot();

        missile.setSilent(true);
        missile.setBaseDamage(5);
        missile.level().addFreshEntity(missile);

        return missile;
    }
}
