package net.StrayBead.hbm_ntm.fluid;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class OilFluid extends ForgeFlowingFluid {
    public static final Properties PROPERTIES = new Properties(() -> ModFluidTypes.OIL_TYPE.get(), () -> ModFluids.CRUDE_OIL.get(), () -> ModFluids.FLOWING_OIL.get())
            .explosionResistance(100f).tickRate(5).slopeFindDistance(1).bucket(() -> ModItems.OIL_BUCKET.get()).block(() -> (LiquidBlock) ModBlocks.OIL.get());

    private OilFluid() {
        super(PROPERTIES);
    }

    public static class Source extends OilFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends OilFluid {
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
