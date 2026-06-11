package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/config/SettingCreateEvent.class */
public class SettingCreateEvent implements Event {
    private final Setting setting;

    public SettingCreateEvent(Setting setting) {
        this.setting = setting;
    }

    public Setting setting() {
        return this.setting;
    }
}
