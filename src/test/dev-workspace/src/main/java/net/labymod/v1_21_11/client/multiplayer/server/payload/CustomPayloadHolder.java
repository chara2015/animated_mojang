package net.labymod.v1_21_11.client.multiplayer.server.payload;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_11.client.multiplayer.server.LabyModCustomPacketPayload;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/multiplayer/server/payload/CustomPayloadHolder.class */
public final class CustomPayloadHolder {
    private static final Map<ResourceLocation, aao<?, ?>> CODECS = new HashMap();

    public static <B extends wx, T extends LabyModCustomPacketPayload> aao<B, T> findOrRegisterCodec(amo channelId) {
        return findOrRegisterCodec((ResourceLocation) channelId);
    }

    public static <B extends wx, T extends LabyModCustomPacketPayload> aao<B, T> findOrRegisterCodec(ResourceLocation resourceLocation) {
        if (!Laby.references().payloadRegistry().canProcessPayload(resourceLocation)) {
            return null;
        }
        aao<?, ?> aaoVar = CODECS.get(resourceLocation);
        if (aaoVar == null) {
            aao<?, ?> aaoVarA = acd.a((v0, v1) -> {
                v0.write(v1);
            }, buffer -> {
                return new LabyModCustomPacketPayload(resourceLocation, buffer);
            });
            CODECS.put(resourceLocation, aaoVarA);
            aaoVar = aaoVarA;
        }
        return (aao<B, T>) aaoVar;
    }
}
