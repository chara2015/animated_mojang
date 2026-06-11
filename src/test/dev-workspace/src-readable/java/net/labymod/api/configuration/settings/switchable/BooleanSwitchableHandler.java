package net.labymod.api.configuration.settings.switchable;

import net.labymod.api.configuration.settings.SwitchableHandler;
import net.labymod.api.configuration.settings.SwitchableInfo;
import net.labymod.api.configuration.settings.type.SettingElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/switchable/BooleanSwitchableHandler.class */
public class BooleanSwitchableHandler implements SwitchableHandler {
    @Override // net.labymod.api.configuration.settings.SwitchableHandler
    public boolean isEnabled(SettingElement setting, Object value, SwitchableInfo info) {
        return (value instanceof Boolean) && ((Boolean) value).booleanValue() == info.getRequiredValue().equalsIgnoreCase("true");
    }
}
