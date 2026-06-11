package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.Category;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.RegionFileVersion;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ChunkRegionIoEvent.class */
@Enabled(false)
@Category({JfrProfiler.ROOT_CATEGORY, JfrProfiler.STORAGE_CATEGORY})
@StackTrace(false)
public abstract class ChunkRegionIoEvent extends Event {

    @Name(Fields.REGION_POS_X)
    @Label("Region X Position")
    public final int regionPosX;

    @Name(Fields.REGION_POS_Z)
    @Label("Region Z Position")
    public final int regionPosZ;

    @Name(Fields.LOCAL_POS_X)
    @Label("Local X Position")
    public final int localChunkPosX;

    @Name(Fields.LOCAL_POS_Z)
    @Label("Local Z Position")
    public final int localChunkPosZ;

    @Name("chunkPosX")
    @Label("Chunk X Position")
    public final int chunkPosX;

    @Name("chunkPosZ")
    @Label("Chunk Z Position")
    public final int chunkPosZ;

    @Name("level")
    @Label("Level Id")
    public final String levelId;

    @Name(Fields.DIMENSION)
    @Label(ServerPlayer.TAG_DIMENSION)
    public final String dimension;

    @Name(Fields.TYPE)
    @Label("Type")
    public final String type;

    @Name(Fields.COMPRESSION)
    @Label("Compression")
    public final String compression;

    @Name("bytes")
    @Label("Bytes")
    public final int bytes;

    public ChunkRegionIoEvent(RegionStorageInfo $$0, ChunkPos $$1, RegionFileVersion $$2, int $$3) {
        this.regionPosX = $$1.getRegionX();
        this.regionPosZ = $$1.getRegionZ();
        this.localChunkPosX = $$1.getRegionLocalX();
        this.localChunkPosZ = $$1.getRegionLocalZ();
        this.chunkPosX = $$1.x;
        this.chunkPosZ = $$1.z;
        this.levelId = $$0.level();
        this.dimension = $$0.dimension().identifier().toString();
        this.type = $$0.type();
        this.compression = "standard:" + $$2.getId();
        this.bytes = $$3;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ChunkRegionIoEvent$Fields.class */
    public static class Fields {
        public static final String REGION_POS_X = "regionPosX";
        public static final String REGION_POS_Z = "regionPosZ";
        public static final String LOCAL_POS_X = "localPosX";
        public static final String LOCAL_POS_Z = "localPosZ";
        public static final String CHUNK_POS_X = "chunkPosX";
        public static final String CHUNK_POS_Z = "chunkPosZ";
        public static final String LEVEL = "level";
        public static final String DIMENSION = "dimension";
        public static final String TYPE = "type";
        public static final String COMPRESSION = "compression";
        public static final String BYTES = "bytes";

        private Fields() {
        }
    }
}
