package net.StrayBead.hbm_ntm.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.Properties;

public class GenericFluid {

    public static class Source extends ForgeFlowingFluid.Source {
        public Source(Properties properties) {
            super(properties);
        }
    }

    public static class Flowing extends ForgeFlowingFluid.Flowing {
        public Flowing(Properties properties) {
            super(properties);
        }
    }
}
