package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;

public class PlaceBlock {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        boolean found = false;
        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -200;
        found = false;
        for (int index0 = 0; index0 < 400; index0++) {
            sy = -200;
            for (int index1 = 0; index1 < 400; index1++) {
                sz = -200;
                for (int index2 = 0; index2 < 400; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == Blocks.GRASS_BLOCK) {
                        if (Mth.nextInt(RandomSource.create(), 1, 10) <= 6) {
                            {
                                BlockPos _bp = BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy), Math.floor(z + sz));
                                BlockState _bs = ModBlocks.DEAD_GRASS.get().defaultBlockState();
                                BlockState _bso = world.getBlockState(_bp);
                                for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                                    Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                                    if (_property != null && _bs.getValue(_property) != null)
                                        try {
                                            _bs = _bs.setValue(_property, (Comparable) entry.getValue());
                                        } catch (Exception e) {
                                        }
                                }
                                world.setBlock(_bp, _bs, 3);
                            }
                        } else {
                            {
                                BlockPos _bp = BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy), Math.floor(z + sz));
                                BlockState _bs = Blocks.GRASS_BLOCK.defaultBlockState();
                                BlockState _bso = world.getBlockState(_bp);
                                for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                                    Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                                    if (_property != null && _bs.getValue(_property) != null)
                                        try {
                                            _bs = _bs.setValue(_property, (Comparable) entry.getValue());
                                        } catch (Exception e) {
                                        }
                                }
                                world.setBlock(_bp, _bs, 3);
                            }
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
    }
}
