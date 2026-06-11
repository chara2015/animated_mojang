package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/navigation/elements/AbstractNavigationElement.class */
public abstract class AbstractNavigationElement<T extends Widget> implements NavigationElement<T> {
    private NavigationWrapper navigation;

    public abstract Component getDisplayName();

    public abstract Icon getIcon();

    public AbstractNavigationElement() {
        Laby.labyAPI().eventBus().registerListener(this);
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public T createWidget(NavigationWrapper wrapper) {
        ButtonWidget button = ButtonWidget.component(getDisplayName(), getIcon());
        button.addId("closed");
        return button;
    }

    public void setNavigation(NavigationWrapper navigation) {
        this.navigation = navigation;
    }

    public void updateWidget() {
        if (this.navigation == null) {
            return;
        }
        this.navigation.updateElement(this);
    }
}
