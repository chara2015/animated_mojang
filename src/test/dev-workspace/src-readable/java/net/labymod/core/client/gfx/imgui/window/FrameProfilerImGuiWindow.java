package net.labymod.core.client.gfx.imgui.window;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.type.ImGuiFloatType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.profiler.frame.FrameProfile;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.FrameStatistics;
import net.labymod.api.profiler.frame.ProfilerSection;
import net.labymod.api.profiler.frame.export.ProfilerExporter;
import net.labymod.api.profiler.frame.export.ProfilerExporters;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/window/FrameProfilerImGuiWindow.class */
public class FrameProfilerImGuiWindow extends ImGuiWindow {
    private static final int COLOR_NORMAL = -1;
    private static final int COLOR_WARNING = -16711681;
    private static final int COLOR_CRITICAL = -11513601;
    private static final int COLOR_SECTION = -13210;
    private static final int COLOR_GOOD = -7798904;
    private static final int COLOR_HEADER = -21931;
    private static final int TREE_NODE_FLAGS_LEAF = 256;
    private static final int TREE_NODE_FLAGS_DEFAULT_OPEN = 32;
    private static final long EXPORT_MESSAGE_DISPLAY_MS = 5000;
    private static final long STATISTICS_CACHE_MS = 1000;
    private final ImGuiBooleanType showAllFrames;
    private final ImGuiFloatType slowThreshold;
    private final ImGuiFloatType displayThreshold;
    private FrameProfile selectedFrameCopy;
    private int currentTab;
    private String lastExportMessage;
    private long lastExportMessageTime;
    private FrameStatistics cachedStatistics;
    private long lastStatisticsUpdate;

    public FrameProfilerImGuiWindow(@Nullable ImGuiBooleanType visible) {
        super("Frame Profiler", visible, 0);
        this.selectedFrameCopy = null;
        this.currentTab = 0;
        this.lastExportMessage = "";
        this.lastExportMessageTime = 0L;
        this.cachedStatistics = null;
        this.lastStatisticsUpdate = 0L;
        this.showAllFrames = LabyImGui.booleanType(false);
        this.slowThreshold = LabyImGui.floatType((float) FrameProfiler.getSlowThresholdMillis());
        this.displayThreshold = LabyImGui.floatType((float) FrameProfiler.getDisplayThresholdMillis());
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void prepareWindow() {
        ImGuiViewport windowViewport = LabyImGui.getWindowViewport();
        FloatVector2 windowPosition = windowViewport.position();
        float x = windowPosition.getX();
        float y = windowPosition.getY();
        LabyImGui.setNextWindowSizeConstraints(500.0f, 400.0f, 900.0f, 700.0f);
        LabyImGui.setNextWindowPos(x + 10.0f, y + 10.0f, 4);
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void renderContent() {
        renderControls();
        LabyImGui.separator();
        renderTabs();
        LabyImGui.separator();
        switch (this.currentTab) {
            case 0:
                renderOverviewTab();
                break;
            case 1:
                renderStatisticsTab();
                break;
            case 2:
                renderFramesTab();
                break;
            case 3:
                renderTopSectionsTab();
                break;
            case 4:
                renderExportTab();
                break;
        }
    }

    private void renderControls() {
        boolean wasEnabled = FrameProfiler.isEnabled();
        ImGuiBooleanType enabledType = LabyImGui.booleanType(wasEnabled);
        if (LabyImGui.checkbox("Enable Profiler", enabledType)) {
            FrameProfiler.setEnabled(enabledType.get());
            this.cachedStatistics = null;
        }
        LabyImGui.sameLine();
        if (LabyImGui.button("Reset")) {
            FrameProfiler.reset();
            this.selectedFrameCopy = null;
            this.cachedStatistics = null;
        }
        LabyImGui.sameLine();
        if (LabyImGui.button("Refresh Stats")) {
            this.cachedStatistics = null;
        }
    }

    private void renderTabs() {
        String[] tabNames = {"Overview", "Statistics", "Frames", "Top Sections", "Export"};
        int i = 0;
        while (i < tabNames.length) {
            if (i > 0) {
                LabyImGui.sameLine();
            }
            int color = this.currentTab == i ? COLOR_SECTION : -1;
            String label = this.currentTab == i ? "[" + tabNames[i] + "]" : tabNames[i];
            LabyImGui.text(label, color);
            if (LabyImGui.isItemClicked()) {
                this.currentTab = i;
            }
            i++;
        }
    }

    private void renderOverviewTab() {
        FrameStatistics stats = getStatistics();
        LabyImGui.text("Quick Overview", COLOR_HEADER);
        LabyImGui.separator();
        long frameCount = FrameProfiler.getFrameCounter();
        int slowCount = FrameProfiler.getSlowFrameCount();
        double worstTime = FrameProfiler.getWorstFrameTimeMillis();
        long worstFrame = FrameProfiler.getWorstFrameNumber();
        LabyImGui.keyValuePair("Total Frames", String.valueOf(frameCount));
        LabyImGui.keyValuePair("History Size", String.valueOf(FrameProfiler.getHistorySize()));
        LabyImGui.keyValuePair("Slow Frames", String.valueOf(slowCount));
        if (slowCount > 0 && frameCount > 0) {
            double percentage = (((double) slowCount) * 100.0d) / frameCount;
            int color = percentage > 5.0d ? COLOR_CRITICAL : percentage > 1.0d ? COLOR_WARNING : COLOR_GOOD;
            LabyImGui.text("  Slow %", color);
            LabyImGui.sameLine(0.0f, 0.0f);
            LabyImGui.text(": ");
            LabyImGui.sameLine(0.0f, 0.0f);
            LabyImGui.text(String.format(Locale.ROOT, "%.2f%%", Double.valueOf(percentage)), color);
        }
        LabyImGui.separator();
        LabyImGui.text("Frame Time Summary", COLOR_HEADER);
        if (stats.getTotalFrames() > 0) {
            LabyImGui.keyValuePair("Average", String.format(Locale.ROOT, "%.2fms (%.0f fps)", Double.valueOf(stats.getAvgFrameTimeMs()), Double.valueOf(stats.getAvgFps())));
            LabyImGui.keyValuePair("Median", String.format(Locale.ROOT, "%.2fms", Double.valueOf(stats.getMedianFrameTimeMs())));
            LabyImGui.keyValuePair("Min/Max", String.format(Locale.ROOT, "%.2fms / %.2fms", Double.valueOf(stats.getMinFrameTimeMs()), Double.valueOf(stats.getMaxFrameTimeMs())));
            LabyImGui.keyValuePair("99th %ile", String.format(Locale.ROOT, "%.2fms", Double.valueOf(stats.getPercentile99Ms())));
        }
        if (worstTime > 0.0d) {
            LabyImGui.separator();
            int color2 = getColorForTime(worstTime);
            LabyImGui.text("  Worst Frame", color2);
            LabyImGui.sameLine(0.0f, 0.0f);
            LabyImGui.text(": ");
            LabyImGui.sameLine(0.0f, 0.0f);
            LabyImGui.text(String.format(Locale.ROOT, "%.2fms (Frame #%d)", Double.valueOf(worstTime), Long.valueOf(worstFrame)), color2);
        }
        LabyImGui.separator();
        LabyImGui.text("Settings", COLOR_HEADER);
        LabyImGui.keyValuePair("Slow Threshold", String.format(Locale.ROOT, "%.1fms", Double.valueOf(FrameProfiler.getSlowThresholdMillis())));
        LabyImGui.keyValuePair("Display Threshold", String.format(Locale.ROOT, "%.1fms", Double.valueOf(FrameProfiler.getDisplayThresholdMillis())));
    }

    private void renderStatisticsTab() {
        FrameStatistics stats = getStatistics();
        if (stats.getTotalFrames() == 0) {
            LabyImGui.text("No data available. Enable profiler and wait for frames.");
            return;
        }
        LabyImGui.text("Frame Time Statistics", COLOR_HEADER);
        LabyImGui.separator();
        LabyImGui.keyValuePair("Minimum", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getMinFrameTimeMs())));
        LabyImGui.keyValuePair("Maximum", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getMaxFrameTimeMs())));
        LabyImGui.keyValuePair("Average", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getAvgFrameTimeMs())));
        LabyImGui.keyValuePair("Median", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getMedianFrameTimeMs())));
        LabyImGui.keyValuePair("Std Dev", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getStdDevFrameTimeMs())));
        LabyImGui.separator();
        LabyImGui.text("Percentiles", COLOR_HEADER);
        LabyImGui.keyValuePair("95th", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getPercentile95Ms())));
        LabyImGui.keyValuePair("99th", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getPercentile99Ms())));
        LabyImGui.keyValuePair("99.9th", String.format(Locale.ROOT, "%.3fms", Double.valueOf(stats.getPercentile999Ms())));
        LabyImGui.separator();
        LabyImGui.text("FPS Statistics", COLOR_HEADER);
        LabyImGui.keyValuePair("Average FPS", String.format(Locale.ROOT, "%.1f", Double.valueOf(stats.getAvgFps())));
        LabyImGui.keyValuePair("Minimum FPS", String.format(Locale.ROOT, "%.1f", Double.valueOf(stats.getMinFps())));
        LabyImGui.keyValuePair("1% Low FPS", String.format(Locale.ROOT, "%.1f", Double.valueOf(stats.getPercentile1Fps())));
        LabyImGui.separator();
        LabyImGui.text("Frame Time Distribution", COLOR_HEADER);
        int[] histogram = stats.getFrameTimeHistogram();
        String[] labels = stats.getHistogramLabels();
        int totalFrames = 0;
        for (int count : histogram) {
            totalFrames += count;
        }
        int i = 0;
        while (i < histogram.length && i < labels.length) {
            double percentage = totalFrames > 0 ? (((double) histogram[i]) * 100.0d) / ((double) totalFrames) : 0.0d;
            int barLength = Math.min((int) (percentage / 2.0d), 25);
            String bar = repeat("█", barLength);
            int color = i < 3 ? COLOR_GOOD : i < 6 ? COLOR_WARNING : COLOR_CRITICAL;
            LabyImGui.text(String.format(Locale.ROOT, "  %-22s %4d (%5.1f%%) ", truncateLabel(labels[i]), Integer.valueOf(histogram[i]), Double.valueOf(percentage)));
            LabyImGui.sameLine(0.0f, 0.0f);
            LabyImGui.text(bar, color);
            i++;
        }
    }

    private void renderFramesTab() {
        LabyImGui.checkbox("Show All Frames", this.showAllFrames);
        int historySize = FrameProfiler.getHistorySize();
        if (historySize == 0) {
            LabyImGui.text("No frames recorded yet.");
            return;
        }
        LabyImGui.separator();
        LabyImGui.text("Frame History", COLOR_HEADER);
        int displayCount = 0;
        for (int i = historySize - 1; i >= 0 && displayCount < 15; i--) {
            FrameProfile frame = FrameProfiler.getFrame(i);
            if (frame != null) {
                boolean isSlow = frame.isFlaggedAsSlow();
                if (this.showAllFrames.get() || isSlow) {
                    displayCount++;
                    Locale locale = Locale.ROOT;
                    Object[] objArr = new Object[3];
                    objArr[0] = Long.valueOf(frame.getFrameNumber());
                    objArr[1] = Double.valueOf(frame.getDurationMillis());
                    objArr[2] = isSlow ? " [SLOW]" : "";
                    String label = String.format(locale, "#%d - %.2fms%s", objArr);
                    int color = getColorForTime(frame.getDurationMillis());
                    boolean isSelected = this.selectedFrameCopy != null && this.selectedFrameCopy.getFrameNumber() == frame.getFrameNumber();
                    if (isSelected) {
                        LabyImGui.text("> ", COLOR_SECTION);
                        LabyImGui.sameLine(0.0f, 0.0f);
                    } else {
                        LabyImGui.text("  ");
                        LabyImGui.sameLine(0.0f, 0.0f);
                    }
                    LabyImGui.text(label, color);
                    if (LabyImGui.isItemHovered()) {
                        LabyImGui.setTooltip("Click to inspect this frame");
                    }
                    if (LabyImGui.isItemClicked()) {
                        this.selectedFrameCopy = frame.copy();
                    }
                }
            }
        }
        if (displayCount == 0) {
            LabyImGui.text("No slow frames detected.");
        }
        LabyImGui.separator();
        renderSelectedFrame();
    }

    private void renderTopSectionsTab() {
        FrameStatistics stats = getStatistics();
        FrameStatistics.SectionStatistics[] sections = stats.getTopSections();
        if (sections.length == 0) {
            LabyImGui.text("No section data available.");
            return;
        }
        LabyImGui.text("Top Sections by Self Time (across all frames)", COLOR_HEADER);
        LabyImGui.separator();
        LabyImGui.text(String.format(Locale.ROOT, "  %-25s %8s %8s %8s %8s %8s", "Section", "Total", "Avg", "Max", "Calls", "Calls/F"));
        for (FrameStatistics.SectionStatistics section : sections) {
            double avgSelf = section.getAvgSelfTimeMs();
            int color = getColorForTime(avgSelf * 2.0d);
            String line = String.format(Locale.ROOT, "  %-25s %6.1fms %6.2fms %6.2fms %6d %8.1f", truncate(section.getName(), 25), Double.valueOf(section.getTotalSelfTimeMs()), Double.valueOf(avgSelf), Double.valueOf(section.getMaxSelfTimeMs()), Integer.valueOf(section.getTotalCalls()), Double.valueOf(section.getAvgCallsPerFrame()));
            LabyImGui.text(line, color);
            if (LabyImGui.isItemHovered()) {
                renderSectionStatisticsTooltip(section);
            }
        }
    }

    private void renderExportTab() {
        LabyImGui.text("Export Profiling Data", COLOR_HEADER);
        LabyImGui.separator();
        Path exportDir = Laby.labyAPI().labyModLoader().getGameDirectory().resolve("profiler_exports");
        LabyImGui.text("Export Directory:");
        LabyImGui.text("  " + exportDir.toString());
        LabyImGui.separator();
        boolean first = true;
        for (ProfilerExporter exporter : ProfilerExporters.getAll()) {
            if (!first) {
                LabyImGui.sameLine();
            }
            first = false;
            if (LabyImGui.button("Export " + exporter.getDisplayName())) {
                exportWithExporter(exporter, exportDir);
            }
        }
        long currentTime = System.currentTimeMillis();
        if (!this.lastExportMessage.isEmpty() && currentTime - this.lastExportMessageTime < EXPORT_MESSAGE_DISPLAY_MS) {
            LabyImGui.separator();
            int color = this.lastExportMessage.startsWith("Error") ? COLOR_CRITICAL : COLOR_GOOD;
            LabyImGui.text(this.lastExportMessage, color);
        }
        LabyImGui.separator();
        LabyImGui.text("Export Formats:", COLOR_HEADER);
        for (ProfilerExporter exporter2 : ProfilerExporters.getAll()) {
            LabyImGui.text("  - " + exporter2.getDisplayName() + ": " + exporter2.getDescription());
        }
    }

    private void exportWithExporter(ProfilerExporter exporter, Path directory) {
        try {
            Path path = exporter.export(directory);
            this.lastExportMessage = exporter.getDisplayName() + " saved to: " + String.valueOf(path.getFileName());
            this.lastExportMessageTime = System.currentTimeMillis();
        } catch (IOException e) {
            this.lastExportMessage = "Error: " + e.getMessage();
            this.lastExportMessageTime = System.currentTimeMillis();
        }
    }

    private void renderSelectedFrame() {
        if (this.selectedFrameCopy == null) {
            LabyImGui.text("Select a frame above to inspect.");
            return;
        }
        FrameProfile frame = this.selectedFrameCopy;
        String header = String.format(Locale.ROOT, "Frame #%d - Total: %.2fms", Long.valueOf(frame.getFrameNumber()), Double.valueOf(frame.getDurationMillis()));
        int headerColor = getColorForTime(frame.getDurationMillis());
        LabyImGui.text(header, headerColor);
        ProfilerSection slowest = frame.findSlowestSection(1.0d);
        if (slowest != null) {
            String slowestText = String.format(Locale.ROOT, "Slowest: %s (%.2fms self)", slowest.getName(), Double.valueOf(slowest.getSelfTimeMillis()));
            LabyImGui.text(slowestText, COLOR_CRITICAL);
        }
        LabyImGui.separator();
        LabyImGui.text("Call Tree:");
        ProfilerSection root = frame.getRoot();
        double threshold = this.displayThreshold.get();
        for (int i = 0; i < root.getChildCount(); i++) {
            renderSection(root.getChildren()[i], threshold, frame.getDurationNanos());
        }
    }

    private void renderSection(ProfilerSection section, double thresholdMs, long frameTotalNanos) {
        if (section.getDurationMillis() < thresholdMs) {
            return;
        }
        ProfilerSection[] children = section.getChildren();
        int childCount = section.getChildCount();
        boolean hasVisibleChildren = false;
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            if (children[i].getDurationMillis() < thresholdMs) {
                i++;
            } else {
                hasVisibleChildren = true;
                break;
            }
        }
        double percentOfFrame = (section.getDurationNanos() * 100.0d) / frameTotalNanos;
        double selfPercent = (section.getSelfTimeNanos() * 100.0d) / frameTotalNanos;
        String label = String.format(Locale.ROOT, "%s - %.2fms (%.1f%%) | self: %.2fms (%.1f%%) x%d", section.getName(), Double.valueOf(section.getDurationMillis()), Double.valueOf(percentOfFrame), Double.valueOf(section.getSelfTimeMillis()), Double.valueOf(selfPercent), Integer.valueOf(section.getCallCount()));
        int flags = hasVisibleChildren ? 32 : 256;
        int color = getColorForTime(section.getSelfTimeMillis());
        LabyImGui.pushStyleColor(0, color);
        boolean nodeOpen = LabyImGui.treeNodeEx(label, flags);
        LabyImGui.popStyleColor();
        if (LabyImGui.isItemHovered()) {
            renderSectionTooltip(section);
        }
        if (nodeOpen && hasVisibleChildren) {
            for (int i2 = 0; i2 < childCount; i2++) {
                renderSection(children[i2], thresholdMs, frameTotalNanos);
            }
        }
        if (nodeOpen) {
            LabyImGui.popTree();
        }
    }

    private void renderSectionTooltip(ProfilerSection section) {
        StringBuilder tooltip = new StringBuilder();
        tooltip.append(section.getName()).append("\n");
        tooltip.append(String.format(Locale.ROOT, "Total: %.3fms\n", Double.valueOf(section.getDurationMillis())));
        tooltip.append(String.format(Locale.ROOT, "Self: %.3fms\n", Double.valueOf(section.getSelfTimeMillis())));
        tooltip.append(String.format(Locale.ROOT, "Calls: %d\n", Integer.valueOf(section.getCallCount())));
        tooltip.append(String.format(Locale.ROOT, "Depth: %d\n", Integer.valueOf(section.getDepth())));
        if (section.getParent() != null) {
            tooltip.append(String.format(Locale.ROOT, "Parent %%: %.1f%%", Double.valueOf(section.getPercentageOfParent())));
        }
        LabyImGui.setTooltip(tooltip.toString());
    }

    private void renderSectionStatisticsTooltip(FrameStatistics.SectionStatistics section) {
        StringBuilder tooltip = new StringBuilder();
        tooltip.append(section.getName()).append("\n\n");
        tooltip.append(String.format(Locale.ROOT, "Total Time: %.2fms\n", Double.valueOf(section.getTotalTimeMs())));
        tooltip.append(String.format(Locale.ROOT, "Total Self Time: %.2fms\n", Double.valueOf(section.getTotalSelfTimeMs())));
        tooltip.append(String.format(Locale.ROOT, "Avg Time: %.3fms\n", Double.valueOf(section.getAvgTimeMs())));
        tooltip.append(String.format(Locale.ROOT, "Avg Self Time: %.3fms\n", Double.valueOf(section.getAvgSelfTimeMs())));
        tooltip.append(String.format(Locale.ROOT, "Max Time: %.2fms\n", Double.valueOf(section.getMaxTimeMs())));
        tooltip.append(String.format(Locale.ROOT, "Max Self Time: %.2fms\n", Double.valueOf(section.getMaxSelfTimeMs())));
        tooltip.append(String.format(Locale.ROOT, "Total Calls: %d\n", Integer.valueOf(section.getTotalCalls())));
        tooltip.append(String.format(Locale.ROOT, "Avg Calls/Frame: %.1f", Double.valueOf(section.getAvgCallsPerFrame())));
        LabyImGui.setTooltip(tooltip.toString());
    }

    private FrameStatistics getStatistics() {
        long currentTime = System.currentTimeMillis();
        if (this.cachedStatistics == null || currentTime - this.lastStatisticsUpdate > 1000) {
            this.cachedStatistics = FrameStatistics.calculate();
            this.lastStatisticsUpdate = currentTime;
        }
        return this.cachedStatistics;
    }

    private int getColorForTime(double timeMs) {
        double threshold = FrameProfiler.getSlowThresholdMillis();
        if (timeMs >= threshold) {
            return COLOR_CRITICAL;
        }
        if (timeMs >= threshold * 0.5d) {
            return COLOR_WARNING;
        }
        return -1;
    }

    private String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    private String truncateLabel(String label) {
        int parenIndex = label.indexOf(40);
        if (parenIndex > 0) {
            return label.substring(0, Math.min(parenIndex, 22)).trim();
        }
        return truncate(label, 22);
    }

    private String repeat(String str, int count) {
        if (count <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
}
