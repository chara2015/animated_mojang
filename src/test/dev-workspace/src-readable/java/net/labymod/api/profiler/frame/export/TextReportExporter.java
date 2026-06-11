package net.labymod.api.profiler.frame.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.profiler.frame.FrameProfile;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.FrameStatistics;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/TextReportExporter.class */
public class TextReportExporter extends AbstractProfilerExporter {
    private static final String REPORT_HEADER_LINE = "================================================================================\n";
    private static final String BAR_CHARACTER = "█";
    private static final int BAR_SCALE_DIVISOR = 2;
    private static final int SECTION_NAME_MAX_LENGTH = 30;
    private static final int REPORT_SEPARATOR_LENGTH = 90;
    private static final int FRAME_LIMIT_UNLIMITED = 260;

    public TextReportExporter() {
        super("profiler_report_", "txt", "Text Report", "Human-readable summary with statistics");
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
        FrameStatistics stats = FrameStatistics.calculate();
        writer.append(REPORT_HEADER_LINE);
        writer.append("                         FRAME PROFILER REPORT\n");
        writer.append("                    Generated: ").append(LocalDateTime.now().toString()).append("\n");
        writer.append("                    Minecraft: ").append(MinecraftVersions.current().getRawVersion()).append("\n");
        writer.append(REPORT_HEADER_LINE).append("\n");
        writer.append("=== OVERVIEW ===\n");
        writer.append(String.format(Locale.ROOT, "Total Frames Profiled: %d\n", Long.valueOf(stats.getTotalFrames())));
        writer.append(String.format(Locale.ROOT, "Slow Frames: %d (%.2f%%)\n", Integer.valueOf(stats.getSlowFrameCount()), Double.valueOf(stats.getSlowFramePercentage())));
        writer.append(String.format(Locale.ROOT, "Slow Threshold: %.1fms\n", Double.valueOf(FrameProfiler.getSlowThresholdMillis())));
        writer.append("\n");
        writeMinecraftSettings(writer);
        writer.append("=== FRAME TIME STATISTICS ===\n");
        writer.append(String.format(Locale.ROOT, "Minimum:     %8.2f ms\n", Double.valueOf(stats.getMinFrameTimeMs())));
        writer.append(String.format(Locale.ROOT, "Maximum:     %8.2f ms\n", Double.valueOf(stats.getMaxFrameTimeMs())));
        writer.append(String.format(Locale.ROOT, "Average:     %8.2f ms\n", Double.valueOf(stats.getAvgFrameTimeMs())));
        writer.append(String.format(Locale.ROOT, "Median:      %8.2f ms\n", Double.valueOf(stats.getMedianFrameTimeMs())));
        writer.append(String.format(Locale.ROOT, "Std Dev:     %8.2f ms\n", Double.valueOf(stats.getStdDevFrameTimeMs())));
        writer.append(String.format(Locale.ROOT, "95th %%ile:   %8.2f ms\n", Double.valueOf(stats.getPercentile95Ms())));
        writer.append(String.format(Locale.ROOT, "99th %%ile:   %8.2f ms\n", Double.valueOf(stats.getPercentile99Ms())));
        writer.append(String.format(Locale.ROOT, "99.9th %%ile: %8.2f ms\n", Double.valueOf(stats.getPercentile999Ms())));
        writer.append("\n");
        writer.append("=== FPS STATISTICS ===\n");
        writer.append(String.format(Locale.ROOT, "Average FPS:    %8.1f\n", Double.valueOf(stats.getAvgFps())));
        writer.append(String.format(Locale.ROOT, "Minimum FPS:    %8.1f\n", Double.valueOf(stats.getMinFps())));
        writer.append(String.format(Locale.ROOT, "1%% Low FPS:     %8.1f\n", Double.valueOf(stats.getPercentile1Fps())));
        writer.append("\n");
        writer.append("=== FRAME TIME DISTRIBUTION ===\n");
        int[] histogram = stats.getFrameTimeHistogram();
        String[] labels = stats.getHistogramLabels();
        int totalFrames = 0;
        for (int count : histogram) {
            totalFrames += count;
        }
        for (int i = 0; i < histogram.length && i < labels.length; i++) {
            double percentage = totalFrames > 0 ? (((double) histogram[i]) * 100.0d) / ((double) totalFrames) : 0.0d;
            int barLength = (int) (percentage / 2.0d);
            String bar = repeat(BAR_CHARACTER, barLength);
            writer.append(String.format(Locale.ROOT, "%-25s %5d (%5.1f%%) %s\n", labels[i], Integer.valueOf(histogram[i]), Double.valueOf(percentage), bar));
        }
        writer.append("\n");
        writer.append("=== TOP SECTIONS BY SELF TIME ===\n");
        writer.append(String.format(Locale.ROOT, "%-30s %10s %10s %10s %10s %10s\n", "Section", "Total", "Avg", "Max", "Calls", "Calls/Frame"));
        writer.append(repeat("-", REPORT_SEPARATOR_LENGTH)).append("\n");
        FrameStatistics.SectionStatistics[] topSections = stats.getTopSections();
        for (FrameStatistics.SectionStatistics section : topSections) {
            writer.append(String.format(Locale.ROOT, "%-30s %8.2fms %8.2fms %8.2fms %8d %10.1f\n", truncate(section.getName(), 30), Double.valueOf(section.getTotalSelfTimeMs()), Double.valueOf(section.getAvgSelfTimeMs()), Double.valueOf(section.getMaxSelfTimeMs()), Integer.valueOf(section.getTotalCalls()), Double.valueOf(section.getAvgCallsPerFrame())));
        }
        writer.append("\n");
        FrameProfile worstFrame = findWorstFrameInHistory();
        if (worstFrame != null) {
            writer.append("=== WORST FRAME CALL TREE ===\n");
            writer.append(worstFrame.buildCallTree(FrameProfiler.getDisplayThresholdMillis()));
            writer.append("\n");
        }
    }

    private FrameProfile findWorstFrameInHistory() {
        int historySize = FrameProfiler.getHistorySize();
        FrameProfile worst = null;
        double worstTime = 0.0d;
        for (int i = 0; i < historySize; i++) {
            FrameProfile frame = FrameProfiler.getFrame(i);
            if (frame != null && frame.getDurationMillis() > worstTime) {
                worst = frame;
                worstTime = frame.getDurationMillis();
            }
        }
        return worst;
    }

    private void writeMinecraftSettings(Appendable writer) throws IOException {
        MinecraftOptions options = Laby.labyAPI().minecraft().options();
        int frameLimit = options.getFrameLimit();
        writer.append("=== MINECRAFT SETTINGS ===\n");
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(frameLimit);
        objArr[1] = frameLimit == 260 ? " (Unlimited)" : "";
        writer.append(String.format(locale, "Frame Limit:         %d FPS%s\n", objArr));
        writer.append(String.format(Locale.ROOT, "Render Distance:     %d chunks\n", Integer.valueOf(options.getRenderDistance())));
        int actualRenderDistance = options.getActualRenderDistance();
        if (actualRenderDistance != options.getRenderDistance()) {
            writer.append(String.format(Locale.ROOT, "Server Render Dist:  %d chunks\n", Integer.valueOf(actualRenderDistance)));
        }
        int simulationDistance = options.getSimulationDistance();
        if (simulationDistance != -1) {
            writer.append(String.format(Locale.ROOT, "Simulation Distance: %d chunks\n", Integer.valueOf(simulationDistance)));
        }
        writer.append(String.format(Locale.ROOT, "Field of View:       %.0f\n", Double.valueOf(options.getFov())));
        Locale locale2 = Locale.ROOT;
        Object[] objArr2 = new Object[1];
        objArr2[0] = options.isFullscreen() ? "Yes" : "No";
        writer.append(String.format(locale2, "Fullscreen:          %s\n", objArr2));
        Locale locale3 = Locale.ROOT;
        Object[] objArr3 = new Object[1];
        objArr3[0] = options.isVSyncEnabled() ? "Yes" : "No";
        writer.append(String.format(locale3, "VSync:               %s\n", objArr3));
        Locale locale4 = Locale.ROOT;
        Object[] objArr4 = new Object[1];
        objArr4[0] = options.isBobbing() ? "Yes" : "No";
        writer.append(String.format(locale4, "View Bobbing:        %s\n", objArr4));
        Locale locale5 = Locale.ROOT;
        Object[] objArr5 = new Object[1];
        objArr5[0] = options.isSmoothCamera() ? "Yes" : "No";
        writer.append(String.format(locale5, "Smooth Camera:       %s\n", objArr5));
        writer.append("\n");
    }
}
