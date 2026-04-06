package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class SimCardItem extends Item {
    public SimCardItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.literal("Free calls"));
        list.add(Component.literal("Free internet"));
        list.add(Component.literal("For everyone"));
        list.add(Component.literal("FOREVER"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        LevelAccessor level = context.getLevel();
        double x = context.getClickedPos().getX();
        double y = context.getClickedPos().getY();
        double z = context.getClickedPos().getZ();
        {
            final Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(ModMobEffects.HAS_SIM_CARD.get(), 1000000, 1, false, false));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        entity.addEffect(new MobEffectInstance(ModMobEffects.HAS_SIM_CARD.get(), 1000000, 1, false, false));
        return retval;
    }
}
