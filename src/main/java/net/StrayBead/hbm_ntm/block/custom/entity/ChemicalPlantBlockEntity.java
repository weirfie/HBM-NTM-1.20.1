package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.item.custom.FluidTankItem;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
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

    // --- RECIPE SYSTEM ---
    public record ChemicalRecipe(
            List<FluidStack> inputFluids,
            List<ItemStack> inputItems,
            List<FluidStack> outputFluids,
            List<ItemStack> outputItems,
            int processTime,
            int energyPerTick
    ) {}

    private static final List<ChemicalRecipe> RECIPES = List.of(
            new ChemicalRecipe(
                    List.of(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 20)),
                    List.of(new ItemStack(ModItems.COAL_POWDER.get()), new ItemStack(ModItems.FLUORITE.get())),
                    List.of(),
                    List.of(new ItemStack(ModItems.POLYMER_BAR.get())),
                    100, 20),

            new ChemicalRecipe(
                    List.of(new FluidStack(ModFluids.UNSATURATED_HYDROCARBONS.get(), 100)),
                    List.of(new ItemStack(ModItems.SULFUR.get())),
                    List.of(),
                    List.of(new ItemStack(ModItems.RUBBER_BAR.get())),
                    50, 20),

            new ChemicalRecipe(
                    List.of(new FluidStack(Fluids.WATER, 100)),
                    List.of(new ItemStack(ModItems.SULFUR.get())),
                    List.of(),
                    List.of(new ItemStack(ModItems.RUBBER_BAR.get())),
                    50, 20),

            new ChemicalRecipe(
                    List.of(new FluidStack(Fluids.WATER, 100)),
                    List.of(new ItemStack(Items.SAND), new ItemStack(Items.GRAVEL)),
                    List.of(),
                    List.of(new ItemStack(Blocks.LIGHT_GRAY_CONCRETE, 12)),
                    50, 20)
    );

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

    // --- TICK LOGIC ---
    public static void tick(Level level, BlockPos pos, BlockState state, ChemicalPlantBlockEntity pEntity) {
        if (level.isClientSide()) return;

        handleFluidItemInput(pEntity);
        pEntity.pushFluidToNeighbors();

        ChemicalRecipe currentRecipe = getMatchingRecipe(pEntity);

        if (currentRecipe != null && pEntity.ENERGY_STORAGE.getEnergyStored() >= currentRecipe.energyPerTick()) {
            pEntity.maxProgress = currentRecipe.processTime();
            pEntity.progress++;
            pEntity.ENERGY_STORAGE.extractEnergy(currentRecipe.energyPerTick(), false);

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

    private static ChemicalRecipe getMatchingRecipe(ChemicalPlantBlockEntity pEntity) {
        FluidTank[] inputTanks = { pEntity.FLUID_TANK, pEntity.inputTank2, pEntity.inputTank3 };

        for (ChemicalRecipe recipe : RECIPES) {
            boolean fluidsMatch = true;
            for (FluidStack reqFluid : recipe.inputFluids()) {
                if (!hasFluidInAnyTank(inputTanks, reqFluid)) {
                    fluidsMatch = false;
                    break;
                }
            }
            if (!fluidsMatch) continue;

            if (!hasItemsInSlots(pEntity, recipe.inputItems())) continue;

            if (canFitOutputs(pEntity, recipe)) return recipe;
        }
        return null;
    }

    private static boolean hasFluidInAnyTank(FluidTank[] tanks, FluidStack req) {
        for (FluidTank tank : tanks) {
            if (tank.getFluid().isFluidEqual(req) && tank.getFluidAmount() >= req.getAmount()) return true;
        }
        return false;
    }

    private static boolean hasItemsInSlots(ChemicalPlantBlockEntity pEntity, List<ItemStack> requirements) {
        if (requirements.isEmpty()) return true;

        ItemStack slot6 = pEntity.itemHandler.getStackInSlot(6);
        ItemStack slot7 = pEntity.itemHandler.getStackInSlot(7);

        if (requirements.size() == 1) {
            return itemMatches(slot6, requirements.get(0)) || itemMatches(slot7, requirements.get(0));
        } else if (requirements.size() == 2) {
            boolean order1 = itemMatches(slot6, requirements.get(0)) && itemMatches(slot7, requirements.get(1));
            boolean order2 = itemMatches(slot6, requirements.get(1)) && itemMatches(slot7, requirements.get(0));
            return order1 || order2;
        }
        return false;
    }

    private static boolean itemMatches(ItemStack slotStack, ItemStack recipeStack) {
        return slotStack.is(recipeStack.getItem()) && slotStack.getCount() >= recipeStack.getCount();
    }

    private static boolean canFitOutputs(ChemicalPlantBlockEntity pEntity, ChemicalRecipe recipe) {
        // Check Item Output (Slot 15)
        ItemStack outputSlot = pEntity.itemHandler.getStackInSlot(15);
        for (ItemStack out : recipe.outputItems()) {
            if (!outputSlot.isEmpty() && (!ItemStack.isSameItem(outputSlot, out) || (outputSlot.getCount() + out.getCount() > outputSlot.getMaxStackSize()))) {
                return false;
            }
        }

        // Check Fluid Outputs
        FluidTank[] outTanks = { pEntity.outputTank1, pEntity.outputTank2, pEntity.outputTank3 };
        for (FluidStack outFluid : recipe.outputFluids()) {
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

    private static void executeCraft(ChemicalPlantBlockEntity pEntity, ChemicalRecipe recipe) {
        FluidTank[] inTanks = { pEntity.FLUID_TANK, pEntity.inputTank2, pEntity.inputTank3 };
        FluidTank[] outTanks = { pEntity.outputTank1, pEntity.outputTank2, pEntity.outputTank3 };

        // Consume Fluids
        for (FluidStack req : recipe.inputFluids()) {
            for (FluidTank tank : inTanks) {
                if (tank.getFluid().isFluidEqual(req)) {
                    tank.drain(req.getAmount(), IFluidHandler.FluidAction.EXECUTE);
                    break;
                }
            }
        }

        // Consume Items
        for (ItemStack req : recipe.inputItems()) {
            if (itemMatches(pEntity.itemHandler.getStackInSlot(6), req)) {
                pEntity.itemHandler.getStackInSlot(6).shrink(req.getCount());
            } else {
                pEntity.itemHandler.getStackInSlot(7).shrink(req.getCount());
            }
        }

        // Produce Fluids
        for (FluidStack out : recipe.outputFluids()) {
            for (FluidTank tank : outTanks) {
                if (tank.fill(out, IFluidHandler.FluidAction.SIMULATE) == out.getAmount()) {
                    tank.fill(out, IFluidHandler.FluidAction.EXECUTE);
                    break;
                }
            }
        }

        for (ItemStack out : recipe.outputItems()) {
            pEntity.itemHandler.insertItem(15, out.copy(), false);
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
        @Override public int fill(FluidStack resource, FluidAction action) {
            for (int i = 0; i < 3; i++) { // Only allow filling input tanks (0, 1, 2)
                int filled = tanks[i].fill(resource, action);
                if (filled > 0) return filled;
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
