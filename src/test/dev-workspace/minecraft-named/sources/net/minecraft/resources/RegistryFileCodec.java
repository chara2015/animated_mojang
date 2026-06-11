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

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/RegistryFileCodec.class */
public final class RegistryFileCodec<E> implements Codec<Holder<E>> {
    private final ResourceKey<? extends Registry<E>> registryKey;
    private final Codec<E> elementCodec;
    private final boolean allowInline;

    public /* synthetic */ DataResult encode(Object obj, DynamicOps dynamicOps, Object obj2) {
        return encode((Holder) obj, (DynamicOps<Object>) dynamicOps, obj2);
    }

    public static <E> RegistryFileCodec<E> create(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1) {
        return create($$0, $$1, true);
    }

    public static <E> RegistryFileCodec<E> create(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1, boolean $$2) {
        return new RegistryFileCodec<>($$0, $$1, $$2);
    }

    private RegistryFileCodec(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1, boolean $$2) {
        this.registryKey = $$0;
        this.elementCodec = $$1;
        this.allowInline = $$2;
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
                }, $$23 -> {
                    return this.elementCodec.encode($$23, $$1, $$2);
                });
            }
        }
        return this.elementCodec.encode($$0.value(), $$1, $$2);
    }

    public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
        if ($$0 instanceof RegistryOps) {
            RegistryOps<?> $$2 = (RegistryOps) $$0;
            Optional<HolderGetter<E>> $$3 = $$2.getter(this.registryKey);
            if ($$3.isEmpty()) {
                return DataResult.error(() -> {
                    return "Registry does not exist: " + String.valueOf(this.registryKey);
                });
            }
            HolderGetter<E> $$4 = $$3.get();
            DataResult<Pair<Identifier, T>> $$5 = Identifier.CODEC.decode($$0, $$1);
            if ($$5.result().isEmpty()) {
                if (!this.allowInline) {
                    return DataResult.error(() -> {
                        return "Inline definitions not allowed here";
                    });
                }
                return this.elementCodec.decode($$0, $$1).map($$02 -> {
                    return $$02.mapFirst(Holder::direct);
                });
            }
            Pair<Identifier, T> $$6 = (Pair) $$5.result().get();
            ResourceKey<T> resourceKeyCreate = ResourceKey.create(this.registryKey, (Identifier) $$6.getFirst());
            return ((DataResult) $$4.get((ResourceKey<E>) resourceKeyCreate).map((v0) -> {
                return DataResult.success(v0);
            }).orElseGet(() -> {
                return DataResult.error(() -> {
                    return "Failed to get element " + String.valueOf(resourceKeyCreate);
                });
            })).map($$12 -> {
                return Pair.of($$12, $$6.getSecond());
            }).setLifecycle(Lifecycle.stable());
        }
        return this.elementCodec.decode($$0, $$1).map($$03 -> {
            return $$03.mapFirst(Holder::direct);
        });
    }

    public String toString() {
        return "RegistryFileCodec[" + String.valueOf(this.registryKey) + " " + String.valueOf(this.elementCodec) + "]";
    }
}
