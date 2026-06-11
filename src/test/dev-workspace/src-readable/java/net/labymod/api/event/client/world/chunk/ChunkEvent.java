package net.labymod.api.event.client.world.chunk;

import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/chunk/ChunkEvent.class */
public class ChunkEvent implements Event {
    private final Chunk chunk;
    private final Type type;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/chunk/ChunkEvent$Type.class */
    public enum Type {
        LOAD,
        UNLOAD
    }

    private ChunkEvent(Chunk chunk, Type type) {
        this.chunk = chunk;
        this.type = type;
    }

    public static ChunkEvent load(Chunk chunk) {
        return new ChunkEvent(chunk, Type.LOAD);
    }

    public static ChunkEvent unload(Chunk chunk) {
        return new ChunkEvent(chunk, Type.UNLOAD);
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    public Type getType() {
        return this.type;
    }
}
