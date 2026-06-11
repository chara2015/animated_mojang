package net.labymod.api.configuration.exception;

import net.labymod.api.configuration.loader.ConfigAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/exception/ConfigurationSaveException.class */
public class ConfigurationSaveException extends ConfigurationException {
    public ConfigurationSaveException(ConfigAccessor configAccessor, Throwable cause) {
        super("Could not save the Configuration \"" + configAccessor.getClass().getSimpleName() + "\"", cause);
    }
}
