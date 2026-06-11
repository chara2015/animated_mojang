package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/navigation/elements/ScreenBaseNavigationElement.class */
public abstract class ScreenBaseNavigationElement<T extends Widget> extends AbstractNavigationElement<T> {
    protected ScreenInstance screen;
    protected long timeLastOpened = -1;

    protected ScreenBaseNavigationElement(ScreenInstance screen) {
        this.screen = screen;
    }

    public ScreenInstance getScreen() {
        return this.screen;
    }

    public long getTimeLastOpened() {
        return this.timeLastOpened;
    }

    public boolean isScreen(Object raw) {
        return this.screen.mostInnerScreen().getClass().equals(raw.getClass());
    }

    public boolean shouldDocumentHandleEscape() {
        return false;
    }

    public void onUpdate(ScreenInstance instance) {
        this.screen = instance;
    }

    public void onCloseScreen() {
    }

    public boolean isFallback() {
        return false;
    }
}
