package net.labymod.v1_20_6.client.multiplayer.server.payload;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_20_6.client.multiplayer.server.LabyModCustomPacketPayload;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/multiplayer/server/payload/CustomPayloadHolder.class */
public final class CustomPayloadHolder {
    private static final Map<ResourceLocation, zn<?, ?>> CODECS = new HashMap();

    public static <B extends wm, T extends LabyModCustomPacketPayload> zn<B, T> findOrRegisterCodec(alf channelId) {
        return findOrRegisterCodec((ResourceLocation) channelId);
    }

    public static <B extends wm, T extends LabyModCustomPacketPayload> zn<B, T> findOrRegisterCodec(ResourceLocation resourceLocation) {
        if (!Laby.references().payloadRegistry().canProcessPayload(resourceLocation)) {
            return null;
        }
        zn<?, ?> znVar = CODECS.get(resourceLocation);
        if (znVar == null) {
            zn<?, ?> znVarA = aax.a((v0, v1) -> {
                v0.write(v1);
            }, buffer -> {
                return new LabyModCustomPacketPayload(resourceLocation, buffer);
            });
            CODECS.put(resourceLocation, znVarA);
            znVar = znVarA;
        }
        return (zn<B, T>) znVar;
    }
}
