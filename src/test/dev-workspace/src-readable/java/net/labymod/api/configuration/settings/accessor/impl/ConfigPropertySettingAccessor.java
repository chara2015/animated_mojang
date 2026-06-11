package net.labymod.api.configuration.settings.accessor.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/accessor/impl/ConfigPropertySettingAccessor.class */
public class ConfigPropertySettingAccessor implements SettingAccessor {
    private final SettingElement setting;
    private final ConfigProperty configProperty;
    private final Config config;
    private final Field field;

    public ConfigPropertySettingAccessor(SettingElement setting, ConfigProperty configProperty, Config config, Field field) {
        this.setting = setting;
        this.configProperty = configProperty;
        this.config = config;
        this.field = field;
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public Class<?> getType() {
        return this.configProperty.getType();
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    @Nullable
    public Type getGenericType() {
        return this.configProperty.getGenericType();
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public Field getField() {
        return this.field;
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public Config config() {
        return this.config;
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public <T> void set(T value) {
        this.configProperty.set(value);
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public <T> T get() {
        return (T) this.configProperty.get();
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public <T> ConfigProperty<T> property() {
        return this.configProperty;
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public SettingElement setting() {
        return this.setting;
    }
}
