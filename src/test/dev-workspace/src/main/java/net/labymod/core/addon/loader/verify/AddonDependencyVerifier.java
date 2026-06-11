package net.labymod.core.addon.loader.verify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/verify/AddonDependencyVerifier.class */
public class AddonDependencyVerifier extends AddonLoaderSubService {
    public AddonDependencyVerifier(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.VERIFY);
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        List<InstalledAddonInfo> addonsAboutToBeUnloaded = new ArrayList<>();
        for (InstalledAddonInfo addonInfo : getAddons()) {
            if (!addonsAboutToBeUnloaded.contains(addonInfo)) {
                isLoadable(addonInfo, addonsAboutToBeUnloaded);
            }
        }
        Iterator<InstalledAddonInfo> it = addonsAboutToBeUnloaded.iterator();
        while (it.hasNext()) {
            getAddons().remove(it.next());
        }
    }

    private boolean isLoadable(InstalledAddonInfo addonInfo, List<InstalledAddonInfo> addonsAboutToBeUnloaded) {
        InstalledAddonInfo dependencyInfo;
        AddonDependency[] dependencies = addonInfo.getCompatibleAddonDependencies(this.labyModLoader.version());
        if (dependencies == null || dependencies.length == 0) {
            return true;
        }
        List<String> missingDependencies = new ArrayList<>();
        for (AddonDependency dependency : dependencies) {
            if (!dependency.isOptional() && ((dependencyInfo = getAddon(dependency.getNamespace())) == null || !isLoadable(dependencyInfo, addonsAboutToBeUnloaded))) {
                missingDependencies.add(dependency.getNamespace());
            }
        }
        if (!missingDependencies.isEmpty()) {
            if (!addonsAboutToBeUnloaded.contains(addonInfo)) {
                this.logger.info("Unloading addon {} because of missing dependencies. ({})", addonInfo.getNamespace(), String.join(", ", missingDependencies));
                addonsAboutToBeUnloaded.add(addonInfo);
                return false;
            }
            return false;
        }
        return true;
    }
}
