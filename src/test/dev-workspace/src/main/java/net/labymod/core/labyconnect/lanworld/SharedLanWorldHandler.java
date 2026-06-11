package net.labymod.core.labyconnect.lanworld;

import net.labymod.api.Laby;
import net.labymod.api.client.chat.ActionBar;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.labypeer.client.tunnel.PeerTunnel;
import net.labymod.labypeer.client.tunnel.handler.TunnelHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/lanworld/SharedLanWorldHandler.class */
public class SharedLanWorldHandler implements TunnelHandler {
    private final ChatExecutor chatExecutor = Laby.labyAPI().minecraft().chatExecutor();
    private final SharedLanWorldService service;
    private final PeerTunnel tunnel;
    private final String username;
    private ActionBar actionBar;

    public SharedLanWorldHandler(SharedLanWorldService service, PeerTunnel tunnel, String username) {
        this.service = service;
        this.tunnel = tunnel;
        this.username = username;
    }

    public void onTunnelOpen() {
    }

    public void onTunnelClose() {
        this.service.getOpenTunnels().values().remove(this.tunnel);
    }

    public void onConnectionLost() {
        execute(() -> {
            this.actionBar = this.chatExecutor.displayActionBarContinuous(Component.translatable("labymod.lanworld.reconnect.connectionLost", NamedTextColor.RED, Component.text(this.username)));
        });
    }

    public void onReconnectFailed(int tryIndex, int upcomingTries) {
        if (upcomingTries != 0) {
            return;
        }
        execute(() -> {
            if (this.actionBar != null) {
                this.actionBar.remove();
                this.actionBar = null;
            }
            Component message = Component.translatable("labymod.lanworld.reconnect.failed", NamedTextColor.RED, Component.text(this.username));
            if (this.service.isHost()) {
                this.chatExecutor.displayActionBar(message);
            } else {
                Laby.labyAPI().minecraft().clientPacketListener().simulateKick(message);
            }
        });
    }

    public void onReconnectSuccess(int tryIndex) {
        execute(() -> {
            if (this.actionBar != null) {
                this.actionBar.remove();
                this.actionBar = null;
            }
            this.chatExecutor.displayActionBar(Component.translatable("labymod.lanworld.reconnect.success", NamedTextColor.GREEN, Component.text(this.username)));
        });
    }

    private void execute(Runnable runnable) {
        Laby.labyAPI().minecraft().executeOnRenderThread(runnable);
    }
}
