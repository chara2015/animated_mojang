package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.adventure.ComponentUtils;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.icon.ping.PingIconRegistry;
import net.labymod.api.client.gui.icon.ping.PingType;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/ServerInfoWidget.class */
@AutoWidget
@Link("activity/multiplayer/server-info.lss")
public class ServerInfoWidget<T extends ServerData> extends ServerEntryWidget {
    private static final PingIconRegistry PING_ICON_REGISTRY = Laby.references().pingIconRegistry();
    private final T serverData;
    private ServerInfo serverInfo;
    private FlexibleContentWidget serverInfoWrapper;
    private IconWidget serverIconWidget;
    private IconWidget pingWidget;
    private final int listIndex;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/ServerInfoWidget$Movable.class */
    public enum Movable {
        NO,
        UP,
        DOWN,
        ALL,
        SWAP,
        ADD
    }

    public ServerInfoWidget(@NotNull T serverData, @NotNull ServerInfo serverInfo, int listIndex) {
        Objects.requireNonNull(serverData, "Server data cannot be null");
        Objects.requireNonNull(serverInfo, "Server info cannot be null");
        this.serverData = serverData;
        this.listIndex = listIndex;
        addId("server-info");
        this.lazy = true;
        updateServerInfo(serverInfo);
    }

    public ServerInfoWidget(@NotNull T serverData, @NotNull ServerInfo serverInfo) {
        this(serverData, serverInfo, 0);
    }

    public ServerInfoWidget(@NotNull T serverData) {
        this(serverData, ServerInfo.loading(serverData.getName(), serverData.address()));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        boolean display = display();
        if (display) {
            startInitialize(parent);
        }
        this.serverIconWidget = serverIconWidget(!display);
        this.serverIconWidget.addId("server-icon");
        addSelectionWidgets(this.serverIconWidget);
        addChild(this.serverIconWidget);
        this.serverInfoWrapper = new FlexibleContentWidget();
        this.serverInfoWrapper.addId("server-info-wrapper");
        FlexibleContentWidget serverInfoHeader = new FlexibleContentWidget();
        serverInfoHeader.addId("server-info-header");
        ComponentWidget serverNameWidget = ComponentWidget.component(serverName());
        serverNameWidget.addId("server-name");
        serverInfoHeader.addContent(serverNameWidget);
        initializeServerInfoNameSuffix(serverInfoHeader);
        DivWidget serverStatusWrapper = new DivWidget();
        serverStatusWrapper.addId("server-status-wrapper");
        ComponentWidget serverStatusWidget = statusWidget();
        serverStatusWidget.addId("server-status");
        serverStatusWrapper.addChild(serverStatusWidget);
        serverInfoHeader.addFlexibleContent(serverStatusWrapper);
        this.pingWidget = pingWidget();
        this.pingWidget.addId("server-ping");
        serverInfoHeader.addContent(this.pingWidget);
        this.serverInfoWrapper.addContent(serverInfoHeader);
        Component description = description(display ? null : ServerInfo.Status.LOADING);
        ComponentWidget descriptionWidget = ComponentWidget.component(description);
        descriptionWidget.addId("server-description");
        this.serverInfoWrapper.addContent(descriptionWidget);
        finishInitialize(parent, this.serverInfoWrapper, display);
        addChild(this.serverInfoWrapper);
    }

    protected void initializeServerInfoNameSuffix(FlexibleContentWidget serverInfoHeader) {
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.pingWidget != null && loadingIcon()) {
            this.pingWidget.icon().set(pingIcon());
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        if (this.serverInfoWrapper == null) {
            return super.getContentHeight(type);
        }
        return this.serverInfoWrapper.bounds().getHeight(BoundsType.OUTER) + bounds().getVerticalOffset(type);
    }

    public final void connect() {
        connect(null);
    }

    public void connect(String command) {
        connect(command, 0);
    }

    public void connect(String command, int delay) {
        if (this.serverData instanceof ConnectableServerData) {
            ((ConnectableServerData) this.serverData).connect(command, delay);
        } else {
            ConnectableServerData.from(this.serverData).connect(command, delay);
        }
    }

    protected boolean loadingIcon() {
        return serverInfo().getStatus() == ServerInfo.Status.LOADING;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget
    public int getListIndex() {
        return this.listIndex;
    }

    protected boolean display() {
        return true;
    }

    protected void startInitialize(Parent parent) {
    }

    protected void finishInitialize(Parent parent, FlexibleContentWidget content, boolean display) {
    }

    protected ServerInfo serverInfo() {
        return this.serverInfo;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget
    public IconWidget getIconWidget() {
        return this.serverIconWidget;
    }

    public T serverData() {
        return this.serverData;
    }

    protected Component serverName() {
        return Component.text(this.serverData.getName());
    }

    protected IconWidget serverIconWidget(boolean loading) {
        Icon completable = Icon.completable(this.serverInfo.getIcon());
        completable.resolution(64, 64);
        return new IconWidget(completable);
    }

    protected Component description(ServerInfo.Status forcedStatus) {
        ServerInfo serverInfo = serverInfo();
        Component description = serverInfo.getDescription();
        if (forcedStatus != null || description == null) {
            ServerInfo.Status status = forcedStatus != null ? forcedStatus : serverInfo.getStatus();
            switch (status) {
                case LOADING:
                    description = Component.translatable("labymod.activity.multiplayer.server.status.pinging", NamedTextColor.GRAY);
                    break;
                case CANNOT_CONNECT:
                    description = Component.translatable("labymod.activity.multiplayer.server.status.cannot_connect", NamedTextColor.DARK_RED);
                    break;
                case UNKNOWN_HOST:
                    description = Component.translatable("labymod.activity.multiplayer.server.status.cannot_resolve", NamedTextColor.DARK_RED);
                    break;
                case PROXY_UNAVAILABLE:
                    description = Component.translatable("labymod.activity.multiplayer.server.status.proxy_unavailable", NamedTextColor.RED);
                    break;
            }
            if (description != null) {
                description = description.append(Component.text("\n "));
            }
        }
        if (description == null) {
            description = Component.text("");
        }
        return mergeStyleRecursive(description, Style.style(NamedTextColor.GRAY));
    }

    protected ComponentWidget statusWidget() {
        Component component = Component.empty();
        ServerInfo serverInfo = serverInfo();
        if (serverInfo.getStatus() == ServerInfo.Status.SUCCESS) {
            component = Component.empty().append(Component.text(Integer.valueOf(serverInfo.getPlayerCount()), NamedTextColor.GRAY)).append(Component.text("/", NamedTextColor.DARK_GRAY)).append(Component.text(Integer.valueOf(serverInfo.getMaxPlayers()), NamedTextColor.GRAY));
        }
        if (serverInfo.getStatus() == ServerInfo.Status.WRONG_VERSION) {
            component = Component.text(serverInfo.getProtocolInfo(), NamedTextColor.RED);
        }
        ComponentWidget widget = ComponentWidget.component(component);
        ServerInfo.Player[] onlinePlayers = serverInfo.getOnlinePlayers();
        if (onlinePlayers != null && onlinePlayers.length != 0) {
            List<RenderableComponent> components = new ArrayList<>();
            for (ServerInfo.Player player : onlinePlayers) {
                components.add(RenderableComponent.of(Component.text(player.getName())));
            }
            widget.setHoverRenderableComponent(RenderableComponent.realignedMerge(components));
        }
        return widget;
    }

    protected IconWidget pingWidget() {
        IconWidget widget = new IconWidget(pingIcon());
        widget.addId("server-ping");
        ServerInfo serverInfo = serverInfo();
        ServerInfo.Status status = serverInfo.getStatus();
        if (status == ServerInfo.Status.WRONG_VERSION) {
            widget.setHoverComponent(Component.translatable("labymod.activity.multiplayer.server.status.incompatible_version", new Component[0]));
        } else if (status == ServerInfo.Status.LOADING) {
            widget.setHoverComponent(Component.translatable("labymod.activity.multiplayer.server.status.pinging", new Component[0]));
        } else if (status == ServerInfo.Status.SUCCESS && serverInfo.getPing() != -1) {
            Component pingComponent = Component.text(serverInfo.getPing() + "ms");
            if (serverInfo.isProxied()) {
                pingComponent = pingComponent.append(Component.newline()).append(Component.translatable("labymod.activity.multiplayer.server.status.proxied", NamedTextColor.GRAY));
            }
            widget.setHoverComponent(pingComponent);
        } else {
            widget.setHoverComponent(Component.translatable("labymod.activity.multiplayer.server.status.no_connection", new Component[0]));
        }
        return widget;
    }

    private Icon pingIcon() {
        if (loadingIcon()) {
            int index = (int) (((TimeUtil.getMillis() / 100) + (((long) this.listIndex) * 2)) & 7);
            if (index > 4) {
                index = 8 - index;
            }
            return PING_ICON_REGISTRY.icon(PingType.LOADING_PING, index);
        }
        ServerInfo serverInfo = serverInfo();
        ServerInfo.Status status = serverInfo.getStatus();
        if (status == ServerInfo.Status.SUCCESS && serverInfo.getPing() != -1) {
            return PING_ICON_REGISTRY.icon(PingType.SERVER_PING, serverInfo.getPing());
        }
        return PING_ICON_REGISTRY.icon(PingType.ERROR);
    }

    private Component mergeStyleRecursive(Component component, Style style) {
        return ComponentUtils.mergeStyleRecursive(component, style, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
    }

    public void updateServerInfo(@NotNull ServerInfo serverInfo) {
        Objects.requireNonNull(serverInfo, "Server info cannot be null");
        this.serverInfo = serverInfo;
        if (this.initialized) {
            reInitialize();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public String toString() {
        return this.serverInfo.getName();
    }
}
