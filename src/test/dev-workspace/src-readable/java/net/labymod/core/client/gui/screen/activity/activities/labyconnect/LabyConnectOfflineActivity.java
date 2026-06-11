package net.labymod.core.client.gui.screen.activity.activities.labyconnect;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectRejectAuthenticationEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.util.TextFormat;
import net.labymod.core.labyconnect.DefaultLabyConnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/LabyConnectOfflineActivity.class */
@Link("activity/labyconnect/laby-connect-offline.lss")
@AutoActivity
public class LabyConnectOfflineActivity extends SimpleActivity {
    private final ComponentWidget title = ComponentWidget.empty();
    private ButtonWidget buttonConnect;

    public LabyConnectOfflineActivity() {
        this.title.addId("title");
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.SimpleActivity
    public boolean shouldRenderBackground() {
        return false;
    }

    public static void fillContextMenu(ContextMenu contextMenu) {
        LabyAPI labyAPI = Laby.labyAPI();
        if (labyAPI.labyConnect().state() != LabyConnectState.OFFLINE) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Disconnect")).simpleClickHandler(entry -> {
                labyAPI.labyConnect().forceDisconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit");
            }).build());
        }
        if (labyAPI.labyConnect().state() != LabyConnectState.PLAY) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Connect to backup server")).simpleClickHandler(entry2 -> {
                LabyConnect labyConnect = labyAPI.labyConnect();
                labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit");
                labyConnect.connect(DefaultLabyConnect.ADDRESS_BACKUP, DefaultLabyConnect.PORT);
            }).build());
        }
        if (Laby.references().gameUserService().clientGameUser().visibleGroup().isStaff()) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Connect to TestServer")).simpleClickHandler(entry3 -> {
                LabyConnect labyConnect = labyAPI.labyConnect();
                labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit");
                labyConnect.connect(DefaultLabyConnect.ADDRESS, DefaultLabyConnect.PORT_TEST_SERVER);
            }).build());
        }
    }

    private void updateState(LabyConnectState state) {
        TextColor textColor;
        Component componentColor;
        String stateId = TextFormat.SNAKE_CASE.toCamelCase(state.name(), true);
        Component componentState = Component.translatable("labymod.activity.labyconnect.offline.state." + stateId, new Component[0]);
        if (state == LabyConnectState.OFFLINE) {
            textColor = NamedTextColor.RED;
        } else if (state == LabyConnectState.HELLO) {
            textColor = NamedTextColor.AQUA;
        } else {
            textColor = NamedTextColor.YELLOW;
        }
        TextColor color = textColor;
        String lastDisconnectReason = this.labyAPI.labyConnect().getLastDisconnectReason();
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        boolean isUnauthenticated = (session == null || session.isPremium() || !session.isConnectionEstablished()) ? false : true;
        if (isUnauthenticated) {
            this.title.setComponent(Component.translatable("labymod.activity.labyconnect.offline.unauthenticated", new Component[0]).color(NamedTextColor.YELLOW));
            this.buttonConnect.updateComponent(Component.translatable("labymod.activity.labyconnect.offline.buttons.disconnect", new Component[0]));
            return;
        }
        if (lastDisconnectReason == null) {
            componentColor = Component.translatable("labymod.activity.labyconnect.offline.title", new Component[0]);
        } else {
            componentColor = Component.translatable(lastDisconnectReason, new Component[0]).color(color);
        }
        Component reason = componentColor;
        if (state == LabyConnectState.OFFLINE) {
            this.title.setComponent(reason);
            this.buttonConnect.updateComponent(Component.translatable("labymod.activity.labyconnect.offline.buttons.connect", new Component[0]));
        } else {
            this.title.setComponent(componentState.color(color));
            this.buttonConnect.updateComponent(Component.translatable("labymod.ui.button.cancel", new Component[0]));
        }
    }

    @Subscribe
    public void onLabyConnectStateUpdate(LabyConnectStateUpdateEvent event) {
        updateState(event.state());
    }

    @Subscribe
    public void onLabyConnectDisconnect(LabyConnectDisconnectEvent event) {
        this.title.setComponent(Component.text(event.getReason()));
        this.title.reInitialize();
    }

    @Subscribe
    public void onLabyConnectRejectAuthentication(LabyConnectRejectAuthenticationEvent event) {
        updateState(event.labyConnect().state());
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget background = new DivWidget();
        background.addId("background");
        DivWidget div = new DivWidget();
        div.addId("offline-container");
        div.addChild(this.title);
        this.buttonConnect = ButtonWidget.component(Component.empty());
        this.buttonConnect.addId("button-connect");
        this.buttonConnect.setPressable(() -> {
            if (this.labyAPI.labyConnect().isConnectionEstablished()) {
                this.labyAPI.labyConnect().forceDisconnect(LabyConnectDisconnectEvent.Initiator.USER, "Force quit");
            } else {
                this.labyAPI.labyConnect().connect();
            }
        });
        div.addChild(this.buttonConnect);
        updateState(this.labyAPI.labyConnect().state());
        background.addChild(div);
        fillContextMenu(background.createContextMenu());
        ((Document) this.document).addChild(background);
    }
}
