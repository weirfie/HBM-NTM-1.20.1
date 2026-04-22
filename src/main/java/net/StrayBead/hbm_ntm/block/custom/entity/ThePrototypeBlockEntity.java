package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
import net.StrayBead.hbm_ntm.screen.ThePrototypeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ThePrototypeBlockEntity extends BlockEntity implements MenuProvider {
    private boolean isDetonating = false;
    private List<ChunkPos> chunkQueue = new ArrayList<>();
    private int detonationRadius = 0;
    private BlockPos centerPos = null;

    public final ItemStackHandler itemHandler = new ItemStackHandler(14) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    public ThePrototypeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.THE_PROTOTYPE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                }
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("The Prototype");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ThePrototypeMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void startDetonation(BlockPos center, int radius) {
        this.isDetonating = true;
        this.detonationRadius = radius;
        this.centerPos = center;
        this.chunkQueue.clear();

        int minX = (center.getX() - radius) >> 4;
        int maxX = (center.getX() + radius) >> 4;
        int minZ = (center.getZ() - radius) >> 4;
        int maxZ = (center.getZ() + radius) >> 4;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                chunkQueue.add(new ChunkPos(x, z));
            }
        }

        final int centerX = center.getX() >> 4;
        final int centerZ = center.getZ() >> 4;

        chunkQueue.sort((a, b) -> {
            long d1 = (long) (a.x - centerX) * (a.x - centerX) + (long) (a.z - centerZ) * (a.z - centerZ);
            long d2 = (long) (b.x - centerX) * (b.x - centerX) + (long) (b.z - centerZ) * (b.z - centerZ);
            return Long.compare(d1, d2);
        });
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ThePrototypeBlockEntity entity) {
        if (!level.isClientSide && entity.isDetonating) {
            int chunksPerTick = 6;

            for (int i = 0; i < chunksPerTick && !entity.chunkQueue.isEmpty(); i++) {
                ChunkPos cPos = entity.chunkQueue.remove(0);
                entity.clearBlocksInChunk(level, cPos);
            }

            if (entity.chunkQueue.isEmpty()) {
                entity.isDetonating = false;
                level.removeBlock(pos, false);
            }
        }
    }

    private void clearBlocksInChunk(Level world, ChunkPos cPos) {
        if (!(world instanceof ServerLevel serverLevel)) return;

        int rSq = detonationRadius * detonationRadius;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        int minY = Math.max(serverLevel.getMinBuildHeight(), centerPos.getY() - detonationRadius);
        int maxY = Math.min(serverLevel.getMaxBuildHeight(), centerPos.getY() + detonationRadius);

        for (int x = cPos.getMinBlockX(); x <= cPos.getMaxBlockX(); x++) {
            int dx = x - centerPos.getX();
            for (int z = cPos.getMinBlockZ(); z <= cPos.getMaxBlockZ(); z++) {
                int dz = z - centerPos.getZ();

                int distSqXZ = dx * dx + dz * dz;
                if (distSqXZ > rSq) continue;

                for (int y = minY; y <= maxY; y++) {
                    int dy = y - centerPos.getY();

                    if (distSqXZ + (dy * dy) <= rSq) {
                        mutable.set(x, y, z);

                        if (mutable.equals(this.worldPosition)) continue;

                        BlockState targetState = serverLevel.getBlockState(mutable);

                        if (targetState.is(Blocks.BEDROCK) || targetState.isAir()) continue;

                        serverLevel.setBlock(mutable, Blocks.AIR.defaultBlockState(), 2 | 16 | 128);
                    }
                }
            }
        }
    }
}
