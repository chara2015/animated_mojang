package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/DirectConnectServerInfoWidget.class */
@AutoWidget
public class DirectConnectServerInfoWidget extends LiveLabyNetServerInfoWidget {
    public DirectConnectServerInfoWidget(@NotNull StorageServerData serverData, @NotNull LabyNetServerInfoCache<StorageServerData> cache) {
        super(serverData, cache);
        setFriendHeadsEnabled(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected Component serverName() {
        ConnectableServerData connectableServerData = (ConnectableServerData) serverData();
        String name = connectableServerData.getName();
        ServerAddress address = connectableServerData.address();
        if (address.getPort() != 25565) {
            name = name + ":" + address.getPort();
        }
        return Component.text(name);
    }

    public void setProgressable(boolean progressable) {
        if (progressable) {
            startRefreshing();
        } else {
            stopRefreshing();
        }
    }

    public float getProgress() {
        long nextUpdateMillis = this.nextUpdateMillis;
        if (nextUpdateMillis == 0) {
            return 0.0f;
        }
        long millis = TimeUtil.getMillis();
        if (nextUpdateMillis <= millis) {
            return 1.0f;
        }
        return (millis - (nextUpdateMillis - 5000)) / 5000.0f;
    }
}
