package net.minecraft.util.profiling.jfr.parse;

import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import net.minecraft.util.profiling.jfr.event.ClientFpsEvent;
import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
import net.minecraft.util.profiling.jfr.stats.ChunkIdentification;
import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
import net.minecraft.util.profiling.jfr.stats.FileIOStat;
import net.minecraft.util.profiling.jfr.stats.FpsStat;
import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
import net.minecraft.util.profiling.jfr.stats.IoSummary;
import net.minecraft.util.profiling.jfr.stats.PacketIdentification;
import net.minecraft.util.profiling.jfr.stats.StructureGenStat;
import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
import net.minecraft.util.profiling.jfr.stats.TickTimeStat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/parse/JfrStatsParser.class */
public class JfrStatsParser {
    private int garbageCollections;
    private Instant recordingStarted = Instant.EPOCH;
    private Instant recordingEnded = Instant.EPOCH;
    private final List<ChunkGenStat> chunkGenStats = new ArrayList();
    private final List<StructureGenStat> structureGenStats = new ArrayList();
    private final List<CpuLoadStat> cpuLoadStat = new ArrayList();
    private final Map<PacketIdentification, MutableCountAndSize> receivedPackets = new HashMap();
    private final Map<PacketIdentification, MutableCountAndSize> sentPackets = new HashMap();
    private final Map<ChunkIdentification, MutableCountAndSize> readChunks = new HashMap();
    private final Map<ChunkIdentification, MutableCountAndSize> writtenChunks = new HashMap();
    private final List<FileIOStat> fileWrites = new ArrayList();
    private final List<FileIOStat> fileReads = new ArrayList();
    private Duration gcTotalDuration = Duration.ZERO;
    private final List<GcHeapStat> gcHeapStats = new ArrayList();
    private final List<ThreadAllocationStat> threadAllocationStats = new ArrayList();
    private final List<FpsStat> fps = new ArrayList();
    private final List<TickTimeStat> serverTickTimes = new ArrayList();
    private Duration worldCreationDuration = null;

    private JfrStatsParser(Stream<RecordedEvent> $$0) {
        capture($$0);
    }

    public static JfrStatsResult parse(Path $$0) {
        try {
            final RecordingFile $$1 = new RecordingFile($$0);
            try {
                Iterator<RecordedEvent> $$2 = new Iterator<RecordedEvent>() { // from class: net.minecraft.util.profiling.jfr.parse.JfrStatsParser.1
                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return $$1.hasMoreEvents();
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // java.util.Iterator
                    public RecordedEvent next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        try {
                            return $$1.readEvent();
                        } catch (IOException $$02) {
                            throw new UncheckedIOException($$02);
                        }
                    }
                };
                Stream<RecordedEvent> $$3 = StreamSupport.stream(Spliterators.spliteratorUnknownSize($$2, 1297), false);
                JfrStatsResult jfrStatsResultResults = new JfrStatsParser($$3).results();
                $$1.close();
                return jfrStatsResultResults;
            } finally {
            }
        } catch (IOException $$4) {
            throw new UncheckedIOException($$4);
        }
    }

    private JfrStatsResult results() {
        Duration $$0 = Duration.between(this.recordingStarted, this.recordingEnded);
        return new JfrStatsResult(this.recordingStarted, this.recordingEnded, $$0, this.worldCreationDuration, this.fps, this.serverTickTimes, this.cpuLoadStat, GcHeapStat.summary($$0, this.gcHeapStats, this.gcTotalDuration, this.garbageCollections), ThreadAllocationStat.summary(this.threadAllocationStats), collectIoStats($$0, this.receivedPackets), collectIoStats($$0, this.sentPackets), collectIoStats($$0, this.writtenChunks), collectIoStats($$0, this.readChunks), FileIOStat.summary($$0, this.fileWrites), FileIOStat.summary($$0, this.fileReads), this.chunkGenStats, this.structureGenStats);
    }

    private void capture(Stream<RecordedEvent> $$0) {
        $$0.forEach($$02 -> {
            if ($$02.getEndTime().isAfter(this.recordingEnded) || this.recordingEnded.equals(Instant.EPOCH)) {
                this.recordingEnded = $$02.getEndTime();
            }
            if ($$02.getStartTime().isBefore(this.recordingStarted) || this.recordingStarted.equals(Instant.EPOCH)) {
                this.recordingStarted = $$02.getStartTime();
            }
            switch ($$02.getEventType().getName()) {
                case "minecraft.ChunkGeneration":
                    this.chunkGenStats.add(ChunkGenStat.from($$02));
                    break;
                case "minecraft.StructureGeneration":
                    this.structureGenStats.add(StructureGenStat.from($$02));
                    break;
                case "minecraft.LoadWorld":
                    this.worldCreationDuration = $$02.getDuration();
                    break;
                case "minecraft.ClientFps":
                    this.fps.add(FpsStat.from($$02, ClientFpsEvent.Fields.FPS));
                    break;
                case "minecraft.ServerTickTime":
                    this.serverTickTimes.add(TickTimeStat.from($$02));
                    break;
                case "minecraft.PacketReceived":
                    incrementPacket($$02, $$02.getInt("bytes"), this.receivedPackets);
                    break;
                case "minecraft.PacketSent":
                    incrementPacket($$02, $$02.getInt("bytes"), this.sentPackets);
                    break;
                case "minecraft.ChunkRegionRead":
                    incrementChunk($$02, $$02.getInt("bytes"), this.readChunks);
                    break;
                case "minecraft.ChunkRegionWrite":
                    incrementChunk($$02, $$02.getInt("bytes"), this.writtenChunks);
                    break;
                case "jdk.ThreadAllocationStatistics":
                    this.threadAllocationStats.add(ThreadAllocationStat.from($$02));
                    break;
                case "jdk.GCHeapSummary":
                    this.gcHeapStats.add(GcHeapStat.from($$02));
                    break;
                case "jdk.CPULoad":
                    this.cpuLoadStat.add(CpuLoadStat.from($$02));
                    break;
                case "jdk.FileWrite":
                    appendFileIO($$02, this.fileWrites, "bytesWritten");
                    break;
                case "jdk.FileRead":
                    appendFileIO($$02, this.fileReads, "bytesRead");
                    break;
                case "jdk.GarbageCollection":
                    this.garbageCollections++;
                    this.gcTotalDuration = this.gcTotalDuration.plus($$02.getDuration());
                    break;
            }
        });
    }

    private void incrementPacket(RecordedEvent $$0, int $$1, Map<PacketIdentification, MutableCountAndSize> $$2) {
        $$2.computeIfAbsent(PacketIdentification.from($$0), $$02 -> {
            return new MutableCountAndSize();
        }).increment($$1);
    }

    private void incrementChunk(RecordedEvent $$0, int $$1, Map<ChunkIdentification, MutableCountAndSize> $$2) {
        $$2.computeIfAbsent(ChunkIdentification.from($$0), $$02 -> {
            return new MutableCountAndSize();
        }).increment($$1);
    }

    private void appendFileIO(RecordedEvent $$0, List<FileIOStat> $$1, String $$2) {
        $$1.add(new FileIOStat($$0.getDuration(), $$0.getString("path"), $$0.getLong($$2)));
    }

    private static <T> IoSummary<T> collectIoStats(Duration $$0, Map<T, MutableCountAndSize> $$1) {
        List<Pair<T, IoSummary.CountAndSize>> $$2 = $$1.entrySet().stream().map($$02 -> {
            return Pair.of($$02.getKey(), ((MutableCountAndSize) $$02.getValue()).toCountAndSize());
        }).toList();
        return new IoSummary<>($$0, $$2);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/parse/JfrStatsParser$MutableCountAndSize.class */
    public static final class MutableCountAndSize {
        private long count;
        private long totalSize;

        public void increment(int $$0) {
            this.totalSize += (long) $$0;
            this.count++;
        }

        public IoSummary.CountAndSize toCountAndSize() {
            return new IoSummary.CountAndSize(this.count, this.totalSize);
        }
    }
}
