package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/elements/SingleplayerNavigationElement.class */
public class SingleplayerNavigationElement extends ScreenNavigationElement {
    public SingleplayerNavigationElement() {
        super(NamedScreen.SINGLEPLAYER.create());
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public String getWidgetId() {
        return "singleplayer";
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.singleplayer", new Component[0]);
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Icon getIcon() {
        return Textures.SpriteCommon.SINGLEPLAYER;
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public boolean isVisible() {
        return !Laby.labyAPI().minecraft().isSingleplayer() && Laby.labyAPI().config().appearance().navigation().showSingleplayer().get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement
    public void onUpdate(ScreenInstance instance) {
        GameScreen gameScreen = GameScreenRegistry.from(instance);
        if (gameScreen != null) {
            this.screen = instance;
        }
    }

    @Override // net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement
    public boolean isScreen(Object raw) {
        GameScreen gameScreen = GameScreenRegistry.from(raw);
        return gameScreen == NamedScreen.SINGLEPLAYER;
    }

    @Override // net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement
    public boolean isFallback() {
        return true;
    }
}
