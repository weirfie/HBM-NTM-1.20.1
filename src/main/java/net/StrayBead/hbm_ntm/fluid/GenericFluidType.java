package net.StrayBead.hbm_ntm.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

import java.util.Properties;
import java.util.function.Consumer;

public class GenericFluidType extends FluidType {
    private final ResourceLocation texture;
    private final int defaultPressure;

    public GenericFluidType(Properties properties, String name, int pressure) {
        super(properties);
        this.texture = new ResourceLocation("hbm_ntm", "block/" + name);
        this.defaultPressure = pressure;
    }

    public int getDefaultPressure() {
        return defaultPressure;
    }

    public static FluidStack getPressurizedStack(Fluid fluid, int amount, int pressure) {
        FluidStack stack = new FluidStack(fluid, amount);
        stack.getOrCreateTag().putInt("Pressure", pressure);
        return stack;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() { return texture; }
            @Override
            public ResourceLocation getFlowingTexture() { return texture; }
        });
    }
}
