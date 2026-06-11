package net.labymod.api.addon;

import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/AddonConfig.class */
@SettingRequires("enabled")
public abstract class AddonConfig extends Config {
    public abstract ConfigProperty<Boolean> enabled();
}
