package net.labymod.api.configuration.settings.creator.version.def;

import java.util.Optional;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.AddonService;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.configuration.settings.creator.version.VersionValidator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/version/def/AddonVersionValidator.class */
public class AddonVersionValidator extends VersionValidator {
    private final AddonService addonService;

    public AddonVersionValidator(LabyAPI labyAPI) {
        super(labyAPI);
        this.addonService = labyAPI.addonService();
    }

    @Override // net.labymod.api.configuration.settings.creator.version.VersionValidator
    public boolean isSupportedVersion(String version, String namespace) {
        Optional<LoadedAddon> selectedAddon = this.addonService.getAddon(namespace);
        if (selectedAddon.isEmpty()) {
            return false;
        }
        LoadedAddon loadedAddon = selectedAddon.get();
        return compareVersion(loadedAddon.info().getVersion(), version);
    }
}
