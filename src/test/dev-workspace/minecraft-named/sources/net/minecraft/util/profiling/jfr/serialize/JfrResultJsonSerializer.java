package net.minecraft.util.profiling.jfr.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.LongSerializationPolicy;
import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.jfr.Percentiles;
import net.minecraft.util.profiling.jfr.event.ChunkGenerationEvent;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.util.profiling.jfr.event.ClientFpsEvent;
import net.minecraft.util.profiling.jfr.event.PacketEvent;
import net.minecraft.util.profiling.jfr.event.StructureGenerationEvent;
import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
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
import net.minecraft.util.profiling.jfr.stats.TimedStatSummary;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/serialize/JfrResultJsonSerializer.class */
public class JfrResultJsonSerializer {
    private static final String BYTES_PER_SECOND = "bytesPerSecond";
    private static final String COUNT = "count";
    private static final String DURATION_NANOS_TOTAL = "durationNanosTotal";
    private static final String TOTAL_BYTES = "totalBytes";
    private static final String COUNT_PER_SECOND = "countPerSecond";
    final Gson gson = new GsonBuilder().setPrettyPrinting().setLongSerializationPolicy(LongSerializationPolicy.DEFAULT).create();

    private static void serializePacketId(PacketIdentification $$0, JsonObject $$1) {
        $$1.addProperty(PacketEvent.Fields.PROTOCOL_ID, $$0.protocolId());
        $$1.addProperty(PacketEvent.Fields.PACKET_ID, $$0.packetId());
    }

    private static void serializeChunkId(ChunkIdentification $$0, JsonObject $$1) {
        $$1.addProperty("level", $$0.level());
        $$1.addProperty(ChunkRegionIoEvent.Fields.DIMENSION, $$0.dimension());
        $$1.addProperty("x", Integer.valueOf($$0.x()));
        $$1.addProperty("z", Integer.valueOf($$0.z()));
    }

    public String format(JfrStatsResult $$0) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty("startedEpoch", Long.valueOf($$0.recordingStarted().toEpochMilli()));
        $$1.addProperty("endedEpoch", Long.valueOf($$0.recordingEnded().toEpochMilli()));
        $$1.addProperty("durationMs", Long.valueOf($$0.recordingDuration().toMillis()));
        Duration $$2 = $$0.worldCreationDuration();
        if ($$2 != null) {
            $$1.addProperty("worldGenDurationMs", Long.valueOf($$2.toMillis()));
        }
        $$1.add("heap", heap($$0.heapSummary()));
        $$1.add("cpuPercent", cpu($$0.cpuLoadStats()));
        $$1.add("network", network($$0));
        $$1.add("fileIO", fileIO($$0));
        $$1.add(ClientFpsEvent.Fields.FPS, fps($$0.fps()));
        $$1.add("serverTick", serverTicks($$0.serverTickTimes()));
        $$1.add("threadAllocation", threadAllocations($$0.threadAllocationSummary()));
        $$1.add("chunkGen", chunkGen($$0.chunkGenSummary()));
        $$1.add("structureGen", structureGen($$0.structureGenStats()));
        return this.gson.toJson($$1);
    }

    private JsonElement heap(GcHeapStat.Summary $$0) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty("allocationRateBytesPerSecond", Double.valueOf($$0.allocationRateBytesPerSecond()));
        $$1.addProperty("gcCount", Integer.valueOf($$0.totalGCs()));
        $$1.addProperty("gcOverHeadPercent", Float.valueOf($$0.gcOverHead()));
        $$1.addProperty("gcTotalDurationMs", Long.valueOf($$0.gcTotalDuration().toMillis()));
        return $$1;
    }

    private JsonElement structureGen(List<StructureGenStat> $$0) {
        JsonObject $$1 = new JsonObject();
        Optional<TimedStatSummary<StructureGenStat>> $$2 = TimedStatSummary.summary($$0);
        if ($$2.isEmpty()) {
            return $$1;
        }
        TimedStatSummary<StructureGenStat> $$3 = $$2.get();
        JsonArray $$4 = new JsonArray();
        $$1.add("structure", $$4);
        ((Map) $$0.stream().collect(Collectors.groupingBy((v0) -> {
            return v0.structureName();
        }))).forEach(($$32, $$42) -> {
            JsonElement jsonElementApply;
            Optional<TimedStatSummary<StructureGenStat>> $$5 = TimedStatSummary.summary($$42);
            if ($$5.isEmpty()) {
                return;
            }
            TimedStatSummary<StructureGenStat> $$6 = $$5.get();
            JsonObject $$7 = new JsonObject();
            $$4.add($$7);
            $$7.addProperty(JigsawBlockEntity.NAME, $$32);
            $$7.addProperty(COUNT, Integer.valueOf($$6.count()));
            $$7.addProperty(DURATION_NANOS_TOTAL, Long.valueOf($$6.totalDuration().toNanos()));
            $$7.addProperty("durationNanosAvg", Long.valueOf($$6.totalDuration().toNanos() / ((long) $$6.count())));
            JsonObject $$8 = (JsonObject) Util.make(new JsonObject(), $$12 -> {
                $$7.add("durationNanosPercentiles", $$12);
            });
            $$6.percentilesNanos().forEach(($$13, $$22) -> {
                $$8.addProperty("p" + $$13, $$22);
            });
            Function<StructureGenStat, JsonElement> $$9 = $$02 -> {
                JsonObject $$14 = new JsonObject();
                $$14.addProperty("durationNanos", Long.valueOf($$02.duration().toNanos()));
                $$14.addProperty("chunkPosX", Integer.valueOf($$02.chunkPos().x));
                $$14.addProperty("chunkPosZ", Integer.valueOf($$02.chunkPos().z));
                $$14.addProperty("structureName", $$02.structureName());
                $$14.addProperty("level", $$02.level());
                $$14.addProperty(StructureGenerationEvent.Fields.SUCCESS, Boolean.valueOf($$02.success()));
                return $$14;
            };
            $$1.add("fastest", $$9.apply((StructureGenStat) $$3.fastest()));
            $$1.add("slowest", $$9.apply((StructureGenStat) $$3.slowest()));
            if ($$3.secondSlowest() != null) {
                jsonElementApply = $$9.apply((StructureGenStat) $$3.secondSlowest());
            } else {
                jsonElementApply = JsonNull.INSTANCE;
            }
            $$1.add("secondSlowest", jsonElementApply);
        });
        return $$1;
    }

    private JsonElement chunkGen(List<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>> $$0) {
        JsonElement jsonElementApply;
        JsonObject $$1 = new JsonObject();
        if ($$0.isEmpty()) {
            return $$1;
        }
        $$1.addProperty(DURATION_NANOS_TOTAL, Double.valueOf($$0.stream().mapToDouble($$02 -> {
            return ((TimedStatSummary) $$02.getSecond()).totalDuration().toNanos();
        }).sum()));
        JsonArray $$2 = (JsonArray) Util.make(new JsonArray(), $$12 -> {
            $$1.add(ChunkGenerationEvent.Fields.STATUS, $$12);
        });
        for (Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>> $$3 : $$0) {
            TimedStatSummary<ChunkGenStat> $$4 = (TimedStatSummary) $$3.getSecond();
            JsonObject jsonObject = new JsonObject();
            Objects.requireNonNull($$2);
            JsonObject $$5 = (JsonObject) Util.make(jsonObject, (v1) -> {
                r1.add(v1);
            });
            $$5.addProperty(StructureTemplate.BLOCK_TAG_STATE, ((ChunkStatus) $$3.getFirst()).toString());
            $$5.addProperty(COUNT, Integer.valueOf($$4.count()));
            $$5.addProperty(DURATION_NANOS_TOTAL, Long.valueOf($$4.totalDuration().toNanos()));
            $$5.addProperty("durationNanosAvg", Long.valueOf($$4.totalDuration().toNanos() / ((long) $$4.count())));
            JsonObject $$6 = (JsonObject) Util.make(new JsonObject(), $$13 -> {
                $$5.add("durationNanosPercentiles", $$13);
            });
            $$4.percentilesNanos().forEach(($$14, $$22) -> {
                $$6.addProperty("p" + $$14, $$22);
            });
            Function<ChunkGenStat, JsonElement> $$7 = $$03 -> {
                JsonObject $$15 = new JsonObject();
                $$15.addProperty("durationNanos", Long.valueOf($$03.duration().toNanos()));
                $$15.addProperty("level", $$03.level());
                $$15.addProperty("chunkPosX", Integer.valueOf($$03.chunkPos().x));
                $$15.addProperty("chunkPosZ", Integer.valueOf($$03.chunkPos().z));
                $$15.addProperty(ChunkGenerationEvent.Fields.WORLD_POS_X, Integer.valueOf($$03.worldPos().x()));
                $$15.addProperty(ChunkGenerationEvent.Fields.WORLD_POS_Z, Integer.valueOf($$03.worldPos().z()));
                return $$15;
            };
            $$5.add("fastest", $$7.apply((ChunkGenStat) $$4.fastest()));
            $$5.add("slowest", $$7.apply((ChunkGenStat) $$4.slowest()));
            if ($$4.secondSlowest() != null) {
                jsonElementApply = $$7.apply((ChunkGenStat) $$4.secondSlowest());
            } else {
                jsonElementApply = JsonNull.INSTANCE;
            }
            $$5.add("secondSlowest", jsonElementApply);
        }
        return $$1;
    }

    private JsonElement threadAllocations(ThreadAllocationStat.Summary $$0) {
        JsonArray $$1 = new JsonArray();
        $$0.allocationsPerSecondByThread().forEach(($$12, $$2) -> {
            $$1.add((JsonElement) Util.make(new JsonObject(), $$2 -> {
                $$2.addProperty("thread", $$12);
                $$2.addProperty(BYTES_PER_SECOND, $$2);
            }));
        });
        return $$1;
    }

    private JsonElement serverTicks(List<TickTimeStat> $$0) {
        if ($$0.isEmpty()) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$1 = new JsonObject();
        double[] $$2 = $$0.stream().mapToDouble($$02 -> {
            return $$02.currentAverage().toNanos() / 1000000.0d;
        }).toArray();
        DoubleSummaryStatistics $$3 = DoubleStream.of($$2).summaryStatistics();
        $$1.addProperty("minMs", Double.valueOf($$3.getMin()));
        $$1.addProperty("averageMs", Double.valueOf($$3.getAverage()));
        $$1.addProperty("maxMs", Double.valueOf($$3.getMax()));
        Map<Integer, Double> $$4 = Percentiles.evaluate($$2);
        $$4.forEach(($$12, $$22) -> {
            $$1.addProperty("p" + $$12, $$22);
        });
        return $$1;
    }

    private JsonElement fps(List<FpsStat> $$0) {
        if ($$0.isEmpty()) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$1 = new JsonObject();
        int[] $$2 = $$0.stream().mapToInt((v0) -> {
            return v0.fps();
        }).toArray();
        IntSummaryStatistics $$3 = IntStream.of($$2).summaryStatistics();
        $$1.addProperty("minFPS", Integer.valueOf($$3.getMin()));
        $$1.addProperty("averageFPS", Double.valueOf($$3.getAverage()));
        $$1.addProperty("maxFPS", Integer.valueOf($$3.getMax()));
        Map<Integer, Double> $$4 = Percentiles.evaluate($$2);
        $$4.forEach(($$12, $$22) -> {
            $$1.addProperty("p" + $$12, $$22);
        });
        return $$1;
    }

    private JsonElement fileIO(JfrStatsResult $$0) {
        JsonObject $$1 = new JsonObject();
        $$1.add("write", fileIoSummary($$0.fileWrites()));
        $$1.add("read", fileIoSummary($$0.fileReads()));
        $$1.add("chunksRead", ioSummary($$0.readChunks(), JfrResultJsonSerializer::serializeChunkId));
        $$1.add("chunksWritten", ioSummary($$0.writtenChunks(), JfrResultJsonSerializer::serializeChunkId));
        return $$1;
    }

    private JsonElement fileIoSummary(FileIOStat.Summary $$0) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty(TOTAL_BYTES, Long.valueOf($$0.totalBytes()));
        $$1.addProperty(COUNT, Long.valueOf($$0.counts()));
        $$1.addProperty(BYTES_PER_SECOND, Double.valueOf($$0.bytesPerSecond()));
        $$1.addProperty(COUNT_PER_SECOND, Double.valueOf($$0.countsPerSecond()));
        JsonArray $$2 = new JsonArray();
        $$1.add("topContributors", $$2);
        $$0.topTenContributorsByTotalBytes().forEach($$12 -> {
            JsonObject $$22 = new JsonObject();
            $$2.add($$22);
            $$22.addProperty("path", (String) $$12.getFirst());
            $$22.addProperty(TOTAL_BYTES, (Number) $$12.getSecond());
        });
        return $$1;
    }

    private JsonElement network(JfrStatsResult $$0) {
        JsonObject $$1 = new JsonObject();
        $$1.add("sent", ioSummary($$0.sentPacketsSummary(), JfrResultJsonSerializer::serializePacketId));
        $$1.add("received", ioSummary($$0.receivedPacketsSummary(), JfrResultJsonSerializer::serializePacketId));
        return $$1;
    }

    private <T> JsonElement ioSummary(IoSummary<T> $$0, BiConsumer<T, JsonObject> $$1) {
        JsonObject $$2 = new JsonObject();
        $$2.addProperty(TOTAL_BYTES, Long.valueOf($$0.getTotalSize()));
        $$2.addProperty(COUNT, Long.valueOf($$0.getTotalCount()));
        $$2.addProperty(BYTES_PER_SECOND, Double.valueOf($$0.getSizePerSecond()));
        $$2.addProperty(COUNT_PER_SECOND, Double.valueOf($$0.getCountsPerSecond()));
        JsonArray $$3 = new JsonArray();
        $$2.add("topContributors", $$3);
        $$0.largestSizeContributors().forEach($$22 -> {
            JsonObject $$32 = new JsonObject();
            $$3.add($$32);
            Object first = $$22.getFirst();
            IoSummary.CountAndSize $$5 = (IoSummary.CountAndSize) $$22.getSecond();
            $$1.accept(first, $$32);
            $$32.addProperty(TOTAL_BYTES, Long.valueOf($$5.totalSize()));
            $$32.addProperty(COUNT, Long.valueOf($$5.totalCount()));
            $$32.addProperty("averageSize", Float.valueOf($$5.averageSize()));
        });
        return $$2;
    }

    private JsonElement cpu(List<CpuLoadStat> $$0) {
        JsonObject $$1 = new JsonObject();
        BiFunction<List<CpuLoadStat>, ToDoubleFunction<CpuLoadStat>, JsonObject> $$2 = ($$02, $$12) -> {
            JsonObject $$22 = new JsonObject();
            DoubleSummaryStatistics $$3 = $$02.stream().mapToDouble($$12).summaryStatistics();
            $$22.addProperty("min", Double.valueOf($$3.getMin()));
            $$22.addProperty("average", Double.valueOf($$3.getAverage()));
            $$22.addProperty("max", Double.valueOf($$3.getMax()));
            return $$22;
        };
        $$1.add("jvm", (JsonElement) $$2.apply($$0, (v0) -> {
            return v0.jvm();
        }));
        $$1.add("userJvm", (JsonElement) $$2.apply($$0, (v0) -> {
            return v0.userJvm();
        }));
        $$1.add("system", (JsonElement) $$2.apply($$0, (v0) -> {
            return v0.system();
        }));
        return $$1;
    }
}
