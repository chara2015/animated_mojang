package net.labymod.api.configuration.loader;

import java.io.IOException;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/ConfigProvider.class */
public abstract class ConfigProvider<T extends ConfigAccessor> {
    private static final Logging LOGGER = Logging.create((Class<?>) ConfigProvider.class);
    private T config;
    private ConfigLoader loader;

    protected abstract Class<? extends ConfigAccessor> getType();

    public T load(ConfigLoader configLoader) {
        try {
            return (T) load(configLoader, 0);
        } catch (ConfigurationLoadException e) {
            captureException(e);
            LOGGER.error("Configuration {} could not be loaded.", getClassName(), e);
            return null;
        }
    }

    public T safeLoad(ConfigLoader configLoader) throws ConfigurationLoadException {
        return (T) load(configLoader, 0);
    }

    private T load(ConfigLoader configLoader, int i) throws ConfigurationLoadException {
        ConfigAccessor configAccessorLoad;
        if (i > 3) {
            throw new ConfigurationLoadException(getType(), null);
        }
        this.loader = configLoader;
        try {
            configAccessorLoad = configLoader.load(getType());
        } catch (ConfigurationLoadException e) {
            try {
                configLoader.invalidate(getType());
                captureException(e);
                LOGGER.error("Configuration {} could not be loaded", getClassName(), e);
                configAccessorLoad = load(configLoader, i + 1);
            } catch (IOException e2) {
                throw e;
            }
        }
        T t = (T) configAccessorLoad;
        this.config = t;
        return t;
    }

    public T loadJson() {
        return (T) load(JsonConfigLoader.createDefault());
    }

    public boolean save() {
        try {
            this.loader.save(this.config);
            return true;
        } catch (Exception exception) {
            captureException(exception);
            LOGGER.error("Configuration {} could not be saved.", getClassName(), exception);
            return false;
        }
    }

    public boolean safeSave() throws ConfigurationSaveException {
        this.loader.save(this.config);
        return true;
    }

    public Object serialize() {
        try {
            return this.loader.serialize(this.config);
        } catch (Exception exception) {
            LOGGER.error("Configuration {} could not be serialized.", getClassName(), exception);
            return null;
        }
    }

    public T get() {
        if (this.config == null) {
            LOGGER.error(getType().getSimpleName() + " is null. Loader is " + (this.loader == null ? "null" : "present"), new Object[0]);
        }
        return this.config;
    }

    public ConfigLoader getLoader() {
        return this.loader;
    }

    private String getClassName() {
        Class<? extends ConfigAccessor> type = getType();
        if (type == null) {
            return "<Unknown>";
        }
        return type.getName();
    }

    private void captureException(Throwable throwable) {
        LabySentry.capture(throwable);
    }
}
