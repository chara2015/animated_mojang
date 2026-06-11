package net.labymod.core.client.network.server.connect;

import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/connect/AddressResolveCallback.class */
public interface AddressResolveCallback {
    void updateStatus(Component component);

    void abort();

    default void abort(Component reason) {
        updateStatus(reason);
        abort();
    }
}
