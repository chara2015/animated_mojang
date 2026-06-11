package net.labymod.serverapi.protocol.api;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/api/ProtocolPlatformLogger.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public interface ProtocolPlatformLogger {
    void info(String str, Object... objArr);

    void info(String str, Throwable th);

    void warn(String str, Object... objArr);

    void warn(String str, Throwable th);

    void error(String str, Object... objArr);

    void error(String str, Throwable th);
}
