package net.labymod.core.main.serverapi;

import net.labymod.api.util.logging.Logging;
import net.labymod.serverapi.api.logger.ProtocolPlatformLogger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/LabyModProtocolPlatformLogger.class */
public final class LabyModProtocolPlatformLogger implements ProtocolPlatformLogger, net.labymod.serverapi.protocol.api.ProtocolPlatformLogger {
    private static final Logging LOGGING = Logging.getLogger();

    @Override // net.labymod.serverapi.protocol.api.ProtocolPlatformLogger
    public void info(String message, Object... objects) {
        LOGGING.info(message, objects);
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolPlatformLogger
    public void info(String message, Throwable throwable) {
        LOGGING.info(message, throwable);
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolPlatformLogger
    public void warn(String message, Object... objects) {
        LOGGING.warn(message, objects);
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolPlatformLogger
    public void warn(String message, Throwable throwable) {
        LOGGING.warn(message, throwable);
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolPlatformLogger
    public void error(String message, Object... objects) {
        LOGGING.error(message, objects);
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolPlatformLogger
    public void error(String message, Throwable throwable) {
        LOGGING.error(message, throwable);
    }
}
