package net.minecraft.util.profiling.jfr;

import com.mojang.logging.LogUtils;
import java.net.SocketAddress;
import java.nio.file.Path;
import jdk.jfr.FlightRecorder;
import net.minecraft.core.Holder;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.storage.RegionFileVersion;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/JvmProfiler.class */
public interface JvmProfiler {
    public static final JvmProfiler INSTANCE;

    boolean start(Environment environment);

    Path stop();

    boolean isRunning();

    boolean isAvailable();

    void onServerTick(float f);

    void onClientTick(int i);

    void onPacketReceived(ConnectionProtocol connectionProtocol, PacketType<?> packetType, SocketAddress socketAddress, int i);

    void onPacketSent(ConnectionProtocol connectionProtocol, PacketType<?> packetType, SocketAddress socketAddress, int i);

    void onRegionFileRead(RegionStorageInfo regionStorageInfo, ChunkPos chunkPos, RegionFileVersion regionFileVersion, int i);

    void onRegionFileWrite(RegionStorageInfo regionStorageInfo, ChunkPos chunkPos, RegionFileVersion regionFileVersion, int i);

    ProfiledDuration onWorldLoadedStarted();

    ProfiledDuration onChunkGenerate(ChunkPos chunkPos, ResourceKey<Level> resourceKey, String str);

    ProfiledDuration onStructureGenerate(ChunkPos chunkPos, ResourceKey<Level> resourceKey, Holder<Structure> holder);

    static {
        INSTANCE = (Runtime.class.getModule().getLayer().findModule("jdk.jfr").isPresent() && FlightRecorder.isAvailable()) ? JfrProfiler.getInstance() : new NoOpProfiler();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/JvmProfiler$NoOpProfiler.class */
    public static class NoOpProfiler implements JvmProfiler {
        private static final Logger LOGGER = LogUtils.getLogger();
        static final ProfiledDuration noOpCommit = $$0 -> {
        };

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public boolean start(Environment $$0) {
            LOGGER.warn("Attempted to start Flight Recorder, but it's not supported on this JVM");
            return false;
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public Path stop() {
            throw new IllegalStateException("Attempted to stop Flight Recorder, but it's not supported on this JVM");
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public boolean isRunning() {
            return false;
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public boolean isAvailable() {
            return false;
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public void onPacketReceived(ConnectionProtocol $$0, PacketType<?> $$1, SocketAddress $$2, int $$3) {
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public void onPacketSent(ConnectionProtocol $$0, PacketType<?> $$1, SocketAddress $$2, int $$3) {
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public void onRegionFileRead(RegionStorageInfo $$0, ChunkPos $$1, RegionFileVersion $$2, int $$3) {
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public void onRegionFileWrite(RegionStorageInfo $$0, ChunkPos $$1, RegionFileVersion $$2, int $$3) {
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public void onServerTick(float $$0) {
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public void onClientTick(int $$0) {
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public ProfiledDuration onWorldLoadedStarted() {
            return noOpCommit;
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public ProfiledDuration onChunkGenerate(ChunkPos $$0, ResourceKey<Level> $$1, String $$2) {
            return null;
        }

        @Override // net.minecraft.util.profiling.jfr.JvmProfiler
        public ProfiledDuration onStructureGenerate(ChunkPos $$0, ResourceKey<Level> $$1, Holder<Structure> $$2) {
            return noOpCommit;
        }
    }
}
