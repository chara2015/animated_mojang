package net.labymod.api.configuration.settings;

import net.labymod.api.client.gui.screen.activity.Activity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/SettingHandler.class */
public interface SettingHandler {
    void created(Setting setting);

    void initialized(Setting setting);

    default void reset(Setting setting) {
    }

    default boolean isEnabled(Setting setting) {
        return true;
    }

    default boolean opensActivity(Setting setting) {
        return false;
    }

    default Activity activity(Setting setting) {
        return null;
    }
}
