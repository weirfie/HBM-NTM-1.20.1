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

public class CentrifugeRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient input;
    private final NonNullList<ItemStack> outputs;

    public CentrifugeRecipe(ResourceLocation id, Ingredient input, NonNullList<ItemStack> outputs) {
        this.id = id;
        this.input = input;
        this.outputs = outputs;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return input.test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input);
        return list;
    }

    public NonNullList<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0).copy();
    }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public RecipeSerializer<?> getSerializer() { return Serializer.INSTANCE; }

    @Override
    public RecipeType<?> getType() { return Type.INSTANCE; }

    public static class Type implements RecipeType<CentrifugeRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "centrifuge";
    }

    public static class Serializer implements RecipeSerializer<CentrifugeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(HBMNTM.MOD_ID, "centrifuge");

        @Override
        public CentrifugeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));
            JsonArray resultsArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "results");

            NonNullList<ItemStack> outputs = NonNullList.create();
            for (int i = 0; i < resultsArray.size() && i < 4; i++) {
                outputs.add(ShapedRecipe.itemStackFromJson(resultsArray.get(i).getAsJsonObject()));
            }

            return new CentrifugeRecipe(pRecipeId, input, outputs);
        }

        @Override
        public @Nullable CentrifugeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            int outputSize = pBuffer.readInt();
            NonNullList<ItemStack> outputs = NonNullList.create();
            for (int i = 0; i < outputSize; i++) {
                outputs.add(pBuffer.readItem());
            }
            return new CentrifugeRecipe(pRecipeId, input, outputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CentrifugeRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.outputs.size());
            for (ItemStack output : pRecipe.outputs) {
                pBuffer.writeItem(output);
            }
        }
    }
}
