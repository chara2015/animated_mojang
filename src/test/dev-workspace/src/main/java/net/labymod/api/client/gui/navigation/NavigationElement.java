package net.labymod.api.client.gui.navigation;

import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/navigation/NavigationElement.class */
public interface NavigationElement<T extends Widget> {
    String getWidgetId();

    T createWidget(NavigationWrapper navigationWrapper);

    default boolean isVisible() {
        return true;
    }

    @Deprecated
    default boolean shouldPlaySound() {
        return true;
    }
}
