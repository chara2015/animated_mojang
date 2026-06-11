package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/navigation/elements/ScreenNavigationElement.class */
public abstract class ScreenNavigationElement extends ScreenBaseNavigationElement<ButtonWidget> {
    protected ScreenNavigationElement(ScreenInstance screen) {
        super(screen);
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement, net.labymod.api.client.gui.navigation.NavigationElement
    public ButtonWidget createWidget(NavigationWrapper wrapper) {
        ButtonWidget widget = (ButtonWidget) super.createWidget(wrapper);
        if (wrapper.isActive(this)) {
            widget.addId("open");
            widget.removeId("closed");
            widget.setEnabled(false);
        } else {
            widget.setPressable(() -> {
                wrapper.displayScreen(this);
            });
        }
        return widget;
    }
}
