package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/elements/LabyModNavigationElement.class */
public class LabyModNavigationElement extends ScreenNavigationElement {
    public LabyModNavigationElement() {
        super(new LabyModActivity());
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public String getWidgetId() {
        return "labymod";
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.labymod", new Component[0]);
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Icon getIcon() {
        return Textures.SpriteCommon.LABYMOD;
    }

    @Override // net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement
    public boolean shouldDocumentHandleEscape() {
        return true;
    }
}
