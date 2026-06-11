package net.labymod.core.flint.downloader;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.I18n;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.addon.loader.AddonValidator;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/downloader/FlintDownloadTask.class */
public class FlintDownloadTask {
    private static final FlintController CONTROLLER = LabyMod.references().flintController();
    private static final Logging LOGGER = Logging.create((Class<?>) FlintDownloader.class);
    private final FlintModification modification;
    private final List<String> skippedDependencies;
    private Consumer<FlintDownloadTask> finishedCallback;
    private DownloadState state;
    private double percentage;

    public FlintDownloadTask(FlintModification modification, List<String> skippedDependencies, Consumer<FlintDownloadTask> finished) {
        this.modification = modification;
        this.skippedDependencies = skippedDependencies == null ? List.of() : skippedDependencies;
        this.finishedCallback = finished;
        this.state = DownloadState.PREPARE;
    }

    public static FlintDownloadTask dummy(FlintModification modification, DownloadState state) {
        FlintDownloadTask flintDownloadTask = new FlintDownloadTask(modification, null, null);
        flintDownloadTask.state = state;
        return flintDownloadTask;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void download() {
        this.state = DownloadState.DOWNLOADING;
        ((AddonDownloadRequest) AddonDownloadRequest.create().async()).namespace(this.modification).downloadDependencies(true).downloadOptionalDependencies(true).skipDependencies(this.skippedDependencies).throwNotInIndexException().existsStrategy((namespace, installedAddonInfo) -> {
            return namespace.equals(this.modification.getNamespace());
        }).percentageConsumer(percentage -> {
            if (percentage.doubleValue() <= 95.0d) {
                this.percentage = percentage.doubleValue();
            } else {
                this.percentage = 95.0d;
            }
        }).execute(result -> {
            if (result.hasException()) {
                this.state = DownloadState.FAILED;
                callFinishedCallback();
                LOGGER.error("Failed to download modification " + this.modification.getNamespace(), result.exception());
            } else if (!result.isPresent()) {
                this.state = DownloadState.FAILED;
                callFinishedCallback();
                LOGGER.warn("Tried to download modification " + this.modification.getNamespace() + " but result is empty", new Object[0]);
            } else if (((AddonDownloadRequest.AddonDownloadResult) result.get()).getAddonInfos().isEmpty()) {
                this.state = DownloadState.FAILED;
                callFinishedCallback();
                LOGGER.warn("Tried to download modification " + this.modification.getNamespace() + " but the list of addons is empty (is it already installed but was not enabled?)", new Object[0]);
            } else {
                this.state = DownloadState.LOADING;
                Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                    this.percentage = 100.0d;
                    try {
                        enableAddons(((AddonDownloadRequest.AddonDownloadResult) result.get()).getAddonInfos());
                        if (this.state == DownloadState.LOADING) {
                            this.state = DownloadState.FINISHED;
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Failed to enable addons. Cause: " + e.getClass().getSimpleName() + ": " + e.getMessage(), new Object[0]);
                        if (this.state == DownloadState.LOADING) {
                            this.state = DownloadState.FAILED;
                        }
                        Notification addonStore = Notification.builder().title(Component.text(Constants.Branding.NAME)).text(Component.text(e.getMessage())).icon(Textures.SpriteLabyMod.DEFAULT_WOLF_HIGH_RES).duration(20000L).build();
                        Laby.references().notificationController().push(addonStore);
                    }
                    if (this.finishedCallback != null) {
                        this.finishedCallback.accept(this);
                    }
                });
            }
        });
    }

    public void setFinishedCallback(Consumer<FlintDownloadTask> finishedCallback) {
        this.finishedCallback = finishedCallback;
    }

    public boolean isFinished() {
        return this.state == DownloadState.FINISHED;
    }

    public DownloadState state() {
        return this.state;
    }

    public double getPercentage() {
        return this.percentage;
    }

    private void callFinishedCallback() {
        if (this.finishedCallback == null) {
            return;
        }
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            this.finishedCallback.accept(this);
        });
    }

    private void enableAddons(List<InstalledAddonInfo> addons) throws IOException {
        FlintIndex.IndexFilter filter = CONTROLLER.getFlintIndex().filter();
        for (InstalledAddonInfo addon : addons) {
            Optional<FlintModification> optionalModification = filter.namespace(addon.getNamespace());
            if (!optionalModification.isPresent()) {
                throw new IllegalArgumentException("Could not find modification for namespace " + addon.getNamespace());
            }
            FlintModification modification = optionalModification.get();
            if (modification.hasMeta(AddonMeta.RESTART_REQUIRED)) {
                this.state = DownloadState.REQUIRES_RESTART;
                return;
            }
        }
        DefaultAddonService addonService = DefaultAddonService.getInstance();
        for (InstalledAddonInfo addon2 : addons) {
            String namespace = addon2.getNamespace();
            Optional<LoadedAddon> optionalAddon = addonService.getAddon(namespace);
            if (optionalAddon.isPresent()) {
                throw new IllegalStateException(I18n.getTranslation("labymod.addons.store.download.alreadyInstalled", addon2.getNamespace()));
            }
        }
        for (InstalledAddonInfo addon3 : addons) {
            if (AddonValidator.isBuildNumberGreater(addon3)) {
                throw new IllegalStateException(I18n.getTranslation("labymod.addons.store.download.outdated", addon3.getNamespace()));
            }
        }
        AddonLoader addonLoader = addonService.addonLoader();
        Iterator<InstalledAddonInfo> it = addons.iterator();
        while (it.hasNext()) {
            addonLoader.addonPreparer().loadAddon(it.next(), AddonPreparer.AddonPrepareContext.RUNTIME);
        }
    }
}
