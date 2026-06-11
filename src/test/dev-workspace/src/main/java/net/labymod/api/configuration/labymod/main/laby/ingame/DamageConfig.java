package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/DamageConfig.class */
public interface DamageConfig extends ConfigAccessor {
    ConfigProperty<Boolean> damageColored();

    ConfigProperty<Color> damageColor();
}
