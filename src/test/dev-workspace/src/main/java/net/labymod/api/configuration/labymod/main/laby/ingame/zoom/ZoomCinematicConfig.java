package net.labymod.api.configuration.labymod.main.laby.ingame.zoom;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/zoom/ZoomCinematicConfig.class */
public interface ZoomCinematicConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> disableAfterTransition();
}
