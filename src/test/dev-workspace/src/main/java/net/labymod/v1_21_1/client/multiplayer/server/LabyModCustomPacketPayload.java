package net.labymod.v1_21_1.client.multiplayer.server;

import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/multiplayer/server/LabyModCustomPacketPayload.class */
public class LabyModCustomPacketPayload implements aaj {
    private final b<? extends aaj> type;
    private byte[] buffer;

    public LabyModCustomPacketPayload(ResourceLocation id, vw buffer) {
        this(id, (byte[]) null);
        read(buffer);
    }

    public LabyModCustomPacketPayload(ResourceLocation id, byte[] buffer) {
        this.type = new b<>((akr) id.getMinecraftLocation());
        this.buffer = buffer;
    }

    public void read(vw buffer) {
        int readable = buffer.readableBytes();
        if (readable > 1048576) {
            throw new RuntimeException("LabyMod custom payload exceeds allowed size: " + readable);
        }
        this.buffer = new byte[readable];
        buffer.b(this.buffer);
    }

    public void write(vw buffer) {
        buffer.c(this.buffer);
    }

    @NotNull
    public b<? extends aaj> a() {
        return this.type;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }
}
