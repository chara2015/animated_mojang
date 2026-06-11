package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/StringRepresentable.class */
public interface StringRepresentable {
    public static final int PRE_BUILT_MAP_THRESHOLD = 16;

    String getSerializedName();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/StringRepresentable$StringRepresentableCodec.class */
    public static class StringRepresentableCodec<S extends StringRepresentable> implements Codec<S> {
        private final Codec<S> codec;

        public /* synthetic */ DataResult encode(Object obj, DynamicOps dynamicOps, Object obj2) {
            return encode((StringRepresentable) obj, (DynamicOps<Object>) dynamicOps, obj2);
        }

        public StringRepresentableCodec(S[] $$0, Function<String, S> $$1, ToIntFunction<S> $$2) {
            this.codec = ExtraCodecs.orCompressed(Codec.stringResolver((v0) -> {
                return v0.getSerializedName();
            }, $$1), ExtraCodecs.idResolverCodec($$2, $$12 -> {
                if ($$12 < 0 || $$12 >= $$0.length) {
                    return null;
                }
                return $$0[$$12];
            }, -1));
        }

        public <T> DataResult<Pair<S, T>> decode(DynamicOps<T> $$0, T $$1) {
            return this.codec.decode($$0, $$1);
        }

        public <T> DataResult<T> encode(S $$0, DynamicOps<T> $$1, T $$2) {
            return this.codec.encode($$0, $$1, $$2);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/StringRepresentable$EnumCodec.class */
    public static class EnumCodec<E extends Enum<E> & StringRepresentable> extends StringRepresentableCodec<E> {
        private final Function<String, E> resolver;

        /* JADX WARN: Incorrect types in method signature: ([TE;Ljava/util/function/Function<Ljava/lang/String;TE;>;)V */
        /* JADX WARN: Multi-variable type inference failed */
        public EnumCodec(Enum[] enumArr, Function function) {
            super(enumArr, function, $$0 -> {
                return ((Enum) $$0).ordinal();
            });
            this.resolver = function;
        }

        /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/String;)TE; */
        public Enum byName(String str) {
            return (Enum) this.resolver.apply(str);
        }

        /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/String;TE;)TE; */
        public Enum byName(String $$0, Enum r5) {
            return (Enum) Objects.requireNonNullElse(byName($$0), r5);
        }

        /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/String;Ljava/util/function/Supplier<+TE;>;)TE; */
        public Enum byName(String $$0, Supplier supplier) {
            return (Enum) Objects.requireNonNullElseGet(byName($$0), supplier);
        }
    }

    static <E extends Enum<E> & StringRepresentable> EnumCodec<E> fromEnum(Supplier<E[]> $$0) {
        return fromEnumWithMapping($$0, $$02 -> {
            return $$02;
        });
    }

    static <E extends Enum<E> & StringRepresentable> EnumCodec<E> fromEnumWithMapping(Supplier<E[]> supplier, Function<String, String> function) {
        Enum[] enumArr = (Enum[]) supplier.get();
        return new EnumCodec<>(enumArr, createNameLookup(enumArr, r4 -> {
            return (String) function.apply(((StringRepresentable) r4).getSerializedName());
        }));
    }

    static <T extends StringRepresentable> Codec<T> fromValues(Supplier<T[]> $$0) {
        T[] $$1 = $$0.get();
        Function<String, T> $$2 = createNameLookup($$1);
        ToIntFunction<T> $$3 = Util.createIndexLookup(Arrays.asList($$1));
        return new StringRepresentableCodec($$1, $$2, $$3);
    }

    static <T extends StringRepresentable> Function<String, T> createNameLookup(T[] $$0) {
        return createNameLookup($$0, (v0) -> {
            return v0.getSerializedName();
        });
    }

    static <T> Function<String, T> createNameLookup(T[] $$0, Function<T, String> $$1) {
        if ($$0.length > 16) {
            Map<String, T> $$2 = (Map) Arrays.stream($$0).collect(Collectors.toMap($$1, $$02 -> {
                return $$02;
            }));
            Objects.requireNonNull($$2);
            return (v1) -> {
                return r0.get(v1);
            };
        }
        return $$22 -> {
            for (Object obj : $$0) {
                if (((String) $$1.apply(obj)).equals($$22)) {
                    return obj;
                }
            }
            return null;
        };
    }

    static Keyable keys(final StringRepresentable[] $$0) {
        return new Keyable() { // from class: net.minecraft.util.StringRepresentable.1
            public <T> Stream<T> keys(DynamicOps<T> $$02) {
                Stream map = Arrays.stream($$0).map((v0) -> {
                    return v0.getSerializedName();
                });
                Objects.requireNonNull($$02);
                return map.map($$02::createString);
            }
        };
    }
}
