package net.labymod.core.addon.loader.verify;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.BuildData;
import net.labymod.api.Constants;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.version.Version;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.addon.loader.AddonValidator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/verify/AddonCompatibilityVerifier.class */
public class AddonCompatibilityVerifier extends AddonLoaderSubService {
    public AddonCompatibilityVerifier(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.VERIFY);
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        Version version = this.labyModLoader.version();
        List<InstalledAddonInfo> addons = new ArrayList<>(getAddons());
        for (InstalledAddonInfo addonInfo : addons) {
            boolean unload = false;
            if (!addonInfo.getCompatibleMinecraftVersions().isCompatible(version)) {
                unload = true;
                this.logger.warn("Addon {} is not compatible with the current Minecraft version {}. Unloading...", addonInfo.getNamespace(), version);
            } else if (AddonValidator.isBuildNumberGreater(addonInfo)) {
                unload = true;
                this.logger.warn("Addon {} requires a newer {} build ({}, current is {}). Unloading...", addonInfo.getNamespace(), Constants.Branding.NAME, Integer.valueOf(addonInfo.getRequiredLabyModBuild()), Integer.valueOf(BuildData.getBuildNumber()));
            }
            if (unload) {
                getAddons().remove(addonInfo);
            }
        }
    }
}
