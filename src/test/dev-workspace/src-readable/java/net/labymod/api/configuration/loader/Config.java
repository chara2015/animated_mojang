package net.labymod.api.configuration.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.creator.SettingCreator;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/Config.class */
public class Config implements ConfigAccessor {

    @Exclude
    private final ConfigProperty<Integer> configVersion = new ConfigProperty<>(Integer.valueOf(getConfigVersion()));

    @Exclude
    private Map<String, String> configMeta;

    public void reset() {
        for (Setting setting : toSettings()) {
            setting.initialize();
            setting.reset();
        }
    }

    public ConfigProperty<Integer> usedConfigVersion() {
        return this.configVersion;
    }

    public int getConfigVersion() {
        return 1;
    }

    @Override // net.labymod.api.configuration.loader.ConfigAccessor
    public List<Setting> toSettings() {
        return toSettings(null);
    }

    @Override // net.labymod.api.configuration.loader.ConfigAccessor
    public List<Setting> toSettings(Setting parent) {
        return toSettings(parent, null);
    }

    @Override // net.labymod.api.configuration.loader.ConfigAccessor
    public RootSettingRegistry asRegistry(String id) {
        String namespace = Laby.labyAPI().getNamespace(this);
        RootSettingRegistry registry = RootSettingRegistry.custom(namespace, id);
        registry.addSettings(toSettings());
        registry.initialize();
        return registry;
    }

    @NotNull
    public List<Setting> toSettings(@Nullable Setting parent, SpriteTexture texture) {
        return new SettingCreator(Laby.labyAPI(), this).createSettings(parent, texture);
    }

    public boolean hasConfigMeta(String key) {
        return this.configMeta != null && this.configMeta.containsKey(key);
    }

    public Map<String, String> configMeta() {
        if (this.configMeta == null) {
            this.configMeta = new HashMap();
        }
        return this.configMeta;
    }
}
