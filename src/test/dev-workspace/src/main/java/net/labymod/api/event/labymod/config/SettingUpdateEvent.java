package net.labymod.api.event.labymod.config;

import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/config/SettingUpdateEvent.class */
@Deprecated
@LabyEvent(background = true)
public class SettingUpdateEvent implements Event {
    private final Phase phase;
    private final SettingElement setting;
    private Object value;

    public SettingUpdateEvent(Phase phase, SettingElement setting, Object value) {
        this.phase = phase;
        this.setting = setting;
        this.value = value;
    }

    public Phase phase() {
        return this.phase;
    }

    public SettingElement setting() {
        return this.setting;
    }

    public <T> T getValue() {
        return (T) this.value;
    }

    public <T> void setValue(T value) {
        this.value = value;
    }
}
