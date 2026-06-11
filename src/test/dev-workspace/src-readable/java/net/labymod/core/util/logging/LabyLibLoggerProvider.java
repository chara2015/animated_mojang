package net.labymod.core.util.logging;

import net.laby.lib.core.log.Logger;
import net.laby.lib.core.log.LoggerProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/logging/LabyLibLoggerProvider.class */
public final class LabyLibLoggerProvider implements LoggerProvider {
    public Logger get(String name) {
        return new LabyLibLoggerBridge(name, DefaultLoggingFactory.createLogger(name));
    }
}
