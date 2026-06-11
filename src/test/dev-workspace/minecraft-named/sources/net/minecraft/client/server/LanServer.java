package net.minecraft.client.server;

import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/server/LanServer.class */
public class LanServer {
    private final String motd;
    private final String address;
    private long pingTime = Util.getMillis();

    public LanServer(String $$0, String $$1) {
        this.motd = $$0;
        this.address = $$1;
    }

    public String getMotd() {
        return this.motd;
    }

    public String getAddress() {
        return this.address;
    }

    public void updatePingTime() {
        this.pingTime = Util.getMillis();
    }
}
