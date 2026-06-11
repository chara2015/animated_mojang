package net.labymod.core.client.gui.screen.activity.activities.worldsharing;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.util.I18n;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/WorldConnectActivity.class */
@AutoActivity
@Link("activity/worldsharing/connect.lss")
public class WorldConnectActivity extends SimpleActivity {
    private static final String[] FRAMES = {"O o o", "o O o", "o o O", "o O o"};
    private static final long INTERVAL_MS = 300;
    private static final String I18N_PREFIX = "labymod.activity.worldsharing.";
    private ComponentWidget titleWidget;
    private ComponentWidget headerWidget;
    private ComponentWidget loadingWidget;
    private boolean completed = false;
    private final CompletableFuture<JoinWorldEvent.Response> joinFuture = new CompletableFuture<>();

    public WorldConnectActivity(NetEventHandler netEventHandler, Friend friend, JoinWorldEvent.Type type) {
        this.joinFuture.orTimeout(5L, TimeUnit.SECONDS).thenAccept(result -> {
            if (result.error() != null) {
                setTitle(result.error().replace("{host}", friend.getName()), true);
            } else if (result.endpoint() == null || result.endpoint().isBlank()) {
                setTitle(I18n.getTranslation("labymod.activity.worldsharing.menu.connect.error", new Object[0]), true);
            } else {
                setTitle(I18n.getTranslation("labymod.activity.worldsharing.menu.connect.connecting", new Object[0]), false);
                Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                    String address;
                    Laby.labyAPI().minecraft().minecraftWindow().closeScreen();
                    if (type == JoinWorldEvent.Type.WORLD) {
                        address = Constants.Domains.createUserLanDomain(friend.getName());
                    } else {
                        address = friend.getName() + ".laby.local";
                    }
                    Laby.labyAPI().serverController().joinServer(Worldsharing.createServerData(address, result.endpoint()));
                });
            }
        }).exceptionally(ex -> {
            setTitle(I18n.getTranslation("labymod.activity.worldsharing.menu.connect.timeout", new Object[0]), true);
            return null;
        });
        netEventHandler.registerJoinWorldCallback(this.joinFuture);
        netEventHandler.publish(new JoinWorldEvent.Request(friend.getUniqueId(), type));
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget container = new DivWidget();
        container.addId("connection-container");
        DivWidget backgroundContainer = new DivWidget();
        backgroundContainer.addId("background-container");
        FlexibleContentWidget window = new FlexibleContentWidget();
        window.addId("window");
        if (this.headerWidget == null) {
            this.headerWidget = ComponentWidget.i18n("labymod.activity.worldsharing.menu.connect.error");
            this.headerWidget.addId("header");
        }
        this.headerWidget.setVisible(this.completed);
        window.addContent(this.headerWidget);
        if (this.titleWidget == null) {
            this.titleWidget = ComponentWidget.i18n("labymod.activity.worldsharing.menu.connect.connecting");
            this.titleWidget.addId("title");
        }
        window.addContent(this.titleWidget);
        if (this.loadingWidget == null) {
            this.loadingWidget = ComponentWidget.text(FRAMES[0]);
            this.loadingWidget.addId("loading-widget");
        }
        this.loadingWidget.setVisible(!this.completed);
        window.addContent(this.loadingWidget);
        ButtonWidget cancelButton = ButtonWidget.i18n(this.completed ? "labymod.ui.button.back" : "labymod.ui.button.cancel");
        cancelButton.addId("cancel-button");
        cancelButton.setActionListener(this::cancel);
        window.addContent(cancelButton);
        backgroundContainer.addChild(window);
        container.addChild(backgroundContainer);
        ((Document) this.document).addChild(container);
    }

    private void cancel() {
        this.joinFuture.cancel(true);
        displayPreviousScreen();
    }

    private void setTitle(String text, boolean error) {
        if (this.titleWidget != null) {
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                this.titleWidget.setText(text);
                if (error && this.loadingWidget != null) {
                    this.completed = true;
                    reload();
                }
            });
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        int index = (int) ((TimeUtil.getMillis() / INTERVAL_MS) % ((long) FRAMES.length));
        this.loadingWidget.setText(FRAMES[index]);
    }
}
