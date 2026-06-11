package net.labymod.api.configuration.settings.creator.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.accessor.impl.ConfigPropertySettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/accessor/ConfigPropertySettingAccessorFactory.class */
public class ConfigPropertySettingAccessorFactory implements SettingAccessorFactory {
    private static final Logging LOGGER = Logging.getLogger();

    @Override // net.labymod.api.configuration.settings.creator.accessor.SettingAccessorFactory
    public SettingAccessor create(SettingElement element, SettingAccessorContext context, Config config) {
        Field field = context.field();
        field.setAccessible(true);
        ConfigProperty property = (ConfigProperty) Reflection.invokeGetterField(config, field);
        if (property == null) {
            LOGGER.warn("Could not create ConfigPropertySettingAccessor for \"{}\"", field.getName());
            return null;
        }
        property.withSettings(element);
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            property.setGenericType(parameterizedType.getActualTypeArguments()[0]);
        }
        return new ConfigPropertySettingAccessor(element, property, config, field);
    }

    @Override // net.labymod.api.configuration.settings.creator.accessor.SettingAccessorFactory
    public boolean isAssignableFrom(Class<?> type) {
        return ConfigProperty.class.isAssignableFrom(type);
    }
}
