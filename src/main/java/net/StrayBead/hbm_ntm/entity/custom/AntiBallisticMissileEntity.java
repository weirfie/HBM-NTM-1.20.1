package net.StrayBead.hbm_ntm.entity.custom;

import net.StrayBead.hbm_ntm.block.custom.Explosion;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.PlayMessages;

import java.util.List;

public class AntiBallisticMissileEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(AntiBallisticMissileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> TARGET_POS = SynchedEntityData.defineId(AntiBallisticMissileEntity.class, EntityDataSerializers.BLOCK_POS);

    private double currentSpeed = 0.0D;
    private int flightTicks = 0;
    private Vec3 startPos = null;
    private double totalDistance = -1;
    private float targetXRot;
    private Entity interceptTarget = null;
    private float targetYRot;

    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.URANIUM_DUST.get());

    public AntiBallisticMissileEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public AntiBallisticMissileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.ANTI_BALLISTIC_MISSILE_ENTITY.get(), world);
    }

    public AntiBallisticMissileEntity(EntityType<? extends AntiBallisticMissileEntity> type, LivingEntity entity, Level world) {
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
        if (this.level().isClientSide()) {
            spawnClientParticles();
            return;
        }

        if (interceptTarget == null || !interceptTarget.isAlive()) {
            AABB searchArea = this.getBoundingBox().inflate(400.0D);
            List<Entity> enemies = this.level().getEntities(this, searchArea, e ->
                    (e instanceof HighExplosiveMissileEntity || e instanceof IncendiaryMissileEntity) && e.isAlive()
            );

            if (!enemies.isEmpty()) {
                Entity bestTarget = null;
                int lowestAttackerCount = Integer.MAX_VALUE;

                for (Entity enemy : enemies) {
                    int attackers = countABMsTargeting(enemy);

                    if (attackers == 0) {
                        bestTarget = enemy;
                        break;
                    }

                    if (attackers < lowestAttackerCount) {
                        lowestAttackerCount = attackers;
                        bestTarget = enemy;
                    }
                }

                this.interceptTarget = bestTarget;
            }
        }

        Vec3 targetVec;
        boolean isIntercepting = false;

        if (interceptTarget != null && interceptTarget.isAlive()) {
            targetVec = interceptTarget.position();
            isIntercepting = true;

            if (this.position().distanceTo(targetVec) < 4.0D) {
                interceptTarget.discard();
                this.detonate();
                return;
            }
        } else {
            BlockPos targetPos = this.entityData.get(TARGET_POS);
            if (targetPos.equals(BlockPos.ZERO)) return;
            targetVec = new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
        }

        if (startPos == null) {
            startPos = this.position();
            totalDistance = startPos.distanceTo(targetVec);
        }

        flightTicks++;

        double maxSpeed = isIntercepting ? 5.0D : 4.0D;
        if (currentSpeed < maxSpeed) currentSpeed += 0.2D;

        Vec3 tangentDir;

        if (isIntercepting) {
            tangentDir = targetVec.subtract(this.position()).normalize();
        } else {
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
            tangentDir = nextPosOnCurve.subtract(currentPosOnCurve).normalize();
        }

        float goalYRot = (float) (Mth.atan2(tangentDir.x, tangentDir.z) * (180D / Math.PI));
        float goalXRot = (float) (Mth.atan2(-tangentDir.y, Math.sqrt(tangentDir.x * tangentDir.x + tangentDir.z * tangentDir.z)) * (180D / Math.PI));

        this.setYRot(approachDegrees(this.getYRot(), goalYRot, 15.0F));
        this.setXRot(approachDegrees(this.getXRot(), goalXRot, 15.0F));

        float fYaw = this.getYRot() * ((float)Math.PI / 180F);
        float fPit = this.getXRot() * ((float)Math.PI / 180F);

        this.setDeltaMovement(
                Mth.sin(fYaw) * Mth.cos(fPit) * currentSpeed,
                -Mth.sin(fPit) * currentSpeed,
                Mth.cos(fYaw) * Mth.cos(fPit) * currentSpeed
        );
    }

    private int countABMsTargeting(Entity target) {
        List<AntiBallisticMissileEntity> activeABMs = this.level().getEntitiesOfClass(
                AntiBallisticMissileEntity.class,
                target.getBoundingBox().inflate(500.0D),
                abm -> abm != this && abm.interceptTarget == target
        );
        return activeABMs.size();
    }

    private void detonate() {
        if (!this.level().isClientSide()) {
            Explosion.create((ServerLevel)this.level(), this.getX(), this.getY(), this.getZ(), 4f, 500);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        this.detonate();
    }

    private void spawnClientParticles() {
        double offsetX = random.nextGaussian() * 0.4;
        double offsetY = random.nextGaussian() * 0.4;
        double offsetZ = random.nextGaussian() * 0.4;
        float baseR = 100f / 255f;
        float baseG = 200f / 255f;
        float baseB = 255f / 255f;
        float brightness = 0.8f + (random.nextFloat() * 0.3f);
        ParticleManager.addParticle((float) (this.getX() + offsetX), (float) (this.getY() + offsetY), (float) (this.getZ() + offsetZ), 1.2f, baseR * brightness, baseG * brightness, baseB * brightness, 1.0f, 0, 0, 0, 100, 0.04f);
    }

    private float approachDegrees(float current, float target, float maxStep) {
        float f = Mth.wrapDegrees(target - current);
        return current + Mth.clamp(f, -maxStep, maxStep);
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

    public static AntiBallisticMissileEntity shoot(LivingEntity entity, LivingEntity target) {
        AntiBallisticMissileEntity missile = new AntiBallisticMissileEntity(ModEntities.ANTI_BALLISTIC_MISSILE_ENTITY.get(), entity, entity.level());

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
