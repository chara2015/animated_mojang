package net.labymod.core.client.gui.screen.widget.widgets.multiplayer.friend;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/friend/FriendPublicServerInfoWidget.class */
@AutoWidget
public class FriendPublicServerInfoWidget extends LabyNetServerInfoWidget<ConnectableServerData> {
    public FriendPublicServerInfoWidget(LabyNetServerInfoCache<ConnectableServerData> serverInfoCache) {
        super(serverInfoCache.serverInfo(), serverInfoCache);
    }
}
