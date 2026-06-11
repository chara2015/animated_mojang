package net.labymod.core.addon.loader.initial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.labymod.api.Constants;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/initial/InstalledAddonLoader.class */
public class InstalledAddonLoader extends AddonLoaderSubService {
    private final List<String> scheduledForRemoval;

    public InstalledAddonLoader(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.INITIAL);
        this.scheduledForRemoval = new ArrayList();
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        if (!IOUtil.exists(Constants.Files.ADDONS)) {
            IOUtil.createDirectories(Constants.Files.ADDONS);
            return;
        }
        gatherAddonsScheduledForRemoval();
        for (Path path : getJarsInAddonDirectory()) {
            loadAddonInfo(path, addonInfo -> {
                if (!this.scheduledForRemoval.contains(addonInfo.getNamespace())) {
                    return false;
                }
                try {
                    Files.delete(path);
                    return true;
                } catch (IOException e) {
                    this.logger.error("Unable to remove addon {}", addonInfo.getNamespace(), e);
                    return true;
                }
            });
        }
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void completed() throws Exception {
        try {
            if (this.scheduledForRemoval.isEmpty()) {
                return;
            }
            if (IOUtil.exists(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL)) {
                Files.delete(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL);
            }
        } catch (Exception e) {
            logError("Could not delete addons scheduled for removal file", e);
        }
    }

    private List<Path> getJarsInAddonDirectory() throws IOException {
        Stream<Path> paths = Files.list(Constants.Files.ADDONS);
        try {
            List<Path> list = (List) paths.filter(path -> {
                return !IOUtil.isDirectory(path);
            }).filter(path2 -> {
                return path2.getFileName().toString().endsWith(".jar");
            }).collect(Collectors.toList());
            if (paths != null) {
                paths.close();
            }
            return list;
        } catch (Throwable th) {
            if (paths != null) {
                try {
                    paths.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void gatherAddonsScheduledForRemoval() {
        if (!IOUtil.exists(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL)) {
            return;
        }
        this.scheduledForRemoval.clear();
        try {
            this.scheduledForRemoval.addAll(Files.readAllLines(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL));
        } catch (IOException e) {
            logError("Could not read addons scheduled for removal file", e);
        }
    }
}
