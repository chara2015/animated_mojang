package net.labymod.v1_21_11.client.gui.screen;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.models.Implements;
import net.minecraft.client.gui.screens.Screen;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gui/screen/VersionedScreenWrapperFactory.class */
@Singleton
@Implements(ScreenWrapper.Factory.class)
public class VersionedScreenWrapperFactory implements ScreenWrapper.Factory {
    public ScreenWrapper create(Object vanillaScreen) {
        if (!(vanillaScreen instanceof Screen)) {
            throw new IllegalArgumentException(vanillaScreen.getClass().getName() + " is not an instance of " + Screen.class.getName());
        }
        Screen screen = (Screen) vanillaScreen;
        return new VersionedScreenWrapper(screen);
    }
}
