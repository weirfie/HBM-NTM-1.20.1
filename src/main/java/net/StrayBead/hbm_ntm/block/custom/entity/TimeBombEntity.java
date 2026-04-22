package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.custom.Explosion;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TimeBombEntity extends BlockEntity {
    private int ticksLeft = 0;
    private boolean isArmed = false;

    public TimeBombEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.TIME_BOMB.get(), pos, state);
    }

    public void addSeconds(int seconds) {
        if (!isArmed) {
            this.ticksLeft += seconds * 20;
            this.setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void arm() {
        this.isArmed = true;
        this.setChanged();
    }

    public int getTicksLeft() { return ticksLeft; }

    public static void tick(Level level, BlockPos pos, BlockState state, TimeBombEntity be) {
        if (be.isArmed && be.ticksLeft > 0) {
            be.ticksLeft--;

            if (be.ticksLeft % 20 == 0) {
                level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.BLOCKS, 1.0f, 2.0f);
                level.sendBlockUpdated(pos, state, state, 3);
            }

            if (be.ticksLeft <= 0 && !level.isClientSide) {
                Explosion.create((ServerLevel) level, pos.getX(), pos.getY(), pos.getZ(), 1, 300);
                level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 6.0f, Level.ExplosionInteraction.TNT);
                level.removeBlock(pos, false);
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.ticksLeft = tag.getInt("timer");
        this.isArmed = tag.getBoolean("armed");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("timer", this.ticksLeft);
        tag.putBoolean("armed", this.isArmed);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
