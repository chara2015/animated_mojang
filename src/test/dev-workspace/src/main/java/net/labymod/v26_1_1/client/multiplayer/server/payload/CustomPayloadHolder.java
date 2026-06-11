package net.labymod.v26_1_1.client.multiplayer.server.payload;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v26_1_1.client.multiplayer.server.LabyModCustomPacketPayload;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/multiplayer/server/payload/CustomPayloadHolder.class */
public final class CustomPayloadHolder {
    private static final Map<ResourceLocation, StreamCodec<?, ?>> CODECS = new HashMap();

    public static <B extends FriendlyByteBuf, T extends LabyModCustomPacketPayload> StreamCodec<B, T> findOrRegisterCodec(Identifier channelId) {
        return findOrRegisterCodec((ResourceLocation) channelId);
    }

    public static <B extends FriendlyByteBuf, T extends LabyModCustomPacketPayload> StreamCodec<B, T> findOrRegisterCodec(ResourceLocation resourceLocation) {
        if (!Laby.references().payloadRegistry().canProcessPayload(resourceLocation)) {
            return null;
        }
        StreamCodec<?, ?> streamCodec = CODECS.get(resourceLocation);
        if (streamCodec == null) {
            StreamCodec<?, ?> streamCodecCodec = CustomPacketPayload.codec((v0, v1) -> {
                v0.write(v1);
            }, buffer -> {
                return new LabyModCustomPacketPayload(resourceLocation, buffer);
            });
            CODECS.put(resourceLocation, streamCodecCodec);
            streamCodec = streamCodecCodec;
        }
        return (StreamCodec<B, T>) streamCodec;
    }
}
