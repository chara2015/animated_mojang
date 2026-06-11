package net.labymod.core.addon.loader;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/AddonLoaderSubService.class */
public abstract class AddonLoaderSubService {
    protected final AddonLoader addonLoader;
    private final SubServiceStage stage;
    private List<InstalledAddonInfo> stageList;
    protected final Logging logger = Logging.create(getClass());
    protected final DefaultLabyModLoader labyModLoader = (DefaultLabyModLoader) LabyMod.getInstance().labyModLoader();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/AddonLoaderSubService$SubServiceStage.class */
    public enum SubServiceStage {
        INITIAL,
        UPDATE,
        DOWNLOAD,
        VERIFY,
        PREPARE
    }

    public abstract void handle() throws Exception;

    protected AddonLoaderSubService(AddonLoader addonLoader, SubServiceStage stage) {
        this.addonLoader = addonLoader;
        this.stage = stage;
    }

    public void completed() throws Exception {
    }

    public final SubServiceStage stage() {
        return this.stage;
    }

    protected final List<InstalledAddonInfo> getAddons() {
        return this.stageList;
    }

    public final void setAddons(List<InstalledAddonInfo> list) {
        this.stageList = list;
    }

    protected final InstalledAddonInfo getAddon(String namespace) {
        InstalledAddonInfo classpathAddon = this.addonLoader.classpathAddonLoader().getAddonInfo();
        if (classpathAddon != null && classpathAddon.getNamespace().equals(namespace)) {
            return classpathAddon;
        }
        return (InstalledAddonInfo) CollectionHelper.get(this.stageList, addonInfo -> {
            return addonInfo.getNamespace().equals(namespace);
        });
    }

    protected final void addAddon(InstalledAddonInfo newAddonInfo) {
        addAddon(newAddonInfo, false);
    }

    protected final void addAddon(InstalledAddonInfo newAddonInfo, boolean forceNewer) {
        boolean useNew;
        InstalledAddonInfo installedAddonInfo = getAddon(newAddonInfo.getNamespace());
        if (installedAddonInfo != null) {
            if (forceNewer) {
                useNew = true;
            } else if (!newAddonInfo.isFlintAddon() && !installedAddonInfo.isFlintAddon()) {
                useNew = isNewVersionHigher(installedAddonInfo, newAddonInfo);
            } else if (newAddonInfo.isFlintAddon() && installedAddonInfo.isFlintAddon()) {
                try {
                    FlintIndex flintIndex = LabyMod.references().flintController().getFlintIndex();
                    FlintModification modification = flintIndex.getModification(newAddonInfo.getNamespace());
                    String fileHash = modification == null ? null : modification.getFileHash();
                    if (fileHash != null && fileHash.equals(installedAddonInfo.getFileHash())) {
                        useNew = false;
                    } else if (fileHash != null && fileHash.equals(newAddonInfo.getFileHash())) {
                        useNew = true;
                    } else {
                        useNew = isNewVersionHigher(installedAddonInfo, newAddonInfo);
                    }
                } catch (Exception e) {
                    useNew = isNewVersionHigher(installedAddonInfo, newAddonInfo);
                    this.logger.warn("Addon could not compare hashes of identical flint addon {}: installed: {} ({}), new: {} ({})", installedAddonInfo.getNamespace(), installedAddonInfo.getVersion(), installedAddonInfo.getFileHash(), newAddonInfo.getVersion(), newAddonInfo.getFileHash());
                }
            } else {
                useNew = !newAddonInfo.isFlintAddon();
            }
            if (!useNew) {
                return;
            }
            this.logger.info("Replacing addon {} v{} ({}) with v{} ({})", installedAddonInfo.getNamespace(), installedAddonInfo.getVersion(), installedAddonInfo.getFileHash(), newAddonInfo.getVersion(), newAddonInfo.getFileHash());
            this.stageList.remove(installedAddonInfo);
        }
        this.stageList.add(newAddonInfo);
    }

    private boolean isNewVersionHigher(InstalledAddonInfo installedAddon, InstalledAddonInfo newAddon) {
        try {
            Version installedVersion = VersionDeserializer.from(installedAddon.getVersion());
            Version newVersion = VersionDeserializer.from(newAddon.getVersion());
            return !installedVersion.isGreaterThan(newVersion);
        } catch (Exception e) {
            this.logger.warn("Addon could not compare versions of identical addon {}: installed: {}, new: {}", installedAddon.getNamespace(), installedAddon.getVersion(), newAddon.getVersion());
            return true;
        }
    }

    protected final InstalledAddonInfo loadAddonInfo(Path path) {
        return loadAddonInfo(path, null);
    }

    protected final InstalledAddonInfo loadAddonInfo(Path path, Predicate<InstalledAddonInfo> predicate) {
        InstalledAddonInfo addonInfo = null;
        try {
            addonInfo = this.addonLoader.loadAddonInfo(path);
            if (predicate != null && predicate.test(addonInfo)) {
                return null;
            }
            addAddon(addonInfo);
        } catch (Exception exception) {
            String identifier = path.getFileName().toString();
            if (addonInfo != null) {
                identifier = identifier + " (" + addonInfo.getNamespace() + ")";
            }
            this.logger.error("Unable to load addon {}. {}: {}", identifier, exception.getClass().getSimpleName(), exception.getMessage());
            addonInfo = null;
        }
        return addonInfo;
    }

    protected final boolean isAddonInList(String namespace) {
        InstalledAddonInfo classpathAddon = this.addonLoader.classpathAddonLoader().getAddonInfo();
        return (classpathAddon != null && classpathAddon.getNamespace().equals(namespace)) || this.addonLoader.isAddonInList(this.stageList, namespace);
    }

    protected final void logError(String message, Throwable throwable) {
        this.logger.error(message + ". {}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
    }
}
