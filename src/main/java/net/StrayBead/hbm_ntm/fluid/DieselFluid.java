package net.StrayBead.hbm_ntm.fluid;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class DieselFluid extends ForgeFlowingFluid {
    public static final Properties PROPERTIES = new Properties(() -> ModFluidTypes.DIESEL_TYPE.get(), () -> ModFluids.DIESEL.get(), () -> ModFluids.FLOWING_DIESEL.get())
            .explosionResistance(100f).tickRate(1).slopeFindDistance(1).block(() -> (LiquidBlock) ModBlocks.DIESEL.get());

    private DieselFluid() {
        super(PROPERTIES);
    }

    public static class Source extends DieselFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends DieselFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }
}
