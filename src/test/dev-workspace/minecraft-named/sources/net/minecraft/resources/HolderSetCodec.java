package net.minecraft.resources;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/HolderSetCodec.class */
public class HolderSetCodec<E> implements Codec<HolderSet<E>> {
    private final ResourceKey<? extends Registry<E>> registryKey;
    private final Codec<Holder<E>> elementCodec;
    private final Codec<List<Holder<E>>> homogenousListCodec;
    private final Codec<Either<TagKey<E>, List<Holder<E>>>> registryAwareCodec;

    public /* synthetic */ DataResult encode(Object obj, DynamicOps dynamicOps, Object obj2) {
        return encode((HolderSet) obj, (DynamicOps<Object>) dynamicOps, obj2);
    }

    private static <E> Codec<List<Holder<E>>> homogenousList(Codec<Holder<E>> $$0, boolean $$1) {
        Codec<List<Holder<E>>> $$2 = $$0.listOf().validate(ExtraCodecs.ensureHomogenous((v0) -> {
            return v0.kind();
        }));
        if ($$1) {
            return $$2;
        }
        return ExtraCodecs.compactListCodec($$0, $$2);
    }

    public static <E> Codec<HolderSet<E>> create(ResourceKey<? extends Registry<E>> $$0, Codec<Holder<E>> $$1, boolean $$2) {
        return new HolderSetCodec($$0, $$1, $$2);
    }

    private HolderSetCodec(ResourceKey<? extends Registry<E>> $$0, Codec<Holder<E>> $$1, boolean $$2) {
        this.registryKey = $$0;
        this.elementCodec = $$1;
        this.homogenousListCodec = homogenousList($$1, $$2);
        this.registryAwareCodec = Codec.either(TagKey.hashedCodec($$0), this.homogenousListCodec);
    }

    public <T> DataResult<Pair<HolderSet<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
        if ($$0 instanceof RegistryOps) {
            RegistryOps<T> $$2 = (RegistryOps) $$0;
            Optional<HolderGetter<E>> $$3 = $$2.getter(this.registryKey);
            if ($$3.isPresent()) {
                HolderGetter<E> $$4 = $$3.get();
                return this.registryAwareCodec.decode($$0, $$1).flatMap($$12 -> {
                    DataResult<HolderSet<E>> $$22 = (DataResult) ((Either) $$12.getFirst()).map($$12 -> {
                        return lookupTag($$4, $$12);
                    }, $$02 -> {
                        return DataResult.success(HolderSet.direct($$02));
                    });
                    return $$22.map($$13 -> {
                        return Pair.of($$13, $$12.getSecond());
                    });
                });
            }
        }
        return decodeWithoutRegistry($$0, $$1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> DataResult<HolderSet<E>> lookupTag(HolderGetter<E> $$0, TagKey<E> $$1) {
        return (DataResult) $$0.get($$1).map((v0) -> {
            return DataResult.success(v0);
        }).orElseGet(() -> {
            return DataResult.error(() -> {
                return "Missing tag: '" + String.valueOf($$1.location()) + "' in '" + String.valueOf($$1.registry().identifier()) + "'";
            });
        });
    }

    public <T> DataResult<T> encode(HolderSet<E> $$0, DynamicOps<T> $$1, T $$2) {
        if ($$1 instanceof RegistryOps) {
            RegistryOps<T> $$3 = (RegistryOps) $$1;
            Optional<HolderOwner<E>> $$4 = $$3.owner(this.registryKey);
            if ($$4.isPresent()) {
                if (!$$0.canSerializeIn($$4.get())) {
                    return DataResult.error(() -> {
                        return "HolderSet " + String.valueOf($$0) + " is not valid in current registry set";
                    });
                }
                return this.registryAwareCodec.encode($$0.unwrap().mapRight((v0) -> {
                    return List.copyOf(v0);
                }), $$1, $$2);
            }
        }
        return encodeWithoutRegistry($$0, $$1, $$2);
    }

    private <T> DataResult<Pair<HolderSet<E>, T>> decodeWithoutRegistry(DynamicOps<T> $$0, T $$1) {
        return this.elementCodec.listOf().decode($$0, $$1).flatMap($$02 -> {
            List<Holder.Direct<E>> $$12 = new ArrayList<>();
            for (Holder<E> $$2 : (List) $$02.getFirst()) {
                if ($$2 instanceof Holder.Direct) {
                    Holder.Direct<E> $$3 = (Holder.Direct) $$2;
                    $$12.add($$3);
                } else {
                    return DataResult.error(() -> {
                        return "Can't decode element " + String.valueOf($$2) + " without registry";
                    });
                }
            }
            return DataResult.success(new Pair(HolderSet.direct($$12), $$02.getSecond()));
        });
    }

    private <T> DataResult<T> encodeWithoutRegistry(HolderSet<E> $$0, DynamicOps<T> $$1, T $$2) {
        return this.homogenousListCodec.encode($$0.stream().toList(), $$1, $$2);
    }
}
