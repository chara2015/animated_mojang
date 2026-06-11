package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/LiveLabyNetServerInfoWidget.class */
@AutoWidget
public class LiveLabyNetServerInfoWidget extends LabyNetServerInfoWidget<StorageServerData> {
    protected static final long UPDATE_INTERVAL_MILLIS = 5000;
    protected long nextUpdateMillis;

    public LiveLabyNetServerInfoWidget(@NotNull StorageServerData serverData, @NotNull LabyNetServerInfoCache<StorageServerData> cache) {
        super(serverData, cache);
    }

    public LiveLabyNetServerInfoWidget(@NotNull StorageServerData serverData) {
        super(serverData, new LabyNetServerInfoCache(serverData, null));
        cache().update();
        setDefaultCallback();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected boolean loadingIcon() {
        return serverInfo().getStatus() == ServerInfo.Status.LOADING;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.nextUpdateMillis != 0 && this.nextUpdateMillis <= TimeUtil.getMillis()) {
            stopRefreshing();
            cache().update();
        }
    }

    protected final void stopRefreshing() {
        this.nextUpdateMillis = 0L;
    }

    protected final void startRefreshing() {
        this.nextUpdateMillis = TimeUtil.getMillis() + UPDATE_INTERVAL_MILLIS;
    }

    public void setDefaultCallback() {
        cache().setCallback(cache -> {
            ServerInfo serverInfo = cache.serverInfo();
            if (serverInfo.getStatus() != ServerInfo.Status.LOADING) {
                startRefreshing();
            }
            updateServerInfo(serverInfo);
        });
    }
}
