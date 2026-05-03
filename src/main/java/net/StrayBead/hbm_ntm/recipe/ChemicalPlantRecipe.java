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
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChemicalPlantRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> inputItems;
    private final List<FluidStack> inputFluids;
    private final ItemStack outputItem;
    private final List<FluidStack> outputFluids;
    private final int processTime;
    private final int energyPerTick;

    public ChemicalPlantRecipe(ResourceLocation id, NonNullList<Ingredient> inputItems, List<FluidStack> inputFluids,
                               ItemStack outputItem, List<FluidStack> outputFluids, int processTime, int energyPerTick) {
        this.id = id;
        this.inputItems = inputItems;
        this.inputFluids = inputFluids;
        this.outputItem = outputItem;
        this.outputFluids = outputFluids;
        this.processTime = processTime;
        this.energyPerTick = energyPerTick;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return true;
    }

    public NonNullList<Ingredient> getItemIngredients() { return inputItems; }
    public List<FluidStack> getFluidIngredients() { return inputFluids; }
    public List<FluidStack> getFluidOutputs() { return outputFluids; }
    public int getProcessTime() { return processTime; }
    public int getEnergyPerTick() { return energyPerTick; }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) { return outputItem.copy(); }
    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }
    @Override
    public ItemStack getResultItem(RegistryAccess access) { return outputItem.copy(); }
    @Override
    public ResourceLocation getId() { return id; }
    @Override
    public RecipeSerializer<?> getSerializer() { return ModRecipes.CHEMICAL_PLANT_SERIALIZER.get(); }
    @Override
    public RecipeType<?> getType() { return ModRecipes.CHEMICAL_PLANT_TYPE.get(); }

    public static class Serializer implements RecipeSerializer<ChemicalPlantRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ChemicalPlantRecipe fromJson(ResourceLocation id, JsonObject json) {
            NonNullList<Ingredient> items = NonNullList.create();
            JsonArray itemArray = GsonHelper.getAsJsonArray(json, "ingredients");
            for (int i = 0; i < itemArray.size(); i++) items.add(Ingredient.fromJson(itemArray.get(i)));

            List<FluidStack> fluidInputs = new ArrayList<>();
            JsonArray fluidIn = GsonHelper.getAsJsonArray(json, "fluid_inputs");
            for (int i = 0; i < fluidIn.size(); i++) fluidInputs.add(deserializeFluid(fluidIn.get(i).getAsJsonObject()));

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            List<FluidStack> fluidOutputs = new ArrayList<>();
            if(json.has("fluid_outputs")) {
                JsonArray fluidOut = GsonHelper.getAsJsonArray(json, "fluid_outputs");
                for (int i = 0; i < fluidOut.size(); i++) fluidOutputs.add(deserializeFluid(fluidOut.get(i).getAsJsonObject()));
            }

            int time = GsonHelper.getAsInt(json, "time", 100);
            int energy = GsonHelper.getAsInt(json, "energy", 20);

            return new ChemicalPlantRecipe(id, items, fluidInputs, output, fluidOutputs, time, energy);
        }

        private FluidStack deserializeFluid(JsonObject json) {
            ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
            int amount = GsonHelper.getAsInt(json, "amount");
            return new FluidStack(net.minecraftforge.registries.ForgeRegistries.FLUIDS.getValue(fluidId), amount);
        }

        @Override
        public @Nullable ChemicalPlantRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int itemSize = buf.readInt();
            NonNullList<Ingredient> items = NonNullList.withSize(itemSize, Ingredient.EMPTY);
            for(int i = 0; i < itemSize; i++) items.set(i, Ingredient.fromNetwork(buf));

            int fluidInSize = buf.readInt();
            List<FluidStack> fluidIn = new ArrayList<>();
            for(int i = 0; i < fluidInSize; i++) fluidIn.add(buf.readFluidStack());

            ItemStack output = buf.readItem();

            int fluidOutSize = buf.readInt();
            List<FluidStack> fluidOut = new ArrayList<>();
            for(int i = 0; i < fluidOutSize; i++) fluidOut.add(buf.readFluidStack());

            return new ChemicalPlantRecipe(id, items, fluidIn, output, fluidOut, buf.readInt(), buf.readInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ChemicalPlantRecipe recipe) {
            buf.writeInt(recipe.inputItems.size());
            for(Ingredient ing : recipe.inputItems) ing.toNetwork(buf);
            buf.writeInt(recipe.inputFluids.size());
            for(FluidStack stack : recipe.inputFluids) buf.writeFluidStack(stack);
            buf.writeItemStack(recipe.outputItem, false);
            buf.writeInt(recipe.outputFluids.size());
            for(FluidStack stack : recipe.outputFluids) buf.writeFluidStack(stack);
            buf.writeInt(recipe.processTime);
            buf.writeInt(recipe.energyPerTick);
        }
    }
}