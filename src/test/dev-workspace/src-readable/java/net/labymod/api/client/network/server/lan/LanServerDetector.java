package net.labymod.api.client.network.server.lan;

import java.net.InetAddress;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/lan/LanServerDetector.class */
@Referenceable
public interface LanServerDetector {
    @NotNull
    InetAddress broadCastAddress();

    void startDetectionTask(@NotNull LanServerCallback lanServerCallback);

    void terminateDetectionTask();

    void reset();

    void close();
}
