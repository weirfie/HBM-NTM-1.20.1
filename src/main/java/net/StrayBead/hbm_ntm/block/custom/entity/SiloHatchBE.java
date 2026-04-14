package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.custom.SiloHatchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SiloHatchBE extends BlockEntity {
    private boolean open = false;
    public float openProgress = 0f;
    public float prevOpenProgress = 0f;

    public SiloHatchBE(BlockPos pos, BlockState state) {
        super(ModBlockEntites.SILO_HATCH.get(), pos, state);
    }

    public void toggle(boolean open) {
        this.open = open;
        setChanged();
        if (level != null) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SiloHatchBE be) {
        be.prevOpenProgress = be.openProgress;
        float speed = 0.08f;

        boolean isOpen = state.getValue(SiloHatchBlock.OPEN);

        if (isOpen && be.openProgress < 1f) {
            be.openProgress = Math.min(1f, be.openProgress + speed);
        } else if (!isOpen && be.openProgress > 0f) {
            be.openProgress = Math.max(0f, be.openProgress - speed);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.open = tag.getBoolean("open");
        this.openProgress = tag.getFloat("progress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("open", open);
        tag.putFloat("progress", this.openProgress);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

    @Override
    public CompoundTag getUpdateTag() { return saveWithoutMetadata(); }
}