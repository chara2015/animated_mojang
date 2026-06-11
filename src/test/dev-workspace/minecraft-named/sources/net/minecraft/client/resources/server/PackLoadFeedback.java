package net.minecraft.client.resources.server;

import java.util.UUID;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/PackLoadFeedback.class */
public interface PackLoadFeedback {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/PackLoadFeedback$FinalResult.class */
    public enum FinalResult {
        DECLINED,
        APPLIED,
        DISCARDED,
        DOWNLOAD_FAILED,
        ACTIVATION_FAILED
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/server/PackLoadFeedback$Update.class */
    public enum Update {
        ACCEPTED,
        DOWNLOADED
    }

    void reportUpdate(UUID uuid, Update update);

    void reportFinalResult(UUID uuid, FinalResult finalResult);
}
