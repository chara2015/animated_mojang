package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget;
import net.labymod.core.configuration.labymod.LabyConfigProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/LabyNetServerListActivity.class */
public abstract class LabyNetServerListActivity<T extends ServerData, W extends ServerEntryWidget> extends ServerListActivity<T, W> {
    private static final Logging LOGGER = Logging.getLogger();
    private long nextUpdateMillis;

    protected LabyNetServerListActivity(String identifier, TextFieldWidget searchField) {
        super(identifier, searchField);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void refresh(boolean visible) {
        this.nextUpdateMillis = 0L;
        this.lastRefreshTime = TimeUtil.getMillis();
        List<T> children = this.serverListWidget.getChildren();
        Iterator it = children.iterator();
        while (it.hasNext()) {
            if (((ServerEntryWidget) it.next()).isDragging()) {
                return;
            }
        }
        int size = children.size();
        for (int index = 0; index < size; index++) {
            try {
                ServerEntryWidget serverEntryWidget = (ServerEntryWidget) children.get(index);
                if (serverEntryWidget != null && !serverEntryWidget.isDragging()) {
                    if (serverEntryWidget instanceof LabyNetServerInfoWidget) {
                        LabyNetServerInfoWidget<?> serverInfo = (LabyNetServerInfoWidget) serverEntryWidget;
                        serverInfo.refresh(visible);
                    }
                }
            } catch (Throwable throwable) {
                LOGGER.debug("Error refreshing server list", throwable);
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        ServerListConfig serverListConfig = LabyConfigProvider.INSTANCE.get().multiplayer().serverList();
        if (!serverListConfig.liveServerList().get().booleanValue()) {
            this.nextUpdateMillis = 0L;
        } else if (this.nextUpdateMillis == 0) {
            this.nextUpdateMillis = TimeUtil.getMillis() + ((long) (serverListConfig.cooldown().get().intValue() * SubmissionOrders.DEBUG));
        } else if (TimeUtil.getMillis() >= this.nextUpdateMillis) {
            refresh(false);
        }
    }
}
