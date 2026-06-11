package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/HitboxConfig.class */
public interface HitboxConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Float> lineSize();

    ConfigProperty<Color> eyeLineColor();

    ConfigProperty<Color> eyeBoxColor();

    ConfigProperty<Color> boxColor();
}
