package net.labymod.core.configuration.settings;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.labymod.config.SettingCreateEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/settings/HotkeySwitchSettingHandler.class */
@Singleton
public class HotkeySwitchSettingHandler {
    private final Set<SettingElement> properties = new HashSet();

    @Subscribe
    public void registerHotkeySetting(SettingCreateEvent event) {
        SettingElement element;
        if (!event.setting().isElement() || (element = event.setting().asElement()) == null || !(element.getAnnotation() instanceof SwitchWidget.SwitchSetting) || element.getAccessor() == null) {
            return;
        }
        ConfigProperty<?> property = element.getAccessor().property();
        if (property.getType() == Boolean.class && ((SwitchWidget.SwitchSetting) element.getAnnotation()).hotkey()) {
            this.properties.add(element);
        }
    }

    @Subscribe
    public void toggleHotkeySettings(KeyEvent event) {
        if (Laby.labyAPI().minecraft().minecraftWindow().getCurrentVersionedScreen() != null || event.state() != KeyEvent.State.PRESS) {
            return;
        }
        for (SettingElement setting : this.properties) {
            String hotkey = setting.getAccessor().config().configMeta().get(setting.getId() + ".hotkey");
            if (hotkey != null) {
                Key key = KeyMapper.getKey(hotkey);
                if (key == event.key()) {
                    Boolean value = (Boolean) setting.getAccessor().get();
                    setting.getAccessor().set(Boolean.valueOf(value == null || !value.booleanValue()));
                }
            }
        }
    }
}
