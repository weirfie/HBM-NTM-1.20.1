package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.concurrent.atomic.AtomicBoolean;

public class SolarPanelOnTickUpdateProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z)) && world instanceof Level _lvl1 && _lvl1.isDay() && new Object() {
            public boolean canReceiveEnergy(LevelAccessor level, BlockPos pos) {
                AtomicBoolean _retval = new AtomicBoolean(false);
                BlockEntity _ent = level.getBlockEntity(pos);
                if (_ent != null)
                    _ent.getCapability(ForgeCapabilities.ENERGY, Direction.UP).ifPresent(capability -> _retval.set(capability.canReceive()));
                return _retval.get();
            }
        }.canReceiveEnergy(world, BlockPos.containing(x, y - 1, z))) {
            {
                BlockEntity _ent = world.getBlockEntity(BlockPos.containing(x, y - 1, z));
                int _amount = 100;
                if (_ent != null)
                    _ent.getCapability(ForgeCapabilities.ENERGY, Direction.UP).ifPresent(capability -> capability.receiveEnergy(_amount, false));
            }
        }
    }
}
