package net.labymod.v1_20_4.client.multiplayer.server.custompayload;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/multiplayer/server/custompayload/LabyModCustomPacketPayload.class */
public class LabyModCustomPacketPayload implements ya {
    private final ResourceLocation id;
    private final byte[] payload;

    private LabyModCustomPacketPayload(ResourceLocation id, byte[] payload) {
        this.id = id;
        this.payload = payload;
    }

    public static LabyModCustomPacketPayload of(ResourceLocation id, byte[] payload) {
        return new LabyModCustomPacketPayload(id, payload);
    }

    public void a(ui buffer) {
        buffer.c(this.payload);
    }

    public ahg a() {
        return this.id;
    }
}
