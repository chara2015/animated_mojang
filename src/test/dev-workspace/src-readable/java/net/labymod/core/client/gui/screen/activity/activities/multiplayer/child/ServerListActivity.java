package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.ServerFolderWidget;
import net.labymod.core.labyconnect.session.advertisement.AdServerData;
import net.labymod.core.labyconnect.session.advertisement.AdvertisementController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/ServerListActivity.class */
@Links({@Link("activity/multiplayer/server-list.lss"), @Link("activity/multiplayer/server-info.lss"), @Link("activity/multiplayer/server-folder.lss")})
public abstract class ServerListActivity<T extends ServerData, W extends ServerEntryWidget> extends Activity {
    private static final String TRANSLATION_KEY_PREFIX_PREFIX = "labymod.activity.multiplayer.";
    protected final List<LabyNetServerInfoCache<T>> serverInfos = Lists.newArrayList();
    protected final ServerListConfig settings;
    protected final ServerController serverController;
    protected final ListSession<W> session;
    protected final ServerListWidget<W> serverListWidget;
    protected final ButtonWidget joinButton;
    private final String identifier;
    private final String translationKeyPrefix;
    private final TextFieldWidget searchField;
    protected long lastRefreshTime;
    private String lastQuery;

    protected abstract void fillServerList(VerticalListWidget<W> verticalListWidget, String str);

    protected abstract void fillButtonContainer(FlexibleContentWidget flexibleContentWidget);

    protected abstract void refresh(boolean z);

    protected ServerListActivity(String identifier, TextFieldWidget searchField) {
        this.identifier = identifier;
        this.translationKeyPrefix = "labymod.activity.multiplayer." + identifier;
        this.searchField = searchField;
        LabyAPI labyAPI = Laby.labyAPI();
        this.settings = labyAPI.config().multiplayer().serverList();
        this.serverController = labyAPI.serverController();
        this.session = new ListSession<>(new MultiplayerActivity.ServerInfoWidgetEqualizer());
        this.serverListWidget = new ServerListWidget<>(this.session);
        this.serverListWidget.addId("server-list");
        this.serverListWidget.setDoubleClickCallback(widget -> {
            if (widget instanceof ServerInfoWidget) {
                ServerInfoWidget<?> serverInfo = (ServerInfoWidget) widget;
                serverInfo.connect();
            }
            if (widget instanceof ServerFolderWidget) {
                ServerFolderWidget serverFolder = (ServerFolderWidget) widget;
                serverFolder.enter();
            }
        });
        this.joinButton = ButtonWidget.i18n("labymod.activity.multiplayer.joinServer", () -> {
            W selectedWidget = this.session.getSelectedEntry();
            if (selectedWidget instanceof ServerInfoWidget) {
                ServerInfoWidget<?> serverInfo = (ServerInfoWidget) selectedWidget;
                serverInfo.connect();
                ServerData data = serverInfo.serverData();
                if (data instanceof AdServerData) {
                    AdServerData ad = (AdServerData) data;
                    ad.sendEvent(AdvertisementController.Event.JOIN);
                }
            }
        });
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        String query;
        super.initialize(parent);
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId(this.identifier + "-server-list-container", "server-list-container");
        DivWidget backgroundContainer = new DivWidget();
        backgroundContainer.addId("background-container");
        FlexibleContentWidget window = new FlexibleContentWidget();
        window.addId("window");
        if (this.searchField != null) {
            this.searchField.updateListener(value -> {
                if (this.lastQuery.equals(value.trim())) {
                    return;
                }
                reload();
            });
            String actualQuery = this.searchField.getText().trim();
            this.lastQuery = actualQuery;
            boolean visible = !actualQuery.isEmpty();
            this.searchField.setFocused(visible);
            this.searchField.setVisible(visible);
            window.addContent(this.searchField);
            if (visible) {
                query = actualQuery.toLowerCase(Locale.ROOT);
            } else {
                query = null;
            }
        } else {
            query = null;
        }
        initializeHeader(window, query);
        fillServerList(this.serverListWidget, query);
        DivWidget content = new DivWidget();
        content.addId("content");
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) this.serverListWidget);
        scrollWidget.addId("center");
        content.addChild(scrollWidget);
        window.addFlexibleContent(content);
        backgroundContainer.addChild(window);
        container.addFlexibleContent(backgroundContainer);
        DivWidget buttonWrapper = new DivWidget();
        buttonWrapper.addId("button-wrapper");
        FlexibleContentWidget buttonContainer = new FlexibleContentWidget();
        buttonContainer.addId("button-container", "center");
        fillButtonContainer(buttonContainer);
        buttonWrapper.addChild(buttonContainer);
        container.addContent(buttonWrapper);
        ((Document) this.document).addChild(container);
    }

    protected void initializeHeader(FlexibleContentWidget container, String searchQuery) {
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        super.render(context);
        ServerEntryWidget draggingWidget = this.serverListWidget.getDraggingWidget();
        if (draggingWidget != null) {
            Stack stack = context.stack();
            stack.push();
            ScrollWidget parent1 = (ScrollWidget) this.serverListWidget.getParent();
            float offsetY = parent1.session().getScrollPositionY();
            stack.translate(0.0f, -offsetY, 0.0f);
            draggingWidget.render(context);
            stack.pop();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
        if (!canRefresh()) {
            return;
        }
        this.lastRefreshTime = TimeUtil.getMillis();
        for (ServerInfoCache<T> serverInfo : this.serverInfos) {
            serverInfo.update();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.F5) {
            refresh(true);
            return true;
        }
        return super.keyPressed(key, type);
    }

    protected final String getTranslationKey(String key) {
        return this.translationKeyPrefix + "." + key;
    }

    protected boolean canRefresh() {
        return this.lastRefreshTime == 0 || TimeUtil.getMillis() - this.lastRefreshTime >= 5000;
    }

    protected LabyNetServerInfoCache<T> registerCache(T serverData, Consumer<ServerInfoCache<T>> callback) {
        LabyNetServerInfoCache<T> cache = getCache(serverData);
        if (cache != null) {
            cache.setCallback(callback);
            return cache;
        }
        LabyNetServerInfoCache<T> cache2 = new LabyNetServerInfoCache<>(serverData, callback);
        this.serverInfos.add(cache2);
        return cache2;
    }

    protected LabyNetServerInfoCache<T> getCache(T serverData) {
        for (LabyNetServerInfoCache<T> serverInfoCache : this.serverInfos) {
            if (serverInfoCache.matches(serverData)) {
                return serverInfoCache;
            }
        }
        return null;
    }

    public ServerInfoCache<T> unregisterCache(T serverData) {
        ServerInfoCache<T> cache = getCache(serverData);
        if (cache != null) {
            this.serverInfos.remove(cache);
        }
        return cache;
    }

    protected void sortCache() {
        this.serverInfos.sort(Comparator.comparingInt((v0) -> {
            return v0.getSortingValue();
        }));
    }

    protected ServerInfoWidget<?> getServerInfoWidget(T serverData) {
        for (T t : this.serverListWidget.getGenericChildren()) {
            if (t instanceof ServerInfoWidget) {
                ServerInfoWidget<?> serverInfo = (ServerInfoWidget) t;
                if (serverInfo.serverData().equals(serverData)) {
                    return serverInfo;
                }
            }
        }
        return null;
    }

    protected void setServerButtonsEnabled(boolean enabled) {
        this.joinButton.setEnabled(enabled);
    }
}
