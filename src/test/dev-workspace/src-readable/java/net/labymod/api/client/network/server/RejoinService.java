package net.labymod.api.client.network.server;

import net.labymod.api.client.component.Component;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/RejoinService.class */
@Referenceable
public interface RejoinService {
    @Nullable
    ConnectableServerData getLastServerData();

    boolean canRejoin();

    void rejoin(Object obj);

    @Nullable
    Component getLastKickReason();
}
