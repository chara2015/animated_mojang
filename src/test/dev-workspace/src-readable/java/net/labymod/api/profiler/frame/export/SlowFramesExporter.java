package net.labymod.api.profiler.frame.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.mod.ModInfo;
import net.labymod.api.profiler.frame.FrameProfile;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.SystemSnapshot;
import net.labymod.laby3d.api.GraphicsDeviceInfo;
import net.labymod.laby3d.api.RenderDevice;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/SlowFramesExporter.class */
public class SlowFramesExporter extends AbstractProfilerExporter {
    private static final String REPORT_HEADER_LINE = "================================================================================\n";
    private static final String SECTION_SEPARATOR_LINE = "--------------------------------------------------------------------------------\n";

    public SlowFramesExporter() {
        super("slow_frames_", "txt", "Slow Frames", "Detailed call trees of all slow frames");
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
        writer.append(REPORT_HEADER_LINE);
        writer.append("                         SLOW FRAMES REPORT\n");
        writer.append("                    Generated: " + LocalDateTime.now().toString() + "\n");
        writer.append("                    Minecraft: " + MinecraftVersions.current().getRawVersion() + "\n");
        writer.append("                    Threshold: " + String.format(Locale.ROOT, "%.1fms", Double.valueOf(FrameProfiler.getSlowThresholdMillis())) + "\n");
        writer.append(REPORT_HEADER_LINE);
        writeGpuInfo(writer);
        writer.append("\n");
        writeAddonsInfo(writer);
        writeModsInfo(writer);
        int slowHistorySize = FrameProfiler.getSlowFrameHistorySize();
        int totalSlowCount = FrameProfiler.getSlowFrameCount();
        if (slowHistorySize == 0) {
            writer.append("No slow frames recorded.\n");
            return;
        }
        for (int i = 0; i < slowHistorySize; i++) {
            FrameProfile frame = FrameProfiler.getSlowFrameFromHistory(i);
            if (frame != null) {
                writer.append(SECTION_SEPARATOR_LINE);
                writer.append(frame.buildCallTree(FrameProfiler.getDisplayThresholdMillis()));
                writeSystemSnapshot(writer, frame.getSystemSnapshot());
                writer.append("\n");
            }
        }
        writer.append(SECTION_SEPARATOR_LINE);
        writer.append(String.format(Locale.ROOT, "Slow frames exported: %d\n", Integer.valueOf(slowHistorySize)));
        if (totalSlowCount > slowHistorySize) {
            writer.append(String.format(Locale.ROOT, "Note: %d additional slow frames were recorded but not stored (limit: %d)\n", Integer.valueOf(totalSlowCount - slowHistorySize), Integer.valueOf(FrameProfiler.getMaxSlowFrames())));
        }
    }

    private void writeGpuInfo(Appendable writer) throws IOException {
        if (!Laby.isInitialized()) {
            return;
        }
        try {
            RenderDevice renderDevice = Laby.references().laby3D().renderDevice();
            GraphicsDeviceInfo info = renderDevice.info();
            writer.append("GPU Information:\n");
            writer.append("  Device Name: " + info.deviceName() + "\n");
            writer.append("  Vendor:   " + info.vendor() + "\n");
            writer.append("  Version:   " + info.version() + "\n");
        } catch (Exception e) {
        }
    }

    private void writeAddonsInfo(Appendable writer) throws IOException {
        if (!Laby.isInitialized()) {
            return;
        }
        try {
            Collection<LoadedAddon> loadedAddons = Laby.labyAPI().addonService().getLoadedAddons();
            if (loadedAddons == null || loadedAddons.isEmpty()) {
                return;
            }
            writer.append("Installed Addons:\n");
            int enabledCount = 0;
            for (LoadedAddon addon : loadedAddons) {
                InstalledAddonInfo info = addon.info();
                if (info != null) {
                    boolean enabled = Laby.labyAPI().addonService().isEnabled(info.getNamespace());
                    if (enabled) {
                        enabledCount++;
                    }
                    String status = enabled ? "enabled" : "disabled";
                    writer.append(String.format(Locale.ROOT, "  - %s v%s [%s]\n", info.getDisplayName(), info.getVersion(), status));
                }
            }
            writer.append(String.format(Locale.ROOT, "  Total: %d addons (%d enabled)\n\n", Integer.valueOf(loadedAddons.size()), Integer.valueOf(enabledCount)));
        } catch (Exception e) {
        }
    }

    private void writeModsInfo(Appendable writer) throws IOException {
        Collection<ModInfo> modInfos;
        if (!Laby.isInitialized()) {
            return;
        }
        try {
            int totalMods = 0;
            for (ModLoader modLoader : Laby.labyAPI().modLoaderRegistry().values()) {
                if (modLoader != null && (modInfos = modLoader.getModInfos()) != null && !modInfos.isEmpty()) {
                    if (totalMods == 0) {
                        writer.append("Loaded Mods:\n");
                    }
                    writer.append(String.format(Locale.ROOT, "  [%s] (%d mods):\n", modLoader.getId(), Integer.valueOf(modInfos.size())));
                    for (ModInfo modInfo : modInfos) {
                        if (modInfo != null) {
                            writer.append(String.format(Locale.ROOT, "    - %s v%s\n", modInfo.getDisplayName(), modInfo.version().toString()));
                            totalMods++;
                        }
                    }
                }
            }
            if (totalMods > 0) {
                writer.append(String.format(Locale.ROOT, "  Total: %d mods\n\n", Integer.valueOf(totalMods)));
            }
        } catch (Exception e) {
        }
    }

    private void writeSystemSnapshot(Appendable writer, SystemSnapshot snapshot) throws IOException {
        if (snapshot == null) {
            return;
        }
        writer.append("  System State:\n");
        writer.append(String.format(Locale.ROOT, "    Heap: %.0f / %.0f MB (%.1f%% used)\n", Double.valueOf(snapshot.getHeapUsedMB()), Double.valueOf(snapshot.getHeapMaxMB()), Double.valueOf(snapshot.getHeapUsagePercent())));
        if (snapshot.hasSystemMemoryInfo()) {
            writer.append(String.format(Locale.ROOT, "    System Memory: %.0f / %.0f MB (%.1f%% used)\n", Double.valueOf(snapshot.getSystemTotalMemoryMB() - snapshot.getSystemFreeMemoryMB()), Double.valueOf(snapshot.getSystemTotalMemoryMB()), Double.valueOf(snapshot.getSystemMemoryUsagePercent())));
        }
        double loadAvg = snapshot.getSystemLoadAverage();
        if (loadAvg >= 0.0d) {
            writer.append(String.format(Locale.ROOT, "    CPU Load Average: %.2f (%d cores)\n", Double.valueOf(loadAvg), Integer.valueOf(snapshot.getAvailableProcessors())));
        } else {
            writer.append(String.format(Locale.ROOT, "    CPU Cores: %d\n", Integer.valueOf(snapshot.getAvailableProcessors())));
        }
        double cpuTemp = snapshot.getCpuTemperature();
        if (cpuTemp >= 0.0d) {
            writer.append(String.format(Locale.ROOT, "    CPU Temperature: %.1f°C\n", Double.valueOf(cpuTemp)));
        }
        double battery = snapshot.getBatteryLevel();
        if (battery >= 0.0d) {
            writer.append(String.format(Locale.ROOT, "    Battery: %.0f%%\n", Double.valueOf(battery)));
        }
    }
}
