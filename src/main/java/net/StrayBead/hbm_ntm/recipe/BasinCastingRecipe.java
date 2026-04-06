package net.StrayBead.hbm_ntm.recipe;

import com.google.gson.JsonObject;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class BasinCastingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final FluidStack fluidIngredient;
    private final Ingredient moldIngredient;
    private final ItemStack output;

    public BasinCastingRecipe(ResourceLocation id, FluidStack fluidIngredient, Ingredient moldIngredient, ItemStack output) {
        this.id = id;
        this.fluidIngredient = fluidIngredient;
        this.moldIngredient = moldIngredient;
        this.output = output;
    }

    public boolean matchesRequirements(FluidStack tankFluid, ItemStack mold) {
        return tankFluid.getFluid().isSame(this.fluidIngredient.getFluid()) &&
                this.moldIngredient.test(mold);
    }

    public FluidStack getFluidIngredient() { return fluidIngredient; }
    public Ingredient getMoldIngredient() { return moldIngredient; }
    public int getFluidAmount() { return fluidIngredient.getAmount(); }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(moldIngredient);
        return list;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BasinCastingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "basin_casting";
    }

    public static class Serializer implements RecipeSerializer<BasinCastingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(HBMNTM.MOD_ID, "basin_casting");

        @Override
        public BasinCastingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonObject fluidJson = GsonHelper.getAsJsonObject(pSerializedRecipe, "fluid");
            ResourceLocation fluidRes = new ResourceLocation(GsonHelper.getAsString(fluidJson, "fluid"));
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidRes);
            int amount = GsonHelper.getAsInt(fluidJson, "amount");
            FluidStack fluidStack = new FluidStack(fluid, amount);

            Ingredient mold = Ingredient.fromJson(pSerializedRecipe.get("mold"));

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            return new BasinCastingRecipe(pRecipeId, fluidStack, mold, output);
        }

        @Override
        public @Nullable BasinCastingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            FluidStack fluidStack = pBuffer.readFluidStack();
            Ingredient mold = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            return new BasinCastingRecipe(pRecipeId, fluidStack, mold, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BasinCastingRecipe pRecipe) {
            pBuffer.writeFluidStack(pRecipe.fluidIngredient);
            pRecipe.moldIngredient.toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.output, false);
        }
    }
}
