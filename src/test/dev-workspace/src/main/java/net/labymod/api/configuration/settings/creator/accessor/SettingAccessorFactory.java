package net.labymod.api.configuration.settings.creator.accessor;

import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/accessor/SettingAccessorFactory.class */
public interface SettingAccessorFactory {
    SettingAccessor create(SettingElement settingElement, SettingAccessorContext settingAccessorContext, Config config);

    boolean isAssignableFrom(Class<?> cls);
}
