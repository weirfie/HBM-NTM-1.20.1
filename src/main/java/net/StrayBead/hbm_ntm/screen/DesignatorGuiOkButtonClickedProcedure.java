package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.network.NTMModVariables;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.world.level.LevelAccessor;

import java.util.HashMap;

public class DesignatorGuiOkButtonClickedProcedure {
    public static void execute(LevelAccessor world, HashMap guistate) {
        if (guistate == null)
            return;
        NTMModVariables.WorldVariables.get(world).designator_power = new Object() {
            double convert(String s) {
                try {
                    return Double.parseDouble(s.trim());
                } catch (Exception e) {
                }
                return 0;
            }
        }.convert(guistate.containsKey("text:power") ? ((EditBox) guistate.get("text:power")).getValue() : "");
        NTMModVariables.WorldVariables.get(world).syncData(world);
    }
}
