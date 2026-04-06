package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.render.custom.FolkvangrFieldRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class AntiSchrabidiumCellItem extends Item {
    public AntiSchrabidiumCellItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();

        if (entity.onGround()) {
            if (!level.isClientSide) {
                double radius = 20.0;
                BlockPos center = entity.blockPosition();

                for (int x = (int) -radius; x <= radius; x++) {
                    for (int y = (int) -radius; y <= radius; y++) {
                        for (int z = (int) -radius; z <= radius; z++) {
                            if (x * x + y * y + z * z <= radius * radius) {
                                level.setBlock(center.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
                            }
                        }
                    }
                }

                {
                    final Vec3 _center = new Vec3(entity.blockPosition().getX(), entity.blockPosition().getY(), entity.blockPosition().getZ());
                    List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        entityiterator.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.EXPLOSION)), 80);
                    }
                }
            } else {
                FolkvangrFieldRenderer.addField(entity.position(), 20.0f, 60);
            }

            entity.discard();
        }
        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Warning: Exposure to matter will create a folkvangr field!").withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("[Dangerous Drop]").withStyle(ChatFormatting.RED));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
