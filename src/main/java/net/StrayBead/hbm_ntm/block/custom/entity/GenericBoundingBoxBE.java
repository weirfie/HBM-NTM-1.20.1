package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.CompressorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class GenericBoundingBoxBE extends BlockEntity {
    private BlockPos corePos = BlockPos.ZERO;

    public GenericBoundingBoxBE(BlockPos pos, BlockState state) {
        super(ModBlockEntites.GENERIC_BOUNDING_BOX.get(), pos, state);
    }

    public void setCorePos(BlockPos pos) {
        this.corePos = pos;
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (level != null && corePos != null && !corePos.equals(BlockPos.ZERO)) {
            BlockEntity coreBE = level.getBlockEntity(corePos);

            if (coreBE != null) {
                if (capability == ForgeCapabilities.FLUID_HANDLER ||
                        capability == ForgeCapabilities.ITEM_HANDLER ||
                        capability == ForgeCapabilities.ENERGY) {

                    return coreBE.getCapability(capability, facing);
                }
            }
        }
        return super.getCapability(capability, facing);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GenericBoundingBoxBE pEntity) {
        if (level.isClientSide) return;

        BlockPos corePos = pEntity.getCorePos();

        if (corePos == null || level.getBlockState(corePos).getBlock() == Blocks.AIR) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            return;
        }

        if (pEntity.corePos != null && !pEntity.corePos.equals(BlockPos.ZERO)) {
            BlockEntity coreBE = level.getBlockEntity(pEntity.corePos);

            if (coreBE != null) {
                pEntity.pushCoreFluidToNeighbors(coreBE);
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void pushCoreFluidToNeighbors(BlockEntity core) {
        int pushAmount = 100;

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = worldPosition.relative(dir);
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.is(ModBlocks.GENERIC_BOUNDING_BOX.get()) || neighborState.is(core.getBlockState().getBlock())) {
                continue;
            }

            BlockEntity neighborBE = level.getBlockEntity(neighborPos);
            if (neighborBE == null) continue;

            neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(neighborHandler -> {

                if (core instanceof OilDerrickBlockEntity derrick) {
                    FluidTank[] derrickTanks = {
                            derrick.getFluidTank(),
                            derrick.getOutputTank()
                    };

                    for (FluidTank tank : derrickTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        String currentFluidName = ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();
                            if (!filter.isEmpty() && !filter.equals(currentFluidName)) {
                                continue;
                            }
                        }

                        FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                        int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                        if (accepted > 0) {
                            tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                            break;
                        }
                    }
                }

                // --- LOGIC FOR OIL REFINERY ---
                else if (core instanceof OilRefineryBlockEntity refinery) {
                    FluidTank[] outputTanks = {
                            refinery.getHeavyOilTank(),
                            refinery.getNaphthaTank(),
                            refinery.getLightOilTank(),
                            refinery.getPetroleumGasTank()
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();
                            boolean isDuctEmpty = duct.getCapability(ForgeCapabilities.FLUID_HANDLER)
                                    .map(h -> h.getFluidInTank(0).isEmpty()).orElse(true);

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                            if (filter.isEmpty() && isDuctEmpty) {
                                continue;
                            }

                            if (!filter.isEmpty() && !filter.equals(currentFluidName)) {
                                continue;
                            }
                        }

                        int simulateAccepted = neighborHandler.fill(stack, IFluidHandler.FluidAction.SIMULATE);
                        if (simulateAccepted > 0) {
                            FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                            if (accepted > 0) {
                                tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                break;
                            }
                        }
                    }
                }

                else if (core instanceof FractionatingTowerBlockEntity tower) {
                    FluidTank[] tanks = { tower.getOutputTank(), tower.getOutputTank2() };

                    for (FluidTank tank : tanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();

                            if (filter.isEmpty()) {
                                continue;
                            }

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }
                        }

                        FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                        int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                        if (accepted > 0) {
                            tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                            break;
                        }
                    }
                }

                else if (core instanceof BoilerBlockEntity boiler) {
                    FluidTank outputTank = boiler.getSteamTank();
                    FluidStack stack = outputTank.getFluid();

                    if (!stack.isEmpty()) {
                        FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                        int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                        if (accepted > 0) {
                            outputTank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                }
                else if (core instanceof CatalyticCrackingTowerBlockEntity crackingTower) {
                    FluidTank[] outputTanks = {
                            crackingTower.getOutputTank1(),
                            crackingTower.getOutputTank2()
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();

                            if (filter.isEmpty()) continue;

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }

                            int amountToPush = Math.min(stack.getAmount(), pushAmount);
                            FluidStack toPush = new FluidStack(stack.getFluid(), amountToPush);

                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                            if (accepted > 0) {
                                tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                break;
                            }
                        }
                    }
                }
                else if (core instanceof ElectrolysisMachineBlockEntity electrolysisMachine) {
                    FluidTank[] outputTanks = {
                            electrolysisMachine.getOutputTank1(),
                            electrolysisMachine.getOutputTank2()
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();

                            if (filter.isEmpty()) continue;

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }

                            int amountToPush = Math.min(stack.getAmount(), pushAmount);
                            FluidStack toPush = new FluidStack(stack.getFluid(), amountToPush);

                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                            if (accepted > 0) {
                                tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                break;
                            }
                        }
                    }
                }
                else if (core instanceof BedrockOreProcessorBlockEntity bedrockOreProcessor) {
                    FluidTank[] outputTanks = {
                            bedrockOreProcessor.getOutputTank1()
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();

                            if (filter.isEmpty()) continue;

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }

                            int amountToPush = Math.min(stack.getAmount(), pushAmount);
                            FluidStack toPush = new FluidStack(stack.getFluid(), amountToPush);

                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                            if (accepted > 0) {
                                tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                break;
                            }
                        }
                    }
                }
                else if (core instanceof PyrolysisOvenBlockEntity pyrolysisOven) {
                    FluidTank[] outputTanks = {
                            pyrolysisOven.getOutputTank1()
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();

                            if (filter.isEmpty()) continue;

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }

                            int amountToPush = Math.min(stack.getAmount(), pushAmount);
                            FluidStack toPush = new FluidStack(stack.getFluid(), amountToPush);

                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                            if (accepted > 0) {
                                tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                break;
                            }
                        }
                    }
                }
                else if (core instanceof DeuteriumExtractionTowerBlockEntity deuteriumExtractionTower) {
                    FluidTank[] outputTanks = {
                            deuteriumExtractionTower.getOutputTank()
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();

                            if (filter.isEmpty()) continue;

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }

                            int amountToPush = Math.min(stack.getAmount(), pushAmount);
                            FluidStack toPush = new FluidStack(stack.getFluid(), amountToPush);

                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                            if (accepted > 0) {
                                tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                break;
                            }
                        }
                    }
                }

                else if (core instanceof WaterTankBlockEntity waterTank) {
                    if (waterTank.isOutputting()) {
                        FluidTank tank = waterTank.getFluidTank();
                        FluidStack stack = tank.getFluid();

                        if (!stack.isEmpty()) {
                            if (neighborBE instanceof FluidBlockEntity duct) {
                                String filter = duct.getAllowedFluid();
                                if (!filter.isEmpty()) {
                                    String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();
                                    if (!filter.equals(currentFluidName)) {
                                        return;
                                    }
                                }
                            }

                            FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                            if (accepted > 0) {
                                tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                            }
                        }
                    }
                }

                else if (core instanceof VacuumRefineryBlockEntity vacuum) {
                    FluidTank[] outputTanks = {
                            vacuum.getVacuumHeavyOilTank(),
                            vacuum.getReformateTank(),
                            vacuum.getVacuumLightOilTank(),
                            vacuum.getSourGasTank()
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();
                            if (filter.isEmpty()) continue;

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();
                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }
                        }

                        FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                        int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                        if (accepted > 0) {
                            tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                            break;
                        }
                    }
                }

                else if (core instanceof CatalyticReformerBlockEntity vacuum) {
                    FluidTank[] outputTanks = {
                            vacuum.getTank1(),
                            vacuum.getTank2(),
                            vacuum.getTank3(),
                    };

                    for (FluidTank tank : outputTanks) {
                        FluidStack stack = tank.getFluid();
                        if (stack.isEmpty()) continue;

                        if (neighborBE instanceof FluidBlockEntity duct) {
                            String filter = duct.getAllowedFluid();
                            if (filter.isEmpty()) continue;

                            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();
                            if (!filter.equals(currentFluidName)) {
                                continue;
                            }
                        }

                        FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                        int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                        if (accepted > 0) {
                            tank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                            break;
                        }
                    }
                }

                else if (core instanceof CompressorBlockEntity compressor) {
                    Direction machineFacing = core.getBlockState().getValue(CompressorBlock.FACING);
                    Direction rightSide = machineFacing.getClockWise();

                    if (dir == rightSide) {
                        FluidTank outputTank = compressor.getOutputTank();
                        FluidStack stack = outputTank.getFluid();

                        if (!stack.isEmpty()) {
                            FluidStack toPush = new FluidStack(stack.getFluid(), Math.min(stack.getAmount(), pushAmount));
                            int accepted = neighborHandler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                            if (accepted > 0) {
                                outputTank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                            }
                        }
                    }
                }
            });
        }
    }

    public BlockPos getCorePos() {
        return corePos;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.corePos = NbtUtils.readBlockPos(tag.getCompound("CorePos"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("CorePos", NbtUtils.writeBlockPos(this.corePos));
    }
}
