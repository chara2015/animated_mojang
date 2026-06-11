package net.labymod.core.client.gui.screen.activity.activities.labyconnect;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.LabyConnectDesktopActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/LabyConnectActivity.class */
@Link("activity/labyconnect/laby-connect.lss")
@AutoActivity
public class LabyConnectActivity extends SimpleActivity {
    private static final String OFFLINE_ID = "offline";
    private static final String ONLINE_ID = "online";
    private final LabyConnectDesktopActivity desktopActivity = new LabyConnectDesktopActivity();
    private final LabyConnectOfflineActivity offlineActivity = new LabyConnectOfflineActivity();
    private ScreenRendererWidget screenRenderer;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ((Document) this.document).addChild(new DivWidget().addId("background", "background-left"));
        ((Document) this.document).addChild(new DivWidget().addId("background", "background-top"));
        ((Document) this.document).addChild(new DivWidget().addId("background", "background-right"));
        ((Document) this.document).addChild(new DivWidget().addId("background", "background-bottom"));
        this.screenRenderer = new ScreenRendererWidget();
        this.screenRenderer.addId("screen-renderer");
        ((Document) this.document).addChild(this.screenRenderer);
        updateScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
    }

    @Subscribe
    public void onLabyConnectStateUpdate(LabyConnectStateUpdateEvent event) {
        updateScreen();
    }

    private void updateScreen() {
        if (this.screenRenderer == null) {
            return;
        }
        ScreenInstance currentScreen = this.screenRenderer.getScreen();
        boolean previousIsConnected = currentScreen instanceof LabyConnectDesktopActivity;
        boolean isAuthenticated = this.labyAPI.labyConnect().isAuthenticated();
        if (currentScreen != null && previousIsConnected == isAuthenticated) {
            return;
        }
        if (isAuthenticated) {
            this.screenRenderer.displayScreen(this.desktopActivity);
            updateScreenRendererIds(ONLINE_ID, OFFLINE_ID);
        } else {
            this.screenRenderer.displayScreen(this.offlineActivity);
            updateScreenRendererIds(OFFLINE_ID, ONLINE_ID);
        }
    }

    private void updateScreenRendererIds(String add, String remove) {
        this.screenRenderer.removeId(remove);
        this.screenRenderer.addId(add);
    }

    public LabyConnectDesktopActivity desktopActivity() {
        return this.desktopActivity;
    }
}
