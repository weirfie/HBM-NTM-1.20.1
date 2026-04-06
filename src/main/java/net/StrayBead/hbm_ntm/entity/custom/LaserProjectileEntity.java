package net.StrayBead.hbm_ntm.entity.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.ControlRodTopBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.ControlRodTopBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.rbmk.ControlRodBlock;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

public class LaserProjectileEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.URANIUM_DUST.get());

    public LaserProjectileEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public LaserProjectileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.NEUTRON_PROJECTILE.get(), world);
    }

    public LaserProjectileEntity(EntityType<? extends LaserProjectileEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
        isNoGravity();
        this.setPierceLevel((byte) 127);
    }

    @Override
    public boolean isNoGravity() {
        return true;
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
        Level world = this.level();

        if (world instanceof ServerLevel _level)
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(this.getX(), this.getY(), this.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                    "particle minecraft:end_rod ~ ~ ~ 0.1 0.1 0.1 0 1 force @a");
        if (!this.inGround) {
            Vec3 movement = this.getDeltaMovement();
        }
        tickCounter++;
        waitTickCounter++;
        if (waitTickCounter > 60) {
            this.discard();
        }
        if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY() - 1, this.getZ()))).getBlock() == ModBlocks.SILEX.get()) {
            {
                BlockEntity _ent = world.getBlockEntity(BlockPos.containing(this.getX(), this.getY() - 1, this.getZ()));
                int _amount = 60;
                if (_ent != null)
                    _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> capability.receiveEnergy(_amount, false));
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        BlockPos hitPos = hitResult.getBlockPos();
        BlockState hitState = this.level().getBlockState(hitPos);

        if (hitState.getExplosionResistance(this.level(), hitPos, null) < 400.0F) {
            if (!this.level().isClientSide()) {
                BlockPos firePos = hitPos.relative(hitResult.getDirection());

                if (this.level().isEmptyBlock(firePos)) {
                    this.level().setBlockAndUpdate(firePos, Blocks.FIRE.defaultBlockState());
                }
            }
        } else {
            this.discard();
        }

        super.onHitBlock(hitResult);
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

    public static LaserProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        LaserProjectileEntity entityarrow = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE.get(), entity, entity.level());
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
