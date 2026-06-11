package net.labymod.core.client.gui.screen.activity.activities.worldsharing;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.DefaultComponentTab;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.util.Color;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.FriendInviteActivity;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.SettingsActivity;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.WhitelistActivity;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.client.worldsharing.model.WorldPrivacy;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/DashboardActivity.class */
@AutoActivity
@Link("activity/worldsharing/dashboard.lss")
public class DashboardActivity extends BoxedTabbedActivity {
    private final Worldsharing worldsharing;
    private final SettingsActivity settingsActivity;
    private static final String I18N_PREFIX = "labymod.activity.worldsharing.";

    public DashboardActivity(Worldsharing worldsharing) {
        this.worldsharing = worldsharing;
        this.settingsActivity = new SettingsActivity(worldsharing);
        register("settings", new DefaultComponentTab(Component.translatable("labymod.ui.button.settings", new Component[0]), this.settingsActivity));
        register("players", new DefaultComponentTab(Component.translatable("labymod.activity.worldsharing.menu.players", new Component[0]), new PlayerActivity(worldsharing.netEventHandler())));
        DefaultComponentTab whitelistTab = new DefaultComponentTab(Component.translatable("labymod.activity.worldsharing.menu.whitelist", new Component[0]), new WhitelistActivity(worldsharing));
        whitelistTab.setHidden(true);
        register("whitelist", whitelistTab);
        Function<Friend, Boolean> alreadyInvitedFunction = friend -> {
            return Boolean.valueOf(this.worldsharing.netEventHandler().isWhitelisted(friend.getName()));
        };
        Function<Friend, Boolean> canInviteFunction = friend2 -> {
            return Boolean.valueOf(!worldsharing.netEventHandler().hasInviteTimeout(friend2.getUniqueId()));
        };
        TranslatableComponent translatableComponentTranslatable = Component.translatable("labymod.activity.worldsharing.menu.invite", new Component[0]);
        NetEventHandler netEventHandler = worldsharing.netEventHandler();
        Objects.requireNonNull(netEventHandler);
        register("invite", new DefaultComponentTab(translatableComponentTranslatable, new FriendInviteActivity(netEventHandler::inviteToWorld, alreadyInvitedFunction, canInviteFunction)));
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.worldsharing.BoxedTabbedActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        boolean isPremium = this.worldsharing.isPremiumAndConnected();
        if (!isPremium) {
            this.worldsharing.netEventHandler().worldPrivacy(WorldPrivacy.LAN);
        }
        changeTabButtonVisibility("invite", isPremium);
        if ((!isPremium || !isHosting()) && !Objects.equals(getActiveTabId(), "settings")) {
            switchTab("settings", false);
        }
        FlexibleContentWidget flexibleContentWidget = (FlexibleContentWidget) new FlexibleContentWidget().addId("container");
        HorizontalListWidget horizontalListWidget = (HorizontalListWidget) new HorizontalListWidget().addId("header");
        ComponentWidget title = ComponentWidget.i18n("labymod.activity.worldsharing.menu.manage_world");
        title.addId("title");
        horizontalListWidget.addEntry(title);
        FlexibleContentWidget status = (FlexibleContentWidget) new FlexibleContentWidget().addId("status");
        IconWidget indicatorWidget = new IconWidget(Textures.SpriteCommon.STATUS_INDICATOR);
        TextColor color = resolveStatusColor();
        ComponentWidget statusText = createStatusText(color);
        indicatorWidget.addId("indicator");
        indicatorWidget.color().set(Integer.valueOf(Color.of(color.getValue(), 255).get()));
        status.addContent(indicatorWidget);
        status.addContent(statusText);
        horizontalListWidget.addEntry(status);
        flexibleContentWidget.addContent(horizontalListWidget);
        if (isHosting()) {
            changeTabButtonVisibility("whitelist", this.worldsharing.netEventHandler().worldPrivacy().isWhitelist());
            flexibleContentWidget.addContent(this.tabMenu);
        }
        flexibleContentWidget.addContent(this.prevScreenRenderer);
        FlexibleContentEntry result = flexibleContentWidget.addContent(this.screenRenderer);
        if (!isPremium) {
            result.addId("minimal");
        }
        ((Document) this.document).addChild(flexibleContentWidget);
    }

    public void changeTabButtonVisibility(String id, boolean visible) {
        Tab tab = getById(id);
        if (tab != null) {
            if (tab.isHidden() == (!visible)) {
                return;
            }
            tab.setHidden(!visible);
            for (T entry : this.tabMenu.getChildren()) {
                Widget widgetChildWidget = entry.childWidget();
                if (widgetChildWidget instanceof TabWidget) {
                    TabWidget tabWidget = (TabWidget) widgetChildWidget;
                    if (tabWidget.getTab() == tab) {
                        tabWidget.setVisible(visible);
                        return;
                    }
                }
            }
        }
    }

    public boolean isHosting() {
        return this.worldsharing.netEventHandler().isPublished() || Worldsharing.integratedServer().isPublished();
    }

    private TextColor resolveStatusColor() {
        NetEventHandler handler = this.worldsharing.netEventHandler();
        boolean isOffline = handler.state().equals(NetEventHandler.State.OFFLINE);
        if (!isOffline) {
            boolean isLanOrWaiting = handler.worldPrivacy().isLan() || handler.state().equals(NetEventHandler.State.WAITING);
            return isLanOrWaiting ? NamedTextColor.YELLOW : NamedTextColor.GREEN;
        }
        boolean isLanPublished = Worldsharing.integratedServer().isPublished() && handler.worldPrivacy().isLan();
        return isLanPublished ? NamedTextColor.YELLOW : NamedTextColor.RED;
    }

    private ComponentWidget createStatusText(@NotNull TextColor color) {
        if (!color.equals(NamedTextColor.YELLOW) && !color.equals(NamedTextColor.GREEN)) {
            return ComponentWidget.i18n("labymod.activity.worldsharing.enums.state.not_hosting");
        }
        String type = "public";
        if (color.equals(NamedTextColor.YELLOW)) {
            type = "lan";
        }
        String ip = getHostingIp(color);
        Component ipComponent = null;
        if (ip != null) {
            ipComponent = Component.text(": ").append(Component.text(ip).decorate(TextDecoration.BOLD));
        }
        ComponentWidget widget = ComponentWidget.component(Component.translatable("labymod.activity.worldsharing.enums.privacy." + type, new Component[0]).append(ipComponent));
        if (ip != null) {
            widget.setPressable(() -> {
                Laby.labyAPI().minecraft().setClipboard(ip);
            });
            widget.setHoverComponent(Component.translatable("labymod.activity.worldsharing.menu.copy_domain", new Component[0]));
            widget.setHoverCursor(CursorTypes.POINTING_HAND, true);
        }
        return widget;
    }

    public void reloadDashboard() {
        if (isOpen()) {
            if (Laby.labyAPI().minecraft().isOnRenderThread()) {
                reload();
            } else {
                Laby.labyAPI().minecraft().executeOnRenderThread(this::reload);
            }
        }
    }

    @Nullable
    private String getHostingIp(@NotNull TextColor color) {
        if (color.equals(NamedTextColor.GREEN)) {
            return Constants.Domains.createUserLanDomain(Laby.labyAPI().getName());
        }
        if (!color.equals(NamedTextColor.YELLOW)) {
            return null;
        }
        try {
            String ipV4 = null;
            String ipV6 = null;
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!ni.isLoopback() && !ni.isVirtual() && ni.isUp()) {
                    String name = ni.getName();
                    if (!name.startsWith("tun") && !name.startsWith("wg") && !name.startsWith("tap")) {
                        for (InetAddress addr : Collections.list(ni.getInetAddresses())) {
                            if (addr.isSiteLocalAddress()) {
                                if (addr instanceof Inet4Address) {
                                    ipV4 = addr.getHostAddress();
                                } else if (addr instanceof Inet6Address) {
                                    ipV6 = addr.getHostAddress();
                                }
                            }
                        }
                    }
                }
            }
            if (ipV4 == null && ipV6 == null) {
                return null;
            }
            String ip = ipV4 != null ? ipV4 : "[" + ipV6 + "]";
            int port = this.settingsActivity.getPort();
            return port > 0 ? ip + ":" + port : ip;
        } catch (SocketException e) {
            LOGGER.debug("Failed to get local hosting IP", e);
            return null;
        }
    }

    public void setUnsavedChanges() {
        this.settingsActivity.setUnsavedChanges(true);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/DashboardActivity$NavElement.class */
    public class NavElement extends ScreenNavigationElement {
        private final Component name;
        private final String widgetId;

        public NavElement(DashboardActivity this$0, String widgetId) {
            super(this$0);
            this.name = Component.text(widgetId);
            this.widgetId = widgetId;
        }

        @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
        public Component getDisplayName() {
            return this.name;
        }

        @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
        public Icon getIcon() {
            return null;
        }

        @Override // net.labymod.api.client.gui.navigation.NavigationElement
        public boolean isVisible() {
            return false;
        }

        @Override // net.labymod.api.client.gui.navigation.NavigationElement
        public String getWidgetId() {
            return this.widgetId;
        }
    }
}
