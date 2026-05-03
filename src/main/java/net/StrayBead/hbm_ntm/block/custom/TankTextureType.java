package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.util.StringRepresentable;

public enum TankTextureType implements StringRepresentable {
    NONE("none"),
    ALUMINA("alumina"),
    AROMATICS("aromatics"),
    CARBON_DIOXIDE("carbon_dioxide"),
    DIESEL("diesel"),
    DEUTERIUM("deuterium"),
    LIQUID_OXYGEN("liquid_oxygen"),
    REFORMATE_GAS("reformate_gas"),
    SYNGAS("syngas"),
    WATER("water"),
    PETROLEUM_GAS("petroleum_gas"),
    LIQUID_HYDROGEN("liquid_hydrogen"),
    CRUDE_OIL("crude_oil"),
    HEAVY_OIL("heavy_oil"),
    LIGHT_OIL("light_oil"),
    SULFURIC_ACID("sulfuric_acid");

    private final String name;

    TankTextureType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getTextureName() {
        return "tank_" + this.name();
    }
}
