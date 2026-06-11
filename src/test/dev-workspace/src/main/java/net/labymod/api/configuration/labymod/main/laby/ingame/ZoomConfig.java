package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomCinematicConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomTransitionConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/ZoomConfig.class */
public interface ZoomConfig extends ConfigAccessor {
    ConfigProperty<Key> zoomKey();

    ConfigProperty<Boolean> zoomHold();

    ZoomCinematicConfig zoomCinematic();

    ZoomTransitionConfig zoomTransition();

    ConfigProperty<Boolean> shouldRenderFirstPersonHand();

    ConfigProperty<Boolean> scrollToZoom();

    ConfigProperty<Double> zoomDistance();
}
