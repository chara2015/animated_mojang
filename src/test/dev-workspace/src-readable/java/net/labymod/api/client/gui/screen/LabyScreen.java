package net.labymod.api.client.gui.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/LabyScreen.class */
public abstract class LabyScreen implements ScreenInstance, ParentScreen {
    protected ParentScreen parent;
    protected Object previousScreen;

    @Override // net.labymod.api.client.gui.Interactable
    public abstract void tick();

    protected abstract void postInitialize();

    public abstract void updateKeyRepeatingMode(boolean z);

    protected LabyScreen() {
    }

    public void updatePreviousScreen() {
        this.previousScreen = Laby.labyAPI().minecraft().minecraftWindow().getCurrentVersionedScreen(true);
    }

    public void load(Parent parent) {
        this.parent = (ParentScreen) parent;
        if (parent instanceof LabyScreen) {
            this.previousScreen = ((LabyScreen) parent).previousScreen;
        }
        if (parent instanceof ScreenRendererWidget) {
            ScreenRendererWidget screenRenderer = (ScreenRendererWidget) parent;
            this.previousScreen = screenRenderer.handlesPreviousScreen() ? screenRenderer.getPreviousScreen() : null;
        }
        initialize(parent);
        postInitialize();
    }

    public boolean isPauseScreen() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreen(ScreenInstance screen) {
        if (this.parent != null) {
            this.parent.displayScreen(screen);
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreenRaw(Object screen) {
        if (this.parent != null) {
            this.parent.displayScreenRaw(screen);
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public LabyScreen currentLabyScreen() {
        return this.parent.currentLabyScreen();
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public ScreenWrapper currentScreen() {
        return this.parent.currentScreen();
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public String getCurrentScreenName() {
        return this.parent.getCurrentScreenName();
    }

    public Object getPreviousScreen() {
        return this.previousScreen;
    }

    public void setPreviousScreen(Object previousScreen) {
        this.previousScreen = previousScreen;
    }

    public boolean allowCustomFont() {
        if (this.parent instanceof ScreenRendererWidget) {
            Parent root = this.parent.getRoot();
            if (root instanceof LabyScreen) {
                return ((LabyScreen) root).allowCustomFont();
            }
            return true;
        }
        return true;
    }
}
