package net.labymod.api.configuration.settings;

import net.labymod.api.configuration.settings.type.SettingElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/SwitchableHandler.class */
public interface SwitchableHandler {
    boolean isEnabled(SettingElement settingElement, Object obj, SwitchableInfo switchableInfo);
}
