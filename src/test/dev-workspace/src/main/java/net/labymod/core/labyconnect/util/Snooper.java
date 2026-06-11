package net.labymod.core.labyconnect.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Namespaces;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.ModLoaderId;
import net.labymod.api.modloader.mod.ModInfo;
import net.labymod.api.platform.launcher.LauncherService;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.main.LabyMod;
import net.labymod.laby3d.api.GraphicsDeviceInfo;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.display.Monitor;
import net.labymod.laby3d.api.display.VideoMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/util/Snooper.class */
public class Snooper {
    private static final int FORMAT_VERSION = 4;
    private static String identifier;
    private static Boolean isInternalReleaseChannel;
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final SystemInfo systemInfo = Laby.references().systemInfo();
    private final Laby3D laby3D = Laby.references().laby3D();
    private static final Logging LOGGER = Logging.getLogger();
    private static final Set<String> MINECRAFT_OPTION_EXCLUDES = Set.of("lastServer", "resourcePacks", "incompatibleResourcePacks");

    public static String getIdentifier() {
        if (identifier == null) {
            Path path = Constants.Files.LABYMOD_DIRECTORY.resolve(".snooper");
            if (Files.exists(path, new LinkOption[0])) {
                try {
                    identifier = new String(Files.readAllBytes(path));
                } catch (IOException e) {
                    LOGGER.error("Failed to read snooper identifier, generating new one", e);
                }
            }
            if (identifier == null) {
                identifier = UUID.randomUUID().toString();
                try {
                    Files.write(path, identifier.getBytes(), new OpenOption[0]);
                } catch (IOException e2) {
                    LOGGER.error("Failed to write snooper identifier", e2);
                }
            }
        }
        return identifier;
    }

    public static boolean isInternalReleaseChannel() {
        if (isInternalReleaseChannel == null) {
            String releaseChannel = DefaultLabyModLoader.getInstance().getEffectiveReleaseChannel();
            isInternalReleaseChannel = Boolean.valueOf(releaseChannel == null || !(releaseChannel.equals("production") || releaseChannel.equals("snapshot")));
        }
        return isInternalReleaseChannel.booleanValue();
    }

    private JsonObject createModLoaderEntry(String modLoaderId) {
        ModLoader modLoader = this.labyAPI.modLoaderRegistry().getById(modLoaderId);
        JsonObject modLoaderEntry = new JsonObject();
        modLoaderEntry.addProperty("installed", Boolean.valueOf(modLoader != null));
        JsonArray mods = new JsonArray();
        if (modLoader != null) {
            for (ModInfo modInfo : modLoader.getModInfos()) {
                JsonObject entry = new JsonObject();
                entry.addProperty("id", modInfo.getId());
                entry.addProperty("version", modInfo.version().toString());
                mods.add(entry);
            }
        }
        modLoaderEntry.add("mods", mods);
        return modLoaderEntry;
    }

    public CompletableFuture<JsonObject> collect() {
        CompletableFuture<JsonObject> future = new CompletableFuture<>();
        if (!isInternalReleaseChannel() && !Laby.labyAPI().config().other().anonymousStatistics().get().booleanValue()) {
            return future;
        }
        try {
            JsonObject obj = new JsonObject();
            obj.addProperty("version", 4);
            JsonObject userObj = new JsonObject();
            userObj.addProperty("identifier", getIdentifier());
            if (isInternalReleaseChannel()) {
                userObj.addProperty("account_uuid", Laby.labyAPI().getUniqueId().toString());
            }
            obj.add("user", userObj);
            JsonObject minecraft = new JsonObject();
            LauncherService launcherService = Laby.references().launcherService();
            JsonObject labymod = new JsonObject();
            JsonArray addons = new JsonArray();
            for (LoadedAddon loadedAddon : this.labyAPI.addonService().getLoadedAddons()) {
                InstalledAddonInfo info = loadedAddon.info();
                JsonObject entry = new JsonObject();
                entry.addProperty("namespace", info.getNamespace());
                entry.addProperty("name", info.getDisplayName());
                entry.addProperty("offline", Boolean.valueOf(!info.isFlintAddon()));
                entry.addProperty("version", info.getVersion());
                entry.addProperty("hash", info.getFileHash());
                addons.add(entry);
            }
            labymod.add("addons", addons);
            labymod.add("settings", (JsonElement) ((LabyMod) this.labyAPI).getLabyConfig().serialize());
            labymod.addProperty("version", this.labyAPI.getVersion());
            JsonObject modPack = new JsonObject();
            if (launcherService.isUsingLabyModLauncher() && launcherService.getModPackName() != null) {
                modPack.addProperty("id", launcherService.getModPackId());
                modPack.addProperty("name", launcherService.getModPackName());
                modPack.addProperty("modLoader", launcherService.getModLoader());
            }
            minecraft.add("labymod", labymod);
            JsonObject minecraftOptions = new JsonObject();
            Map<String, String> optionCache = LabyMod.references().optionsTranslator().getOptionCache();
            for (Map.Entry<String, String> entry2 : optionCache.entrySet()) {
                if (!MINECRAFT_OPTION_EXCLUDES.contains(entry2.getKey())) {
                    minecraftOptions.addProperty(entry2.getKey(), entry2.getValue());
                }
            }
            minecraft.add("options", minecraftOptions);
            minecraft.add(ModLoaderId.FORGE, createModLoaderEntry(ModLoaderId.FORGE));
            minecraft.add("neo_forge", createModLoaderEntry(ModLoaderId.NEO_FORGE));
            minecraft.add("fabric", createModLoaderEntry(ModLoaderId.FABRIC));
            minecraft.addProperty("version", this.labyAPI.minecraft().getVersion());
            minecraft.addProperty("protocol_version", Integer.valueOf(this.labyAPI.minecraft().getProtocolVersion()));
            minecraft.addProperty("launcher_brand", launcherService.getLauncherOrDefault("unknown"));
            minecraft.addProperty("launcher_version", (String) Objects.requireNonNullElse(launcherService.getLauncherVersion(), "unknown"));
            obj.add(Namespaces.MINECRAFT, minecraft);
            JsonObject operatingSystem = new JsonObject();
            operatingSystem.addProperty("name", System.getProperty("os.name"));
            operatingSystem.addProperty("version", System.getProperty("os.version"));
            operatingSystem.addProperty("arch", System.getProperty("os.arch"));
            obj.add("os", operatingSystem);
            JsonObject java = new JsonObject();
            java.addProperty("version", System.getProperty("java.version"));
            obj.add("java", java);
            RenderDevice renderDevice = this.laby3D.renderDevice();
            GraphicsDeviceInfo info2 = renderDevice.info();
            JsonObject opengl = new JsonObject();
            opengl.addProperty("version", info2.version());
            opengl.addProperty("vendor", info2.vendor());
            opengl.addProperty("name", info2.deviceName());
            obj.add("graphicsDeviceInfo", opengl);
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                try {
                    JsonObject hardware = new JsonObject();
                    hardware.addProperty("cpu", this.systemInfo.processor().name());
                    try {
                        hardware.addProperty("memory", Long.valueOf(this.systemInfo.getTotalMemorySize()));
                    } catch (Throwable t) {
                        LOGGER.error("Can't access memory information", t);
                    }
                    JsonArray monitors = new JsonArray();
                    for (Monitor monitor : Monitor.all()) {
                        VideoMode videoMode = monitor.currentMode();
                        if (videoMode != null) {
                            JsonObject monitorObject = new JsonObject();
                            monitorObject.addProperty("width", Integer.valueOf(videoMode.width()));
                            monitorObject.addProperty("height", Integer.valueOf(videoMode.height()));
                            monitorObject.addProperty("refresh_rate", Integer.valueOf(videoMode.refreshRate()));
                            monitors.add(monitorObject);
                        }
                    }
                    hardware.add("monitors", monitors);
                    obj.add("hardware", hardware);
                    future.complete(obj);
                } catch (Throwable e) {
                    LOGGER.error("Can't access GL information", e);
                }
            });
        } catch (Throwable e) {
            LOGGER.error("Can't create snooper object", e);
        }
        return future;
    }
}
