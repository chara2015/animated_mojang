package net.labymod.api.configuration.settings.creator.hotkey;

import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.settings.creator.SettingEntry;
import net.labymod.api.configuration.settings.type.SettingElement;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/hotkey/DefaultHotkeyFactory.class */
public class DefaultHotkeyFactory implements HotkeyFactory {
    @Override // net.labymod.api.configuration.settings.creator.hotkey.HotkeyFactory
    public boolean hasSettingAnnotation(MemberInspector inspector) {
        return inspector.isAnnotationPresent(KeybindWidget.KeyBindSetting.class);
    }

    @Override // net.labymod.api.configuration.settings.creator.hotkey.HotkeyFactory
    @Nullable
    public Hotkey create(MemberInspector inspector, String displayName, SettingEntry entry, String namespace) {
        SettingElement element = entry.setting();
        SettingAccessor accessor = element.getAccessor();
        if (accessor == null) {
            return null;
        }
        ConfigProperty<Key> property = accessor.property();
        String str = element.getTranslationKey() + ".name";
        Objects.requireNonNull(property);
        Supplier supplier = property::get;
        Objects.requireNonNull(property);
        Supplier supplier2 = property::defaultValue;
        Objects.requireNonNull(property);
        KeyAccessor keyAccessor = new KeyAccessor(supplier, supplier2, (v1) -> {
            r9.set(v1);
        });
        Objects.requireNonNull(element);
        return new Hotkey(displayName, namespace, str, keyAccessor, element::isVisible);
    }
}
