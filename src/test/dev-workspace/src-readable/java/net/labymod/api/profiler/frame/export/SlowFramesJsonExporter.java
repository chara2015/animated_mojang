package net.labymod.api.profiler.frame.export;

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
import net.labymod.api.profiler.frame.FrameProfile;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.ProfilerSection;
import net.labymod.api.profiler.frame.SystemSnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/SlowFramesJsonExporter.class */
public class SlowFramesJsonExporter extends AbstractProfilerExporter {
    private static final Gson GSON = new GsonBuilder().create();

    public SlowFramesJsonExporter() {
        super("slow_frames_", "json", "Slow Frames JSON", "JSON format for flame graphs and analysis tools");
    }

    @Override // net.labymod.api.profiler.frame.export.ProfilerExporter
    public Path export(Path directory) throws IOException {
        Path filePath = createExportFile(directory);
        BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, new OpenOption[0]);
        try {
            writeReport(writer);
            if (writer != null) {
                writer.close();
            }
            return filePath;
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

    public void writeReport(Appendable writer) throws IOException {
        Map<String, Object> report = buildReport();
        GSON.toJson(report, writer);
    }

    private Map<String, Object> buildReport() {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("generatedAt", LocalDateTime.now().toString());
        ProfilerReportData.addEnvironmentData(report);
        report.put("totalSlowFrameCount", Integer.valueOf(FrameProfiler.getSlowFrameCount()));
        report.put("exportedFrameCount", Integer.valueOf(FrameProfiler.getSlowFrameHistorySize()));
        report.put("maxStoredFrames", Integer.valueOf(FrameProfiler.getMaxSlowFrames()));
        report.put("frames", buildFrames());
        return report;
    }

    private List<Map<String, Object>> buildFrames() {
        List<Map<String, Object>> frames = new ArrayList<>();
        int slowHistorySize = FrameProfiler.getSlowFrameHistorySize();
        for (int i = 0; i < slowHistorySize; i++) {
            FrameProfile frame = FrameProfiler.getSlowFrameFromHistory(i);
            if (frame != null) {
                frames.add(buildFrame(frame));
            }
        }
        return frames;
    }

    private Map<String, Object> buildFrame(FrameProfile frame) {
        Map<String, Object> frameData = new LinkedHashMap<>();
        frameData.put("frameNumber", Long.valueOf(frame.getFrameNumber()));
        frameData.put("durationMs", Double.valueOf(frame.getDurationMillis()));
        frameData.put("flaggedAsSlow", Boolean.valueOf(frame.isFlaggedAsSlow()));
        SystemSnapshot snapshot = frame.getSystemSnapshot();
        if (snapshot != null) {
            frameData.put("system", buildSystemSnapshot(snapshot));
        }
        frameData.put("root", buildSection(frame.getRoot()));
        return frameData;
    }

    private Map<String, Object> buildSystemSnapshot(SystemSnapshot snapshot) {
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

    private Map<String, Object> buildSection(ProfilerSection section) {
        Map<String, Object> sectionData = new LinkedHashMap<>();
        sectionData.put("name", section.getName());
        sectionData.put("durationMs", Double.valueOf(section.getDurationMillis()));
        sectionData.put("selfTimeMs", Double.valueOf(section.getSelfTimeMillis()));
        sectionData.put("callCount", Integer.valueOf(section.getCallCount()));
        sectionData.put("depth", Integer.valueOf(section.getDepth()));
        if (section.getCallCount() > 1) {
            sectionData.put("avgCallTimeMs", Double.valueOf(section.getAvgCallTimeMillis()));
            sectionData.put("minCallTimeMs", Double.valueOf(section.getMinCallTimeMillis()));
            sectionData.put("maxCallTimeMs", Double.valueOf(section.getMaxCallTimeMillis()));
        }
        if (section.getParent() != null) {
            sectionData.put("percentageOfParent", Double.valueOf(section.getPercentageOfParent()));
            sectionData.put("selfPercentageOfParent", Double.valueOf(section.getSelfPercentageOfParent()));
        }
        int childCount = section.getChildCount();
        if (childCount > 0) {
            List<Map<String, Object>> children = new ArrayList<>(childCount);
            ProfilerSection[] childSections = section.getChildren();
            for (int i = 0; i < childCount; i++) {
                children.add(buildSection(childSections[i]));
            }
            sectionData.put("children", children);
        }
        return sectionData;
    }
}
