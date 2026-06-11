package net.labymod.api.profiler.frame.export;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.mod.ModInfo;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.laby3d.api.GraphicsDeviceInfo;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.display.Monitor;
import net.labymod.laby3d.api.display.VideoMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/ProfilerReportData.class */
public final class ProfilerReportData {
    private ProfilerReportData() {
    }

    public static String getMinecraftVersion() {
        return MinecraftVersions.current().getRawVersion();
    }

    public static double getSlowThresholdMs() {
        return FrameProfiler.getSlowThresholdMillis();
    }

    public static double getDisplayThresholdMs() {
        return FrameProfiler.getDisplayThresholdMillis();
    }

    public static Map<String, Object> buildGpuInfo() {
        if (!Laby.isInitialized()) {
            return null;
        }
        try {
            RenderDevice renderDevice = Laby.references().laby3D().renderDevice();
            GraphicsDeviceInfo info = renderDevice.info();
            Map<String, Object> gpu = new LinkedHashMap<>();
            gpu.put("implementation", renderDevice.getClass().getName());
            gpu.put("deviceName", info.deviceName());
            gpu.put("vendor", info.vendor());
            gpu.put("version", info.version());
            try {
                List<Monitor> monitors = Monitor.all();
                if (!monitors.isEmpty()) {
                    List<Map<String, Object>> monitorList = new ArrayList<>(monitors.size());
                    for (Monitor monitor : monitors) {
                        Map<String, Object> monitorData = new LinkedHashMap<>();
                        VideoMode videoMode = monitor.currentMode();
                        if (videoMode != null) {
                            monitorData.put("width", Integer.valueOf(videoMode.width()));
                            monitorData.put("height", Integer.valueOf(videoMode.height()));
                            monitorData.put("refreshRate", Integer.valueOf(videoMode.refreshRate()));
                        }
                        monitorList.add(monitorData);
                    }
                    gpu.put("monitors", monitorList);
                }
            } catch (Exception e) {
            }
            return gpu;
        } catch (Exception e2) {
            return null;
        }
    }

    public static List<Map<String, Object>> buildAddonsInfo() {
        if (!Laby.isInitialized()) {
            return null;
        }
        try {
            Collection<LoadedAddon> loadedAddons = Laby.labyAPI().addonService().getLoadedAddons();
            if (loadedAddons == null || loadedAddons.isEmpty()) {
                return null;
            }
            List<Map<String, Object>> addons = new ArrayList<>();
            for (LoadedAddon addon : loadedAddons) {
                InstalledAddonInfo info = addon.info();
                if (info != null) {
                    Map<String, Object> addonData = new LinkedHashMap<>();
                    addonData.put("namespace", info.getNamespace());
                    addonData.put("displayName", info.getDisplayName());
                    addonData.put("version", info.getVersion());
                    addonData.put("enabled", Boolean.valueOf(Laby.labyAPI().addonService().isEnabled(info.getNamespace())));
                    String author = info.getAuthor();
                    if (author != null && !author.isEmpty()) {
                        addonData.put("author", author);
                    }
                    addons.add(addonData);
                }
            }
            return addons;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Map<String, Object>> buildModsInfo() {
        if (!Laby.isInitialized()) {
            return null;
        }
        try {
            List<Map<String, Object>> mods = new ArrayList<>();
            for (ModLoader modLoader : Laby.labyAPI().modLoaderRegistry().values()) {
                if (modLoader != null) {
                    Collection<ModInfo> modInfos = modLoader.getModInfos();
                    if (!modInfos.isEmpty()) {
                        for (ModInfo modInfo : modInfos) {
                            if (modInfo != null) {
                                Map<String, Object> modData = new LinkedHashMap<>();
                                modData.put("id", modInfo.getId());
                                modData.put("displayName", modInfo.getDisplayName());
                                modData.put("version", modInfo.version().toString());
                                modData.put("loader", modLoader.getId());
                                Collection<String> authors = modInfo.getAuthors();
                                if (!authors.isEmpty()) {
                                    modData.put("authors", new ArrayList(authors));
                                }
                                mods.add(modData);
                            }
                        }
                    }
                }
            }
            return mods;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> buildMinecraftOptions() {
        if (!Laby.isInitialized()) {
            return null;
        }
        try {
            MinecraftOptions options = Laby.labyAPI().minecraft().options();
            if (options == null) {
                return null;
            }
            Map<String, Object> optionsData = new LinkedHashMap<>();
            optionsData.put("frameLimit", Integer.valueOf(options.getFrameLimit()));
            optionsData.put("renderDistance", Integer.valueOf(options.getRenderDistance()));
            optionsData.put("actualRenderDistance", Integer.valueOf(options.getActualRenderDistance()));
            int simDistance = options.getSimulationDistance();
            if (simDistance != -1) {
                optionsData.put("simulationDistance", Integer.valueOf(simDistance));
            }
            optionsData.put("vsync", Boolean.valueOf(options.isVSyncEnabled()));
            optionsData.put("fullscreen", Boolean.valueOf(options.isFullscreen()));
            optionsData.put("fov", Double.valueOf(options.getFov()));
            optionsData.put("fovEffectScale", Double.valueOf(options.getFovEffectScale()));
            optionsData.put("bobbing", Boolean.valueOf(options.isBobbing()));
            optionsData.put("smoothCamera", Boolean.valueOf(options.isSmoothCamera()));
            optionsData.put("perspective", options.perspective().name());
            optionsData.put("language", options.getCurrentLanguage());
            optionsData.put("mainHand", options.mainHand().name());
            return optionsData;
        } catch (Exception e) {
            return null;
        }
    }

    public static void addEnvironmentData(Map<String, Object> report) {
        report.put("minecraftVersion", getMinecraftVersion());
        report.put("slowThresholdMs", Double.valueOf(getSlowThresholdMs()));
        report.put("displayThresholdMs", Double.valueOf(getDisplayThresholdMs()));
        Map<String, Object> gpuInfo = buildGpuInfo();
        if (gpuInfo != null) {
            report.put("gpu", gpuInfo);
        }
        List<Map<String, Object>> addons = buildAddonsInfo();
        if (addons != null && !addons.isEmpty()) {
            report.put("addons", addons);
        }
        List<Map<String, Object>> mods = buildModsInfo();
        if (mods != null && !mods.isEmpty()) {
            report.put("mods", mods);
        }
        Map<String, Object> minecraftOptions = buildMinecraftOptions();
        if (minecraftOptions != null) {
            report.put("minecraftOptions", minecraftOptions);
        }
    }
}
