package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.ScreenFactory;
import net.labymod.api.client.gui.screen.ScreenInstance;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/navigation/elements/ScreenFactoryNavigationElement.class */
public abstract class ScreenFactoryNavigationElement extends ScreenNavigationElement {
    private final ScreenFactory factory;

    public ScreenFactoryNavigationElement(ScreenFactory factory) {
        super(factory.create());
        this.factory = factory;
    }

    @Override // net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement
    public void onUpdate(ScreenInstance instance) {
        this.screen = this.factory.create();
    }
}
