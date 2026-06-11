package net.labymod.api.labyconnect;

import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/LabyConnect.class */
@Referenceable
public interface LabyConnect {
    void connect();

    void connect(String str, int i);

    void disconnect(LabyConnectDisconnectEvent.Initiator initiator, String str);

    void forceDisconnect(LabyConnectDisconnectEvent.Initiator initiator, String str);

    LabyConnectState state();

    boolean isAuthenticated();

    boolean isConnectionEstablished();

    @Nullable
    LabyConnectSession getSession();

    String getLastDisconnectReason();

    ConfigProvider<LabyConnectConfigAccessor> configProvider();

    @Deprecated
    default boolean isConnected() {
        return isAuthenticated();
    }
}
