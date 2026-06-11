package net.labymod.api.configuration.loader;

import java.util.List;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/ConfigAccessor.class */
public interface ConfigAccessor {
    List<Setting> toSettings();

    List<Setting> toSettings(Setting setting);

    RootSettingRegistry asRegistry(String str);
}
