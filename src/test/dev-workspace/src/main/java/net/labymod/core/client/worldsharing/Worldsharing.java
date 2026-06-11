package net.labymod.core.client.worldsharing;

import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.EventBus;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.DashboardActivity;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.server.AbstractIntegratedServer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/Worldsharing.class */
@Singleton
@Referenceable
public class Worldsharing {
    public static final byte MIN_SLOTS = 2;
    public static final byte MAX_SLOTS = 6;
    public static final byte MAX_SLOTS_PLUS = 12;
    public static final String NAMESPACE = "worldsharing";
    public static final String CHANNEL = "laby:transfer";
    public static final String CLOUD_DOMAIN_PATTERN = ".laby.cloud:";
    public static final String CLOUD_DOMAIN_SUFFIX = ".laby.cloud";
    public static final int ADDR_SIZE_LIMIT = 128;
    private static AtomicReference<ChannelHandler> capturedChannelHandler;
    private static AtomicReference<EventLoopGroup> capturedEventLoop;
    private final NetEventHandler netEventHandler = new NetEventHandler(this);
    private final WorldStatusBroadcast broadcast = new WorldStatusBroadcast(netEventHandler());
    private final DashboardActivity dashboardActivity = new DashboardActivity(this);

    public Worldsharing(EventBus eventBus) {
        eventBus.registerListener(this.broadcast);
        eventBus.registerListener(this.netEventHandler);
    }

    public NetEventHandler netEventHandler() {
        return this.netEventHandler;
    }

    public DashboardActivity dashboardActivity() {
        return this.dashboardActivity;
    }

    public WorldStatusBroadcast broadcast() {
        return this.broadcast;
    }

    public static AbstractIntegratedServer integratedServer() {
        return (AbstractIntegratedServer) Laby.references().integratedServer();
    }

    public static void setChannelHandler(ChannelHandler channelHandler) {
        if (capturedChannelHandler == null) {
            capturedChannelHandler = new AtomicReference<>(channelHandler);
        } else {
            capturedChannelHandler.set(channelHandler);
        }
    }

    public static void setEventLoopGroup(EventLoopGroup eventLoop) {
        if (capturedEventLoop == null) {
            capturedEventLoop = new AtomicReference<>(eventLoop);
        } else {
            capturedEventLoop.set(eventLoop);
        }
    }

    public static EventLoopGroup getEventLoopGroup() {
        if (capturedEventLoop == null) {
            return null;
        }
        return capturedEventLoop.get();
    }

    public static ChannelHandler getChannelHandler() {
        if (capturedChannelHandler == null) {
            return null;
        }
        return capturedChannelHandler.get();
    }

    public static boolean checkAddr(String target) {
        int colon;
        if (target == null || (colon = target.indexOf(58)) <= 0 || colon == target.length() - 1) {
            return false;
        }
        try {
            int port = Integer.parseInt(target.substring(colon + 1));
            if (port < 1 || port > 65535) {
                return false;
            }
            return target.substring(0, colon).toLowerCase(Locale.ROOT).endsWith(CLOUD_DOMAIN_SUFFIX);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isPremiumAndConnected() {
        return Laby.labyAPI().minecraft().sessionAccessor().isPremium() && Laby.labyAPI().labyConnect().isAuthenticated();
    }

    public static void handle(String address, String target) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            Laby.labyAPI().minecraft().minecraftWindow().closeScreen();
            Laby.labyAPI().serverController().joinServer(createServerData(address, target));
        });
    }

    public static ConnectableServerData createServerData(String address, String target) {
        return ConnectableServerData.builder().name(address).address(target).build();
    }
}
