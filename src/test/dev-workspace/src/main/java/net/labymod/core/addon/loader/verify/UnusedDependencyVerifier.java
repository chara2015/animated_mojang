package net.labymod.core.addon.loader.verify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/verify/UnusedDependencyVerifier.class */
public class UnusedDependencyVerifier extends AddonLoaderSubService {
    private final IncompatibleAddonVerifier incompatibleAddonVerifier;

    public UnusedDependencyVerifier(AddonLoader addonLoader, IncompatibleAddonVerifier incompatibleAddonVerifier) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.VERIFY);
        this.incompatibleAddonVerifier = incompatibleAddonVerifier;
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        Version version = this.labyModLoader.version();
        List<String> requiredDependencies = new ArrayList<>();
        Iterator<InstalledAddonInfo> it = getAddons().iterator();
        while (it.hasNext()) {
            for (AddonDependency dependency : it.next().getCompatibleAddonDependencies(version)) {
                if (!dependency.isOptional() && !requiredDependencies.contains(dependency.getNamespace())) {
                    requiredDependencies.add(dependency.getNamespace());
                }
            }
        }
        this.logger.info("Filtering out unused dependencies", new Object[0]);
        List<InstalledAddonInfo> addons = new ArrayList<>(getAddons());
        for (InstalledAddonInfo addon : addons) {
            if (addon.hasMeta(AddonMeta.HIDDEN) && !requiredDependencies.contains(addon.getNamespace())) {
                this.logger.info("Removing unused dependency {}", addon.getNamespace());
                getAddons().remove(addon);
                if (CollectionHelper.anyMatch(this.incompatibleAddonVerifier.getIncompatibleAddons(), incompatibleAddon -> {
                    return CollectionHelper.anyMatch(incompatibleAddon.getCompatibleAddonDependencies(version), dependency2 -> {
                        return dependency2.getNamespace().equals(addon.getNamespace());
                    });
                })) {
                    this.logger.info("Won't delete unused dependency {} as it is used by an incompatible addon", addon.getNamespace());
                } else {
                    try {
                        IOUtil.delete(addon.getPath());
                    } catch (Exception e) {
                        logError("Could not delete unused dependency " + addon.getNamespace(), e);
                    }
                }
            }
        }
    }
}
