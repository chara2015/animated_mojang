package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/config/SettingResetEvent.class */
public class SettingResetEvent implements Event {
    private final Phase phase;
    private final SettingElement setting;

    public SettingResetEvent(Phase phase, SettingElement setting) {
        this.phase = phase;
        this.setting = setting;
    }

    public Phase phase() {
        return this.phase;
    }

    public SettingElement setting() {
        return this.setting;
    }
}
