package net.StrayBead.hbm_ntm.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PylonBlockEntity extends BlockEntity {
    public static final Set<PylonBlockEntity> LOADED_PYLONS = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final List<BlockPos> connections = new ArrayList<>();

    public PylonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.PYLON.get(), pos, state);
    }

    public void addConnection(BlockPos target) {
        if (!connections.contains(target)) {
            connections.add(target);
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.level != null && this.level.isClientSide) {
            LOADED_PYLONS.add(this);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.level != null && this.level.isClientSide) {
            LOADED_PYLONS.remove(this);
        }
    }

    public List<BlockPos> getConnections() { return connections; }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        ListTag list = new ListTag();
        for (BlockPos p : connections) list.add(LongTag.valueOf(p.asLong()));
        tag.put("connections", list);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        connections.clear();
        ListTag list = tag.getList("connections", 4);
        for (int i = 0; i < list.size(); i++) connections.add(BlockPos.of(((LongTag)list.get(i)).getAsLong()));
    }

    @Override public CompoundTag getUpdateTag() { return saveWithoutMetadata(); }
    @Override public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }
}
