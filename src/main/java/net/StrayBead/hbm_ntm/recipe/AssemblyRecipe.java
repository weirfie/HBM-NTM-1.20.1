package net.StrayBead.hbm_ntm.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

public class AssemblyRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<ItemStack> recipeStacks;
    private final Ingredient template;

    public AssemblyRecipe(ResourceLocation id, ItemStack output, NonNullList<ItemStack> recipeStacks, Ingredient template) {
        this.id = id;
        this.output = output;
        this.recipeStacks = recipeStacks;
        this.template = template;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if(level.isClientSide()) return false;
        if(!template.test(container.getItem(13))) return false;

        for (ItemStack requirement : recipeStacks) {
            int countFound = 0;
            for (int i = 0; i < 13; i++) {
                ItemStack stackInSlot = container.getItem(i);
                if (ItemStack.isSameItem(stackInSlot, requirement)) {
                    countFound += stackInSlot.getCount();
                }
            }
            if (countFound < requirement.getCount()) return false;
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (ItemStack stack : recipeStacks) {
            ingredients.add(Ingredient.of(stack));
        }
        return ingredients;
    }

    public NonNullList<ItemStack> getRecipeStacks() {
        return recipeStacks;
    }
    public Ingredient getTemplate() { return template; }
    @Override public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) { return output.copy(); }
    @Override public boolean canCraftInDimensions(int p_43999_, int p_44000_) { return true; }
    @Override public ItemStack getResultItem(RegistryAccess p_267052_) { return output.copy(); }
    @Override public ResourceLocation getId() { return id; }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ASSEMBLY_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ASSEMBLY_TYPE.get();
    }

//    public static class Type implements RecipeType<AssemblyRecipe> {
//        public static final Type INSTANCE = new Type();
//    }

    public static class Serializer implements RecipeSerializer<AssemblyRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public AssemblyRecipe fromJson(ResourceLocation id, JsonObject json) {
            System.out.println("hbm_ntm DEBUG: Loading recipe with ID: " + id.toString());
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            System.out.println("Output: " + output);
            Ingredient template = Ingredient.fromJson(json.get("template"));
            System.out.println("Templates json: " + template);
            JsonArray ingredientsArray = GsonHelper.getAsJsonArray(json, "ingredients");
            System.out.println("Ingredients Array: " + ingredientsArray);

            NonNullList<ItemStack> inputs = NonNullList.create();
            for (int i = 0; i < ingredientsArray.size(); i++) {
                JsonObject obj = ingredientsArray.get(i).getAsJsonObject();
                ItemStack stack = new ItemStack(GsonHelper.getAsItem(obj, "item"));
                stack.setCount(GsonHelper.getAsInt(obj, "count", 1)); // This gets the 12, 8, 6, etc.
                inputs.add(stack);
            }
            return new AssemblyRecipe(id, output, inputs, template);
        }

        @Override
        public @Nullable AssemblyRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient template = Ingredient.fromNetwork(buf);
            int size = buf.readInt();
            NonNullList<ItemStack> inputs = NonNullList.withSize(size, ItemStack.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, buf.readItem());
            }

            ItemStack output = buf.readItem();
            return new AssemblyRecipe(id, output, inputs, template);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AssemblyRecipe recipe) {
            recipe.template.toNetwork(buf);
            buf.writeInt(recipe.recipeStacks.size());
            for (ItemStack stack : recipe.recipeStacks) {
                buf.writeItem(stack);
            }
            buf.writeItemStack(recipe.output, false);
        }
    }
}
