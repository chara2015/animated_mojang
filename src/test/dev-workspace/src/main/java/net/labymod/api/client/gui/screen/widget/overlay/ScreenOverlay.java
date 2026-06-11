package net.labymod.api.client.gui.screen.widget.overlay;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.window.Window;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/overlay/ScreenOverlay.class */
public abstract class ScreenOverlay extends Activity {
    private final int zLayer;
    private boolean active;
    private boolean closing;

    public ScreenOverlay(int zLayer) {
        this.zLayer = zLayer;
    }

    public ScreenOverlay(int zLayer, boolean handleStyleSheet) {
        super(handleStyleSheet);
        this.zLayer = zLayer;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity
    public boolean displayPreviousScreen() {
        return false;
    }

    public int getZLayer() {
        return this.zLayer;
    }

    public boolean isActive() {
        return !Laby.labyAPI().minecraft().isLoadingOverlayPresent() && this.active;
    }

    public void setActive(boolean active) {
        boolean reInitialize = this.active != active;
        this.active = active;
        if (active && reInitialize) {
            Window window = Laby.labyAPI().minecraft().minecraftWindow();
            reloadMarkup();
            resize(window.getAbsoluteScaledWidth(), window.getAbsoluteScaledHeight());
            load(window);
            onOpenScreen();
            return;
        }
        onCloseScreen();
    }

    public void reloadOverlayStyles() {
        boolean active = this.active;
        setActive(!active);
        setActive(active);
    }

    public boolean isClosing() {
        return this.closing;
    }

    protected void setClosing(boolean closing) {
        this.closing = closing;
    }

    public boolean isHideGui() {
        return true;
    }
}
