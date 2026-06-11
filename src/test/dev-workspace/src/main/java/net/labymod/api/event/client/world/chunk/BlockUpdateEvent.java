package net.labymod.api.event.client.world.chunk;

import net.labymod.api.Laby;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.event.Event;
import net.labymod.api.util.math.vector.IntVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/chunk/BlockUpdateEvent.class */
public class BlockUpdateEvent implements Event {
    private final IntVector3 blockPosition;
    private final BlockState blockState;
    private final Chunk chunk;
    private final int flags;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/chunk/BlockUpdateEvent$Flags.class */
    public static class Flags {
        public static final int UPDATE_NEIGHBORS = 1;
        public static final int UPDATE_CLIENTS = 2;
        public static final int UPDATE_INVISIBLE = 4;
        public static final int UPDATE_IMMEDIATE = 8;
        public static final int UPDATE_KNOWN_SHAPE = 16;
        public static final int UPDATE_SUPPRESS_DROPS = 32;
        public static final int UPDATE_MOVE_BY_PISTON = 64;
    }

    public BlockUpdateEvent(BlockState blockState, int flags) {
        this.blockState = blockState;
        this.blockPosition = blockState.position();
        this.flags = flags;
        this.chunk = Laby.references().clientWorld().getChunk(this.blockPosition.getX() >> 4, this.blockPosition.getZ() >> 4);
    }

    public IntVector3 getBlockPosition() {
        return this.blockPosition;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    public int getFlags() {
        return this.flags;
    }
}
