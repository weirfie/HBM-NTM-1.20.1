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

import java.util.ArrayList;
import java.util.List;

public class AnvilBlockRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public AnvilBlockRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;

        List<ItemStack> tempInv = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!container.getItem(i).isEmpty()) {
                tempInv.add(container.getItem(i).copy());
            }
        }

        for (Ingredient ingredient : recipeItems) {
            boolean found = false;
            for (ItemStack stack : tempInv) {
                if (ingredient.test(stack) && stack.getCount() > 0) {
                    stack.shrink(1);
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ANVIL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ANVIL_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public static class Type implements RecipeType<AnvilBlockRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "anvil_crafting";
    }

    public static class Serializer implements RecipeSerializer<AnvilBlockRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(HBMNTM.MOD_ID, "anvil_crafting");

        @Override
        public AnvilBlockRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");

            List<Ingredient> tmpInputs = new ArrayList<>();

            for (int i = 0; i < ingredientsJson.size(); i++) {
                JsonObject obj = ingredientsJson.get(i).getAsJsonObject();

                Ingredient ing;
                if (obj.get("item").isJsonObject()) {
                    ing = Ingredient.fromJson(obj.get("item"));
                } else {
                    ing = Ingredient.fromJson(obj);
                }

                int count = GsonHelper.getAsInt(obj, "count", 1);
                for (int j = 0; j < count; j++) {
                    tmpInputs.add(ing);
                }
            }

            NonNullList<Ingredient> inputs = NonNullList.create();
            inputs.addAll(tmpInputs);
            return new AnvilBlockRecipe(id, output, inputs);
        }

        @Override
        public @Nullable AnvilBlockRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int size = buf.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            return new AnvilBlockRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AnvilBlockRecipe recipe) {
            buf.writeInt(recipe.recipeItems.size());
            for (Ingredient ing : recipe.recipeItems) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.output, false);
        }
    }
}
