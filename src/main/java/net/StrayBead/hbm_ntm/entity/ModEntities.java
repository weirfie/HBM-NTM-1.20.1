package net.StrayBead.hbm_ntm.entity;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.custom.entity.FlyingTerrainEntity;
import net.StrayBead.hbm_ntm.entity.custom.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HBMNTM.MOD_ID);

    public static final RegistryObject<EntityType<NeutronProjectileEntity>> NEUTRON_PROJECTILE =
            ENTITY_TYPES.register("neutron_projectile", () -> EntityType.Builder.<NeutronProjectileEntity>of(NeutronProjectileEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(0.5f, 0.5f).build("neutron_projectile"));

    public static final RegistryObject<EntityType<SubmunitionProjectileEntity>> SUBMUNITION_PROJECTILE =
            ENTITY_TYPES.register("submunition_projectile", () -> EntityType.Builder.<SubmunitionProjectileEntity>of(SubmunitionProjectileEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(0.5f, 0.5f).build("submunition_projectile"));

    public static final RegistryObject<EntityType<FlyingTerrainEntity>> FLYING_TERRAIN =
            ENTITY_TYPES.register("flying_terrain",
                    () -> EntityType.Builder.<FlyingTerrainEntity>of(FlyingTerrainEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .clientTrackingRange(10)
                            .updateInterval(1)
                            .setShouldReceiveVelocityUpdates(true)
                            .build("flying_terrain"));

    public static final RegistryObject<EntityType<FatManNukeDropProjectileEntity>> FAT_MAN_NUKE_DROP_ENTITY = register("fat_man_nuke_drop_entity",
            EntityType.Builder.<FatManNukeDropProjectileEntity>of(FatManNukeDropProjectileEntity::new, MobCategory.MISC).setCustomClientFactory(FatManNukeDropProjectileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<HighExplosiveMissileEntity>> HIGH_EXPLOSIVE_MISSILE_ENTITY = register("high_explosive_missile",
            EntityType.Builder.<HighExplosiveMissileEntity>of(HighExplosiveMissileEntity::new, MobCategory.MISC).setCustomClientFactory(HighExplosiveMissileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<IncendiaryMissileEntity>> INCENDIARY_MISSILE_ENTITY = register("incendiary_missile",
            EntityType.Builder.<IncendiaryMissileEntity>of(IncendiaryMissileEntity::new, MobCategory.MISC).setCustomClientFactory(IncendiaryMissileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<NuclearMissileEntity>> NUCLEAR_MISSILE_ENTITY = register("nuclear_missile",
            EntityType.Builder.<NuclearMissileEntity>of(NuclearMissileEntity::new, MobCategory.MISC).setCustomClientFactory(NuclearMissileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<AntiBallisticMissileEntity>> ANTI_BALLISTIC_MISSILE_ENTITY = register("anti_ballistic_missile",
            EntityType.Builder.<AntiBallisticMissileEntity>of(AntiBallisticMissileEntity::new, MobCategory.MISC).setCustomClientFactory(AntiBallisticMissileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITY_TYPES.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
    }

    public static final RegistryObject<EntityType<LaserProjectileEntity>> LASER_PROJECTILE =
            ENTITY_TYPES.register("laser_projectile", () -> EntityType.Builder.<LaserProjectileEntity>of(LaserProjectileEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(0.5f, 0.5f).build("laser_projectile"));

    public static final RegistryObject<EntityType<ShockwaveProjectileEntity>> SHOCKWAVE_PROJECTILE =
            ENTITY_TYPES.register("shockwave_projectile", () -> EntityType.Builder.<ShockwaveProjectileEntity>of(ShockwaveProjectileEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(0.5f, 0.5f).build("shockwave_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
