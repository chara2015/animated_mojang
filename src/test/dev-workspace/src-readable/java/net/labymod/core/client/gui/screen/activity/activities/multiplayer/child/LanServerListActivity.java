package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LanServerInfoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/LanServerListActivity.class */
@AutoActivity
@Link("activity/multiplayer/lan-server-list.lss")
public class LanServerListActivity extends ServerListActivity<ConnectableServerData, ServerInfoWidget<ConnectableServerData>> {
    private final Map<ConnectableServerData, ServerInfoCache<ConnectableServerData>> lanServers;
    private Runnable serverRemoveCallback;
    private int updateTick;

    public LanServerListActivity() {
        super("lan", null);
        this.lanServers = new HashMap();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        int i = this.updateTick;
        this.updateTick = i + 1;
        if (i == 50) {
            this.updateTick = 0;
            refresh(false);
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillServerList(VerticalListWidget<ServerInfoWidget<ConnectableServerData>> serverListWidget, String searchQuery) {
        for (ServerInfoCache<ConnectableServerData> serverCache : this.lanServers.values()) {
            addServerWidget(serverCache, false);
        }
    }

    private void addServerWidget(ServerInfoCache<ConnectableServerData> cache, boolean initialize) {
        LanServerInfoWidget serverInfoWidget = new LanServerInfoWidget(cache);
        serverInfoWidget.setMovable(ServerInfoWidget.Movable.ADD, movable -> {
            serverInfoWidget.serverData().connect();
        });
        if (initialize) {
            this.serverListWidget.addChildInitialized(serverInfoWidget);
        } else {
            this.serverListWidget.addChild(serverInfoWidget);
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private void removeServerWidget(ServerInfoCache<ConnectableServerData> serverInfoCache) {
        Iterator it = this.serverListWidget.findChildrenIf((Predicate<T>) widget -> {
            return ((ConnectableServerData) widget.serverData()).address().equals(serverInfoCache.serverAddress());
        }).iterator();
        while (it.hasNext()) {
            this.serverListWidget.removeChild((ServerInfoWidget) ((Widget) it.next()));
        }
        if (this.serverRemoveCallback != null) {
            this.serverRemoveCallback.run();
        }
    }

    public void removeLanServer(ConnectableServerData data) {
        ServerInfoCache<ConnectableServerData> cache = this.lanServers.remove(data);
        if (cache != null) {
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                removeServerWidget(cache);
            });
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void refresh(boolean visible) {
        for (ServerInfoCache<ConnectableServerData> cache : this.lanServers.values()) {
            cache.update();
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillButtonContainer(FlexibleContentWidget container) {
        container.addFlexibleContent(this.joinButton);
    }

    public void addLanServer(ConnectableServerData serverData, Runnable runnable) {
        if (this.lanServers.containsKey(serverData)) {
            return;
        }
        ServerInfoCache<ConnectableServerData> cache = new ServerInfoCache<>(serverData, null);
        cache.setProtectIp(false);
        this.lanServers.put(serverData, cache);
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            if (((Document) this.document).isInitialized()) {
                addServerWidget(cache, true);
            }
            runnable.run();
        });
        cache.setTimeout(2500);
        cache.update();
    }

    public Map<ConnectableServerData, ServerInfoCache<ConnectableServerData>> getLanServers() {
        return this.lanServers;
    }

    public void setServerRemoveCallback(Runnable serverRemoveCallback) {
        this.serverRemoveCallback = serverRemoveCallback;
    }
}
