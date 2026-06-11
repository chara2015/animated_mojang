package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/config/SettingInitializeEvent.class */
@LabyEvent(background = true)
public class SettingInitializeEvent implements Event {
    private final Setting setting;

    public SettingInitializeEvent(Setting setting) {
        this.setting = setting;
    }

    public Setting setting() {
        return this.setting;
    }
}
