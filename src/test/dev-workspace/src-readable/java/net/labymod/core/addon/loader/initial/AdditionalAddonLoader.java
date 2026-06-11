package net.labymod.core.addon.loader.initial;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Constants;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/initial/AdditionalAddonLoader.class */
public class AdditionalAddonLoader extends AddonLoaderSubService {
    public static final Path ADDITIONAL_ADDONS_DIRECTORY = Constants.Files.FILE_CACHE.resolve("addons");
    private final List<String> additionalAddons;
    private final List<String> missingAdditionalAddons;

    public AdditionalAddonLoader(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.INITIAL);
        this.missingAdditionalAddons = new ArrayList();
        String property = System.getProperty("net.labymod.additional-addons");
        if (property != null) {
            String[] split = property.split(",");
            this.additionalAddons = List.of((Object[]) split);
        } else {
            this.additionalAddons = List.of();
        }
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        if (this.additionalAddons.isEmpty()) {
            return;
        }
        if (!IOUtil.exists(ADDITIONAL_ADDONS_DIRECTORY)) {
            IOUtil.createDirectories(ADDITIONAL_ADDONS_DIRECTORY);
        }
        this.missingAdditionalAddons.clear();
        for (String additionalAddon : this.additionalAddons) {
            boolean alreadyInstalled = isAddonInList(additionalAddon);
            if (alreadyInstalled) {
                this.logger.info("Additional addon {} is already installed", additionalAddon);
            } else {
                Path path = ADDITIONAL_ADDONS_DIRECTORY.resolve(additionalAddon + ".jar");
                InstalledAddonInfo addonInfo = null;
                if (IOUtil.exists(path)) {
                    addonInfo = loadAddonInfo(path, addon -> {
                        return !addon.getNamespace().equals(additionalAddon);
                    });
                }
                if (addonInfo == null) {
                    this.missingAdditionalAddons.add(additionalAddon);
                }
            }
        }
    }

    public List<String> getMissingAdditionalAddons() {
        return this.missingAdditionalAddons;
    }
}
