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

import javax.annotation.Nullable;

public class ArcWelderRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<Integer> counts;

    public ArcWelderRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, NonNullList<Integer> counts) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.counts = counts;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        for (int i = 0; i < recipeItems.size(); i++) {
            ItemStack stackInSlot = pContainer.getItem(i);
            Ingredient ingredient = recipeItems.get(i);
            int requiredCount = counts.get(i);

            if (ingredient.isEmpty()) continue;

            if (!ingredient.test(stackInSlot) || stackInSlot.getCount() < requiredCount) {
                return false;
            }
        }
        return true;
    }

    public NonNullList<Integer> getCounts() { return counts; }

    @Override
    public NonNullList<Ingredient> getIngredients() { return recipeItems; }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) { return output.copy(); }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) { return true; }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) { return output.copy(); }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public RecipeSerializer<?> getSerializer() { return Serializer.INSTANCE; }

    @Override
    public RecipeType<?> getType() { return Type.INSTANCE; }

    public static class Type implements RecipeType<ArcWelderRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "welding";
    }

    public static class Serializer implements RecipeSerializer<ArcWelderRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(HBMNTM.MOD_ID, "welding");

        @Override
        public ArcWelderRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            NonNullList<Integer> counts = NonNullList.withSize(ingredients.size(), 1);

            for (int i = 0; i < ingredients.size(); i++) {
                JsonObject obj = ingredients.get(i).getAsJsonObject();

                inputs.set(i, Ingredient.fromJson(obj));

                counts.set(i, GsonHelper.getAsInt(obj, "count", 1));
            }

            return new ArcWelderRecipe(pRecipeId, output, inputs, counts);
        }

        @Override
        public @Nullable ArcWelderRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            NonNullList<Integer> counts = NonNullList.withSize(inputs.size(), 1);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
                counts.set(i, pBuffer.readInt());
            }

            ItemStack output = pBuffer.readItem();
            return new ArcWelderRecipe(pRecipeId, output, inputs, counts);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ArcWelderRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (int i = 0; i < pRecipe.getIngredients().size(); i++) {
                pRecipe.getIngredients().get(i).toNetwork(pBuffer);
                pBuffer.writeInt(pRecipe.getCounts().get(i));
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
