package net.StrayBead.hbm_ntm.screen;

import net.minecraft.client.gui.components.Checkbox;

import java.util.HashMap;

public class GetCheckboxProcedure {
    public static boolean execute(HashMap guistate) {
        if (guistate == null)
            return false;
        if (guistate.containsKey("checkbox:Extract") && ((Checkbox) guistate.get("checkbox:Extract")).selected()) {
            return true;
        }
        return false;
    }
}
