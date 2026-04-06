package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.HashMap;

public class SaveButtonClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, HashMap guistate) {
        if (guistate == null)
            return;
        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -50;
        for (int index0 = 0; index0 < 100; index0++) {
            sy = -50;
            for (int index1 = 0; index1 < 100; index1++) {
                sz = -50;
                for (int index2 = 0; index2 < 100; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.PWR_CONTROL_ROD.get()) {
                        {
                            int _value = (int) new Object() {
                                double convert(String s) {
                                    try {
                                        return Double.parseDouble(s.trim());
                                    } catch (Exception e) {
                                    }
                                    return 0;
                                }
                            }.convert(guistate.containsKey("text:control_rod_level") ? ((EditBox) guistate.get("text:control_rod_level")).getValue() : "");
                            BlockPos _pos = BlockPos.containing(x + sx, y + sy, z + sz);
                            BlockState _bs = world.getBlockState(_pos);
                            if (_bs.getBlock().getStateDefinition().getProperty("extraction_amount") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                                world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
    }
}
