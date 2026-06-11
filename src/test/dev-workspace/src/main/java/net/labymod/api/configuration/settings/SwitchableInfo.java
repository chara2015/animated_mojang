package net.labymod.api.configuration.settings;

import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/SwitchableInfo.class */
public class SwitchableInfo {
    private final String switchId;
    private final boolean invert;
    private final String requiredValue;
    private SettingAccessor switchAccessor;
    private SwitchableHandler handler;

    public SwitchableInfo(SettingRequires requires) {
        this(requires.value(), requires.invert(), requires.required());
    }

    public SwitchableInfo(String switchId, boolean invert, String requiredValue) {
        this.switchId = switchId;
        this.invert = invert;
        this.requiredValue = requiredValue;
    }

    public String getSwitchId() {
        return this.switchId;
    }

    public boolean isInvert() {
        return this.invert;
    }

    public String getRequiredValue() {
        return this.requiredValue;
    }

    public SwitchableHandler getHandler() {
        return this.handler;
    }

    public SettingAccessor getSwitchAccessor() {
        return this.switchAccessor;
    }

    public void setHandler(SwitchableHandler handler) {
        this.handler = handler;
    }

    public void setSwitchAccessor(SettingAccessor switchAccessor) {
        this.switchAccessor = switchAccessor;
    }
}
