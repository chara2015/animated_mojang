package net.minecraft.core;

import com.mojang.serialization.Codec;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/RegistryCodecs.class */
public class RegistryCodecs {
    public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1) {
        return homogeneousList($$0, $$1, false);
    }

    public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1, boolean $$2) {
        return HolderSetCodec.create($$0, RegistryFileCodec.create($$0, $$1), $$2);
    }

    public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0) {
        return homogeneousList((ResourceKey) $$0, false);
    }

    public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0, boolean $$1) {
        return HolderSetCodec.create($$0, RegistryFixedCodec.create($$0), $$1);
    }
}
