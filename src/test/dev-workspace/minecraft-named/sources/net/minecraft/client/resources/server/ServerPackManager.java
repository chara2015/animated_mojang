package net.minecraft.client.resources.server;

import com.google.common.hash.HashCode;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.resources.server.PackLoadFeedback;
import net.minecraft.client.resources.server.PackReloadConfig;
import net.minecraft.server.packs.DownloadQueue;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/ServerPackManager.class */
public class ServerPackManager {
    private final PackDownloader downloader;
    final PackLoadFeedback packLoadFeedback;
    private final PackReloadConfig reloadConfig;
    private final Runnable updateRequest;
    private PackPromptStatus packPromptStatus;
    final List<ServerPackData> packs = new ArrayList();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/ServerPackManager$ActivationStatus.class */
    enum ActivationStatus {
        INACTIVE,
        PENDING,
        ACTIVE
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/ServerPackManager$PackDownloadStatus.class */
    enum PackDownloadStatus {
        REQUESTED,
        PENDING,
        DONE
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/ServerPackManager$PackPromptStatus.class */
    public enum PackPromptStatus {
        PENDING,
        ALLOWED,
        DECLINED
    }

    public ServerPackManager(PackDownloader $$0, PackLoadFeedback $$1, PackReloadConfig $$2, Runnable $$3, PackPromptStatus $$4) {
        this.downloader = $$0;
        this.packLoadFeedback = $$1;
        this.reloadConfig = $$2;
        this.updateRequest = $$3;
        this.packPromptStatus = $$4;
    }

    void registerForUpdate() {
        this.updateRequest.run();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/ServerPackManager$RemovalReason.class */
    enum RemovalReason {
        DOWNLOAD_FAILED(PackLoadFeedback.FinalResult.DOWNLOAD_FAILED),
        ACTIVATION_FAILED(PackLoadFeedback.FinalResult.ACTIVATION_FAILED),
        DECLINED(PackLoadFeedback.FinalResult.DECLINED),
        DISCARDED(PackLoadFeedback.FinalResult.DISCARDED),
        SERVER_REMOVED(null),
        SERVER_REPLACED(null);

        final PackLoadFeedback.FinalResult serverResponse;

        RemovalReason(PackLoadFeedback.FinalResult $$0) {
            this.serverResponse = $$0;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/ServerPackManager$ServerPackData.class */
    static class ServerPackData {
        final UUID id;
        final URL url;
        final HashCode hash;
        Path path;
        RemovalReason removalReason;
        PackDownloadStatus downloadStatus = PackDownloadStatus.REQUESTED;
        ActivationStatus activationStatus = ActivationStatus.INACTIVE;
        boolean promptAccepted;

        ServerPackData(UUID $$0, URL $$1, HashCode $$2) {
            this.id = $$0;
            this.url = $$1;
            this.hash = $$2;
        }

        public void setRemovalReasonIfNotSet(RemovalReason $$0) {
            if (this.removalReason == null) {
                this.removalReason = $$0;
            }
        }

        public boolean isRemoved() {
            return this.removalReason != null;
        }
    }

    private void markExistingPacksAsRemoved(UUID $$0) {
        for (ServerPackData $$1 : this.packs) {
            if ($$1.id.equals($$0)) {
                $$1.setRemovalReasonIfNotSet(RemovalReason.SERVER_REPLACED);
            }
        }
    }

    public void pushPack(UUID $$0, URL $$1, HashCode $$2) {
        if (this.packPromptStatus == PackPromptStatus.DECLINED) {
            this.packLoadFeedback.reportFinalResult($$0, PackLoadFeedback.FinalResult.DECLINED);
        } else {
            pushNewPack($$0, new ServerPackData($$0, $$1, $$2));
        }
    }

    public void pushLocalPack(UUID $$0, Path $$1) {
        if (this.packPromptStatus == PackPromptStatus.DECLINED) {
            this.packLoadFeedback.reportFinalResult($$0, PackLoadFeedback.FinalResult.DECLINED);
            return;
        }
        try {
            URL $$4 = $$1.toUri().toURL();
            ServerPackData $$5 = new ServerPackData($$0, $$4, null);
            $$5.downloadStatus = PackDownloadStatus.DONE;
            $$5.path = $$1;
            pushNewPack($$0, $$5);
        } catch (MalformedURLException $$3) {
            throw new IllegalStateException("Can't convert path to URL " + String.valueOf($$1), $$3);
        }
    }

    private void pushNewPack(UUID $$0, ServerPackData $$1) {
        markExistingPacksAsRemoved($$0);
        this.packs.add($$1);
        if (this.packPromptStatus == PackPromptStatus.ALLOWED) {
            acceptPack($$1);
        }
        registerForUpdate();
    }

    private void acceptPack(ServerPackData $$0) {
        this.packLoadFeedback.reportUpdate($$0.id, PackLoadFeedback.Update.ACCEPTED);
        $$0.promptAccepted = true;
    }

    private ServerPackData findPackInfo(UUID $$0) {
        for (ServerPackData $$1 : this.packs) {
            if (!$$1.isRemoved() && $$1.id.equals($$0)) {
                return $$1;
            }
        }
        return null;
    }

    public void popPack(UUID $$0) {
        ServerPackData $$1 = findPackInfo($$0);
        if ($$1 != null) {
            $$1.setRemovalReasonIfNotSet(RemovalReason.SERVER_REMOVED);
            registerForUpdate();
        }
    }

    public void popAll() {
        for (ServerPackData $$0 : this.packs) {
            $$0.setRemovalReasonIfNotSet(RemovalReason.SERVER_REMOVED);
        }
        registerForUpdate();
    }

    public void allowServerPacks() {
        this.packPromptStatus = PackPromptStatus.ALLOWED;
        for (ServerPackData $$0 : this.packs) {
            if (!$$0.promptAccepted && !$$0.isRemoved()) {
                acceptPack($$0);
            }
        }
        registerForUpdate();
    }

    public void rejectServerPacks() {
        this.packPromptStatus = PackPromptStatus.DECLINED;
        for (ServerPackData $$0 : this.packs) {
            if (!$$0.promptAccepted) {
                $$0.setRemovalReasonIfNotSet(RemovalReason.DECLINED);
            }
        }
        registerForUpdate();
    }

    public void resetPromptStatus() {
        this.packPromptStatus = PackPromptStatus.PENDING;
    }

    public void tick() {
        boolean $$0 = updateDownloads();
        if (!$$0) {
            triggerReloadIfNeeded();
        }
        cleanupRemovedPacks();
    }

    private void cleanupRemovedPacks() {
        this.packs.removeIf($$0 -> {
            if ($$0.activationStatus == ActivationStatus.INACTIVE && $$0.removalReason != null) {
                PackLoadFeedback.FinalResult $$1 = $$0.removalReason.serverResponse;
                if ($$1 != null) {
                    this.packLoadFeedback.reportFinalResult($$0.id, $$1);
                    return true;
                }
                return true;
            }
            return false;
        });
    }

    private void onDownload(Collection<ServerPackData> $$0, DownloadQueue.BatchResult $$1) {
        if (!$$1.failed().isEmpty()) {
            for (ServerPackData $$2 : this.packs) {
                if ($$2.activationStatus != ActivationStatus.ACTIVE) {
                    if ($$1.failed().contains($$2.id)) {
                        $$2.setRemovalReasonIfNotSet(RemovalReason.DOWNLOAD_FAILED);
                    } else {
                        $$2.setRemovalReasonIfNotSet(RemovalReason.DISCARDED);
                    }
                }
            }
        }
        for (ServerPackData $$3 : $$0) {
            Path $$4 = $$1.downloaded().get($$3.id);
            if ($$4 != null) {
                $$3.downloadStatus = PackDownloadStatus.DONE;
                $$3.path = $$4;
                if (!$$3.isRemoved()) {
                    this.packLoadFeedback.reportUpdate($$3.id, PackLoadFeedback.Update.DOWNLOADED);
                }
            }
        }
        registerForUpdate();
    }

    private boolean updateDownloads() {
        List<ServerPackData> $$0 = new ArrayList<>();
        boolean $$1 = false;
        for (ServerPackData $$2 : this.packs) {
            if (!$$2.isRemoved() && $$2.promptAccepted) {
                if ($$2.downloadStatus != PackDownloadStatus.DONE) {
                    $$1 = true;
                }
                if ($$2.downloadStatus == PackDownloadStatus.REQUESTED) {
                    $$2.downloadStatus = PackDownloadStatus.PENDING;
                    $$0.add($$2);
                }
            }
        }
        if (!$$0.isEmpty()) {
            Map<UUID, DownloadQueue.DownloadRequest> $$3 = new HashMap<>();
            for (ServerPackData $$4 : $$0) {
                $$3.put($$4.id, new DownloadQueue.DownloadRequest($$4.url, $$4.hash));
            }
            this.downloader.download($$3, $$12 -> {
                onDownload($$0, $$12);
            });
        }
        return $$1;
    }

    private void triggerReloadIfNeeded() {
        boolean $$0 = false;
        final List<ServerPackData> $$1 = new ArrayList<>();
        final List<ServerPackData> $$2 = new ArrayList<>();
        for (ServerPackData $$3 : this.packs) {
            if ($$3.activationStatus == ActivationStatus.PENDING) {
                return;
            }
            boolean $$4 = $$3.promptAccepted && $$3.downloadStatus == PackDownloadStatus.DONE && !$$3.isRemoved();
            if ($$4 && $$3.activationStatus == ActivationStatus.INACTIVE) {
                $$1.add($$3);
                $$0 = true;
            }
            if ($$3.activationStatus == ActivationStatus.ACTIVE) {
                if (!$$4) {
                    $$0 = true;
                    $$2.add($$3);
                } else {
                    $$1.add($$3);
                }
            }
        }
        if ($$0) {
            for (ServerPackData $$5 : $$1) {
                if ($$5.activationStatus != ActivationStatus.ACTIVE) {
                    $$5.activationStatus = ActivationStatus.PENDING;
                }
            }
            for (ServerPackData $$6 : $$2) {
                $$6.activationStatus = ActivationStatus.PENDING;
            }
            this.reloadConfig.scheduleReload(new PackReloadConfig.Callbacks() { // from class: net.minecraft.client.resources.server.ServerPackManager.1
                @Override // net.minecraft.client.resources.server.PackReloadConfig.Callbacks
                public void onSuccess() {
                    for (ServerPackData $$02 : $$1) {
                        $$02.activationStatus = ActivationStatus.ACTIVE;
                        if ($$02.removalReason == null) {
                            ServerPackManager.this.packLoadFeedback.reportFinalResult($$02.id, PackLoadFeedback.FinalResult.APPLIED);
                        }
                    }
                    for (ServerPackData $$12 : $$2) {
                        $$12.activationStatus = ActivationStatus.INACTIVE;
                    }
                    ServerPackManager.this.registerForUpdate();
                }

                @Override // net.minecraft.client.resources.server.PackReloadConfig.Callbacks
                public void onFailure(boolean $$02) {
                    if (!$$02) {
                        $$1.clear();
                        for (ServerPackData $$12 : ServerPackManager.this.packs) {
                            switch ($$12.activationStatus) {
                                case INACTIVE:
                                    $$12.setRemovalReasonIfNotSet(RemovalReason.DISCARDED);
                                    break;
                                case PENDING:
                                    $$12.activationStatus = ActivationStatus.INACTIVE;
                                    $$12.setRemovalReasonIfNotSet(RemovalReason.ACTIVATION_FAILED);
                                    break;
                                case ACTIVE:
                                    $$1.add($$12);
                                    break;
                            }
                        }
                        ServerPackManager.this.registerForUpdate();
                        return;
                    }
                    for (ServerPackData $$22 : ServerPackManager.this.packs) {
                        if ($$22.activationStatus == ActivationStatus.PENDING) {
                            $$22.activationStatus = ActivationStatus.INACTIVE;
                        }
                    }
                }

                @Override // net.minecraft.client.resources.server.PackReloadConfig.Callbacks
                public List<PackReloadConfig.IdAndPath> packsToLoad() {
                    return $$1.stream().map($$02 -> {
                        return new PackReloadConfig.IdAndPath($$02.id, $$02.path);
                    }).toList();
                }
            });
        }
    }
}
