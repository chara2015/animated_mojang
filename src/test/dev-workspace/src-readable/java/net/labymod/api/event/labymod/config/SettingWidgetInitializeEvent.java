package net.labymod.api.event.labymod.config;

import java.util.List;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/config/SettingWidgetInitializeEvent.class */
@LabyEvent(background = true)
public class SettingWidgetInitializeEvent implements Event {
    private final ParentScreen parentScreen;
    private final Setting holder;
    private final List<Widget> settings;

    public SettingWidgetInitializeEvent(ParentScreen parentScreen, Setting holder, List<Widget> settings) {
        this.parentScreen = parentScreen;
        this.holder = holder;
        this.settings = settings;
    }

    public ParentScreen parentScreen() {
        return this.parentScreen;
    }

    public Setting holder() {
        return this.holder;
    }

    public List<Widget> getSettings() {
        return this.settings;
    }
}
