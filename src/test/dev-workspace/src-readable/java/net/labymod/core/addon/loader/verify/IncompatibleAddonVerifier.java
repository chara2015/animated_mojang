package net.labymod.core.addon.loader.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.platform.launcher.DefaultLauncherService;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherIncompatibleAddonPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/verify/IncompatibleAddonVerifier.class */
public class IncompatibleAddonVerifier extends AddonLoaderSubService {
    private final List<InstalledAddonInfo> incompatibleAddons;

    public IncompatibleAddonVerifier(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.VERIFY);
        this.incompatibleAddons = new ArrayList();
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        DefaultLauncherService launcherService = (DefaultLauncherService) Laby.references().launcherService();
        if (launcherService.isConnectedToLauncher()) {
            List<LauncherIncompatibleAddonPacket.IncompatibleAddon> incompatible = new ArrayList<>();
            for (InstalledAddonInfo addon : getAddons()) {
                String[] incompatibleAddons = addon.getIncompatibleAddons();
                if (incompatibleAddons != null && incompatibleAddons.length != 0) {
                    List<String> dependenciesList = new ArrayList<>();
                    gatherDependingAddons(addon, dependenciesList);
                    String[] dependencies = (String[]) dependenciesList.toArray(new String[0]);
                    List<String> incompatibleDependencies = new ArrayList<>();
                    for (String incompatibleAddon : incompatibleAddons) {
                        InstalledAddonInfo incompatibleAddonInfo = getAddon(incompatibleAddon);
                        if (incompatibleAddonInfo != null) {
                            if (!incompatibleDependencies.contains(incompatibleAddon)) {
                                incompatibleDependencies.add(incompatibleAddon);
                            }
                            gatherDependingAddons(incompatibleAddonInfo, incompatibleDependencies);
                        }
                    }
                    incompatible.add(new LauncherIncompatibleAddonPacket.IncompatibleAddon(addon.getNamespace(), dependencies, (String[]) incompatibleDependencies.toArray(new String[0])));
                }
            }
            if (incompatible.isEmpty()) {
                return;
            }
            LauncherIncompatibleAddonPacket launcherIncompatibleAddonPacket = (LauncherIncompatibleAddonPacket) launcherService.getCommunicator().sendPacket(new LauncherIncompatibleAddonPacket(incompatible), LauncherIncompatibleAddonPacket.class);
            if (launcherIncompatibleAddonPacket != null) {
                for (LauncherIncompatibleAddonPacket.IncompatibleAddon incompatibleAddon2 : launcherIncompatibleAddonPacket.getIncompatibleAddons()) {
                    InstalledAddonInfo addon2 = getAddon(incompatibleAddon2.getNamespace());
                    if (addon2 != null) {
                        if (incompatibleAddon2.disableIncompatible()) {
                            disableIncompatibleAddons(addon2, incompatibleNamespace -> {
                                this.logger.info("Addon {} is incompatible with addon {} and User decided against the incompatibilities. Unloading {}...", addon2.getNamespace(), incompatibleNamespace, incompatibleNamespace);
                            });
                        } else {
                            getAddons().remove(addon2);
                            this.incompatibleAddons.add(addon2);
                            this.logger.info("User decided to remove addon {}, due to incompatibilities with other addons. Unloading {}...", addon2.getNamespace(), addon2.getNamespace());
                        }
                    }
                }
                return;
            }
        }
        disableIncompatibleAddonsFallback();
    }

    public List<InstalledAddonInfo> getIncompatibleAddons() {
        return this.incompatibleAddons;
    }

    private void gatherDependingAddons(InstalledAddonInfo info, List<String> dependencies) {
        for (InstalledAddonInfo addon : getAddons()) {
            String namespace = addon.getNamespace();
            if (!namespace.equals(info.getNamespace()) && !addon.hasMeta(AddonMeta.HIDDEN)) {
                AddonDependency[] compatibleAddonDependencies = addon.getCompatibleAddonDependencies(this.labyModLoader.version());
                for (AddonDependency compatibleAddonDependency : compatibleAddonDependencies) {
                    String dependencyNamespace = compatibleAddonDependency.getNamespace();
                    if (!compatibleAddonDependency.isOptional() && dependencyNamespace.equals(info.getNamespace())) {
                        if (!dependencies.contains(namespace)) {
                            dependencies.add(namespace);
                        }
                        gatherDependingAddons(addon, dependencies);
                    }
                }
            }
        }
    }

    private void disableIncompatibleAddonsFallback() {
        List<InstalledAddonInfo> addons = new ArrayList<>(getAddons());
        for (InstalledAddonInfo addonInfo : addons) {
            disableIncompatibleAddons(addonInfo, incompatible -> {
                this.logger.warn("Addon {} is incompatible with addon {}. Unloading {}...", addonInfo.getNamespace(), incompatible, incompatible);
            });
        }
    }

    private void disableIncompatibleAddons(InstalledAddonInfo addonInfo, Consumer<String> logger) {
        String[] incompatibleAddons = addonInfo.getIncompatibleAddons();
        if (incompatibleAddons == null) {
            return;
        }
        for (String incompatibleAddon : incompatibleAddons) {
            InstalledAddonInfo incompatibleAddonInfo = getAddon(incompatibleAddon);
            if (incompatibleAddonInfo != null) {
                this.incompatibleAddons.add(incompatibleAddonInfo);
                getAddons().remove(incompatibleAddonInfo);
                logger.accept(incompatibleAddonInfo.getNamespace());
            }
        }
    }
}
