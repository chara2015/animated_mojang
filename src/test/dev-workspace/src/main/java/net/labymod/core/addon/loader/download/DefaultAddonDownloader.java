package net.labymod.core.addon.loader.download;

import net.labymod.api.Laby;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.flint.FlintDefaultModifications;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/download/DefaultAddonDownloader.class */
public class DefaultAddonDownloader extends AddonLoaderSubService {
    private final FlintDefaultModifications defaultModifications;

    public DefaultAddonDownloader(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.DOWNLOAD);
        this.defaultModifications = FlintDefaultModifications.instance();
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        this.defaultModifications.loadDefaultAddons();
        if (Laby.references().launcherService().isUsingModPack() || LabyMod.getInstance().labyModLoader().isDevelopmentEnvironment()) {
            return;
        }
        for (String namespace : this.defaultModifications.getDefaultModifications()) {
            if (!this.defaultModifications.hasInstalledBefore(namespace)) {
                if (isAddonInList(namespace)) {
                    this.defaultModifications.install(namespace);
                } else {
                    try {
                        Response<AddonDownloadRequest.AddonDownloadResult> response = AddonDownloadRequest.create().loadUniqueIdFromLoader().namespace(namespace).existsStrategy((requiredNamespace, existingInfo) -> {
                            return false;
                        }).ignoreUnsupported().executeSync();
                        if (response.hasException()) {
                            logError("Could not download default addon " + namespace, response.exception());
                        }
                        if (response.isPresent()) {
                            AddonDownloadRequest.AddonDownloadResult result = response.get();
                            if (!result.hasSkippedMainAddon()) {
                                this.logger.info("Successfully downloaded default addon {}", namespace);
                            }
                            for (InstalledAddonInfo addonInfo : result.getAddonInfos()) {
                                if (!isAddonInList(addonInfo.getNamespace())) {
                                    addAddon(addonInfo);
                                    this.defaultModifications.install(addonInfo.getNamespace());
                                }
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        logError("Default addon " + namespace + " could now be found in the index", e);
                    }
                }
            }
        }
    }
}
