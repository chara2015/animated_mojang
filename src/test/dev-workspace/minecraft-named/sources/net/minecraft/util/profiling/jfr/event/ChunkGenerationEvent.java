package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.Category;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ChunkGenerationEvent.class */
@Category({JfrProfiler.ROOT_CATEGORY, JfrProfiler.WORLD_GEN_CATEGORY})
@Label("Chunk Generation")
@StackTrace(false)
@Enabled(false)
@DontObfuscate
@Name(ChunkGenerationEvent.EVENT_NAME)
public class ChunkGenerationEvent extends Event {
    public static final String EVENT_NAME = "minecraft.ChunkGeneration";
    public static final EventType TYPE = EventType.getEventType(ChunkGenerationEvent.class);

    @Name(Fields.WORLD_POS_X)
    @Label("First Block X World Position")
    public final int worldPosX;

    @Name(Fields.WORLD_POS_Z)
    @Label("First Block Z World Position")
    public final int worldPosZ;

    @Name("chunkPosX")
    @Label("Chunk X Position")
    public final int chunkPosX;

    @Name("chunkPosZ")
    @Label("Chunk Z Position")
    public final int chunkPosZ;

    @Name(Fields.STATUS)
    @Label("Status")
    public final String targetStatus;

    @Name("level")
    @Label("Level")
    public final String level;

    public ChunkGenerationEvent(ChunkPos $$0, ResourceKey<Level> $$1, String $$2) {
        this.targetStatus = $$2;
        this.level = $$1.identifier().toString();
        this.chunkPosX = $$0.x;
        this.chunkPosZ = $$0.z;
        this.worldPosX = $$0.getMinBlockX();
        this.worldPosZ = $$0.getMinBlockZ();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ChunkGenerationEvent$Fields.class */
    public static class Fields {
        public static final String WORLD_POS_X = "worldPosX";
        public static final String WORLD_POS_Z = "worldPosZ";
        public static final String CHUNK_POS_X = "chunkPosX";
        public static final String CHUNK_POS_Z = "chunkPosZ";
        public static final String STATUS = "status";
        public static final String LEVEL = "level";

        private Fields() {
        }
    }
}
