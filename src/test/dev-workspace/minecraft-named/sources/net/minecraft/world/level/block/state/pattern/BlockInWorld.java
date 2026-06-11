package net.minecraft.world.level.block.state.pattern;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/pattern/BlockInWorld.class */
public class BlockInWorld {
    private final LevelReader level;
    private final BlockPos pos;
    private final boolean loadChunks;
    private BlockState state;
    private BlockEntity entity;
    private boolean cachedEntity;

    public BlockInWorld(LevelReader $$0, BlockPos $$1, boolean $$2) {
        this.level = $$0;
        this.pos = $$1.immutable();
        this.loadChunks = $$2;
    }

    public BlockState getState() {
        if (this.state == null && (this.loadChunks || this.level.hasChunkAt(this.pos))) {
            this.state = this.level.getBlockState(this.pos);
        }
        return this.state;
    }

    public BlockEntity getEntity() {
        if (this.entity == null && !this.cachedEntity) {
            this.entity = this.level.getBlockEntity(this.pos);
            this.cachedEntity = true;
        }
        return this.entity;
    }

    public LevelReader getLevel() {
        return this.level;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public static Predicate<BlockInWorld> hasState(Predicate<BlockState> $$0) {
        return $$1 -> {
            return $$1 != null && $$0.test($$1.getState());
        };
    }
}
