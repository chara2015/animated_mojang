package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/MenuBlurConfig.class */
public interface MenuBlurConfig {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/MenuBlurConfig$ScreenType.class */
    public enum ScreenType {
        TITLE_SCREEN,
        PAUSE_MENU,
        INVENTORIES,
        EMOTE_WHEEL,
        INTERACTION_WHEEL,
        SPRAY_WHEEL
    }

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Float> strength();

    ConfigProperty<Boolean> enhancedContrast();

    ConfigProperty<Boolean> titleScreen();

    ConfigProperty<Boolean> pauseMenu();

    ConfigProperty<Boolean> inventories();

    ConfigProperty<Boolean> emoteWheel();

    ConfigProperty<Boolean> sprayWheel();

    ConfigProperty<Boolean> interactionWheel();

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    default boolean isMenuBlurEnabled(ScreenType screenType) throws MatchException {
        ConfigProperty<Boolean> configPropertySprayWheel;
        if (!enabled().get().booleanValue()) {
            return false;
        }
        switch (screenType) {
            case TITLE_SCREEN:
                configPropertySprayWheel = titleScreen();
                break;
            case PAUSE_MENU:
                configPropertySprayWheel = pauseMenu();
                break;
            case INVENTORIES:
                configPropertySprayWheel = inventories();
                break;
            case EMOTE_WHEEL:
                configPropertySprayWheel = emoteWheel();
                break;
            case INTERACTION_WHEEL:
                configPropertySprayWheel = interactionWheel();
                break;
            case SPRAY_WHEEL:
                configPropertySprayWheel = sprayWheel();
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        ConfigProperty<Boolean> property = configPropertySprayWheel;
        return property.get().booleanValue();
    }
}
