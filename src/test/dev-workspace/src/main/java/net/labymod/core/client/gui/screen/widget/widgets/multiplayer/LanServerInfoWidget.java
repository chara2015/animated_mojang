package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.ServerInfoCache;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/LanServerInfoWidget.class */
@AutoWidget
public class LanServerInfoWidget extends ServerInfoWidget<ConnectableServerData> {
    public LanServerInfoWidget(@NotNull ServerInfoCache<ConnectableServerData> serverInfoCache) {
        super(serverInfoCache.serverInfo(), serverInfoCache.serverInfo());
        serverInfoCache.setCallback(this::updateServerInfo);
    }

    public void updateServerInfo(ServerInfoCache<ConnectableServerData> serverInfoCache) {
        ServerInfo serverInfo = serverInfoCache.serverInfo();
        updateServerInfo(serverInfo);
    }
}
