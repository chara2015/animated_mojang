package net.labymod.api.configuration.exception;

import net.labymod.api.configuration.loader.ConfigAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/exception/ConfigurationLoadException.class */
public class ConfigurationLoadException extends ConfigurationException {
    public ConfigurationLoadException(Class<? extends ConfigAccessor> clazz, Throwable cause) {
        super("Could not load the Configuration \"" + clazz.getSimpleName() + "\"", cause);
    }
}
