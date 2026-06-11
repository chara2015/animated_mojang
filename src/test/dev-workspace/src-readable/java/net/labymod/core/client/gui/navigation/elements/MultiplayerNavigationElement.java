package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect.DirectConnectActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/elements/MultiplayerNavigationElement.class */
public class MultiplayerNavigationElement extends ScreenNavigationElement {
    public MultiplayerNavigationElement() {
        super(NamedScreen.MULTIPLAYER.create());
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public String getWidgetId() {
        return "multiplayer";
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.multiplayer", new Component[0]);
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Icon getIcon() {
        return Textures.SpriteCommon.MULTIPLAYER;
    }

    @Override // net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement
    public boolean isScreen(Object raw) {
        return super.isScreen(raw) || (raw instanceof MultiplayerActivity) || (raw instanceof DirectConnectActivity);
    }

    @Override // net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement
    public boolean isFallback() {
        return true;
    }
}
