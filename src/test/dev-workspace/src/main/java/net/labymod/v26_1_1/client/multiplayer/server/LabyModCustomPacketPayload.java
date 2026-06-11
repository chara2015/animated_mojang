package net.labymod.v26_1_1.client.multiplayer.server;

import net.labymod.api.client.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/multiplayer/server/LabyModCustomPacketPayload.class */
public class LabyModCustomPacketPayload implements CustomPacketPayload {
    private final CustomPacketPayload.Type<? extends CustomPacketPayload> type;
    private byte[] buffer;

    public LabyModCustomPacketPayload(ResourceLocation id, FriendlyByteBuf buffer) {
        this(id, (byte[]) null);
        read(buffer);
    }

    public LabyModCustomPacketPayload(ResourceLocation id, byte[] buffer) {
        this.type = new CustomPacketPayload.Type<>((Identifier) id.getMinecraftLocation());
        this.buffer = buffer;
    }

    public void read(FriendlyByteBuf buffer) {
        int readable = buffer.readableBytes();
        if (readable > 1048576) {
            throw new RuntimeException("LabyMod custom payload exceeds allowed size: " + readable);
        }
        this.buffer = new byte[readable];
        buffer.readBytes(this.buffer);
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBytes(this.buffer);
    }

    @NotNull
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return this.type;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }
}
