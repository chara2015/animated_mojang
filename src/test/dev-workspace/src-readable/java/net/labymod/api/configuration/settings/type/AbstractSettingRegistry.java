package net.labymod.api.configuration.settings.type;

import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/AbstractSettingRegistry.class */
public abstract class AbstractSettingRegistry extends AbstractSetting {
    private boolean holdable;

    protected AbstractSettingRegistry(String id, Icon icon) {
        super(id, icon);
        this.holdable = true;
    }

    public AbstractSettingRegistry holdable(boolean holdable) {
        this.holdable = holdable;
        return this;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public boolean isHoldable() {
        return this.holdable;
    }
}
