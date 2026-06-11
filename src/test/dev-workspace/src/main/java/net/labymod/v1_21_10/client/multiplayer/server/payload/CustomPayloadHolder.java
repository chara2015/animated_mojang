package net.labymod.v1_21_10.client.multiplayer.server.payload;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_10.client.multiplayer.server.LabyModCustomPacketPayload;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/multiplayer/server/payload/CustomPayloadHolder.class */
public final class CustomPayloadHolder {
    private static final Map<ResourceLocation, aae<?, ?>> CODECS = new HashMap();

    public static <B extends wn, T extends LabyModCustomPacketPayload> aae<B, T> findOrRegisterCodec(amj channelId) {
        return findOrRegisterCodec((ResourceLocation) channelId);
    }

    public static <B extends wn, T extends LabyModCustomPacketPayload> aae<B, T> findOrRegisterCodec(ResourceLocation resourceLocation) {
        if (!Laby.references().payloadRegistry().canProcessPayload(resourceLocation)) {
            return null;
        }
        aae<?, ?> aaeVar = CODECS.get(resourceLocation);
        if (aaeVar == null) {
            aae<?, ?> aaeVarA = abt.a((v0, v1) -> {
                v0.write(v1);
            }, buffer -> {
                return new LabyModCustomPacketPayload(resourceLocation, buffer);
            });
            CODECS.put(resourceLocation, aaeVarA);
            aaeVar = aaeVarA;
        }
        return (aae<B, T>) aaeVar;
    }
}
