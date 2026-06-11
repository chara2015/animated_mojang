package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.gui.screen.NamedScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/elements/MenuNavigationElement.class */
public class MenuNavigationElement extends ScreenNavigationElement {
    public MenuNavigationElement() {
        super(NamedScreen.INGAME_MENU.create());
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public String getWidgetId() {
        return "menu";
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.menu", new Component[0]);
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Icon getIcon() {
        return Textures.SpriteCommon.WORKBENCH;
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public boolean isVisible() {
        return Laby.labyAPI().minecraft().isIngame();
    }
}
