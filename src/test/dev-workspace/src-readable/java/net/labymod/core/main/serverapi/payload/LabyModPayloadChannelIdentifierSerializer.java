package net.labymod.core.main.serverapi.payload;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.util.collection.TimestampedCache;
import net.labymod.core.util.collection.TimestampedValue;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/payload/LabyModPayloadChannelIdentifierSerializer.class */
public class LabyModPayloadChannelIdentifierSerializer {
    private final TimestampedCache<ResourceLocation, PayloadChannelIdentifier> deserialized = createCache();
    private final TimestampedCache<PayloadChannelIdentifier, ResourceLocation> serialized = createCache();

    private static <K, V> TimestampedCache<K, V> createCache() {
        return new TimestampedCache<>(16, 1800000L, 300000L);
    }

    public ResourceLocation serialize(PayloadChannelIdentifier payloadChannelIdentifier) {
        return this.serialized.computeIfAbsent(payloadChannelIdentifier, identifier -> {
            return new TimestampedValue(ResourceLocation.create(identifier.getNamespace(), identifier.getPath()));
        }).getValue();
    }

    public PayloadChannelIdentifier deserialize(ResourceLocation resourceLocation) {
        return this.deserialized.computeIfAbsent(resourceLocation, location -> {
            return new TimestampedValue(PayloadChannelIdentifier.create(resourceLocation.getNamespace(), resourceLocation.getPath()));
        }).getValue();
    }
}
