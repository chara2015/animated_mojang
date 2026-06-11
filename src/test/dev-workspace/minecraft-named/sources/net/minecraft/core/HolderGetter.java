package net.minecraft.core;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/HolderGetter.class */
public interface HolderGetter<T> {
    Optional<Holder.Reference<T>> get(ResourceKey<T> resourceKey);

    Optional<HolderSet.Named<T>> get(TagKey<T> tagKey);

    default Holder.Reference<T> getOrThrow(ResourceKey<T> $$0) {
        return get($$0).orElseThrow(() -> {
            return new IllegalStateException("Missing element " + String.valueOf($$0));
        });
    }

    default HolderSet.Named<T> getOrThrow(TagKey<T> $$0) {
        return get($$0).orElseThrow(() -> {
            return new IllegalStateException("Missing tag " + String.valueOf($$0));
        });
    }

    default Optional<Holder<T>> getRandomElementOf(TagKey<T> tagKey, RandomSource randomSource) {
        return (Optional<Holder<T>>) get(tagKey).flatMap($$1 -> {
            return $$1.getRandomElement(randomSource);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/HolderGetter$Provider.class */
    public interface Provider {
        <T> Optional<? extends HolderGetter<T>> lookup(ResourceKey<? extends Registry<? extends T>> resourceKey);

        default <T> HolderGetter<T> lookupOrThrow(ResourceKey<? extends Registry<? extends T>> $$0) {
            return lookup($$0).orElseThrow(() -> {
                return new IllegalStateException("Registry " + String.valueOf($$0.identifier()) + " not found");
            });
        }

        default <T> Optional<Holder.Reference<T>> get(ResourceKey<T> resourceKey) {
            return (Optional<Holder.Reference<T>>) lookup(resourceKey.registryKey()).flatMap($$1 -> {
                return $$1.get(resourceKey);
            });
        }

        default <T> Holder.Reference<T> getOrThrow(ResourceKey<T> $$0) {
            return (Holder.Reference) lookup($$0.registryKey()).flatMap($$1 -> {
                return $$1.get($$0);
            }).orElseThrow(() -> {
                return new IllegalStateException("Missing element " + String.valueOf($$0));
            });
        }
    }
}
