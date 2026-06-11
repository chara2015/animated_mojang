package net.labymod.v1_21.client.multiplayer.server.payload;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21.client.multiplayer.server.LabyModCustomPacketPayload;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/multiplayer/server/payload/CustomPayloadHolder.class */
public final class CustomPayloadHolder {
    private static final Map<ResourceLocation, yx<?, ?>> CODECS = new HashMap();

    public static <B extends vw, T extends LabyModCustomPacketPayload> yx<B, T> findOrRegisterCodec(akr channelId) {
        return findOrRegisterCodec((ResourceLocation) channelId);
    }

    public static <B extends vw, T extends LabyModCustomPacketPayload> yx<B, T> findOrRegisterCodec(ResourceLocation resourceLocation) {
        if (!Laby.references().payloadRegistry().canProcessPayload(resourceLocation)) {
            return null;
        }
        yx<?, ?> yxVar = CODECS.get(resourceLocation);
        if (yxVar == null) {
            yx<?, ?> yxVarA = aaj.a((v0, v1) -> {
                v0.write(v1);
            }, buffer -> {
                return new LabyModCustomPacketPayload(resourceLocation, buffer);
            });
            CODECS.put(resourceLocation, yxVarA);
            yxVar = yxVarA;
        }
        return (yx<B, T>) yxVar;
    }
}
