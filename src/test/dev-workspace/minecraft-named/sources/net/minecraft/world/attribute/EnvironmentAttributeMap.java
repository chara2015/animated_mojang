package net.minecraft.world.attribute;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.Util;
import net.minecraft.world.attribute.modifier.AttributeModifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeMap.class */
public final class EnvironmentAttributeMap {
    public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(Map.of());
    public static final Codec<EnvironmentAttributeMap> CODEC = Codec.lazyInitialized(() -> {
        return Codec.dispatchedMap(EnvironmentAttributes.CODEC, Util.memoize(Entry::createCodec)).xmap(EnvironmentAttributeMap::new, $$0 -> {
            return $$0.entries;
        });
    });
    public static final Codec<EnvironmentAttributeMap> NETWORK_CODEC = CODEC.xmap(EnvironmentAttributeMap::filterSyncable, EnvironmentAttributeMap::filterSyncable);
    public static final Codec<EnvironmentAttributeMap> CODEC_ONLY_POSITIONAL = CODEC.validate($$0 -> {
        List<EnvironmentAttribute<?>> $$1 = $$0.keySet().stream().filter($$0 -> {
            return !$$0.isPositional();
        }).toList();
        if (!$$1.isEmpty()) {
            return DataResult.error(() -> {
                return "The following attributes cannot be positional: " + String.valueOf($$1);
            });
        }
        return DataResult.success($$0);
    });
    final Map<EnvironmentAttribute<?>, Entry<?, ?>> entries;

    private static EnvironmentAttributeMap filterSyncable(EnvironmentAttributeMap $$0) {
        return new EnvironmentAttributeMap(Map.copyOf(Maps.filterKeys($$0.entries, (v0) -> {
            return v0.isSyncable();
        })));
    }

    EnvironmentAttributeMap(Map<EnvironmentAttribute<?>, Entry<?, ?>> $$0) {
        this.entries = $$0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public <Value> Entry<Value, ?> get(EnvironmentAttribute<Value> $$0) {
        return (Entry) this.entries.get($$0);
    }

    public <Value> Value applyModifier(EnvironmentAttribute<Value> $$0, Value $$1) {
        Entry<Value, ?> $$2 = get($$0);
        return $$2 != null ? $$2.applyModifier($$1) : $$1;
    }

    public boolean contains(EnvironmentAttribute<?> $$0) {
        return this.entries.containsKey($$0);
    }

    public Set<EnvironmentAttribute<?>> keySet() {
        return this.entries.keySet();
    }

    public boolean equals(Object $$0) {
        if ($$0 == this) {
            return true;
        }
        if ($$0 instanceof EnvironmentAttributeMap) {
            EnvironmentAttributeMap $$1 = (EnvironmentAttributeMap) $$0;
            if (this.entries.equals($$1.entries)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.entries.hashCode();
    }

    public String toString() {
        return this.entries.toString();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeMap$Entry.class */
    public static final class Entry<Value, Argument> extends Record {
        private final Argument argument;
        private final AttributeModifier<Value, Argument> modifier;

        public Entry(Argument $$0, AttributeModifier<Value, Argument> $$1) {
            this.argument = $$0;
            this.modifier = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Entry.class), Entry.class, "argument;modifier", "FIELD:Lnet/minecraft/world/attribute/EnvironmentAttributeMap$Entry;->argument:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/attribute/EnvironmentAttributeMap$Entry;->modifier:Lnet/minecraft/world/attribute/modifier/AttributeModifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Entry.class), Entry.class, "argument;modifier", "FIELD:Lnet/minecraft/world/attribute/EnvironmentAttributeMap$Entry;->argument:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/attribute/EnvironmentAttributeMap$Entry;->modifier:Lnet/minecraft/world/attribute/modifier/AttributeModifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Entry.class, Object.class), Entry.class, "argument;modifier", "FIELD:Lnet/minecraft/world/attribute/EnvironmentAttributeMap$Entry;->argument:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/attribute/EnvironmentAttributeMap$Entry;->modifier:Lnet/minecraft/world/attribute/modifier/AttributeModifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Argument argument() {
            return this.argument;
        }

        public AttributeModifier<Value, Argument> modifier() {
            return this.modifier;
        }

        private static <Value> Codec<Entry<Value, ?>> createCodec(EnvironmentAttribute<Value> $$0) {
            Codec<Entry<Value, ?>> $$1 = $$0.type().modifierCodec().dispatch("modifier", (v0) -> {
                return v0.modifier();
            }, Util.memoize($$12 -> {
                return createFullCodec($$0, $$12);
            }));
            return Codec.either($$0.valueCodec(), $$1).xmap($$02 -> {
                return (Entry) $$02.map($$02 -> {
                    return new Entry($$02, AttributeModifier.override());
                }, $$03 -> {
                    return $$03;
                });
            }, $$03 -> {
                if ($$03.modifier == AttributeModifier.override()) {
                    return Either.left($$03.argument());
                }
                return Either.right($$03);
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <Value, Argument> MapCodec<Entry<Value, Argument>> createFullCodec(EnvironmentAttribute<Value> $$0, AttributeModifier<Value, Argument> $$1) {
            return RecordCodecBuilder.mapCodec($$2 -> {
                return $$2.group($$1.argumentCodec($$0).fieldOf("argument").forGetter((v0) -> {
                    return v0.argument();
                })).apply($$2, $$12 -> {
                    return new Entry($$12, $$1);
                });
            });
        }

        public Value applyModifier(Value $$0) {
            return this.modifier.apply($$0, this.argument);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/EnvironmentAttributeMap$Builder.class */
    public static class Builder {
        private final Map<EnvironmentAttribute<?>, Entry<?, ?>> entries = new HashMap();

        Builder() {
        }

        public Builder putAll(EnvironmentAttributeMap $$0) {
            this.entries.putAll($$0.entries);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public <Value, Parameter> Builder modify(EnvironmentAttribute<Value> $$0, AttributeModifier<Value, Parameter> attributeModifier, Parameter $$2) {
            $$0.type().checkAllowedModifier(attributeModifier);
            this.entries.put($$0, new Entry<>($$2, attributeModifier));
            return this;
        }

        public <Value> Builder set(EnvironmentAttribute<Value> $$0, Value $$1) {
            return modify($$0, AttributeModifier.override(), $$1);
        }

        public EnvironmentAttributeMap build() {
            if (this.entries.isEmpty()) {
                return EnvironmentAttributeMap.EMPTY;
            }
            return new EnvironmentAttributeMap(Map.copyOf(this.entries));
        }
    }
}
