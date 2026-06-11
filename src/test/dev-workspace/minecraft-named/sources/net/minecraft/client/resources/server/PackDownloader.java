package net.minecraft.client.resources.server;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.server.packs.DownloadQueue;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/PackDownloader.class */
public interface PackDownloader {
    void download(Map<UUID, DownloadQueue.DownloadRequest> map, Consumer<DownloadQueue.BatchResult> consumer);
}
