package net.labymod.core.client.gui.screen.widget.widgets.multiplayer.friend;

import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/friend/FriendWorldsharingServerInfoWidget.class */
@AutoWidget
public class FriendWorldsharingServerInfoWidget extends ServerInfoWidget<ConnectableServerData> {
    private final Friend friend;
    private final Consumer<Friend> connectCallback;

    public FriendWorldsharingServerInfoWidget(Friend friend, @NotNull ServerInfoCache<ConnectableServerData> serverInfoCache, Consumer<Friend> connectCallback) {
        super(serverInfoCache.serverInfo(), serverInfoCache.serverInfo());
        serverInfoCache.setCallback(this::updateServerInfo);
        this.connectCallback = connectCallback;
        this.friend = friend;
    }

    public void updateServerInfo(ServerInfoCache<ConnectableServerData> serverInfoCache) {
        ServerInfo serverInfo = serverInfoCache.serverInfo();
        updateServerInfo(serverInfo);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    public void connect(String command, int delay) {
        this.connectCallback.accept(this.friend);
    }
}
