package net.minecraft.core;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/WritableRegistry.class */
public interface WritableRegistry<T> extends Registry<T> {
    Holder.Reference<T> register(ResourceKey<T> resourceKey, T t, RegistrationInfo registrationInfo);

    void bindTag(TagKey<T> tagKey, List<Holder<T>> list);

    boolean isEmpty();

    HolderGetter<T> createRegistrationLookup();
}
