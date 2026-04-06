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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    private final FluidTank FLUID_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final FluidTank inputTank2 = createOutputTank(40000, 2);
    private final FluidTank inputTank3 = createOutputTank(40000, 3);
    private final FluidTank outputTank1 = createOutputTank(40000, 4);
    private final FluidTank outputTank2 = createOutputTank(40000, 5);
    private final FluidTank outputTank3 = createOutputTank(40000, 6);

    private FluidTank createOutputTank(int capacity, int tankID) {
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

    public FluidTank getOutputTank1() { return this.outputTank1; }
    public FluidTank getOutputTank2() { return this.outputTank2; }
    public FluidTank getOutputTank3() { return this.outputTank3; }

    public FluidTank getInputTank2() { return this.inputTank2; }
    public FluidTank getInputTank3() { return this.inputTank3; }

    public void setOutputTank1(FluidStack stack) { this.outputTank1.setFluid(stack); }
    public void setOutputTank2(FluidStack stack) { this.outputTank2.setFluid(stack); }
    public void setOutputTank3(FluidStack stack) { this.outputTank3.setFluid(stack); }

    public void setInputTank2(FluidStack stack) { this.inputTank2.setFluid(stack); }
    public void setInputTank3(FluidStack stack) { this.inputTank3.setFluid(stack); }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    public record ChemicalRecipe(
            Fluid inputFluid, int fluidAmount,
            ItemStack input1, ItemStack input2,
            ItemStack output, int processTime
    ) {}

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedFluidHandler = new CombinedFluidHandler(
            FLUID_TANK, inputTank2, inputTank3, outputTank1, outputTank2, outputTank3
    );
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

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
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Chemical Plant");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        ModMessages.sendToClients(new EnergySyncS2CPacket(this.ENERGY_STORAGE.getEnergyStored(), getBlockPos()));
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return new ChemicalPlantMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    private static final java.util.List<ChemicalRecipe> RECIPES = java.util.List.of(
            new ChemicalRecipe(ModFluids.PETROLEUM_GAS.get(), 20,
                    new ItemStack(ModItems.COAL_POWDER.get()), new ItemStack(ModItems.FLUORITE.get()),
                    new ItemStack(ModItems.POLYMER_BAR.get()), 100),

            new ChemicalRecipe(ModFluids.UNSATURATED_HYDROCARBONS.get(), 100,
                    new ItemStack(ModItems.SULFUR.get()), ItemStack.EMPTY,
                    new ItemStack(ModItems.RUBBER_BAR.get()), 50),

            new ChemicalRecipe(Fluids.WATER, 100,
                    new ItemStack(Items.SAND), new ItemStack(Items.GRAVEL),
                    new ItemStack(Blocks.LIGHT_GRAY_CONCRETE, 12), 50)
    );

    public static void tick(Level level, BlockPos pos, BlockState state, ChemicalPlantBlockEntity pEntity) {
        if (level.isClientSide()) return;

        handleFluidItemInput(pEntity);
        pEntity.pushFluidToNeighbors();

        ChemicalRecipe currentRecipe = getMatchingRecipe(pEntity);

        if (currentRecipe != null && pEntity.ENERGY_STORAGE.getEnergyStored() >= 20) {
            pEntity.maxProgress = currentRecipe.processTime();
            pEntity.progress++;
            pEntity.ENERGY_STORAGE.extractEnergy(20, false);

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

    private static void spawnWorkingParticles(Level level, BlockPos pos, BlockState state) {
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            Direction facing = state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING);

            double localX = 0.42;
            double localZ = -0.4;
            double finalX, finalZ;

            switch (facing) {
                case NORTH -> { finalX = localX; finalZ = localZ; }
                case SOUTH -> { finalX = -localX; finalZ = -localZ; }
                case WEST -> { finalX = localZ; finalZ = -localX; }
                case EAST -> { finalX = -localZ; finalZ = localX; }
                default -> { finalX = 0; finalZ = 0; }
            }

            double x = pos.getX() + 0.5 + finalX;
            double y = pos.getY() + 3.5;
            double z = pos.getZ() + 0.5 + finalZ;
            serverLevel.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, serverLevel, 4, "", Component.literal(""), serverLevel.getServer(), null).withSuppressedOutput(),
                    "particle minecraft:smoke ~ ~ ~ 0.01 0.01 0.01 0 10 force @a");

        }
    }

    private static ChemicalRecipe getMatchingRecipe(ChemicalPlantBlockEntity pEntity) {
        FluidStack internalFluid = pEntity.FLUID_TANK.getFluid();
        ItemStack slot6 = pEntity.itemHandler.getStackInSlot(6);
        ItemStack slot7 = pEntity.itemHandler.getStackInSlot(7);

        for (ChemicalRecipe recipe : RECIPES) {
            if (!internalFluid.getFluid().isSame(recipe.inputFluid()) || internalFluid.getAmount() < recipe.fluidAmount()) continue;

            boolean matchNormal = itemMatches(slot6, recipe.input1()) && itemMatches(slot7, recipe.input2());
            boolean matchSwapped = itemMatches(slot6, recipe.input2()) && itemMatches(slot7, recipe.input1());

            if (matchNormal || matchSwapped) {
                ItemStack outputSlot = pEntity.itemHandler.getStackInSlot(15);
                if (outputSlot.isEmpty() || (ItemStack.isSameItem(outputSlot, recipe.output()) &&
                        outputSlot.getCount() + recipe.output().getCount() <= outputSlot.getMaxStackSize())) {
                    return recipe;
                }
            }
        }
        return null;
    }

    private static boolean itemMatches(ItemStack slotStack, ItemStack recipeStack) {
        if (recipeStack.isEmpty()) return true;
        return slotStack.is(recipeStack.getItem()) && slotStack.getCount() >= recipeStack.getCount();
    }

    private static void executeCraft(ChemicalPlantBlockEntity pEntity, ChemicalRecipe recipe) {
        pEntity.FLUID_TANK.drain(recipe.fluidAmount(), IFluidHandler.FluidAction.EXECUTE);

        consumeInput(pEntity.itemHandler, 6, recipe.input1(), recipe.input2());
        consumeInput(pEntity.itemHandler, 7, recipe.input1(), recipe.input2());

        ItemStack result = recipe.output().copy();
        pEntity.itemHandler.insertItem(15, result, false);
        pEntity.setChanged();
    }

    private static void consumeInput(ItemStackHandler handler, int slot, ItemStack req1, ItemStack req2) {
        ItemStack stack = handler.getStackInSlot(slot);
        if (stack.isEmpty()) return;

        if (!req1.isEmpty() && stack.is(req1.getItem())) {
            stack.shrink(req1.getCount());
        } else if (!req2.isEmpty() && stack.is(req2.getItem())) {
            stack.shrink(req2.getCount());
        }
    }

    private static void handleFluidItemInput(ChemicalPlantBlockEntity pEntity) {
        for (int i = 0; i <= 2; i++) {
            ItemStack stack = pEntity.itemHandler.getStackInSlot(i);

            if (!stack.isEmpty() && stack.getItem() instanceof FluidTankItem tankItem) {
                String itemFluidId = FluidTankItem.getFluidType(stack);
                int amountInItem = FluidTankItem.getFluidAmount(stack);

                if (amountInItem > 0) {
                    FluidTank targetTank = pEntity.FLUID_TANK;


                    int spaceInTank = targetTank.getCapacity() - targetTank.getFluidAmount();
                    int transfer = Math.min(amountInItem, Math.min(spaceInTank, 1000));

                    if (transfer > 0) {
                        String fluidPath = itemFluidId.contains(":") ? itemFluidId : "hbm_ntm:" + itemFluidId;
                        var fluid = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidPath));

                        if (fluid != null && fluid != net.minecraft.world.level.material.Fluids.EMPTY) {
                            targetTank.fill(new FluidStack(fluid, transfer), IFluidHandler.FluidAction.EXECUTE);

                            int newAmount = amountInItem - transfer;
                            FluidTankItem.setFluidData(stack, newAmount, newAmount > 0 ? itemFluidId : "");

                            pEntity.setChanged();
                        }
                    }
                }
            }
        }
    }

    private static boolean canProcess(ChemicalPlantBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= 20 &&
                pEntity.itemHandler.getStackInSlot(6).getItem() == ModItems.COAL_POWDER.get() &&
                pEntity.itemHandler.getStackInSlot(7).getItem() == ModItems.FLUORITE.get() &&
                pEntity.FLUID_TANK.getFluidAmount() >= 20 &&
                pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.PETROLEUM_GAS.get();
    }

    private static void craftItem(ChemicalPlantBlockEntity pEntity) {
        pEntity.FLUID_TANK.drain(20, IFluidHandler.FluidAction.EXECUTE);
        pEntity.itemHandler.getStackInSlot(6).shrink(1);
        pEntity.itemHandler.getStackInSlot(7).shrink(1);
        pEntity.itemHandler.setStackInSlot(15, new ItemStack(ModItems.POLYMER_BAR.get()));
    }

    private void pushFluidToNeighbors() {
        FluidTank[] outputTanks = {outputTank1, outputTank2, outputTank3};
        int pushAmount = 5;

        for (FluidTank tank : outputTanks) {
            FluidStack stackInTank = tank.getFluid();
            if (stackInTank.isEmpty()) continue;

            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stackInTank.getFluid()).getPath();

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = worldPosition.relative(dir);
                BlockEntity neighborBE = level.getBlockEntity(neighborPos);

                if (neighborBE != null) {
                    if (neighborBE instanceof FluidBlockEntity duct) {
                        String filter = duct.getAllowedFluid();

                        if (filter.isEmpty() || !filter.equals(currentFluidName)) {
                            continue;
                        }
                    } else {
                    }

                    neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(handler -> {
                        FluidStack toPush = new FluidStack(stackInTank.getFluid(), Math.min(stackInTank.getAmount(), pushAmount));
                        int filled = handler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                        if (filled > 0) {
                            tank.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                        }
                    });
                }
            }
        }
    }

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
        nbt.putInt("chemical_plant.progress", this.progress);
        nbt.putInt("chemical_plant.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.put("input_tank", FLUID_TANK.writeToNBT(new CompoundTag()));
        nbt.put("input_tank_2", inputTank2.writeToNBT(new CompoundTag()));
        nbt.put("input_tank_3", inputTank3.writeToNBT(new CompoundTag()));
        nbt.put("output_tank_1", outputTank1.writeToNBT(new CompoundTag()));
        nbt.put("output_tank_2", outputTank2.writeToNBT(new CompoundTag()));
        nbt.put("output_tank_3", outputTank3.writeToNBT(new CompoundTag()));

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("chemical_plant.progress");
        ENERGY_STORAGE.setEnergy(nbt.getInt("chemical_plant.energy"));
        FLUID_TANK.readFromNBT(nbt.getCompound("input_tank"));
        inputTank2.readFromNBT(nbt.getCompound("input_tank_2"));
        inputTank3.readFromNBT(nbt.getCompound("input_tank_3"));
        outputTank1.readFromNBT(nbt.getCompound("output_tank_1"));
        outputTank2.readFromNBT(nbt.getCompound("output_tank_2"));
        outputTank3.readFromNBT(nbt.getCompound("output_tank_3"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private static void fillTankWithFluid(ChemicalPlantBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasEnoughEnergy(ChemicalPlantBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private static class CombinedFluidHandler implements IFluidHandler {
        private final FluidTank[] tanks;

        public CombinedFluidHandler(FluidTank... tanks) {
            this.tanks = tanks;
        }

        @Override public int getTanks() { return tanks.length; }
        @Override public FluidStack getFluidInTank(int tank) { return tanks[tank].getFluid(); }
        @Override public int getTankCapacity(int tank) { return tanks[tank].getCapacity(); }
        @Override public boolean isFluidValid(int tank, FluidStack stack) { return tanks[tank].isFluidValid(stack); }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (tanks[0].isFluidValid(resource)) return tanks[0].fill(resource, action);
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (tank.getFluid().isFluidEqual(resource)) return tank.drain(resource, action);
            }
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (!tank.getFluid().isEmpty()) return tank.drain(maxDrain, action);
            }
            return FluidStack.EMPTY;
        }
    }
}
