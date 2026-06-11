package net.labymod.api.configuration.settings.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.type.SettingElement;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/accessor/SettingAccessor.class */
public interface SettingAccessor {
    Class<?> getType();

    @Nullable
    Type getGenericType();

    Field getField();

    Config config();

    <T> void set(T t);

    <T> T get();

    <T> ConfigProperty<T> property();

    SettingElement setting();

    default boolean isSet() {
        return get() != null;
    }
}
