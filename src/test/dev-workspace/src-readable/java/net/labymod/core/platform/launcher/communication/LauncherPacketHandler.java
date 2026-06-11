package net.labymod.core.platform.launcher.communication;

import java.nio.file.Path;
import java.util.Objects;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherAddonInstalledPacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherStopPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/LauncherPacketHandler.class */
public class LauncherPacketHandler {
    private final LauncherCommunicationClient client;
    private final Logging logger = Logging.create((Class<?>) LauncherPacketHandler.class);

    public LauncherPacketHandler(LauncherCommunicationClient client) {
        this.client = client;
    }

    public void handleStopPacket(LauncherStopPacket packet) {
        this.logger.info("Received stop packet, shutting down Minecraft...", new Object[0]);
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft != null) {
            Objects.requireNonNull(minecraft);
            minecraft.executeOnRenderThread(minecraft::shutdownGame);
        } else {
            System.exit(0);
        }
    }

    public void handleAddonInstalledPacket(LauncherAddonInstalledPacket packet) {
        DefaultAddonService addonService = DefaultAddonService.getInstance();
        AddonLoader addonLoader = addonService.addonLoader();
        addonLoader.executeServiceTask(() -> {
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                if (addonService.getAddon(packet.getNamespace()).isPresent()) {
                    this.logger.warn("Addon " + packet.getNamespace() + " is already installed!", new Object[0]);
                    return;
                }
                try {
                    Path addonsRoot = Constants.Files.ADDONS.toAbsolutePath().normalize();
                    Path addonPath = addonsRoot.resolve(packet.getFileName()).normalize();
                    if (!addonPath.startsWith(addonsRoot)) {
                        this.logger.warn("Refusing addon-install packet for {}: resolved path {} escapes addons directory.", packet.getNamespace(), addonPath);
                        return;
                    }
                    InstalledAddonInfo addonInfo = addonLoader.loadAddonInfo(addonPath);
                    if (addonInfo.hasMeta(AddonMeta.RESTART_REQUIRED)) {
                        this.logger.info("Addon " + packet.getNamespace() + " requires a restart!", new Object[0]);
                    } else {
                        this.logger.info("Loading addon " + packet.getNamespace() + " from launcher packet...", new Object[0]);
                        addonLoader.addonPreparer().loadAddon(addonInfo, AddonPreparer.AddonPrepareContext.RUNTIME);
                    }
                } catch (Exception e) {
                    this.logger.error("Failed to load addon " + packet.getNamespace() + "!", e);
                }
            });
        });
    }
}
