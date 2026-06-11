package net.minecraft.data.worldgen;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/worldgen/BootstrapContext.class */
public interface BootstrapContext<T> {
    Holder.Reference<T> register(ResourceKey<T> resourceKey, T t, Lifecycle lifecycle);

    <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> resourceKey);

    default Holder.Reference<T> register(ResourceKey<T> $$0, T $$1) {
        return register($$0, $$1, Lifecycle.stable());
    }
}
