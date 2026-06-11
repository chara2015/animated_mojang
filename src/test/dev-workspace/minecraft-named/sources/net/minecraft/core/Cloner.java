package net.minecraft.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JavaOps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/Cloner.class */
public class Cloner<T> {
    private final Codec<T> directCodec;

    Cloner(Codec<T> $$0) {
        this.directCodec = $$0;
    }

    public T clone(T t, HolderLookup.Provider provider, HolderLookup.Provider provider2) {
        RegistryOps registryOpsCreateSerializationContext = provider.createSerializationContext(JavaOps.INSTANCE);
        return (T) this.directCodec.parse(provider2.createSerializationContext(JavaOps.INSTANCE), this.directCodec.encodeStart(registryOpsCreateSerializationContext, t).getOrThrow($$0 -> {
            return new IllegalStateException("Failed to encode: " + $$0);
        })).getOrThrow($$02 -> {
            return new IllegalStateException("Failed to decode: " + $$02);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/Cloner$Factory.class */
    public static class Factory {
        private final Map<ResourceKey<? extends Registry<?>>, Cloner<?>> codecs = new HashMap();

        public <T> Factory addCodec(ResourceKey<? extends Registry<? extends T>> $$0, Codec<T> $$1) {
            this.codecs.put($$0, new Cloner<>($$1));
            return this;
        }

        public <T> Cloner<T> cloner(ResourceKey<? extends Registry<? extends T>> $$0) {
            return (Cloner) this.codecs.get($$0);
        }
    }
}
