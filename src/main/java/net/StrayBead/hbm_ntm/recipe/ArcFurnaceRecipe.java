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
import org.jetbrains.annotations.Nullable;

public class ArcFurnaceRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack output;

    public ArcFurnaceRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.input);
        return list;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return input.test(container.getItem(0));
    }

    public Ingredient getInput() { return input; }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) { return output.copy(); }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }

    @Override
    public ItemStack getResultItem(RegistryAccess access) { return output.copy(); }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public RecipeSerializer<?> getSerializer() { return ModRecipes.ARC_FURNACE_SERIALIZER.get(); }

    @Override
    public RecipeType<?> getType() { return ModRecipes.ARC_FURNACE_TYPE.get(); }

    public static class Serializer implements RecipeSerializer<ArcFurnaceRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ArcFurnaceRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new ArcFurnaceRecipe(id, input, output);
        }

        @Override
        public @Nullable ArcFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            return new ArcFurnaceRecipe(id, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ArcFurnaceRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeItemStack(recipe.output, false);
        }
    }
}