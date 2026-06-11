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
import net.labymod.api.Laby;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.profiler.frame.FrameProfile;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.FrameStatistics;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/JsonReportExporter.class */
public class JsonReportExporter extends AbstractProfilerExporter {
    private static final Gson GSON = new GsonBuilder().create();

    public JsonReportExporter() {
        super("profiler_report_", "json", "JSON Report", "Machine-readable format for analysis tools");
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
        FrameStatistics stats = FrameStatistics.calculate();
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("generatedAt", LocalDateTime.now().toString());
        report.put("minecraftVersion", MinecraftVersions.current().getRawVersion());
        report.put("minecraftSettings", buildMinecraftSettings());
        report.put("overview", buildOverview(stats));
        report.put("frameTime", buildFrameTime(stats));
        report.put("fps", buildFps(stats));
        report.put("histogram", buildHistogram(stats));
        report.put("topSections", buildTopSections(stats));
        report.put("slowFrames", buildSlowFrames());
        return report;
    }

    private Map<String, Object> buildOverview(FrameStatistics stats) {
        Map<String, Object> overview = new LinkedHashMap<>();
        overview.put("totalFrames", Long.valueOf(stats.getTotalFrames()));
        overview.put("slowFrames", Integer.valueOf(stats.getSlowFrameCount()));
        overview.put("slowFramePercentage", Double.valueOf(stats.getSlowFramePercentage()));
        overview.put("slowThresholdMs", Double.valueOf(FrameProfiler.getSlowThresholdMillis()));
        return overview;
    }

    private Map<String, Object> buildFrameTime(FrameStatistics stats) {
        Map<String, Object> frameTime = new LinkedHashMap<>();
        frameTime.put("minMs", Double.valueOf(stats.getMinFrameTimeMs()));
        frameTime.put("maxMs", Double.valueOf(stats.getMaxFrameTimeMs()));
        frameTime.put("avgMs", Double.valueOf(stats.getAvgFrameTimeMs()));
        frameTime.put("medianMs", Double.valueOf(stats.getMedianFrameTimeMs()));
        frameTime.put("stdDevMs", Double.valueOf(stats.getStdDevFrameTimeMs()));
        frameTime.put("percentile95Ms", Double.valueOf(stats.getPercentile95Ms()));
        frameTime.put("percentile99Ms", Double.valueOf(stats.getPercentile99Ms()));
        frameTime.put("percentile999Ms", Double.valueOf(stats.getPercentile999Ms()));
        return frameTime;
    }

    private Map<String, Object> buildFps(FrameStatistics stats) {
        Map<String, Object> fps = new LinkedHashMap<>();
        fps.put("avg", Double.valueOf(stats.getAvgFps()));
        fps.put("min", Double.valueOf(stats.getMinFps()));
        fps.put("percentile1", Double.valueOf(stats.getPercentile1Fps()));
        return fps;
    }

    private List<Map<String, Object>> buildHistogram(FrameStatistics stats) {
        List<Map<String, Object>> histogram = new ArrayList<>();
        int[] counts = stats.getFrameTimeHistogram();
        String[] labels = stats.getHistogramLabels();
        for (int i = 0; i < counts.length && i < labels.length; i++) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("label", labels[i]);
            entry.put("count", Integer.valueOf(counts[i]));
            histogram.add(entry);
        }
        return histogram;
    }

    private List<Map<String, Object>> buildTopSections(FrameStatistics stats) {
        List<Map<String, Object>> sections = new ArrayList<>();
        for (FrameStatistics.SectionStatistics section : stats.getTopSections()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("name", section.getName());
            entry.put("totalSelfTimeMs", Double.valueOf(section.getTotalSelfTimeMs()));
            entry.put("avgSelfTimeMs", Double.valueOf(section.getAvgSelfTimeMs()));
            entry.put("maxSelfTimeMs", Double.valueOf(section.getMaxSelfTimeMs()));
            entry.put("totalCalls", Integer.valueOf(section.getTotalCalls()));
            entry.put("avgCallsPerFrame", Double.valueOf(section.getAvgCallsPerFrame()));
            sections.add(entry);
        }
        return sections;
    }

    private List<Map<String, Object>> buildSlowFrames() {
        List<Map<String, Object>> frames = new ArrayList<>();
        int slowHistorySize = FrameProfiler.getSlowFrameHistorySize();
        for (int i = 0; i < slowHistorySize; i++) {
            FrameProfile frame = FrameProfiler.getSlowFrameFromHistory(i);
            if (frame != null) {
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.put("frameNumber", Long.valueOf(frame.getFrameNumber()));
                entry.put("durationMs", Double.valueOf(frame.getDurationMillis()));
                frames.add(entry);
            }
        }
        return frames;
    }

    private Map<String, Object> buildMinecraftSettings() {
        MinecraftOptions options = Laby.labyAPI().minecraft().options();
        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("frameLimit", Integer.valueOf(options.getFrameLimit()));
        settings.put("vsync", Boolean.valueOf(options.isVSyncEnabled()));
        settings.put("renderDistance", Integer.valueOf(options.getRenderDistance()));
        settings.put("actualRenderDistance", Integer.valueOf(options.getActualRenderDistance()));
        int simulationDistance = options.getSimulationDistance();
        if (simulationDistance != -1) {
            settings.put("simulationDistance", Integer.valueOf(simulationDistance));
        }
        settings.put("fov", Double.valueOf(options.getFov()));
        settings.put("fullscreen", Boolean.valueOf(options.isFullscreen()));
        settings.put("viewBobbing", Boolean.valueOf(options.isBobbing()));
        settings.put("smoothCamera", Boolean.valueOf(options.isSmoothCamera()));
        return settings;
    }
}
