package net.labymod.api.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/appearance/DynamicBackgroundConfig.class */
public interface DynamicBackgroundConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> shader();

    ConfigProperty<Boolean> coloredLight();

    ConfigProperty<Float> brightness();

    ConfigProperty<Float> resolution();

    ConfigProperty<Boolean> limitFpsWhenUnfocused();

    ConfigProperty<Boolean> animatedTextures();

    ConfigProperty<Boolean> snowflakes();
}
