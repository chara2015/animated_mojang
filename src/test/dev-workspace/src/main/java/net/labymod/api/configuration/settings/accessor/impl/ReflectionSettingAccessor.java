package net.labymod.api.configuration.settings.accessor.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/accessor/impl/ReflectionSettingAccessor.class */
public class ReflectionSettingAccessor implements SettingAccessor {
    private final SettingElement setting;
    private final Field field;
    private final Config config;
    private final ConfigProperty<?> property;

    public ReflectionSettingAccessor(SettingElement setting, Field field, Config config) {
        this.setting = setting;
        this.field = field;
        this.config = config;
        this.field.setAccessible(true);
        this.property = new ConfigProperty<>(get());
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public Class<?> getType() {
        return this.field.getType();
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    @Nullable
    public Type getGenericType() {
        return this.field.getGenericType();
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
    public <T> void set(T t) {
        if (this.setting.isInitialized()) {
            SettingUpdateEvent settingUpdateEvent = new SettingUpdateEvent(Phase.PRE, this.setting, t);
            Laby.labyAPI().eventBus().fire(settingUpdateEvent);
            t = (T) settingUpdateEvent.getValue();
        }
        Reflection.invokeSetterField(this.config, this.field, t);
        Laby.labyAPI().eventBus().fire(new SettingUpdateEvent(Phase.POST, this.setting, t));
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public <T> T get() {
        return (T) Reflection.invokeGetterField(this.config, this.field);
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public <T> ConfigProperty<T> property() {
        this.property.set(get());
        return (ConfigProperty<T>) this.property;
    }

    @Override // net.labymod.api.configuration.settings.accessor.SettingAccessor
    public SettingElement setting() {
        return this.setting;
    }
}
