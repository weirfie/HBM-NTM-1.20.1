package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import java.util.HashMap;
import java.util.Map;

public class FlyingTerrainEntity extends Entity implements IEntityAdditionalSpawnData {
    // Map relative positions to the block state
    private final Map<BlockPos, BlockState> blocks = new HashMap<>();
    public float rotationX, rotationY, rotationZ;
    public float rotSpeedX, rotSpeedY, rotSpeedZ;

    public FlyingTerrainEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public FlyingTerrainEntity(Level level, BlockPos origin, int radius) {
        this(ModEntities.FLYING_TERRAIN.get(), level);
        this.setPos(origin.getX() + 0.5, origin.getY(), origin.getZ() + 0.5);

        // Random rotation speeds
        this.rotSpeedX = level.random.nextFloat() * 5.0f;
        this.rotSpeedY = level.random.nextFloat() * 5.0f;
        this.rotSpeedZ = level.random.nextFloat() * 5.0f;

        captureTerrain(level, origin, radius);
    }

    private void captureTerrain(Level level, BlockPos origin, int radius) {
        // Iterate in a small area
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))) {
            // Check distance for a "jagged sphere" look
            if (pos.distSqr(origin) <= radius * radius && level.random.nextFloat() > 0.3f) {
                BlockState state = level.getBlockState(pos);
                // Don't pick up air, water, or bedrock
                if (!state.isAir() && state.getDestroySpeed(level, pos) >= 0) {
                    blocks.put(pos.immutable().subtract(origin), state);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().add(0, -0.04, 0)); // Gravity
        this.move(MoverType.SELF, this.getDeltaMovement());

        this.rotationX += rotSpeedX;
        this.rotationY += rotSpeedY;
        this.rotationZ += rotSpeedZ;

        if (this.level().isClientSide) {
            // Spawn 2-3 particles per tick for a "thick" smoke trail
            for (int i = 0; i < 2; i++) {
                // High variation in gray: 0.1 (dark) to 0.6 (light)
                float gray = 0.1f + this.random.nextFloat() * 0.5f;

                // Randomize position slightly so it's not a perfect line
                float ox = (this.random.nextFloat() - 0.5f) * 0.8f;
                float oy = (this.random.nextFloat() - 0.5f) * 0.8f;
                float oz = (this.random.nextFloat() - 0.5f) * 0.8f;

                net.StrayBead.hbm_ntm.render.custom.ExplosionParticleManager.addParticle(
                        (float) this.getX() + ox,
                        (float) this.getY() + oy + 0.5f,
                        (float) this.getZ() + oz,
                        1.2f + this.random.nextFloat() * 0.8f,
                        gray, gray, gray,
                        0.8f,
                        0, 0.02f, 0,
                        70 + this.random.nextInt(30),
                        0.0f,
                        true
                );
            }
        }

        if (this.onGround() || this.horizontalCollision) {
            if (!level().isClientSide) {
                for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
                    BlockPos target = this.blockPosition().offset(entry.getKey());
                    if (level().getBlockState(target).isAir()) {
                        level().setBlock(target, entry.getValue(), 3);
                    }
                }
                this.discard();
            }
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        // Send the size of the map
        buffer.writeInt(this.blocks.size());
        for (Map.Entry<BlockPos, BlockState> entry : this.blocks.entrySet()) {
            // Send the relative position
            buffer.writeBlockPos(entry.getKey());
            // Send the BlockState (via its global ID)
            buffer.writeInt(Block.getId(entry.getValue()));
        }
        // Also sync the rotation speeds so they look the same on both sides
        buffer.writeFloat(this.rotSpeedX);
        buffer.writeFloat(this.rotSpeedY);
        buffer.writeFloat(this.rotSpeedZ);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        this.blocks.clear();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            BlockPos pos = buffer.readBlockPos();
            BlockState state = Block.stateById(buffer.readInt());
            this.blocks.put(pos, state);
        }
        this.rotSpeedX = buffer.readFloat();
        this.rotSpeedY = buffer.readFloat();
        this.rotSpeedZ = buffer.readFloat();
    }

    @Override protected void defineSynchedData() {}
    @Override protected void readAdditionalSaveData(CompoundTag pCompound) {}
    @Override protected void addAdditionalSaveData(CompoundTag pCompound) {}
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

    public Map<BlockPos, BlockState> getBlocks() { return blocks; }
}
