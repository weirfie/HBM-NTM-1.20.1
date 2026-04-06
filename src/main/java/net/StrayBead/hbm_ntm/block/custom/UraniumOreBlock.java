package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.entity.custom.ShockwaveProjectileEntity;
import net.StrayBead.hbm_ntm.particle.ModParticles;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UraniumOreBlock extends Block {
    private boolean isActive = false;
    private int counter = 0;

    public UraniumOreBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void tick(BlockState p_222945_, ServerLevel level, BlockPos pos, RandomSource p_222948_) {
        super.tick(p_222945_, level, pos, p_222948_);
        if(isActive) {
            counter++;
            if(counter > 300) {
                level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(pos.getX(), pos.getY() + 1, pos.getZ()), Vec2.ZERO, level, 4, "", Component.literal(""), level.getServer(), null).withSuppressedOutput(),
                        "kill @e[type=hbm_ntm:shockwave_projectile]");
            }
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(blockState, world, pos, neighborBlock, fromPos, moving);

        int signalStrength = world.getBestNeighborSignal(pos);

        if (signalStrength > 0) {
            isActive = true;
            for (Entity entityiterator : new ArrayList<>(world.players())) {
                if (!world.isClientSide()) {
                    world.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("hbm_ntm:nuclear_explosion")), SoundSource.MASTER, 4, 1);
                } else {
                    world.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("hbm_ntm:nuclear_explosion")), SoundSource.MASTER, 4, 1, false);
                }
            }

            createMushroomCloud(world, pos);

            createNuclearCrater(world, pos, 30);

            ShockwaveProjectileEntity.summonProjectilesInCircle(world, pos.getX(), pos.getY() + 70, pos.getZ(), 1000, 20.0);

            if (world instanceof ServerLevel _level) {
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(pos.getX(), pos.getY() + 1, pos.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                        "particle hbm_ntm:smoke ~ ~-40 ~ 5 50 5 0 5000 force @a");
            }
            if (world instanceof ServerLevel _level) {
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(pos.getX(), pos.getY() + 1, pos.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                        "particle hbm_ntm:smoke ~ ~40 ~ 25 25 25 0 10000 force @a");
            }
            final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                entityiterator.setSecondsOnFire(100);
            }
        }
    }


    // Method to create the mushroom cloud effect
    private void createMushroomCloud(Level world, BlockPos pos) {

        // Step 2: Create mushroom cloud particles
        spawnMushroomCloudParticles(world, pos);

        // Step 3: Play explosion sound effect
        world.playSound(null, pos, net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE,
                net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    // Method to spawn mushroom cloud particles
    private void spawnMushroomCloudParticles(Level world, BlockPos pos) {
        for (int i = 0; i < 100; i++) {  // 100 particles
            double x = pos.getX() + world.random.nextGaussian();
            double y = pos.getY() + world.random.nextGaussian() * 0.5;  // Adjust vertical movement
            double z = pos.getZ() + world.random.nextGaussian();
            world.addParticle(ModParticles.SMOKE_PARTICLE.get(), x, y, z, 0, 0.1, 0);
        }
    }

    private void createNuclearCrater(Level world, BlockPos pos, int radius) {
        int minX = pos.getX() - radius;
        int maxX = pos.getX() + radius;
        int minZ = pos.getZ() - radius;
        int maxZ = pos.getZ() + radius;

        int maxDepth = 5;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(z - pos.getZ(), 2));

                if (distance < radius) {
                    int depth = (int) (maxDepth * (1 - (distance / radius)));

                    for (int y = pos.getY(); y >= pos.getY() - depth; y--) {
                        BlockPos targetPos = new BlockPos(x, y, z);
                        BlockState blockState = world.getBlockState(targetPos);

                        if (blockState.getBlock() != Blocks.AIR) {
                            world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
