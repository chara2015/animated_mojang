package net.labymod.api.profiler.frame.export;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.stream.ProfilerStreamReader;
import net.labymod.api.profiler.frame.stream.StreamedFrameProfile;
import net.labymod.api.profiler.frame.stream.StreamedSection;
import net.labymod.api.profiler.frame.stream.StreamedSystemSnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/ZipReportExporter.class */
public class ZipReportExporter extends AbstractProfilerExporter {
    private static final Gson GSON = new GsonBuilder().create();
    private static final String ZIP_ENTRY_REPORT_TXT = "report.txt";
    private static final String ZIP_ENTRY_REPORT_JSON = "report.json";
    private static final String ZIP_ENTRY_SLOW_FRAMES_TXT = "slow_frames.txt";
    private static final String ZIP_ENTRY_SLOW_FRAMES_JSON = "slow_frames.json";
    private final TextReportExporter textExporter;
    private final JsonReportExporter jsonExporter;
    private final SlowFramesExporter slowFramesExporter;
    private final SlowFramesJsonExporter slowFramesJsonExporter;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/ZipReportExporter$ReportWriter.class */
    @FunctionalInterface
    private interface ReportWriter {
        void write(Appendable appendable) throws IOException;
    }

    public ZipReportExporter() {
        super("profiler_export_", "zip", "ZIP Archive", "Single archive with all reports (recommended)");
        this.textExporter = new TextReportExporter();
        this.jsonExporter = new JsonReportExporter();
        this.slowFramesExporter = new SlowFramesExporter();
        this.slowFramesJsonExporter = new SlowFramesJsonExporter();
    }

    @Override // net.labymod.api.profiler.frame.export.ProfilerExporter
    public Path export(Path directory) throws IOException {
        return export(directory, null);
    }

    public Path export(Path directory, Path streamedFramesFile) throws IOException {
        Path zipPath = createExportFile(directory);
        ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath, new OpenOption[0]), StandardCharsets.UTF_8);
        try {
            TextReportExporter textReportExporter = this.textExporter;
            Objects.requireNonNull(textReportExporter);
            writeEntry(zipOut, ZIP_ENTRY_REPORT_TXT, textReportExporter::writeReport);
            JsonReportExporter jsonReportExporter = this.jsonExporter;
            Objects.requireNonNull(jsonReportExporter);
            writeEntry(zipOut, ZIP_ENTRY_REPORT_JSON, jsonReportExporter::writeReport);
            if (streamedFramesFile != null && Files.exists(streamedFramesFile, new LinkOption[0])) {
                writeStreamedSlowFramesTxt(zipOut, streamedFramesFile);
                writeStreamedSlowFramesJson(zipOut, streamedFramesFile);
            } else if (FrameProfiler.getSlowFrameHistorySize() > 0) {
                SlowFramesExporter slowFramesExporter = this.slowFramesExporter;
                Objects.requireNonNull(slowFramesExporter);
                writeEntry(zipOut, ZIP_ENTRY_SLOW_FRAMES_TXT, slowFramesExporter::writeReport);
                SlowFramesJsonExporter slowFramesJsonExporter = this.slowFramesJsonExporter;
                Objects.requireNonNull(slowFramesJsonExporter);
                writeEntry(zipOut, ZIP_ENTRY_SLOW_FRAMES_JSON, slowFramesJsonExporter::writeReport);
            }
            zipOut.close();
            return zipPath;
        } catch (Throwable th) {
            try {
                zipOut.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void writeEntry(ZipOutputStream zipOut, String entryName, ReportWriter writer) throws IOException {
        zipOut.putNextEntry(new ZipEntry(entryName));
        Writer outputWriter = new OutputStreamWriter(zipOut, StandardCharsets.UTF_8);
        writer.write(outputWriter);
        outputWriter.flush();
        zipOut.closeEntry();
    }

    private void writeStreamedSlowFramesTxt(ZipOutputStream zipOut, Path streamedFramesFile) throws IOException {
        zipOut.putNextEntry(new ZipEntry(ZIP_ENTRY_SLOW_FRAMES_TXT));
        Writer writer = new OutputStreamWriter(zipOut, StandardCharsets.UTF_8);
        writer.append("=== SLOW FRAMES FROM STREAM ===\n");
        writer.append("Source: ").append((CharSequence) streamedFramesFile.getFileName().toString()).append((CharSequence) "\n");
        writer.append("Generated: ").append((CharSequence) LocalDateTime.now().toString()).append((CharSequence) "\n\n");
        ProfilerStreamReader reader = new ProfilerStreamReader(streamedFramesFile);
        int slowCount = 0;
        while (true) {
            try {
                StreamedFrameProfile frame = reader.readFrame();
                if (frame != null) {
                    if (frame.isFlaggedAsSlow()) {
                        slowCount++;
                        writer.append(frame.buildCallTree(FrameProfiler.getDisplayThresholdMillis()));
                        writer.append("\n");
                        StreamedSystemSnapshot snapshot = frame.getSystemSnapshot();
                        if (snapshot != null) {
                            writer.append(String.format("  System: Heap %.0f/%.0f MB (%.1f%%), CPU Temp: %.1f°C\n", Double.valueOf(snapshot.getHeapUsedMB()), Double.valueOf(snapshot.getHeapMaxMB()), Double.valueOf(snapshot.getHeapUsagePercent()), Double.valueOf(snapshot.getCpuTemperature())));
                        }
                        writer.append("\n");
                    }
                } else {
                    writer.append("Total slow frames: ").append((CharSequence) String.valueOf(slowCount)).append((CharSequence) "\n");
                    reader.close();
                    writer.flush();
                    zipOut.closeEntry();
                    return;
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

    private void writeStreamedSlowFramesJson(ZipOutputStream zipOut, Path streamedFramesFile) throws IOException {
        zipOut.putNextEntry(new ZipEntry(ZIP_ENTRY_SLOW_FRAMES_JSON));
        Writer writer = new OutputStreamWriter(zipOut, StandardCharsets.UTF_8);
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("generatedAt", LocalDateTime.now().toString());
        report.put("sourceFile", streamedFramesFile.getFileName().toString());
        ProfilerReportData.addEnvironmentData(report);
        List<Map<String, Object>> slowFrames = new ArrayList<>();
        long totalFrameCount = 0;
        ProfilerStreamReader reader = new ProfilerStreamReader(streamedFramesFile);
        while (true) {
            try {
                StreamedFrameProfile frame = reader.readFrame();
                if (frame != null) {
                    totalFrameCount++;
                    if (frame.isFlaggedAsSlow()) {
                        slowFrames.add(buildFrame(frame));
                    }
                } else {
                    reader.close();
                    report.put("totalFrameCount", Long.valueOf(totalFrameCount));
                    report.put("slowFrameCount", Integer.valueOf(slowFrames.size()));
                    report.put("frames", slowFrames);
                    GSON.toJson(report, writer);
                    writer.flush();
                    zipOut.closeEntry();
                    return;
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

    private Map<String, Object> buildFrame(StreamedFrameProfile frame) {
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

    private Map<String, Object> buildSystemSnapshot(StreamedSystemSnapshot snapshot) {
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

    private Map<String, Object> buildSection(StreamedSection section) {
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
