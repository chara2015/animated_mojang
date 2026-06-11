package net.labymod.v1_16_5.client.gui.screen;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gui/screen/VersionedScreenWrapperFactory.class */
@Singleton
@Implements(ScreenWrapper.Factory.class)
public class VersionedScreenWrapperFactory implements ScreenWrapper.Factory {
    @Override // net.labymod.api.client.gui.screen.ScreenWrapper.Factory
    public ScreenWrapper create(Object vanillaScreen) {
        if (!(vanillaScreen instanceof dot)) {
            throw new IllegalArgumentException(vanillaScreen.getClass().getName() + " is not an instance of " + dot.class.getName());
        }
        dot screen = (dot) vanillaScreen;
        return new VersionedScreenWrapper(screen);
    }
}
