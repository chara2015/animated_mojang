package net.labymod.core.client.gui.screen.activity.activities.multiplayer;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.TabbedActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.action.Equalizer;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.DefaultComponentTab;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddressResolver;
import net.labymod.api.client.network.server.lan.LanServerCallback;
import net.labymod.api.client.network.server.lan.LanServerDetector;
import net.labymod.api.client.network.server.storage.ServerList;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.FriendsServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.LanServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PrivateServerListActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.PublicServerListActivity;
import net.labymod.core.localization.keys.ActivityTranslationKeys;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/MultiplayerActivity.class */
@AutoActivity
public class MultiplayerActivity extends TabbedActivity {
    private static final LanServerDetector LAN_SERVER_DETECTOR = Laby.references().lanServerDetector();
    public static final MultiplayerActivity INSTANCE = new MultiplayerActivity();
    private final LanServerListActivity lanServerListActivity = new LanServerListActivity();
    private final PrivateServerListActivity privateServerListActivity;
    private final TextFieldWidget searchField;
    private final Tab lanTab;

    private MultiplayerActivity() {
        ServerListConfig serverListConfig = Laby.labyAPI().config().multiplayer().serverList();
        this.lanTab = new DefaultComponentTab(Component.translatable("labymod.activity.multiplayer.tab.lan", new Component[0]), this.lanServerListActivity);
        register("lan", this.lanTab);
        this.searchField = new TextFieldWidget();
        this.searchField.addId("search-field");
        this.privateServerListActivity = new PrivateServerListActivity(this, this.searchField);
        DefaultComponentTab defaultComponentTab = new DefaultComponentTab(Component.translatable("labymod.activity.multiplayer.tab.private", new Component[0]), this.privateServerListActivity);
        register("private", defaultComponentTab);
        FriendsServerListActivity friendsServerListActivity = new FriendsServerListActivity(this);
        Laby.labyAPI().eventBus().registerListener(friendsServerListActivity);
        DefaultComponentTab friendsTab = new DefaultComponentTab(ActivityTranslationKeys.getLabyconnectFriendsTitle(), friendsServerListActivity);
        ConfigProperty<Boolean> friendsServerList = serverListConfig.friendsServerList();
        friendsTab.setHidden(!friendsServerList.get().booleanValue());
        friendsServerList.addChangeListener(v -> {
            friendsTab.setHidden(!v.booleanValue());
        });
        register("friends", friendsTab);
        DefaultComponentTab publicTab = new DefaultComponentTab(Component.translatable("labymod.activity.multiplayer.tab.public", new Component[0]), new PublicServerListActivity(this.searchField));
        ConfigProperty<Boolean> publicServerList = serverListConfig.publicServerList();
        publicTab.setHidden(!publicServerList.get().booleanValue());
        publicServerList.addChangeListener(v2 -> {
            publicTab.setHidden(!v2.booleanValue());
        });
        register("public", publicTab);
        switchTab(defaultComponentTab);
        LAN_SERVER_DETECTOR.reset();
        this.lanServerListActivity.setServerRemoveCallback(() -> {
            ThreadSafe.executeOnRenderThread(this::refreshLanTab);
        });
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        if (!this.searchField.isFocused()) {
            this.searchField.setFocused(true);
            this.searchField.setVisible(true);
        }
        boolean handled = super.charTyped(key, character);
        if (this.searchField.isVisible() && this.searchField.getText().trim().isEmpty()) {
            this.searchField.setFocused(false);
            this.searchField.setVisible(false);
            this.searchField.setText("");
        }
        return handled;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        boolean handled = super.keyPressed(key, type);
        if (this.searchField.isVisible() && this.searchField.getText().trim().isEmpty()) {
            this.searchField.setFocused(false);
            this.searchField.setVisible(false);
            this.searchField.setText("");
        }
        if (!this.searchField.isFocused() && key == Key.F && KeyHandler.isControlDown()) {
            this.searchField.setFocused(true);
            this.searchField.setVisible(true);
        }
        return handled;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        boolean consumed = super.mouseClicked(mouse, mouseButton);
        if (!this.searchField.isFocused() && this.searchField.isVisible() && this.searchField.getText().trim().isEmpty()) {
            this.searchField.setFocused(false);
            this.searchField.setVisible(false);
            this.searchField.setText("");
        }
        return consumed;
    }

    public void cacheServerList() {
        ServerList serverList = this.labyAPI.serverController().serverList();
        ServerAddressResolver serverAddressResolver = Laby.references().serverAddressResolver();
        Task.builder(() -> {
            for (int i = 0; i < serverList.size() && i != 50; i++) {
                ConnectableServerData serverData = serverList.get(i);
                serverAddressResolver.register(serverData.address());
            }
        }).build().execute();
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public Object getPreviousScreen() {
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.TabbedActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        refreshLanTab();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        switchToNextVisibleTab();
        LAN_SERVER_DETECTOR.startDetectionTask(new LanServerCallback() { // from class: net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity.1
            @Override // net.labymod.api.client.network.server.lan.LanServerCallback
            public void onServerAdd(ConnectableServerData data) {
                MultiplayerActivity.this.lanServerListActivity.addLanServer(data, () -> {
                    TabWidget lanTabWidget = MultiplayerActivity.this.getTabWidget(MultiplayerActivity.this.lanTab);
                    if (lanTabWidget != null && !lanTabWidget.isVisible()) {
                        MultiplayerActivity.this.refreshLanTab();
                    }
                });
            }

            @Override // net.labymod.api.client.network.server.lan.LanServerCallback
            public void onServerRemove(ConnectableServerData data) {
                MultiplayerActivity.this.lanServerListActivity.removeLanServer(data);
                Minecraft minecraft = MultiplayerActivity.this.labyAPI.minecraft();
                MultiplayerActivity multiplayerActivity = MultiplayerActivity.this;
                Objects.requireNonNull(multiplayerActivity);
                minecraft.executeOnRenderThread(multiplayerActivity::refreshLanTab);
            }
        });
        super.onOpenScreen();
    }

    private void switchToNextVisibleTab() {
        Tab activeTab = getActiveTab();
        if (activeTab != null && activeTab.isHidden()) {
            for (Tab value : values()) {
                if (!value.isHidden()) {
                    switchTab(value);
                    return;
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        LAN_SERVER_DETECTOR.terminateDetectionTask();
        super.onCloseScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        if (getActiveTab() != this.lanTab) {
            this.lanServerListActivity.tick();
        }
        super.tick();
    }

    private void refreshLanTab() {
        refreshTab(this.lanServerListActivity.getLanServers().isEmpty(), this.lanTab);
    }

    private void refreshTab(boolean hide, Tab tab) {
        tab.setHidden(hide);
        TabWidget tabWidget = getTabWidget(tab);
        if (hide) {
            if (tabWidget != null) {
                tabWidget.setVisible(false);
            }
            if (getActiveTab() == tab) {
                switchTab("private");
                return;
            }
            return;
        }
        if (tabWidget != null) {
            tabWidget.setVisible(true);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/MultiplayerActivity$ServerInfoWidgetEqualizer.class */
    public static class ServerInfoWidgetEqualizer<T extends ServerEntryWidget> implements Equalizer<T> {
        @Override // net.labymod.api.client.gui.screen.widget.action.Equalizer
        public boolean equals(T a, T b) {
            if ((a instanceof ServerInfoWidget) && (b instanceof ServerInfoWidget)) {
                return a.getListIndex() == b.getListIndex();
            }
            return a.equals(b);
        }
    }
}
