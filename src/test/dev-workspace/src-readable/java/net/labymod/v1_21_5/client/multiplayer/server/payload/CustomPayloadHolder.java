package net.labymod.v1_21_5.client.multiplayer.server.payload;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_5.client.multiplayer.server.LabyModCustomPacketPayload;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/multiplayer/server/payload/CustomPayloadHolder.class */
public final class CustomPayloadHolder {
    private static final Map<ResourceLocation, ze<?, ?>> CODECS = new HashMap();

    public static <B extends vy, T extends LabyModCustomPacketPayload> ze<B, T> findOrRegisterCodec(alr channelId) {
        return findOrRegisterCodec((ResourceLocation) channelId);
    }

    public static <B extends vy, T extends LabyModCustomPacketPayload> ze<B, T> findOrRegisterCodec(ResourceLocation resourceLocation) {
        if (!Laby.references().payloadRegistry().canProcessPayload(resourceLocation)) {
            return null;
        }
        ze<?, ?> zeVar = CODECS.get(resourceLocation);
        if (zeVar == null) {
            ze<?, ?> zeVarA = aat.a((v0, v1) -> {
                v0.write(v1);
            }, buffer -> {
                return new LabyModCustomPacketPayload(resourceLocation, buffer);
            });
            CODECS.put(resourceLocation, zeVarA);
            zeVar = zeVarA;
        }
        return (ze<B, T>) zeVar;
    }
}
