package net.labymod.core.addon.loader.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/verify/AddonSortingVerifier.class */
public class AddonSortingVerifier extends AddonLoaderSubService {
    public AddonSortingVerifier(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.VERIFY);
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        Map<InstalledAddonInfo, AddonDependency[]> addons = new HashMap<>();
        for (InstalledAddonInfo addonInfo : getAddons()) {
            addons.put(addonInfo, addonInfo.getCompatibleAddonDependencies(this.labyModLoader.version()));
        }
        Map<String, List<String>> graph = new HashMap<>();
        for (Map.Entry<InstalledAddonInfo, AddonDependency[]> entry : addons.entrySet()) {
            List<String> dependencies = new ArrayList<>();
            for (AddonDependency dependency : entry.getValue()) {
                dependencies.add(dependency.getNamespace());
            }
            graph.put(entry.getKey().getNamespace(), dependencies);
        }
        Set<String> visited = new HashSet<>();
        List<String> sortedAddons = new ArrayList<>();
        for (InstalledAddonInfo installedAddonInfo : addons.keySet()) {
            if (!visited.contains(installedAddonInfo.getNamespace())) {
                dfs(installedAddonInfo.getNamespace(), graph, visited, sortedAddons);
            }
        }
        List<InstalledAddonInfo> sortedAddonsList = new ArrayList<>();
        for (String sortedAddon : sortedAddons) {
            Iterator<InstalledAddonInfo> it = addons.keySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    InstalledAddonInfo installedAddonInfo2 = it.next();
                    if (installedAddonInfo2.getNamespace().equals(sortedAddon)) {
                        sortedAddonsList.add(installedAddonInfo2);
                        break;
                    }
                }
            }
        }
        getAddons().clear();
        getAddons().addAll(sortedAddonsList);
    }

    private void dfs(String addon, Map<String, List<String>> graph, Set<String> visited, List<String> sortedAddons) {
        visited.add(addon);
        for (String dependency : graph.getOrDefault(addon, Collections.emptyList())) {
            if (!visited.contains(dependency)) {
                dfs(dependency, graph, visited, sortedAddons);
            }
        }
        sortedAddons.add(addon);
    }
}
