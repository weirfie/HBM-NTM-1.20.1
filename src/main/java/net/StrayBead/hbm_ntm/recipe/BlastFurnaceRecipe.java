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

public class BlastFurnaceRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient input1;
    private final Ingredient input2;
    private final int fluidAmount;

    public BlastFurnaceRecipe(ResourceLocation id, ItemStack output, Ingredient input1, Ingredient input2, int fluidAmount) {
        this.id = id;
        this.output = output;
        this.input1 = input1;
        this.input2 = input2;
        this.fluidAmount = fluidAmount;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;
        return input1.test(pContainer.getItem(1)) && input2.test(pContainer.getItem(2));
    }

    public Ingredient getInput1() { return input1; }
    public Ingredient getInput2() { return input2; }
    public int getFluidAmount() { return fluidAmount; }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input1);
        list.add(input2);
        return list;
    }

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
    public RecipeType<?> getType() {
        return ModRecipes.BLAST_FURNACE_TYPE.get();
    }

    public static class Type implements RecipeType<BlastFurnaceRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "blast_furnace";
    }

    public static class Serializer implements RecipeSerializer<BlastFurnaceRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(HBMNTM.MOD_ID, "blast_furnace");

        @Override
        public BlastFurnaceRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input1 = Ingredient.fromJson(pSerializedRecipe.get("input1"));
            Ingredient input2 = Ingredient.fromJson(pSerializedRecipe.get("input2"));
            int fluidAmount = GsonHelper.getAsInt(pSerializedRecipe, "fluid_amount");
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            return new BlastFurnaceRecipe(pRecipeId, output, input1, input2, fluidAmount);
        }

        @Override
        public @Nullable BlastFurnaceRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient input1 = Ingredient.fromNetwork(pBuffer);
            Ingredient input2 = Ingredient.fromNetwork(pBuffer);
            int fluidAmount = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            return new BlastFurnaceRecipe(pRecipeId, output, input1, input2, fluidAmount);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BlastFurnaceRecipe pRecipe) {
            pRecipe.input1.toNetwork(pBuffer);
            pRecipe.input2.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.fluidAmount);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
