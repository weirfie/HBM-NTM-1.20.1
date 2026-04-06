package net.StrayBead.hbm_ntm.fluid;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class IndustrialOilFluid extends ForgeFlowingFluid {
    public static final Properties PROPERTIES = new Properties(() -> ModFluidTypes.INDUSTRIAL_OIL_TYPE.get(), () -> ModFluids.INDUSTRIAL_OIL.get(), () -> ModFluids.FLOWING_INDUSTRIAL_OIL.get())
            .explosionResistance(100f).tickRate(1).slopeFindDistance(1).block(() -> (LiquidBlock) ModBlocks.INDUSTRIAL_OIL.get());

    private IndustrialOilFluid() {
        super(PROPERTIES);
    }

    public static class Source extends IndustrialOilFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends IndustrialOilFluid {
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
