package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/EmotesConfig.class */
public interface EmotesConfig extends ConfigAccessor {
    ConfigProperty<Boolean> emotes();

    ConfigProperty<Boolean> orderEmotesClockwise();

    ConfigProperty<Boolean> showCosmeticsInWheel();

    ConfigProperty<Boolean> firstPersonHeadAnimation();

    ConfigProperty<Boolean> emotePerspective();

    ConfigProperty<Boolean> emoteDebug();
}
