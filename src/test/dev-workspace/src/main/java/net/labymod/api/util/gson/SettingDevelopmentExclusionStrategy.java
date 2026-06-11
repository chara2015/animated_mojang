package net.labymod.api.util.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.settings.annotation.SettingDevelopment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/SettingDevelopmentExclusionStrategy.class */
public class SettingDevelopmentExclusionStrategy implements ExclusionStrategy {
    private final LabyAPI labyAPI;

    public SettingDevelopmentExclusionStrategy(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        SettingDevelopment annotation = (SettingDevelopment) f.getAnnotation(SettingDevelopment.class);
        if (annotation == null) {
            return false;
        }
        String configNamespace = this.labyAPI.getNamespace(f.getClass());
        return !this.labyAPI.labyModLoader().isDevelopmentEnvironment(configNamespace);
    }

    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
