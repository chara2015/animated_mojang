package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.RegionFileVersion;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ChunkRegionReadEvent.class */
@DontObfuscate
@Name(ChunkRegionReadEvent.EVENT_NAME)
@Label("Region File Read")
public class ChunkRegionReadEvent extends ChunkRegionIoEvent {
    public static final String EVENT_NAME = "minecraft.ChunkRegionRead";
    public static final EventType TYPE = EventType.getEventType(ChunkRegionReadEvent.class);

    public ChunkRegionReadEvent(RegionStorageInfo $$0, ChunkPos $$1, RegionFileVersion $$2, int $$3) {
        super($$0, $$1, $$2, $$3);
    }
}
