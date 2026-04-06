package net.StrayBead.hbm_ntm.recipe;

import com.google.gson.JsonArray;
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

public class BurnerPressRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient stamp;
    private final NonNullList<Ingredient> recipeItems;

    public BurnerPressRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, Ingredient stamp) {
        this.id = id;
        this.output = output;
        this.stamp = stamp;
        this.recipeItems = recipeItems;
    }

    public Ingredient getStamp() { return stamp; }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return stamp.test(container.getItem(0)) && recipeItems.get(0).test(container.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
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

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public static class Type implements RecipeType<BurnerPressRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "burner_press";
    }

    public static class Serializer implements RecipeSerializer<BurnerPressRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(HBMNTM.MOD_ID, "burner_press");

        @Override
        public BurnerPressRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            Ingredient stamp = Ingredient.fromJson(json.get("stamp"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            inputs.set(0, Ingredient.fromJson(ingredients.get(0)));

            return new BurnerPressRecipe(pRecipeId, output, inputs, stamp);
        }

        @Override
        public @Nullable BurnerPressRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient stamp = Ingredient.fromNetwork(buf);

            int size = buf.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();

            return new BurnerPressRecipe(id, output, inputs, stamp);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, BurnerPressRecipe recipe) {
            recipe.stamp.toNetwork(buf);

            buf.writeInt(recipe.recipeItems.size());
            for (Ingredient ing : recipe.recipeItems) {
                ing.toNetwork(buf);
            }

            buf.writeItemStack(recipe.output, false);
        }
    }
}
