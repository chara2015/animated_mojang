package net.labymod.core.addon.loader.download;

import java.util.List;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.addon.loader.initial.AdditionalAddonLoader;
import net.labymod.core.flint.downloader.AddonDownloadRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/download/AdditionalAddonDownloader.class */
public class AdditionalAddonDownloader extends AddonLoaderSubService {
    private final AdditionalAddonLoader additionalAddonLoader;

    public AdditionalAddonDownloader(AddonLoader addonLoader, AdditionalAddonLoader additionalAddonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.DOWNLOAD);
        this.additionalAddonLoader = additionalAddonLoader;
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        List<String> missingAdditionalAddons = this.additionalAddonLoader.getMissingAdditionalAddons();
        if (missingAdditionalAddons.isEmpty()) {
            return;
        }
        for (String namespace : missingAdditionalAddons) {
            if (!isAddonInList(namespace)) {
                try {
                    Response<AddonDownloadRequest.AddonDownloadResult> response = AddonDownloadRequest.create().loadUniqueIdFromLoader().namespace(namespace).path(AdditionalAddonLoader.ADDITIONAL_ADDONS_DIRECTORY, namespace + ".jar").existsStrategy((requiredNamespace, existingInfo) -> {
                        return existingInfo == null || !existingInfo.getNamespace().equalsIgnoreCase(requiredNamespace);
                    }).ignoreUnsupported().executeSync();
                    if (response.hasException()) {
                        logError("Could not download additional addon " + namespace, response.exception());
                    }
                    if (response.isPresent()) {
                        AddonDownloadRequest.AddonDownloadResult result = response.get();
                        if (!result.hasSkippedMainAddon()) {
                            this.logger.info("Successfully downloaded additional addon {}", namespace);
                        }
                        for (InstalledAddonInfo addonInfo : result.getAddonInfos()) {
                            if (!isAddonInList(addonInfo.getNamespace())) {
                                addAddon(addonInfo);
                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    logError("Additional addon " + namespace + " could now be found in the index", e);
                }
            }
        }
    }
}
