package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import java.util.ArrayList;
import java.util.Locale;
import java.util.function.BiConsumer;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.network.server.global.PublicServerData;
import net.labymod.api.client.network.server.global.PublicServerListService;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.PublicServerInfoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/PublicServerListActivity.class */
@AutoActivity
@Link("activity/multiplayer/public-server-list.lss")
public class PublicServerListActivity extends LabyNetServerListActivity<PublicServerData, PublicServerInfoWidget> {
    private final PublicServerListService serverList;
    private final ButtonWidget saveButton;

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public PublicServerListActivity(TextFieldWidget textFieldWidget) {
        super("public", textFieldWidget);
        this.serverList = this.serverController.publicServerListService();
        getOrRegisterServerData(null);
        this.saveButton = ButtonWidget.i18n(getTranslationKey("button.saveServer"), this::saveSelected);
        setServerButtonsEnabled(false);
        this.serverListWidget.setSelectCallback((Selectable<T>) widget -> {
            setServerButtonsEnabled(true);
        });
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillServerList(VerticalListWidget<PublicServerInfoWidget> serverListWidget, String searchQuery) {
        getOrRegisterServerData((data, cache) -> {
            if (searchQuery != null) {
                String name = data.getName().trim().toLowerCase(Locale.ROOT);
                String address = data.address().getHost().trim().toLowerCase(Locale.ROOT);
                if (!name.contains(searchQuery) && !address.contains(searchQuery)) {
                    return;
                }
            }
            PublicServerInfoWidget widget = new PublicServerInfoWidget(data, cache, this::canSave, this::save);
            widget.setMovable(ServerInfoWidget.Movable.ADD, movable -> {
                widget.connect();
            });
            serverListWidget.addChild(widget, false);
        });
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillButtonContainer(FlexibleContentWidget container) {
        setServerButtonsEnabled(this.session.getSelectedEntry() != null);
        container.addFlexibleContent(this.joinButton);
        container.addFlexibleContent(this.saveButton);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void setServerButtonsEnabled(boolean enabled) {
        super.setServerButtonsEnabled(enabled);
        this.saveButton.setEnabled(canSaveSelected());
    }

    private void saveSelected() {
        if (save((PublicServerInfoWidget) this.session.getSelectedEntry())) {
            this.saveButton.setEnabled(false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean save(PublicServerInfoWidget serverInfoWidget) {
        if (serverInfoWidget == null) {
            return false;
        }
        PublicServerData publicServerData = (PublicServerData) serverInfoWidget.serverData();
        this.serverController.serverList().add(StorageServerData.of(publicServerData.getName(), publicServerData.address()));
        setServerButtonsEnabled(this.session.getSelectedEntry() != null);
        return true;
    }

    private boolean canSaveSelected() {
        return canSave((PublicServerInfoWidget) this.session.getSelectedEntry());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean canSave(PublicServerInfoWidget serverInfoWidget) {
        if (serverInfoWidget == null) {
            return false;
        }
        PublicServerData publicServerData = (PublicServerData) serverInfoWidget.serverData();
        return !this.serverController.serverList().has(publicServerData.address());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void getOrRegisterServerData(BiConsumer<PublicServerData, LabyNetServerInfoCache<PublicServerData>> biConsumer) {
        ArrayList<PublicServerData> servers = this.serverList.getServers();
        for (int i = 0; i < servers.size(); i++) {
            PublicServerData serverData = servers.get(i);
            LabyNetServerInfoCache<T> labyNetServerInfoCacheRegisterCache = registerCache(serverData, server -> {
                ServerInfoWidget<?> serverInfoWidget = getServerInfoWidget(serverData);
                if (serverInfoWidget != null) {
                    serverInfoWidget.updateServerInfo(server.serverInfo());
                }
            });
            labyNetServerInfoCacheRegisterCache.setSortingValue(i);
            if (biConsumer != null) {
                biConsumer.accept(serverData, labyNetServerInfoCacheRegisterCache);
            }
        }
        sortCache();
    }
}
