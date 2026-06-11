package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/RegistryFixedCodec.class */
public final class RegistryFixedCodec<E> implements Codec<Holder<E>> {
    private final ResourceKey<? extends Registry<E>> registryKey;

    public /* synthetic */ DataResult encode(Object obj, DynamicOps dynamicOps, Object obj2) {
        return encode((Holder) obj, (DynamicOps<Object>) dynamicOps, obj2);
    }

    public static <E> RegistryFixedCodec<E> create(ResourceKey<? extends Registry<E>> $$0) {
        return new RegistryFixedCodec<>($$0);
    }

    private RegistryFixedCodec(ResourceKey<? extends Registry<E>> $$0) {
        this.registryKey = $$0;
    }

    public <T> DataResult<T> encode(Holder<E> $$0, DynamicOps<T> $$1, T $$2) {
        if ($$1 instanceof RegistryOps) {
            RegistryOps<?> $$3 = (RegistryOps) $$1;
            Optional<HolderOwner<E>> $$4 = $$3.owner(this.registryKey);
            if ($$4.isPresent()) {
                if (!$$0.canSerializeIn($$4.get())) {
                    return DataResult.error(() -> {
                        return "Element " + String.valueOf($$0) + " is not valid in current registry set";
                    });
                }
                return (DataResult) $$0.unwrap().map($$22 -> {
                    return Identifier.CODEC.encode($$22.identifier(), $$1, $$2);
                }, $$02 -> {
                    return DataResult.error(() -> {
                        return "Elements from registry " + String.valueOf(this.registryKey) + " can't be serialized to a value";
                    });
                });
            }
        }
        return DataResult.error(() -> {
            return "Can't access registry " + String.valueOf(this.registryKey);
        });
    }

    public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
        if ($$0 instanceof RegistryOps) {
            RegistryOps<?> $$2 = (RegistryOps) $$0;
            Optional<HolderGetter<E>> $$3 = $$2.getter(this.registryKey);
            if ($$3.isPresent()) {
                return Identifier.CODEC.decode($$0, $$1).flatMap($$12 -> {
                    Identifier $$22 = (Identifier) $$12.getFirst();
                    return ((DataResult) ((HolderGetter) $$3.get()).get(ResourceKey.create(this.registryKey, $$22)).map((v0) -> {
                        return DataResult.success(v0);
                    }).orElseGet(() -> {
                        return DataResult.error(() -> {
                            return "Failed to get element " + String.valueOf($$22);
                        });
                    })).map($$12 -> {
                        return Pair.of($$12, $$12.getSecond());
                    }).setLifecycle(Lifecycle.stable());
                });
            }
        }
        return DataResult.error(() -> {
            return "Can't access registry " + String.valueOf(this.registryKey);
        });
    }

    public String toString() {
        return "RegistryFixedCodec[" + String.valueOf(this.registryKey) + "]";
    }
}
