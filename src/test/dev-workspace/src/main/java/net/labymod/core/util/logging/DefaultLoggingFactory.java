package net.labymod.core.util.logging;

import java.util.function.BooleanSupplier;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.laby.lib.core.log.LoggerFactory;
import net.labymod.api.models.Implements;
import net.labymod.api.util.logging.ConditionLogging;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/logging/DefaultLoggingFactory.class */
@Singleton
@Implements(Logging.Factory.class)
public class DefaultLoggingFactory implements Logging.Factory {
    private static Logging.Factory instance;

    static {
        LoggerFactory.setProvider(new LabyLibLoggerProvider());
    }

    private static Logging.Factory getInstance() {
        if (instance == null) {
            instance = new DefaultLoggingFactory();
        }
        return instance;
    }

    @Inject
    public DefaultLoggingFactory() {
    }

    @ApiStatus.Internal
    public static Logging createLogger(String name) {
        return getInstance().create(name);
    }

    @ApiStatus.Internal
    public static Logging createLogger(Class<?> cls) {
        return getInstance().create(cls);
    }

    @Override // net.labymod.api.util.logging.Logging.Factory
    @NotNull
    public Logging create(String name) {
        return new DefaultLogging(name);
    }

    @Override // net.labymod.api.util.logging.Logging.Factory
    @NotNull
    public Logging create(Class<?> cls) {
        return new DefaultLogging(cls);
    }

    @Override // net.labymod.api.util.logging.Logging.Factory
    public Logging create(String name, BooleanSupplier condition) {
        if (condition == null) {
            return create(name);
        }
        return new ConditionLogging(create(name), condition);
    }

    @Override // net.labymod.api.util.logging.Logging.Factory
    public Logging create(Class<?> cls, BooleanSupplier condition) {
        if (condition == null) {
            return create(cls);
        }
        return new ConditionLogging(create(cls), condition);
    }
}
