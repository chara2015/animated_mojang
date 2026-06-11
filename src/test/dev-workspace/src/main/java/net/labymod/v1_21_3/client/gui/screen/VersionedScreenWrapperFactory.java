package net.labymod.v1_21_3.client.gui.screen;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gui/screen/VersionedScreenWrapperFactory.class */
@Singleton
@Implements(ScreenWrapper.Factory.class)
public class VersionedScreenWrapperFactory implements ScreenWrapper.Factory {
    @Override // net.labymod.api.client.gui.screen.ScreenWrapper.Factory
    public ScreenWrapper create(Object vanillaScreen) {
        if (!(vanillaScreen instanceof fty)) {
            throw new IllegalArgumentException(vanillaScreen.getClass().getName() + " is not an instance of " + fty.class.getName());
        }
        fty screen = (fty) vanillaScreen;
        return new VersionedScreenWrapper(screen);
    }
}
