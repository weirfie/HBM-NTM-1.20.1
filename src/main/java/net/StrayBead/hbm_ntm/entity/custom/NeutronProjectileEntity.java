package net.StrayBead.hbm_ntm.entity.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.ControlRodTopBlock;
import net.StrayBead.hbm_ntm.block.custom.rbmk.ControlRodBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.ControlRodTopBlockEntity;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

public class NeutronProjectileEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.URANIUM_DUST.get());
    public static final EntityDataAccessor<Boolean> DATA_isFastNeutron = SynchedEntityData.defineId(NeutronProjectileEntity.class, EntityDataSerializers.BOOLEAN);

    public NeutronProjectileEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public NeutronProjectileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.NEUTRON_PROJECTILE.get(), world);
    }

    public NeutronProjectileEntity(EntityType<? extends NeutronProjectileEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
        isNoGravity();
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
        this.entityData.define(DATA_isFastNeutron, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("DataisFastNeutron", this.entityData.get(DATA_isFastNeutron));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("DataisFastNeutron"))
            this.entityData.set(DATA_isFastNeutron, compound.getBoolean("DataisFastNeutron"));
    }

    private int tickCounter = 0;
    private int waitTickCounter = 0;

    @Override
    public void tick() {
        super.tick();
        Level world = this.level();
        if (!this.inGround) {
            Vec3 movement = this.getDeltaMovement();
        }
        tickCounter++;
        waitTickCounter++;
        if (waitTickCounter > 300) {
            this.discard();
        }        if(this.inGround) {
            if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.NEUTRON_ABSORBER.get()) {
                this.discard();
                this.discard();
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.GRAPHITE_MODERATOR.get()) {
                if (this.entityData.get(DATA_isFastNeutron)) {
                    this.entityData.set(DATA_isFastNeutron, false);
                    Vec3 currentVelocity = this.getDeltaMovement();
                    double speedReductionFactor = -0.8;
                    Vec3 newVelocity = currentVelocity.scale(-0.2);
                    this.setDeltaMovement(newVelocity);

                    this.inGround = false;
                    this.setNoGravity(true);

                    this.setDeltaMovement(newVelocity);

                    this.setPos(this.getX() + newVelocity.x, this.getY() + newVelocity.y, this.getZ() + newVelocity.z);
                }
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.NEUTRON_REFLECTOR.get() || (world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.PWR_NEUTRON_REFLECTOR.get()) {
                Vec3 currentVelocity = this.getDeltaMovement();
                Vec3 reversedVelocity = currentVelocity.scale(-1);
                this.setDeltaMovement(reversedVelocity);
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.FUEL_ROD.get()) {
                {
                    BlockEntity _ent = world.getBlockEntity(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
                    int _amount = 200;
                    if (_ent != null)
                        _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> capability.receiveEnergy(_amount, false));
                }
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.PWR_FUEL_ROD.get()) {
                {
                    BlockEntity _ent = world.getBlockEntity(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
                    int _amount = 100;
                    if (_ent != null)
                        _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> capability.receiveEnergy(_amount, false));
                }
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.PWR_NEUTRON_SOURCE.get()) {
                {
                    BlockEntity _ent = world.getBlockEntity(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
                    int _amount = 100;
                    if (_ent != null)
                        _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> capability.receiveEnergy(_amount, false));
                }
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.STEAM_CHANNEL.get()) {
                BlockPos hitPos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
                BlockEntity _ent = world.getBlockEntity(hitPos);

                if (_ent != null) {
                    _ent.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(handler -> {
                        FluidStack waterInTank = handler.getFluidInTank(0);
                        int conversionAmount = 20;

                        if (waterInTank.getFluid().isSame(Fluids.WATER) && waterInTank.getAmount() >= conversionAmount) {

                            handler.drain(new FluidStack(Fluids.WATER, conversionAmount), IFluidHandler.FluidAction.EXECUTE);

                            handler.fill(new FluidStack(ModFluids.STEAM.get(), conversionAmount), IFluidHandler.FluidAction.EXECUTE);

                            if (world instanceof ServerLevel _level) {
                                _level.sendParticles(ParticleTypes.CLOUD, hitPos.getX() + 0.5, hitPos.getY() + 0.5, hitPos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.05);
                            }
                        }
                    });
                }
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.CONTROL_ROD.get()) {
                BlockPos hitPos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
                BlockEntity _ent = world.getBlockEntity(hitPos);

                double efficiency = 1.0;
                int maxHeight = 74;

                for (int i = 0; i < maxHeight; i++) {
                    BlockPos checkPos = BlockPos.containing(this.getX(), this.getY() + i, this.getZ());
                    BlockEntity be = world.getBlockEntity(checkPos);

                    if (be instanceof ControlRodTopBlockEntity top) {
                        float currentTick = top.animationTick;
                        float maxTick = 40.0f;
                        efficiency = Math.max(0, (maxTick - currentTick) / maxTick);
                        break;
                    }

                    if (!(world.getBlockState(checkPos).getBlock() instanceof ControlRodBlock)) {
                        if (!(world.getBlockState(checkPos).getBlock() instanceof ControlRodTopBlock)) break;
                    }
                }

                int baseAmount = 100;
                int finalAmount = (int) (baseAmount * efficiency);

                if (finalAmount <= 0) return;

                int maxThreshold = 15000;
                int absorptionThreshold = (int) (maxThreshold * efficiency);

                if (Mth.nextInt(RandomSource.create(), 1, maxThreshold) <= absorptionThreshold) {
                    this.discard();
                }
            } else if ((world.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ()))).getBlock() == ModBlocks.PWR_CONTROL_ROD.get()) {
                BlockPos hitPos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
                BlockState blockstate = world.getBlockState(hitPos);

                int extraction = blockstate.getBlock().getStateDefinition().getProperty("extraction_amount") instanceof IntegerProperty _getip1
                        ? blockstate.getValue(_getip1) : 0;

                float maxExtraction = 20.0f;

                float absorptionChance = 1.0f - (extraction / maxExtraction);

                if (this.random.nextFloat() < absorptionChance) {
                    this.discard();
                } else {
                    this.inGround = false;
                }
            }
            this.noPhysics = true;
        } else {
            this.noPhysics = false;
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
    }

    @Override
    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    public static NeutronProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        NeutronProjectileEntity entityarrow = new NeutronProjectileEntity(ModEntities.NEUTRON_PROJECTILE.get(), entity, entity.level());
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
