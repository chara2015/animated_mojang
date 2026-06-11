package net.labymod.api.profiler.frame.stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.profiler.frame.export.ProfilerReportData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/stream/StreamToJsonConverter.class */
public final class StreamToJsonConverter {
    private static final Gson GSON = new GsonBuilder().create();

    private StreamToJsonConverter() {
    }

    public static Path convert(Path binaryFile) throws IOException {
        String fileName = binaryFile.getFileName().toString();
        String jsonFileName = fileName.replace(".lbfp", ".json");
        Path jsonFile = binaryFile.getParent().resolve(jsonFileName);
        return convert(binaryFile, jsonFile);
    }

    public static Path convert(Path binaryFile, Path jsonFile) throws IOException {
        Map<String, Object> report = buildReport(binaryFile);
        BufferedWriter writer = Files.newBufferedWriter(jsonFile, StandardCharsets.UTF_8, new OpenOption[0]);
        try {
            GSON.toJson(report, writer);
            if (writer != null) {
                writer.close();
            }
            return jsonFile;
        } catch (Throwable th) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static Map<String, Object> buildReport(Path binaryFile) throws IOException {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("generatedAt", LocalDateTime.now().toString());
        report.put("sourceFile", binaryFile.getFileName().toString());
        ProfilerReportData.addEnvironmentData(report);
        List<Map<String, Object>> frames = new ArrayList<>();
        long slowFrameCount = 0;
        ProfilerStreamReader reader = new ProfilerStreamReader(binaryFile);
        while (true) {
            try {
                StreamedFrameProfile frame = reader.readFrame();
                if (frame != null) {
                    frames.add(buildFrame(frame));
                    if (frame.isFlaggedAsSlow()) {
                        slowFrameCount++;
                    }
                } else {
                    reader.close();
                    report.put("frameCount", Integer.valueOf(frames.size()));
                    report.put("slowFrameCount", Long.valueOf(slowFrameCount));
                    report.put("frames", frames);
                    return report;
                }
            } catch (Throwable th) {
                try {
                    reader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    private static Map<String, Object> buildFrame(StreamedFrameProfile frame) {
        Map<String, Object> frameData = new LinkedHashMap<>();
        frameData.put("frameNumber", Long.valueOf(frame.getFrameNumber()));
        frameData.put("durationMs", Double.valueOf(frame.getDurationMillis()));
        frameData.put("flaggedAsSlow", Boolean.valueOf(frame.isFlaggedAsSlow()));
        StreamedSystemSnapshot snapshot = frame.getSystemSnapshot();
        if (snapshot != null) {
            frameData.put("system", buildSystemSnapshot(snapshot));
        }
        frameData.put("root", buildSection(frame.getRoot()));
        return frameData;
    }

    private static Map<String, Object> buildSystemSnapshot(StreamedSystemSnapshot snapshot) {
        Map<String, Object> system = new LinkedHashMap<>();
        Map<String, Object> heap = new LinkedHashMap<>();
        heap.put("usedMB", Double.valueOf(snapshot.getHeapUsedMB()));
        heap.put("maxMB", Double.valueOf(snapshot.getHeapMaxMB()));
        heap.put("committedMB", Double.valueOf(snapshot.getHeapCommittedMB()));
        heap.put("usagePercent", Double.valueOf(snapshot.getHeapUsagePercent()));
        system.put("heap", heap);
        system.put("nonHeapUsedMB", Double.valueOf(snapshot.getNonHeapUsedMB()));
        if (snapshot.hasSystemMemoryInfo()) {
            Map<String, Object> systemMemory = new LinkedHashMap<>();
            systemMemory.put("totalMB", Double.valueOf(snapshot.getSystemTotalMemoryMB()));
            systemMemory.put("freeMB", Double.valueOf(snapshot.getSystemFreeMemoryMB()));
            systemMemory.put("usagePercent", Double.valueOf(snapshot.getSystemMemoryUsagePercent()));
            system.put("systemMemory", systemMemory);
        }
        system.put("availableProcessors", Integer.valueOf(snapshot.getAvailableProcessors()));
        double loadAvg = snapshot.getSystemLoadAverage();
        if (loadAvg >= 0.0d) {
            system.put("systemLoadAverage", Double.valueOf(loadAvg));
        }
        double cpuTemp = snapshot.getCpuTemperature();
        if (cpuTemp >= 0.0d) {
            system.put("cpuTemperature", Double.valueOf(cpuTemp));
        }
        double battery = snapshot.getBatteryLevel();
        if (battery >= 0.0d) {
            system.put("batteryLevel", Double.valueOf(battery));
        }
        return system;
    }

    private static Map<String, Object> buildSection(StreamedSection section) {
        Map<String, Object> sectionData = new LinkedHashMap<>();
        sectionData.put("name", section.getName());
        sectionData.put("durationMs", Double.valueOf(section.getDurationMillis()));
        sectionData.put("selfTimeMs", Double.valueOf(section.getSelfTimeMillis()));
        sectionData.put("callCount", Integer.valueOf(section.getCallCount()));
        int callCount = section.getCallCount();
        if (callCount > 1) {
            sectionData.put("avgCallTimeMs", Double.valueOf(section.getAvgCallTimeMillis()));
            sectionData.put("minCallTimeMs", Double.valueOf(section.getMinCallTimeMillis()));
            sectionData.put("maxCallTimeMs", Double.valueOf(section.getMaxCallTimeMillis()));
        }
        StreamedSection[] children = section.getChildren();
        if (children.length > 0) {
            List<Map<String, Object>> childList = new ArrayList<>(children.length);
            for (StreamedSection child : children) {
                childList.add(buildSection(child));
            }
            sectionData.put("children", childList);
        }
        return sectionData;
    }
}
