package net.labymod.api.client.gui.navigation;

import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/navigation/NavigationRegistry.class */
@Referenceable
public interface NavigationRegistry extends Registry<NavigationElement<?>> {
    @Nullable
    NavigationElement<?> getActiveScreenElement();

    void setIngameMenuActive();

    ScreenInstance createNavigation(ScreenBaseNavigationElement<?> screenBaseNavigationElement);
}
