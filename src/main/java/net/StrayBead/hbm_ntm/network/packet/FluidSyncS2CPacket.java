package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.StrayBead.hbm_ntm.screen.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidSyncS2CPacket {
    private final FluidStack fluidStack;
    private final BlockPos pos;
    private final int tankID;

    public FluidSyncS2CPacket(FluidStack fluidStack, BlockPos pos) {
        this.fluidStack = fluidStack;
        this.pos = pos;
        this.tankID = 0;
    }

    public FluidSyncS2CPacket(FluidStack fluidStack, BlockPos pos, int tankID) {
        this.fluidStack = fluidStack;
        this.pos = pos;
        this.tankID = tankID;
    }

    public FluidSyncS2CPacket(FriendlyByteBuf buf) {
        this.fluidStack = buf.readFluidStack();
        this.pos = buf.readBlockPos();
        this.tankID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluidStack);
        buf.writeBlockPos(pos);
        buf.writeInt(tankID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof BoilerBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof BoilerMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof MeltdownBlastFurnaceBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof MeltdownBlastFurnaceMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof WaterTankBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof BigAssTankGuiMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ShallowFoundryBasinBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof CrucibleBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof FlareStackBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ZirnoxNuclearReactorBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof ZirnoxNuclearReactorGuiMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof OilDerrickBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof OilDerrickMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof SiloLaunchPadBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof SiloLaunchPadMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof SolderingStationBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof SolderingStationMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ArcWelderBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof ArcWelderMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof OilRefineryBlockEntity refinery) {
                switch (tankID) {
                    case 0 -> refinery.setFluid(this.fluidStack);
                    case 1 -> refinery.setHeavyOil(this.fluidStack);
                    case 2 -> refinery.setNaphtha(this.fluidStack);
                    case 3 -> refinery.setLightOil(this.fluidStack);
                    case 4 -> refinery.setPetroleumGas(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ChemicalPlantBlockEntity chemicalPlant) {
                switch (tankID) {
                    case 0 -> chemicalPlant.setFluid(this.fluidStack);
                    case 1 -> chemicalPlant.setInputTank2(this.fluidStack);
                    case 2 -> chemicalPlant.setInputTank3(this.fluidStack);
                    case 3 -> chemicalPlant.setOutputTank1(this.fluidStack);
                    case 4 -> chemicalPlant.setOutputTank2(this.fluidStack);
                    case 5 -> chemicalPlant.setOutputTank3(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof VacuumRefineryBlockEntity refinery) {
                switch (tankID) {
                    case 0 -> refinery.setFluid(this.fluidStack);
                    case 1 -> refinery.setVacuumHeavyOil(this.fluidStack);
                    case 2 -> refinery.setReformate(this.fluidStack);
                    case 3 -> refinery.setVacuumLightOil(this.fluidStack);
                    case 4 -> refinery.setSourGas(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof CatalyticReformerBlockEntity refinery) {
                switch (tankID) {
                    case 0 -> refinery.setFluid(this.fluidStack);
                    case 1 -> refinery.setTank1(this.fluidStack);
                    case 2 -> refinery.setTank2(this.fluidStack);
                    case 3 -> refinery.setTank3(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof DeuteriumExtractionTowerBlockEntity deuteriumExtractionTower) {
                switch (tankID) {
                    case 0 -> deuteriumExtractionTower.setFluid(this.fluidStack);
                    case 1 -> deuteriumExtractionTower.setOutputTank(this.fluidStack);
                }
            } else if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof CompressorBlockEntity refinery) {
                switch (tankID) {
                    case 0 -> refinery.setFluid(this.fluidStack);
                    case 1 -> refinery.setOutputTank(this.fluidStack);
                }
            }
        });
        return true;
    }
}
