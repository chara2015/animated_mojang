package net.labymod.core.util.logging;

import net.labymod.api.Constants;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.util.property.SystemProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/logging/DefaultLogging.class */
final class DefaultLogging implements Logging {
    private static final boolean DEBUG_LOGGING = SystemProperties.DEBUG_LOGGER.get().booleanValue();
    private final Logger logger;
    private final String name;

    public DefaultLogging(String name) {
        this.name = name;
        this.logger = LogManager.getLogger(name);
        if (DEBUG_LOGGING) {
            org.apache.logging.log4j.core.Logger logger = this.logger;
            if (logger instanceof org.apache.logging.log4j.core.Logger) {
                org.apache.logging.log4j.core.Logger loggerImpl = logger;
                loggerImpl.setLevel(Level.DEBUG);
            } else {
                warn("Failed to set debug log level for logger: " + name, new Object[0]);
            }
        }
    }

    public DefaultLogging(Class<?> cls) {
        this(buildLoggerName(cls));
    }

    private static String buildLoggerName(Class<?> cls) {
        String name = cls.getSimpleName();
        String namespace = Constants.Branding.NAME;
        ClassLoader classLoader = cls.getClassLoader();
        if (classLoader instanceof AddonClassLoader) {
            AddonClassLoader addonClassLoader = (AddonClassLoader) classLoader;
            namespace = addonClassLoader.getAddonInfo().getNamespace();
        }
        return name + "/" + namespace;
    }

    @Override // net.labymod.api.util.logging.Logging
    public void info(CharSequence message, Throwable throwable) {
        this.logger.info(CharSequences.toString(message), throwable);
    }

    @Override // net.labymod.api.util.logging.Logging
    public void info(CharSequence message, Object... arguments) {
        this.logger.info(CharSequences.toString(message), arguments);
    }

    @Override // net.labymod.api.util.logging.Logging
    public void warn(CharSequence message, Throwable throwable) {
        this.logger.warn(CharSequences.toString(message), throwable);
    }

    @Override // net.labymod.api.util.logging.Logging
    public void warn(CharSequence message, Object... arguments) {
        this.logger.warn(CharSequences.toString(message), arguments);
    }

    @Override // net.labymod.api.util.logging.Logging
    public void error(CharSequence message, Throwable throwable) {
        this.logger.error(CharSequences.toString(message), throwable);
    }

    @Override // net.labymod.api.util.logging.Logging
    public void error(CharSequence message, Object... arguments) {
        this.logger.error(CharSequences.toString(message), arguments);
    }

    @Override // net.labymod.api.util.logging.Logging
    public void debug(CharSequence message, Throwable throwable) {
        this.logger.debug(CharSequences.toString(message), throwable);
    }

    @Override // net.labymod.api.util.logging.Logging
    public void debug(CharSequence message, Object... arguments) {
        this.logger.debug(CharSequences.toString(message), arguments);
    }
}
