package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphiteModeratorButtonClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, HashMap guistate) {
        if (guistate == null)
            return;
        boolean found = false;
        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -100;
        found = false;
        for (int index0 = 0; index0 < 200; index0++) {
            sy = -100;
            for (int index1 = 0; index1 < 200; index1++) {
                sz = -100;
                for (int index2 = 0; index2 < 200; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.FUEL_ROD.get()) {
                        if (guistate.get("text:temperature") instanceof EditBox _tf)
                            _tf.setValue(("Temperature: " + (new Object() {
                                public int getEnergyStored(LevelAccessor level, BlockPos pos) {
                                    AtomicInteger _retval = new AtomicInteger(0);
                                    BlockEntity _ent = level.getBlockEntity(pos);
                                    if (_ent != null)
                                        _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> _retval.set(capability.getEnergyStored()));
                                    return _retval.get();
                                }
                            }.getEnergyStored(world, BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy), Math.floor(z + sz)))) + " / " + (new Object() {
                                public int getMaxEnergyStored(LevelAccessor level, BlockPos pos) {
                                    AtomicInteger _retval = new AtomicInteger(0);
                                    BlockEntity _ent = level.getBlockEntity(pos);
                                    if (_ent != null)
                                        _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> _retval.set(capability.getMaxEnergyStored()));
                                    return _retval.get();
                                }
                            }.getMaxEnergyStored(world, BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy), Math.floor(z + sz))))));
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
    }
}
