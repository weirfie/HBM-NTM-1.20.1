package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.PWRControllerBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.PWRFuelRodBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class PWRControllerLinkingDeviceItem extends Item {
    public PWRControllerLinkingDeviceItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        net.minecraft.world.level.Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        net.minecraft.world.entity.player.Player player = context.getPlayer();

        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (player != null) player.displayClientMessage(net.minecraft.network.chat.Component.literal("§eClicked: " + state.getBlock().getName().getString()), true);

        if (state.is(ModBlocks.PWR_CONTROLLER.get())) {

            boolean isLinked = state.getValue(PWRControllerBlock.HAS_BEEN_LINKED);
            if (player != null) player.displayClientMessage(net.minecraft.network.chat.Component.literal("§bLinked State: " + isLinked), false);

            level.setBlock(pos, state.setValue(PWRControllerBlock.HAS_BEEN_LINKED, true), 3);

            int numberOfHeatsinks = 0;
            int fuelRodsFound = 0;
            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();

            for (int sx = -10; sx <= 10; sx++) {
                for (int sy = -10; sy <= 10; sy++) {
                    for (int sz = -10; sz <= 10; sz++) {
                        mutPos.set(pos.getX() + sx, pos.getY() + sy, pos.getZ() + sz);
                        if (level.getBlockState(mutPos).is(ModBlocks.PWR_HEATSINK.get())) {
                            numberOfHeatsinks++;
                        }
                    }
                }
            }

            double multiplier = 1.0 + (0.05 * numberOfHeatsinks);

            for (int sx = -10; sx <= 10; sx++) {
                for (int sy = -10; sy <= 10; sy++) {
                    for (int sz = -10; sz <= 10; sz++) {
                        mutPos.set(pos.getX() + sx, pos.getY() + sy, pos.getZ() + sz);
                        if (level.getBlockState(mutPos).is(ModBlocks.PWR_FUEL_ROD.get())) {
                            BlockEntity be = level.getBlockEntity(mutPos);
                            if (be instanceof PWRFuelRodBlockEntity rod) {
                                fuelRodsFound++;
                                rod.setMaxCapacity((int)(400000 * multiplier));
                            }
                        }
                    }
                }
            }

            for (int sx = -10; sx <= 10; sx++) {
                for (int sy = -10; sy <= 10; sy++) {
                    for (int sz = -10; sz <= 10; sz++) {
                        mutPos.set(pos.getX() + sx, pos.getY() + sy, pos.getZ() + sz);
                        if (level.getBlockState(mutPos).is(ModBlocks.PWR_NEUTRON_SOURCE.get())) {
                            {
                                BlockPos _pos = mutPos;
                                BlockState _bs = level.getBlockState(_pos);
                                if (_bs.getBlock().getStateDefinition().getProperty("is_activated") instanceof BooleanProperty _booleanProp)
                                    level.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
                            }
                        }
                    }
                }
            }

            if (player != null) {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal(
                        "§aSuccess! Found " + numberOfHeatsinks + " heatsinks and " + fuelRodsFound + " rods."
                ), false);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
