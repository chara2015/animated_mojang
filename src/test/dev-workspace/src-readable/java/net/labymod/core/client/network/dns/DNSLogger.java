package net.labymod.core.client.network.dns;

import net.labymod.api.util.logging.Logging;
import org.xbill.DNS.logger.Logger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/dns/DNSLogger.class */
public class DNSLogger implements Logger {
    private final Logging logging;

    public DNSLogger(String name) {
        this.logging = Logging.create(name);
    }

    public void info(String s, Object... objects) {
        this.logging.info(s, objects);
    }

    public void warn(String s, Object... objects) {
        this.logging.warn(s, objects);
    }

    public void error(String s, Object... objects) {
        this.logging.error(s, objects);
    }

    public void debug(String s, Object... objects) {
        this.logging.debug(s, objects);
    }

    public void trace(String s, Object... objects) {
    }
}
