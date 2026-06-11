package net.minecraft.core.component.predicates;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/predicates/DataComponentPredicate.class */
public interface DataComponentPredicate {
    public static final Codec<Map<Type<?>, DataComponentPredicate>> CODEC = Codec.dispatchedMap(Type.CODEC, (v0) -> {
        return v0.codec();
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, Single<?>> SINGLE_STREAM_CODEC = Type.STREAM_CODEC.dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.singleStreamCodec();
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, Map<Type<?>, DataComponentPredicate>> STREAM_CODEC = SINGLE_STREAM_CODEC.apply(ByteBufCodecs.list(64)).map($$0 -> {
        return (Map) $$0.stream().collect(Collectors.toMap((v0) -> {
            return v0.type();
        }, (v0) -> {
            return v0.predicate();
        }));
    }, $$02 -> {
        return $$02.entrySet().stream().map(Single::fromEntry).toList();
    });

    boolean matches(DataComponentGetter dataComponentGetter);

    static MapCodec<Single<?>> singleCodec(String $$0) {
        return Type.CODEC.dispatchMap($$0, (v0) -> {
            return v0.type();
        }, (v0) -> {
            return v0.wrappedCodec();
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/predicates/DataComponentPredicate$Type.class */
    public interface Type<T extends DataComponentPredicate> {
        public static final Codec<Type<?>> CODEC = Codec.either(BuiltInRegistries.DATA_COMPONENT_PREDICATE_TYPE.byNameCodec(), BuiltInRegistries.DATA_COMPONENT_TYPE.byNameCodec()).xmap(Type::copyOrCreateType, Type::unpackType);
        public static final StreamCodec<RegistryFriendlyByteBuf, Type<?>> STREAM_CODEC = ByteBufCodecs.either(ByteBufCodecs.registry(Registries.DATA_COMPONENT_PREDICATE_TYPE), ByteBufCodecs.registry(Registries.DATA_COMPONENT_TYPE)).map(Type::copyOrCreateType, Type::unpackType);

        Codec<T> codec();

        MapCodec<Single<T>> wrappedCodec();

        StreamCodec<RegistryFriendlyByteBuf, Single<T>> singleStreamCodec();

        /* JADX WARN: Incorrect types in method signature: <T::Lnet/minecraft/core/component/predicates/DataComponentPredicate$Type<*>;>(TT;)Lcom/mojang/datafixers/util/Either<TT;Lnet/minecraft/core/component/DataComponentType<*>;>; */
        private static Either unpackType(Type type) {
            if (!(type instanceof AnyValueType)) {
                return Either.left(type);
            }
            AnyValueType $$1 = (AnyValueType) type;
            return Either.right($$1.componentType());
        }

        private static Type<?> copyOrCreateType(Either<Type<?>, DataComponentType<?>> $$0) {
            return (Type) $$0.map($$02 -> {
                return $$02;
            }, AnyValueType::create);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/predicates/DataComponentPredicate$TypeBase.class */
    public static abstract class TypeBase<T extends DataComponentPredicate> implements Type<T> {
        private final Codec<T> codec;
        private final MapCodec<Single<T>> wrappedCodec;
        private final StreamCodec<RegistryFriendlyByteBuf, Single<T>> singleStreamCodec;

        public TypeBase(Codec<T> $$0) {
            this.codec = $$0;
            this.wrappedCodec = Single.wrapCodec(this, $$0);
            this.singleStreamCodec = ByteBufCodecs.fromCodecWithRegistries($$0).map($$02 -> {
                return new Single(this, $$02);
            }, (v0) -> {
                return v0.predicate();
            });
        }

        @Override // net.minecraft.core.component.predicates.DataComponentPredicate.Type
        public Codec<T> codec() {
            return this.codec;
        }

        @Override // net.minecraft.core.component.predicates.DataComponentPredicate.Type
        public MapCodec<Single<T>> wrappedCodec() {
            return this.wrappedCodec;
        }

        @Override // net.minecraft.core.component.predicates.DataComponentPredicate.Type
        public StreamCodec<RegistryFriendlyByteBuf, Single<T>> singleStreamCodec() {
            return this.singleStreamCodec;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/predicates/DataComponentPredicate$ConcreteType.class */
    public static final class ConcreteType<T extends DataComponentPredicate> extends TypeBase<T> {
        public ConcreteType(Codec<T> $$0) {
            super($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/predicates/DataComponentPredicate$AnyValueType.class */
    public static final class AnyValueType extends TypeBase<AnyValue> {
        private final AnyValue predicate;

        public AnyValueType(AnyValue $$0) {
            super(MapCodec.unitCodec($$0));
            this.predicate = $$0;
        }

        public AnyValue predicate() {
            return this.predicate;
        }

        public DataComponentType<?> componentType() {
            return this.predicate.type();
        }

        public static AnyValueType create(DataComponentType<?> $$0) {
            return new AnyValueType(new AnyValue($$0));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/predicates/DataComponentPredicate$Single.class */
    public static final class Single<T extends DataComponentPredicate> extends Record {
        private final Type<T> type;
        private final T predicate;

        public Single(Type<T> $$0, T $$1) {
            this.type = $$0;
            this.predicate = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Single.class), Single.class, "type;predicate", "FIELD:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Single;->type:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Type;", "FIELD:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Single;->predicate:Lnet/minecraft/core/component/predicates/DataComponentPredicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Single.class), Single.class, "type;predicate", "FIELD:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Single;->type:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Type;", "FIELD:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Single;->predicate:Lnet/minecraft/core/component/predicates/DataComponentPredicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Single.class, Object.class), Single.class, "type;predicate", "FIELD:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Single;->type:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Type;", "FIELD:Lnet/minecraft/core/component/predicates/DataComponentPredicate$Single;->predicate:Lnet/minecraft/core/component/predicates/DataComponentPredicate;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Type<T> type() {
            return this.type;
        }

        public T predicate() {
            return this.predicate;
        }

        static <T extends DataComponentPredicate> MapCodec<Single<T>> wrapCodec(Type<T> $$0, Codec<T> $$1) {
            return RecordCodecBuilder.mapCodec($$2 -> {
                return $$2.group($$1.fieldOf("value").forGetter((v0) -> {
                    return v0.predicate();
                })).apply($$2, $$12 -> {
                    return new Single($$0, $$12);
                });
            });
        }

        private static <T extends DataComponentPredicate> Single<T> fromEntry(Map.Entry<Type<?>, T> $$0) {
            return new Single<>($$0.getKey(), $$0.getValue());
        }
    }
}
