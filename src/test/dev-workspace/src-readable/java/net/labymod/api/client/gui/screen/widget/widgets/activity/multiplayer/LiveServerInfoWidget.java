package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.ServerInfoCache;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/LiveServerInfoWidget.class */
@AutoWidget
public class LiveServerInfoWidget<T extends ServerData> extends ServerInfoWidget<T> {
    private static final int UPDATE_INTERVAL_TICKS = 100;
    private final ServerInfoCache<T> cache;
    private int tick;

    public LiveServerInfoWidget(@NotNull T serverData) {
        super(serverData);
        this.cache = new ServerInfoCache<>(serverData, cache -> {
            updateServerInfo(cache.serverInfo());
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        int i = this.tick;
        this.tick = i + 1;
        if (i % UPDATE_INTERVAL_TICKS == 0) {
            this.cache.update();
        }
    }
}
