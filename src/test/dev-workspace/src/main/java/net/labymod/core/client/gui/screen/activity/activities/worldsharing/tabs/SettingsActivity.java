package net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.NumberTextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.client.worldsharing.model.GameDifficulty;
import net.labymod.core.client.worldsharing.model.GameMode;
import net.labymod.core.client.worldsharing.model.WorldPrivacy;
import net.labymod.core.server.AbstractIntegratedServer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/tabs/SettingsActivity.class */
@AutoActivity
@Link("activity/worldsharing/tabs/settings.lss")
public class SettingsActivity extends Activity {
    private int port;
    private int tempSlots;
    private WorldPrivacy tempPrivacy;
    private long timeSinceChange = 0;
    private final Worldsharing worldsharing;
    private static final String I18N_PREFIX = "labymod.activity.worldsharing.";

    public SettingsActivity(Worldsharing worldsharing) {
        this.worldsharing = worldsharing;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        AbstractIntegratedServer server = Worldsharing.integratedServer();
        if (this.port == 0) {
            this.port = server.getSuitableLanPort();
        }
        if (this.tempSlots == 0) {
            this.tempSlots = server.getSlots();
        }
        boolean isHosting = this.worldsharing.dashboardActivity().isHosting();
        FlexibleContentWidget flexibleContentWidget = (FlexibleContentWidget) new FlexibleContentWidget().addId("container");
        FlexibleContentWidget worldSettings = (FlexibleContentWidget) new FlexibleContentWidget().addId("settings");
        ComponentWidget worldSettingsTitle = ComponentWidget.i18n("labymod.activity.worldsharing.menu.dashboard.game_settings");
        worldSettingsTitle.addId("section-title");
        worldSettings.addContent(worldSettingsTitle);
        FlexibleContentWidget options = new FlexibleContentWidget();
        options.addId("options");
        DropdownWidget<GameMode> gameModeDropDown = new DropdownWidget<>();
        gameModeDropDown.addAll(GameMode.values());
        gameModeDropDown.setSelected(server.getGameMode());
        Objects.requireNonNull(server);
        gameModeDropDown.setChangeListener(server::changeGameMode);
        DropdownWidget<GameDifficulty> difficultyDropDown = new DropdownWidget<>();
        difficultyDropDown.addAll(GameDifficulty.values());
        difficultyDropDown.setSelected(server.getDifficulty());
        Objects.requireNonNull(server);
        difficultyDropDown.setChangeListener(server::changeDifficulty);
        Objects.requireNonNull(server);
        SwitchWidget allowCheatsSwitch = SwitchWidget.create(server::setCheatsEnabled);
        allowCheatsSwitch.setValue(server.cheatsEnabled());
        addOption(options, "game_mode", gameModeDropDown);
        addOption(options, "difficulty", difficultyDropDown);
        addOption(options, "allow_cheats", allowCheatsSwitch);
        worldSettings.addContent(options);
        flexibleContentWidget.addContent(worldSettings);
        FlexibleContentWidget sharingSettings = (FlexibleContentWidget) new FlexibleContentWidget().addId("settings", "host");
        ComponentWidget sharingSettingsTitle = ComponentWidget.i18n("labymod.activity.worldsharing.menu.dashboard.hosting_settings");
        sharingSettingsTitle.addId("section-title");
        sharingSettings.addContent(sharingSettingsTitle);
        FlexibleContentWidget sharingOptions = (FlexibleContentWidget) new FlexibleContentWidget().addId("options", "host");
        DropdownWidget<WorldPrivacy> privacyDropDown = new DropdownWidget<>();
        privacyDropDown.addAll(WorldPrivacy.values());
        privacyDropDown.setSelected(this.worldsharing.netEventHandler().worldPrivacy());
        privacyDropDown.setChangeListener(privacy -> {
            if (isHosting) {
                this.tempPrivacy = privacy;
                setUnsavedChanges(true);
            } else {
                this.worldsharing.netEventHandler().worldPrivacy(privacy);
                this.tempPrivacy = null;
            }
        });
        boolean isPlus = isLabyPlus();
        Component moreSlotsHoverComponent = null;
        if (!isPlus) {
            moreSlotsHoverComponent = Component.translatable("labymod.activity.worldsharing.messages.laby_plus_slots", Component.text((Object) 6)).color(NamedTextColor.GOLD);
        }
        SliderWidget maxSlotsSlider = new SliderWidget().range(2.0f, isPlus ? 12.0f : 6.0f);
        maxSlotsSlider.setValue(this.tempSlots);
        maxSlotsSlider.setInteraction(val -> {
            if (!isPlus && val > 6.0f) {
                maxSlotsSlider.setValue(6.0d);
                return;
            }
            if (this.tempSlots == ((int) val)) {
                return;
            }
            this.tempSlots = (int) val;
            if (isHosting) {
                setUnsavedChanges(true);
            } else {
                server.setSlots(this.tempSlots);
            }
        });
        NumberTextFieldWidget portInput = (NumberTextFieldWidget) new NumberTextFieldWidget().addId("port-input");
        portInput.placeholder(Component.translatable("labymod.activity.worldsharing.menu.dashboard.port", new Component[0]));
        portInput.maximalLength(5);
        portInput.onUpdate(i -> {
            this.port = i;
        });
        portInput.setValue(this.port);
        portInput.setEditable(!isHosting);
        if (this.worldsharing.isPremiumAndConnected()) {
            addSharingOption(sharingOptions, Component.translatable("labymod.activity.worldsharing.messages.privacy", new Component[0]), privacyDropDown);
        }
        addSharingOption(sharingOptions, Component.translatable("labymod.activity.worldsharing.menu.dashboard.slots", new Component[0]), maxSlotsSlider, moreSlotsHoverComponent);
        addSharingOption(sharingOptions, Component.translatable("labymod.activity.worldsharing.menu.dashboard.port", new Component[0]), portInput);
        sharingSettings.addContent(sharingOptions);
        flexibleContentWidget.addContent(sharingSettings);
        ButtonWidget hostButton = new ButtonWidget();
        String[] strArr = new String[2];
        strArr[0] = "host-button";
        strArr[1] = isHosting ? "stop" : "start";
        hostButton.addId(strArr);
        if (isHosting) {
            hostButton.text().set(Component.translatable("labymod.activity.worldsharing.menu.stop_hosting", new Component[0]));
            hostButton.setActionListener(() -> {
                int onlineCount = server.onlinePlayerCount() - 1;
                if (onlineCount < 1) {
                    close();
                } else {
                    SimpleAdvancedPopup.builder().title(Component.translatable("labymod.activity.worldsharing.messages.warn_stop_sharing", new Component[0])).description(Component.translatable("labymod.activity.worldsharing.messages.warn_players_kicked", Component.text(Integer.valueOf(onlineCount)))).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.ui.button.close", new Component[0]), (Consumer<SimpleAdvancedPopup.SimplePopupButton>) e -> {
                        close();
                    })).build().displayAsActivity();
                }
            });
        } else {
            hostButton.text().set(Component.translatable("labymod.activity.worldsharing.menu.start_hosting", new Component[0]));
            hostButton.setActionListener(() -> {
                server.setSlots(this.tempSlots);
                if (this.worldsharing.netEventHandler().worldPrivacy().isLan()) {
                    if (!publishLanWorld(this.port, server.getGameMode(), server.cheatsEnabled())) {
                        this.port = server.getSuitableLanPort();
                    }
                } else {
                    init();
                }
                reload();
            });
        }
        flexibleContentWidget.addContent(hostButton);
        ((Document) this.document).addChild(flexibleContentWidget);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.timeSinceChange > 0 && TimeUtil.getMillis() - this.timeSinceChange > 150) {
            setUnsavedChanges(false);
            Worldsharing.integratedServer().setSlots(this.tempSlots);
            if (this.tempPrivacy != null) {
                this.worldsharing.netEventHandler().worldPrivacy(this.tempPrivacy);
                this.worldsharing.dashboardActivity().changeTabButtonVisibility("whitelist", this.tempPrivacy.isWhitelist());
            }
            boolean wasConnected = this.worldsharing.netEventHandler().isPublished();
            boolean wasPublished = Worldsharing.integratedServer().isPublished();
            WorldPrivacy currentPrivacy = this.worldsharing.netEventHandler().worldPrivacy();
            if (currentPrivacy.isLan()) {
                if (wasConnected) {
                    this.worldsharing.netEventHandler().updateConfig();
                }
            } else if (wasPublished && !wasConnected) {
                this.worldsharing.netEventHandler().init();
            } else if (wasConnected) {
                this.worldsharing.netEventHandler().updateConfig();
            }
        }
    }

    private void addOption(FlexibleContentWidget options, String value, Widget widget) {
        addOption(options, Component.translatable("labymod.activity.worldsharing.menu.dashboard." + value, new Component[0]), widget);
    }

    private void addOption(FlexibleContentWidget options, Component component, Widget widget) {
        HorizontalListWidget container = new HorizontalListWidget();
        container.addId("option");
        container.addEntry(ComponentWidget.component(component).addId("label"));
        if (widget != null) {
            widget.addId("widget");
            container.addEntry(widget);
        }
        options.addContent(container);
    }

    private void addSharingOption(FlexibleContentWidget parent, Component component, Widget widget) {
        addSharingOption(parent, component, widget, null);
    }

    private void addSharingOption(FlexibleContentWidget parent, Component component, Widget widget, @Nullable Component notice) {
        HorizontalListWidget container = new HorizontalListWidget();
        container.addId("option", "host");
        container.addEntry(ComponentWidget.component(component).addId("label"));
        if (notice != null) {
            IconWidget noticeWidget = new IconWidget(Textures.SpriteLabyMod.WHITE_WOLF_HIGH_RES);
            noticeWidget.setHoverComponent(notice);
            container.addEntry(noticeWidget);
        }
        if (widget != null) {
            container.addEntry(widget);
        }
        parent.addContent(container);
    }

    private synchronized void init() {
        if (Worldsharing.integratedServer().isPublished() || publishLanWorld(this.port, Worldsharing.integratedServer().getGameMode(), Worldsharing.integratedServer().cheatsEnabled())) {
            this.tempSlots = Worldsharing.integratedServer().getSlots();
            this.worldsharing.netEventHandler().init();
            this.worldsharing.dashboardActivity().reloadDashboard();
        } else {
            LOGGER.warn("failed to publish lan world", new Object[0]);
            this.port = Worldsharing.integratedServer().getSuitableLanPort();
            Notification.builder().title(Component.translatable("labymod.activity.worldsharing.messages.failed_to_publish", new Component[0])).text(Component.translatable("labymod.activity.worldsharing.messages.generic_publish_error", new Component[0])).buildAndPush();
        }
    }

    private boolean publishLanWorld(int port, GameMode gameMode, boolean allowCheats) {
        if (port <= 0 || port > 65535) {
            port = Worldsharing.integratedServer().getSuitableLanPort();
            this.port = port;
        }
        boolean success = Worldsharing.integratedServer().publishLanWorld(port, gameMode, allowCheats);
        if (success) {
            this.worldsharing.netEventHandler().setHost();
            this.worldsharing.dashboardActivity().reload();
            this.labyAPI.minecraft().chatExecutor().displayClientMessage(Component.translatable("labymod.activity.worldsharing.messages.local_published", Component.text(Integer.valueOf(this.port))));
        }
        return success;
    }

    private boolean isLabyPlus() {
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        return (session == null || session.self().gameUser().visibleGroup().isDefault()) ? false : true;
    }

    public void setUnsavedChanges(boolean value) {
        this.timeSinceChange = value ? TimeUtil.getMillis() : 0L;
    }

    private void close() {
        AbstractIntegratedServer integratedServer = Worldsharing.integratedServer();
        String host = integratedServer.getHost();
        Component component = Component.translatable(MinecraftVersions.V1_8_9.isCurrent() ? "Server closed." : "multiplayer.disconnect.server_shutdown", new Component[0]);
        if (integratedServer.isPublished()) {
            List<String> toKick = new ArrayList<>();
            integratedServer.iterateOnlinePlayers((name, gamemode, isBedrock) -> {
                if (host == null || !host.equals(name)) {
                    toKick.add(name);
                }
            });
            for (String player : toKick) {
                integratedServer.kickPlayer(player, component);
            }
            integratedServer.stopServer();
        }
        this.worldsharing.netEventHandler().stop();
        this.worldsharing.dashboardActivity().reload();
    }

    public int getPort() {
        return this.port;
    }
}
