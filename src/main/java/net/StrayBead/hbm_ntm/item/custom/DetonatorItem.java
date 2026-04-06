package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.client.ClientExplosionEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Objects;

public class DetonatorItem extends Item {
    public DetonatorItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        LevelAccessor world = context.getLevel();
        double x = context.getClickedPos().getX();
        double y = context.getClickedPos().getY();
        double z = context.getClickedPos().getZ();
        Objects.requireNonNull(context.getPlayer()).sendSystemMessage(Component.literal("Detonated linked!"));
        {
            BlockPos _pos = BlockPos.containing(x, y, z);
            BlockState _bs = world.getBlockState(_pos);
            if (_bs.getBlock().getStateDefinition().getProperty("has_detonation_set") instanceof BooleanProperty _booleanProp)
                world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ClientExplosionEffects.explosionCenter = entity.blockPosition().immutable();
        ClientExplosionEffects.effectStartTime = world.getGameTime();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -30;
        for (int index0 = 0; index0 < 60; index0++) {
            sy = -30;
            for (int index1 = 0; index1 < 60; index1++) {
                sz = -30;
                for (int index2 = 0; index2 < 60; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.DET_CORD.get()) {
                        if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock().getStateDefinition().getProperty("has_detonation_set") instanceof BooleanProperty _getbp3
                                && (world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getValue(_getbp3)) {
                            world.explode(null, Math.floor(x + sx), Math.floor(y + sy), Math.floor(z + sz), 6, Level.ExplosionInteraction.BLOCK);
                        } else {
                            entity.sendSystemMessage(Component.literal("Detonated has not been linked!"));
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
        return ar;
    }
}
