package net.labymod.api.configuration.settings.creator.hotkey;

import java.util.Objects;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.settings.creator.SettingEntry;
import net.labymod.api.configuration.settings.type.SettingElement;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/hotkey/SwitchSettingHotkeyFactory.class */
public class SwitchSettingHotkeyFactory implements HotkeyFactory {
    @Override // net.labymod.api.configuration.settings.creator.hotkey.HotkeyFactory
    public boolean hasSettingAnnotation(MemberInspector inspector) {
        return inspector.isAnnotationPresent(SwitchWidget.SwitchSetting.class);
    }

    @Override // net.labymod.api.configuration.settings.creator.hotkey.HotkeyFactory
    @Nullable
    public Hotkey create(MemberInspector inspector, String displayName, SettingEntry entry, String namespace) {
        SettingElement element = entry.setting();
        SettingAccessor accessor = element.getAccessor();
        if (accessor == null) {
            return null;
        }
        ConfigProperty<?> property = accessor.property();
        if (property.getType() != Boolean.class) {
            return null;
        }
        SwitchWidget.SwitchSetting annotation = (SwitchWidget.SwitchSetting) inspector.getAnnotation(SwitchWidget.SwitchSetting.class);
        if (!annotation.hotkey()) {
            return null;
        }
        String str = element.getTranslationKey() + ".name";
        KeyAccessor keyAccessor = new KeyAccessor(() -> {
            String hotkey = accessor.config().configMeta().get(element.getId() + ".hotkey");
            return hotkey == null ? Key.NONE : KeyMapper.getKey(hotkey);
        }, () -> {
            return Key.NONE;
        }, newKey -> {
            accessor.config().configMeta().put(element.getId() + ".hotkey", newKey.getActualName());
        });
        Objects.requireNonNull(element);
        return new Hotkey(displayName, namespace, str, keyAccessor, element::isVisible);
    }
}
