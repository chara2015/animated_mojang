package net.minecraft.core.component;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentPatch.class */
public final class DataComponentPatch {
    public static final DataComponentPatch EMPTY = new DataComponentPatch(Reference2ObjectMaps.emptyMap());
    public static final Codec<DataComponentPatch> CODEC = Codec.dispatchedMap(PatchKey.CODEC, (v0) -> {
        return v0.valueCodec();
    }).xmap($$0 -> {
        if ($$0.isEmpty()) {
            return EMPTY;
        }
        Reference2ObjectArrayMap reference2ObjectArrayMap = new Reference2ObjectArrayMap($$0.size());
        for (Map.Entry<PatchKey, ?> $$2 : $$0.entrySet()) {
            PatchKey $$3 = $$2.getKey();
            if ($$3.removed()) {
                reference2ObjectArrayMap.put($$3.type(), Optional.empty());
            } else {
                reference2ObjectArrayMap.put($$3.type(), Optional.of($$2.getValue()));
            }
        }
        return new DataComponentPatch(reference2ObjectArrayMap);
    }, $$02 -> {
        Reference2ObjectArrayMap reference2ObjectArrayMap = new Reference2ObjectArrayMap($$02.map.size());
        ObjectIterator it = Reference2ObjectMaps.fastIterable($$02.map).iterator();
        while (it.hasNext()) {
            Map.Entry<DataComponentType<?>, Optional<?>> $$2 = (Map.Entry) it.next();
            DataComponentType<?> $$3 = $$2.getKey();
            if (!$$3.isTransient()) {
                Optional<?> $$4 = $$2.getValue();
                if ($$4.isPresent()) {
                    reference2ObjectArrayMap.put(new PatchKey($$3, false), $$4.get());
                } else {
                    reference2ObjectArrayMap.put(new PatchKey($$3, true), Unit.INSTANCE);
                }
            }
        }
        return reference2ObjectArrayMap;
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, DataComponentPatch> STREAM_CODEC = createStreamCodec(new CodecGetter() { // from class: net.minecraft.core.component.DataComponentPatch.1
        @Override // net.minecraft.core.component.DataComponentPatch.CodecGetter
        public <T> StreamCodec<RegistryFriendlyByteBuf, T> apply(DataComponentType<T> dataComponentType) {
            return (StreamCodec<RegistryFriendlyByteBuf, T>) dataComponentType.streamCodec().cast();
        }
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, DataComponentPatch> DELIMITED_STREAM_CODEC = createStreamCodec(new CodecGetter() { // from class: net.minecraft.core.component.DataComponentPatch.2
        @Override // net.minecraft.core.component.DataComponentPatch.CodecGetter
        public <T> StreamCodec<RegistryFriendlyByteBuf, T> apply(DataComponentType<T> $$0) {
            return $$0.streamCodec().cast().apply(ByteBufCodecs.registryFriendlyLengthPrefixed(Integer.MAX_VALUE));
        }
    });
    private static final String REMOVED_PREFIX = "!";
    final Reference2ObjectMap<DataComponentType<?>, Optional<?>> map;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentPatch$CodecGetter.class */
    @FunctionalInterface
    interface CodecGetter {
        <T> StreamCodec<? super RegistryFriendlyByteBuf, T> apply(DataComponentType<T> dataComponentType);
    }

    private static StreamCodec<RegistryFriendlyByteBuf, DataComponentPatch> createStreamCodec(final CodecGetter $$0) {
        return new StreamCodec<RegistryFriendlyByteBuf, DataComponentPatch>() { // from class: net.minecraft.core.component.DataComponentPatch.3
            @Override // net.minecraft.network.codec.StreamDecoder
            public DataComponentPatch decode(RegistryFriendlyByteBuf $$02) {
                int $$1 = $$02.readVarInt();
                int $$2 = $$02.readVarInt();
                if ($$1 == 0 && $$2 == 0) {
                    return DataComponentPatch.EMPTY;
                }
                int $$3 = $$1 + $$2;
                Reference2ObjectArrayMap reference2ObjectArrayMap = new Reference2ObjectArrayMap(Math.min($$3, ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE));
                for (int $$5 = 0; $$5 < $$1; $$5++) {
                    DataComponentType<?> $$6 = DataComponentType.STREAM_CODEC.decode($$02);
                    Object $$7 = $$0.apply($$6).decode($$02);
                    reference2ObjectArrayMap.put($$6, Optional.of($$7));
                }
                for (int $$8 = 0; $$8 < $$2; $$8++) {
                    DataComponentType<?> $$9 = DataComponentType.STREAM_CODEC.decode($$02);
                    reference2ObjectArrayMap.put($$9, Optional.empty());
                }
                return new DataComponentPatch(reference2ObjectArrayMap);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(RegistryFriendlyByteBuf $$02, DataComponentPatch $$1) {
                if ($$1.isEmpty()) {
                    $$02.writeVarInt(0);
                    $$02.writeVarInt(0);
                    return;
                }
                int $$2 = 0;
                int $$3 = 0;
                ObjectIterator it = Reference2ObjectMaps.fastIterable($$1.map).iterator();
                while (it.hasNext()) {
                    Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> $$4 = (Reference2ObjectMap.Entry) it.next();
                    if (((Optional) $$4.getValue()).isPresent()) {
                        $$2++;
                    } else {
                        $$3++;
                    }
                }
                $$02.writeVarInt($$2);
                $$02.writeVarInt($$3);
                ObjectIterator it2 = Reference2ObjectMaps.fastIterable($$1.map).iterator();
                while (it2.hasNext()) {
                    Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> $$5 = (Reference2ObjectMap.Entry) it2.next();
                    Optional<?> $$6 = (Optional) $$5.getValue();
                    if ($$6.isPresent()) {
                        DataComponentType<?> $$7 = (DataComponentType) $$5.getKey();
                        DataComponentType.STREAM_CODEC.encode($$02, $$7);
                        encodeComponent($$02, $$7, $$6.get());
                    }
                }
                ObjectIterator it3 = Reference2ObjectMaps.fastIterable($$1.map).iterator();
                while (it3.hasNext()) {
                    Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> $$8 = (Reference2ObjectMap.Entry) it3.next();
                    if (((Optional) $$8.getValue()).isEmpty()) {
                        DataComponentType<?> $$9 = (DataComponentType) $$8.getKey();
                        DataComponentType.STREAM_CODEC.encode($$02, $$9);
                    }
                }
            }

            private <T> void encodeComponent(RegistryFriendlyByteBuf $$02, DataComponentType<T> $$1, Object $$2) {
                $$0.apply($$1).encode($$02, $$2);
            }
        };
    }

    DataComponentPatch(Reference2ObjectMap<DataComponentType<?>, Optional<?>> $$0) {
        this.map = $$0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> Optional<? extends T> get(DataComponentType<? extends T> $$0) {
        return (Optional) this.map.get($$0);
    }

    public Set<Map.Entry<DataComponentType<?>, Optional<?>>> entrySet() {
        return this.map.entrySet();
    }

    public int size() {
        return this.map.size();
    }

    public DataComponentPatch forget(Predicate<DataComponentType<?>> $$0) {
        if (isEmpty()) {
            return EMPTY;
        }
        Reference2ObjectArrayMap reference2ObjectArrayMap = new Reference2ObjectArrayMap(this.map);
        reference2ObjectArrayMap.keySet().removeIf($$0);
        if (reference2ObjectArrayMap.isEmpty()) {
            return EMPTY;
        }
        return new DataComponentPatch(reference2ObjectArrayMap);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentPatch$SplitResult.class */
    public static final class SplitResult extends Record {
        private final DataComponentMap added;
        private final Set<DataComponentType<?>> removed;
        public static final SplitResult EMPTY = new SplitResult(DataComponentMap.EMPTY, Set.of());

        public SplitResult(DataComponentMap $$0, Set<DataComponentType<?>> $$1) {
            this.added = $$0;
            this.removed = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SplitResult.class), SplitResult.class, "added;removed", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$SplitResult;->added:Lnet/minecraft/core/component/DataComponentMap;", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$SplitResult;->removed:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SplitResult.class), SplitResult.class, "added;removed", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$SplitResult;->added:Lnet/minecraft/core/component/DataComponentMap;", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$SplitResult;->removed:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SplitResult.class, Object.class), SplitResult.class, "added;removed", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$SplitResult;->added:Lnet/minecraft/core/component/DataComponentMap;", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$SplitResult;->removed:Ljava/util/Set;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public DataComponentMap added() {
            return this.added;
        }

        public Set<DataComponentType<?>> removed() {
            return this.removed;
        }
    }

    public SplitResult split() {
        if (isEmpty()) {
            return SplitResult.EMPTY;
        }
        DataComponentMap.Builder $$0 = DataComponentMap.builder();
        Set<DataComponentType<?>> $$1 = Sets.newIdentityHashSet();
        this.map.forEach(($$2, $$3) -> {
            if ($$3.isPresent()) {
                $$0.setUnchecked($$2, $$3.get());
            } else {
                $$1.add($$2);
            }
        });
        return new SplitResult($$0.build(), $$1);
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof DataComponentPatch) {
            DataComponentPatch $$1 = (DataComponentPatch) $$0;
            if (this.map.equals($$1.map)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        return toString(this.map);
    }

    static String toString(Reference2ObjectMap<DataComponentType<?>, Optional<?>> $$0) {
        StringBuilder $$1 = new StringBuilder();
        $$1.append('{');
        boolean $$2 = true;
        ObjectIterator it = Reference2ObjectMaps.fastIterable($$0).iterator();
        while (it.hasNext()) {
            Map.Entry<DataComponentType<?>, Optional<?>> $$3 = (Map.Entry) it.next();
            if ($$2) {
                $$2 = false;
            } else {
                $$1.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT);
            }
            Optional<?> $$4 = $$3.getValue();
            if ($$4.isPresent()) {
                $$1.append($$3.getKey());
                $$1.append("=>");
                $$1.append($$4.get());
            } else {
                $$1.append(REMOVED_PREFIX);
                $$1.append($$3.getKey());
            }
        }
        $$1.append('}');
        return $$1.toString();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentPatch$PatchKey.class */
    static final class PatchKey extends Record {
        private final DataComponentType<?> type;
        private final boolean removed;
        public static final Codec<PatchKey> CODEC = Codec.STRING.flatXmap($$0 -> {
            boolean $$1 = $$0.startsWith(DataComponentPatch.REMOVED_PREFIX);
            if ($$1) {
                $$0 = $$0.substring(DataComponentPatch.REMOVED_PREFIX.length());
            }
            Identifier $$2 = Identifier.tryParse($$0);
            DataComponentType<?> $$3 = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue($$2);
            if ($$3 == null) {
                return DataResult.error(() -> {
                    return "No component with type: '" + String.valueOf($$2) + "'";
                });
            }
            if ($$3.isTransient()) {
                return DataResult.error(() -> {
                    return "'" + String.valueOf($$2) + "' is not a persistent component";
                });
            }
            return DataResult.success(new PatchKey($$3, $$1));
        }, $$02 -> {
            DataComponentType<?> $$1 = $$02.type();
            Identifier $$2 = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey($$1);
            if ($$2 == null) {
                return DataResult.error(() -> {
                    return "Unregistered component: " + String.valueOf($$1);
                });
            }
            return DataResult.success($$02.removed() ? "!" + String.valueOf($$2) : $$2.toString());
        });

        PatchKey(DataComponentType<?> $$0, boolean $$1) {
            this.type = $$0;
            this.removed = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PatchKey.class), PatchKey.class, "type;removed", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$PatchKey;->type:Lnet/minecraft/core/component/DataComponentType;", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$PatchKey;->removed:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PatchKey.class), PatchKey.class, "type;removed", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$PatchKey;->type:Lnet/minecraft/core/component/DataComponentType;", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$PatchKey;->removed:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PatchKey.class, Object.class), PatchKey.class, "type;removed", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$PatchKey;->type:Lnet/minecraft/core/component/DataComponentType;", "FIELD:Lnet/minecraft/core/component/DataComponentPatch$PatchKey;->removed:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public DataComponentType<?> type() {
            return this.type;
        }

        public boolean removed() {
            return this.removed;
        }

        public Codec<?> valueCodec() {
            return this.removed ? Codec.EMPTY.codec() : this.type.codecOrThrow();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentPatch$Builder.class */
    public static class Builder {
        private final Reference2ObjectMap<DataComponentType<?>, Optional<?>> map = new Reference2ObjectArrayMap();

        Builder() {
        }

        public <T> Builder set(DataComponentType<T> $$0, T $$1) {
            this.map.put($$0, Optional.of($$1));
            return this;
        }

        public <T> Builder remove(DataComponentType<T> $$0) {
            this.map.put($$0, Optional.empty());
            return this;
        }

        public <T> Builder set(TypedDataComponent<T> $$0) {
            return set($$0.type(), $$0.value());
        }

        public DataComponentPatch build() {
            if (this.map.isEmpty()) {
                return DataComponentPatch.EMPTY;
            }
            return new DataComponentPatch(this.map);
        }
    }
}
