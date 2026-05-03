package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.item.custom.FluidTankItem;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.recipe.ChemicalPlantRecipe;
import net.StrayBead.hbm_ntm.recipe.ModRecipes;
import net.StrayBead.hbm_ntm.screen.ChemicalPlantMenu;
import net.StrayBead.hbm_ntm.screen.OilRefineryMenu;
import net.StrayBead.hbm_ntm.util.ModEnergyStorage;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChemicalPlantBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(22) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(600000, 10000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 32;

    private final FluidTank FLUID_TANK = createTank(64000, 1);
    private final FluidTank inputTank2 = createTank(40000, 2);
    private final FluidTank inputTank3 = createTank(40000, 3);
    private final FluidTank outputTank1 = createTank(40000, 4);
    private final FluidTank outputTank2 = createTank(40000, 5);
    private final FluidTank outputTank3 = createTank(40000, 6);

    private FluidTank createTank(int capacity, int tankID) {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (level != null && !level.isClientSide()) {
                    ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition, tankID));
                }
            }
        };
    }
    private final FluidTank inputTank1 = createTank(64000, 0);

    public IEnergyStorage getEnergyStorage() { return ENERGY_STORAGE; }


    public void setFluid(FluidStack stack) { this.inputTank1.setFluid(stack); }
    public void setInputTank2(FluidStack stack) { this.inputTank2.setFluid(stack); }
    public void setInputTank3(FluidStack stack) { this.inputTank3.setFluid(stack); }
    public void setOutputTank1(FluidStack stack) { this.outputTank1.setFluid(stack); }
    public void setOutputTank2(FluidStack stack) { this.outputTank2.setFluid(stack); }
    public void setOutputTank3(FluidStack stack) { this.outputTank3.setFluid(stack); }

    public FluidStack getFluidStack() { return this.inputTank1.getFluid(); }
    public FluidTank getInputTank1() { return this.inputTank1; }
    public FluidTank getInputTank2() { return this.inputTank2; }
    public FluidTank getInputTank3() { return this.inputTank3; }
    public FluidTank getOutputTank1() { return this.outputTank1; }
    public FluidTank getOutputTank2() { return this.outputTank2; }
    public FluidTank getOutputTank3() { return this.outputTank3; }

//    private static final List<ChemicalRecipe> RECIPES = List.of(
//            new ChemicalRecipe(
//                    List.of(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 20)),
//                    List.of(new ItemStack(ModItems.COAL_POWDER.get()), new ItemStack(ModItems.FLUORITE.get())),
//                    List.of(),
//                    List.of(new ItemStack(ModItems.POLYMER_BAR.get())),
//                    100, 20),
//
//            new ChemicalRecipe(
//                    List.of(new FluidStack(ModFluids.UNSATURATED_HYDROCARBONS.get(), 100)),
//                    List.of(new ItemStack(ModItems.SULFUR.get())),
//                    List.of(),
//                    List.of(new ItemStack(ModItems.RUBBER_BAR.get())),
//                    50, 20),
//
//            new ChemicalRecipe(
//                    List.of(new FluidStack(Fluids.WATER, 100)),
//                    List.of(new ItemStack(ModItems.SULFUR.get())),
//                    List.of(),
//                    List.of(new ItemStack(ModItems.RUBBER_BAR.get())),
//                    50, 20),
//
//            new ChemicalRecipe(
//                    List.of(new FluidStack(Fluids.WATER, 100)),
//                    List.of(new ItemStack(Items.SAND), new ItemStack(Items.GRAVEL)),
//                    List.of(),
//                    List.of(new ItemStack(Blocks.LIGHT_GRAY_CONCRETE, 12)),
//                    50, 20),
//
//            new ChemicalRecipe(
//                    List.of(new FluidStack(ModFluids.DEUTERIUM.get(), 100), new FluidStack(ModFluids.REFORMATE_GAS.get(), 100), new FluidStack(ModFluids.SYNGAS.get(), 100)),
//                    List.of(),
//                    List.of(new FluidStack(ModFluids.DEUTERATED_HYDROCARBON.get(), 100)),
//                    List.of(),
//                    50, 20)
//    );

    // --- CAPS & DATA ---
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedFluidHandler = new CombinedFluidHandler(
            FLUID_TANK, inputTank2, inputTank3, outputTank1, outputTank2, outputTank3
    );

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    public ChemicalPlantBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.CHEMICAL_PLANT.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ChemicalPlantBlockEntity.this.progress;
                    case 1 -> ChemicalPlantBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ChemicalPlantBlockEntity.this.progress = value;
                    case 1 -> ChemicalPlantBlockEntity.this.maxProgress = value;
                }
            }
            @Override
            public int getCount() { return 2; }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ChemicalPlantBlockEntity pEntity) {
        if (level.isClientSide()) return;

        handleFluidItemInput(pEntity);
        pEntity.pushFluidToNeighbors();

        ChemicalPlantRecipe currentRecipe = getMatchingRecipe(pEntity);
        System.out.println("FLUID_TANK amount: " + pEntity.FLUID_TANK.getFluidAmount() + "FLUID_TANK type: " + pEntity.FLUID_TANK.getFluid().getFluid());
        System.out.println("Input 1 amount: " + pEntity.inputTank1.getFluidAmount() + "Input tank 1 type: " + pEntity.inputTank1.getFluid().getFluid());
        System.out.println("Input 2 amount: " + pEntity.inputTank2.getFluidAmount() + "Input tank 2 type: " + pEntity.inputTank2.getFluid().getFluid());
        System.out.println("Input 3 amount: " + pEntity.inputTank3.getFluidAmount() + "Input tank 3 type: " + pEntity.inputTank3.getFluid().getFluid());

        System.out.println("Energy inside block: " + pEntity.getEnergyStorage());
        System.out.printf("current recipe: " + currentRecipe);
        if (currentRecipe == null) {
            System.out.println("is processing: false");
        } else {
            System.out.println("is processing: true");
        }

        if (currentRecipe != null && pEntity.ENERGY_STORAGE.getEnergyStored() >= currentRecipe.getEnergyPerTick()) {
            pEntity.maxProgress = currentRecipe.getProcessTime();
            pEntity.progress++;
            pEntity.ENERGY_STORAGE.extractEnergy(currentRecipe.getEnergyPerTick(), false);

            if (level.getGameTime() % 2 == 0) {
                spawnWorkingParticles(level, pos, state);
            }

            if (pEntity.progress >= pEntity.maxProgress) {
                executeCraft(pEntity, currentRecipe);
                pEntity.progress = 0;
            }
        } else {
            pEntity.progress = 0;
        }
    }

    private static ChemicalPlantRecipe getMatchingRecipe(ChemicalPlantBlockEntity pEntity) {
        Level level = pEntity.level;
        if (level == null) return null;

        var recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.CHEMICAL_PLANT_TYPE.get());

        // ALL input tanks available to the machine
        FluidTank[] inTanks = { pEntity.inputTank1, pEntity.FLUID_TANK, pEntity.inputTank2, pEntity.inputTank3 };

        for (ChemicalPlantRecipe recipe : recipes) {
            List<FluidStack> neededFluids = recipe.getFluidIngredients();
            boolean allIngredientsFound = true;

            // TRACK amounts so we don't use the same fluid for two different requirements
            int[] virtualAmounts = new int[inTanks.length];
            for(int i = 0; i < inTanks.length; i++) {
                virtualAmounts[i] = inTanks[i].getFluidAmount();
            }

            for (FluidStack req : neededFluids) {
                boolean currentReqSatisfied = false;

                for (int i = 0; i < inTanks.length; i++) {
                    FluidStack tankFluid = inTanks[i].getFluid();

                    // Compare by Registry Name to avoid strict object mismatch
                    ResourceLocation tankName = ForgeRegistries.FLUIDS.getKey(tankFluid.getFluid());
                    ResourceLocation reqName = ForgeRegistries.FLUIDS.getKey(req.getFluid());

                    if (tankName != null && tankName.equals(reqName) && virtualAmounts[i] >= req.getAmount()) {
                        virtualAmounts[i] -= req.getAmount(); // Reserve this amount
                        currentReqSatisfied = true;
                        break; // Move to the next needed fluid ingredient
                    }
                }

                if (!currentReqSatisfied) {
                    allIngredientsFound = false;
                    break; // This recipe cannot work, stop checking its ingredients
                }
            }

            if (allIngredientsFound && hasItemsInSlots(pEntity, recipe.getItemIngredients()) && canFitOutputs(pEntity, recipe)) {
                return recipe;
            }
        }
        return null;
    }

    private static boolean hasFluidInAnyTank(FluidTank[] tanks, FluidStack req) {
        for (FluidTank tank : tanks) {
            if (tank.getFluid().isFluidEqual(req) && tank.getFluidAmount() >= req.getAmount()) return true;
        }
        return false;
    }

    private static boolean hasItemsInSlots(ChemicalPlantBlockEntity pEntity, List<Ingredient> requirements) {
        if (requirements.isEmpty()) return true;

        ItemStack slot6 = pEntity.itemHandler.getStackInSlot(6);
        ItemStack slot7 = pEntity.itemHandler.getStackInSlot(7);

        if (requirements.size() == 1) {
            return requirements.get(0).test(slot6) || requirements.get(0).test(slot7);
        }

        if (requirements.size() == 2) {
            Ingredient ing1 = requirements.get(0);
            Ingredient ing2 = requirements.get(1);

            boolean matchNormal = ing1.test(slot6) && ing2.test(slot7);
            boolean matchSwapped = ing1.test(slot7) && ing2.test(slot6);

            return matchNormal || matchSwapped;
        }

        return false;
    }

    private static boolean canFitOutputs(ChemicalPlantBlockEntity pEntity, ChemicalPlantRecipe recipe) {
        ItemStack recipeOutput = recipe.getResultItem(pEntity.level.registryAccess());

        if (!recipeOutput.is(Items.AIR)) {
            ItemStack outputSlot = pEntity.itemHandler.getStackInSlot(15);
            if (!outputSlot.isEmpty() && (!ItemStack.isSameItem(outputSlot, recipeOutput) ||
                    (outputSlot.getCount() + recipeOutput.getCount() > outputSlot.getMaxStackSize()))) {
                return false;
            }
        }

        FluidTank[] outTanks = { pEntity.outputTank1, pEntity.outputTank2, pEntity.outputTank3 };
        for (FluidStack outFluid : recipe.getFluidOutputs()) {
            boolean canFit = false;
            for (FluidTank tank : outTanks) {
                if (tank.fill(outFluid, IFluidHandler.FluidAction.SIMULATE) == outFluid.getAmount()) {
                    canFit = true;
                    break;
                }
            }
            if (!canFit) return false;
        }
        return true;
    }

    private static void executeCraft(ChemicalPlantBlockEntity pEntity, ChemicalPlantRecipe recipe) {
        FluidTank[] inTanks = { pEntity.inputTank1, pEntity.FLUID_TANK, pEntity.inputTank2, pEntity.inputTank3 };
        FluidTank[] outTanks = { pEntity.outputTank1, pEntity.outputTank2, pEntity.outputTank3 };

        // 1. DRAIN INPUT FLUIDS (Flexible matching)
        for (FluidStack req : recipe.getFluidIngredients()) {
            ResourceLocation reqName = ForgeRegistries.FLUIDS.getKey(req.getFluid());
            for (FluidTank tank : inTanks) {
                ResourceLocation tankName = ForgeRegistries.FLUIDS.getKey(tank.getFluid().getFluid());
                if (tankName != null && tankName.equals(reqName)) {
                    tank.drain(req.getAmount(), IFluidHandler.FluidAction.EXECUTE);
                    break;
                }
            }
        }

        // 2. SHRINK INPUT ITEMS
        for (Ingredient ing : recipe.getItemIngredients()) {
            if (ing.test(pEntity.itemHandler.getStackInSlot(6))) {
                pEntity.itemHandler.getStackInSlot(6).shrink(1);
            } else if (ing.test(pEntity.itemHandler.getStackInSlot(7))) {
                pEntity.itemHandler.getStackInSlot(7).shrink(1);
            }
        }

        // 3. FILL OUTPUT FLUIDS
        for (FluidStack out : recipe.getFluidOutputs()) {
            for (FluidTank tank : outTanks) {
                // Check if tank is empty or matches the output fluid by name
                ResourceLocation tankFluidName = ForgeRegistries.FLUIDS.getKey(tank.getFluid().getFluid());
                ResourceLocation outFluidName = ForgeRegistries.FLUIDS.getKey(out.getFluid());

                if (tank.getFluid().isEmpty() || (tankFluidName != null && tankFluidName.equals(outFluidName))) {
                    if (tank.fill(out, IFluidHandler.FluidAction.SIMULATE) == out.getAmount()) {
                        tank.fill(out, IFluidHandler.FluidAction.EXECUTE);
                        break;
                    }
                }
            }
        }

        // 4. INSERT OUTPUT ITEMS
        ItemStack result = recipe.getResultItem(pEntity.level.registryAccess());
        if (!result.isEmpty() && !result.is(Items.AIR)) {
            pEntity.itemHandler.insertItem(15, result.copy(), false);
        }

        pEntity.setChanged();
    }

    private static void handleFluidItemInput(ChemicalPlantBlockEntity pEntity) {
        for (int i = 0; i <= 2; i++) {
            ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof FluidTankItem) {
                String fluidId = FluidTankItem.getFluidType(stack);
                int amount = FluidTankItem.getFluidAmount(stack);
                if (amount > 0) {
                    FluidTank target = pEntity.FLUID_TANK;
                    int space = target.getCapacity() - target.getFluidAmount();
                    int transfer = Math.min(amount, Math.min(space, 1000));
                    if (transfer > 0) {
                        var fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidId.contains(":") ? fluidId : "hbm_ntm:" + fluidId));
                        if (fluid != null && fluid != Fluids.EMPTY) {
                            target.fill(new FluidStack(fluid, transfer), IFluidHandler.FluidAction.EXECUTE);
                            FluidTankItem.setFluidData(stack, amount - transfer, (amount - transfer) > 0 ? fluidId : "");
                            pEntity.setChanged();
                        }
                    }
                }
            }
        }
    }

    private void pushFluidToNeighbors() {
        FluidTank[] outputTanks = {outputTank1, outputTank2, outputTank3};
        for (FluidTank tank : outputTanks) {
            FluidStack stack = tank.getFluid();
            if (stack.isEmpty()) continue;
            for (Direction dir : Direction.values()) {
                BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
                if (be != null) {
                    be.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(handler -> {
                        int filled = handler.fill(new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), 100)), IFluidHandler.FluidAction.EXECUTE);
                        tank.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                    });
                }
            }
        }
    }

    private static void spawnWorkingParticles(Level level, BlockPos pos, BlockState state) {
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            Direction facing = state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING);
            double lx = 0.42, lz = -0.4, fx, fz;
            switch (facing) {
                case NORTH -> { fx = lx; fz = lz; }
                case SOUTH -> { fx = -lx; fz = -lz; }
                case WEST -> { fx = lz; fz = -lx; }
                case EAST -> { fx = -lz; fz = lx; }
                default -> { fx = 0; fz = 0; }
            }
            double x = pos.getX() + 0.5 + fx, y = pos.getY() + 3.5, z = pos.getZ() + 0.5 + fz;
            serverLevel.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, serverLevel, 4, "", Component.literal(""), serverLevel.getServer(), null).withSuppressedOutput(),
                    "particle minecraft:smoke ~ ~ ~ 0.01 0.01 0.01 0 10 force @a");
        }
    }

    // --- OVERRIDES ---
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> combinedFluidHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("progress", progress);
        nbt.putInt("energy", ENERGY_STORAGE.getEnergyStored());
        nbt.put("t1", FLUID_TANK.writeToNBT(new CompoundTag()));
        nbt.put("t2", inputTank2.writeToNBT(new CompoundTag()));
        nbt.put("t3", inputTank3.writeToNBT(new CompoundTag()));
        nbt.put("o1", outputTank1.writeToNBT(new CompoundTag()));
        nbt.put("o2", outputTank2.writeToNBT(new CompoundTag()));
        nbt.put("o3", outputTank3.writeToNBT(new CompoundTag()));
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("progress");
        ENERGY_STORAGE.setEnergy(nbt.getInt("energy"));
        FLUID_TANK.readFromNBT(nbt.getCompound("t1"));
        inputTank2.readFromNBT(nbt.getCompound("t2"));
        inputTank3.readFromNBT(nbt.getCompound("t3"));
        outputTank1.readFromNBT(nbt.getCompound("o1"));
        outputTank2.readFromNBT(nbt.getCompound("o2"));
        outputTank3.readFromNBT(nbt.getCompound("o3"));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER) return lazyFluidHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() { return Component.literal("Chemical Plant"); }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new ChemicalPlantMenu(id, inv, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) inventory.setItem(i, itemHandler.getStackInSlot(i));
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    // --- INNER HANDLER ---
    private static class CombinedFluidHandler implements IFluidHandler {
        private final FluidTank[] tanks;
        public CombinedFluidHandler(FluidTank... tanks) { this.tanks = tanks; }
        @Override public int getTanks() { return tanks.length; }
        @Override public FluidStack getFluidInTank(int tank) { return tanks[tank].getFluid(); }
        @Override public int getTankCapacity(int tank) { return tanks[tank].getCapacity(); }
        @Override public boolean isFluidValid(int tank, FluidStack stack) { return tanks[tank].isFluidValid(stack); }
        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty()) return 0;

            for (int i = 0; i < 3; i++) {
                if (!tanks[i].getFluid().isEmpty() && tanks[i].getFluid().isFluidEqual(resource)) {
                    return tanks[i].fill(resource, action);
                }
            }

            for (int i = 0; i < 3; i++) {
                if (tanks[i].getFluid().isEmpty()) {
                    return tanks[i].fill(resource, action);
                }
            }

            return 0;
        }
        @Override public FluidStack drain(FluidStack resource, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (tank.getFluid().isFluidEqual(resource)) return tank.drain(resource, action);
            }
            return FluidStack.EMPTY;
        }
        @Override public FluidStack drain(int maxDrain, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (!tank.getFluid().isEmpty()) return tank.drain(maxDrain, action);
            }
            return FluidStack.EMPTY;
        }
    }
}
