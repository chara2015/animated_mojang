package net.minecraft.core;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/RegistryAccess.class */
public interface RegistryAccess extends HolderLookup.Provider {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Frozen EMPTY = new ImmutableRegistryAccess((Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>>) Map.of()).freeze();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/RegistryAccess$Frozen.class */
    public interface Frozen extends RegistryAccess {
    }

    @Override // net.minecraft.core.HolderLookup.Provider, net.minecraft.core.HolderGetter.Provider
    <E> Optional<Registry<E>> lookup(ResourceKey<? extends Registry<? extends E>> resourceKey);

    Stream<RegistryEntry<?>> registries();

    @Override // net.minecraft.core.HolderLookup.Provider, net.minecraft.core.HolderGetter.Provider
    default <E> Registry<E> lookupOrThrow(ResourceKey<? extends Registry<? extends E>> $$0) {
        return lookup($$0).orElseThrow(() -> {
            return new IllegalStateException("Missing registry: " + String.valueOf($$0));
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/RegistryAccess$RegistryEntry.class */
    public static final class RegistryEntry<T> extends Record {
        private final ResourceKey<? extends Registry<T>> key;
        private final Registry<T> value;

        public RegistryEntry(ResourceKey<? extends Registry<T>> $$0, Registry<T> $$1) {
            this.key = $$0;
            this.value = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RegistryEntry.class), RegistryEntry.class, "key;value", "FIELD:Lnet/minecraft/core/RegistryAccess$RegistryEntry;->key:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/core/RegistryAccess$RegistryEntry;->value:Lnet/minecraft/core/Registry;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RegistryEntry.class), RegistryEntry.class, "key;value", "FIELD:Lnet/minecraft/core/RegistryAccess$RegistryEntry;->key:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/core/RegistryAccess$RegistryEntry;->value:Lnet/minecraft/core/Registry;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RegistryEntry.class, Object.class), RegistryEntry.class, "key;value", "FIELD:Lnet/minecraft/core/RegistryAccess$RegistryEntry;->key:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/core/RegistryAccess$RegistryEntry;->value:Lnet/minecraft/core/Registry;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResourceKey<? extends Registry<T>> key() {
            return this.key;
        }

        public Registry<T> value() {
            return this.value;
        }

        private static <T, R extends Registry<? extends T>> RegistryEntry<T> fromMapEntry(Map.Entry<? extends ResourceKey<? extends Registry<?>>, R> $$0) {
            return fromUntyped($$0.getKey(), $$0.getValue());
        }

        private static <T> RegistryEntry<T> fromUntyped(ResourceKey<? extends Registry<?>> $$0, Registry<?> $$1) {
            return new RegistryEntry<>($$0, $$1);
        }

        private RegistryEntry<T> freeze() {
            return new RegistryEntry<>(this.key, this.value.freeze());
        }
    }

    @Override // net.minecraft.core.HolderLookup.Provider
    default Stream<ResourceKey<? extends Registry<?>>> listRegistryKeys() {
        return registries().map($$0 -> {
            return $$0.key;
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/RegistryAccess$ImmutableRegistryAccess.class */
    public static class ImmutableRegistryAccess implements RegistryAccess {
        private final Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> registries;

        public ImmutableRegistryAccess(List<? extends Registry<?>> $$0) {
            this.registries = (Map) $$0.stream().collect(Collectors.toUnmodifiableMap((v0) -> {
                return v0.key();
            }, $$02 -> {
                return $$02;
            }));
        }

        public ImmutableRegistryAccess(Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> $$0) {
            this.registries = Map.copyOf($$0);
        }

        public ImmutableRegistryAccess(Stream<RegistryEntry<?>> $$0) {
            this.registries = (Map) $$0.collect(ImmutableMap.toImmutableMap((v0) -> {
                return v0.key();
            }, (v0) -> {
                return v0.value();
            }));
        }

        @Override // net.minecraft.core.RegistryAccess, net.minecraft.core.HolderLookup.Provider, net.minecraft.core.HolderGetter.Provider
        public <E> Optional<Registry<E>> lookup(ResourceKey<? extends Registry<? extends E>> $$0) {
            return Optional.ofNullable(this.registries.get($$0)).map($$02 -> {
                return $$02;
            });
        }

        @Override // net.minecraft.core.RegistryAccess
        public Stream<RegistryEntry<?>> registries() {
            return this.registries.entrySet().stream().map(RegistryEntry::fromMapEntry);
        }
    }

    static Frozen fromRegistryOfRegistries(final Registry<? extends Registry<?>> $$0) {
        return new Frozen() { // from class: net.minecraft.core.RegistryAccess.1
            @Override // net.minecraft.core.RegistryAccess, net.minecraft.core.HolderLookup.Provider, net.minecraft.core.HolderGetter.Provider
            public <T> Optional<Registry<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$02) {
                Registry<Registry<T>> $$1 = $$0;
                return $$1.getOptional((ResourceKey<Registry<T>>) $$02);
            }

            @Override // net.minecraft.core.RegistryAccess
            public Stream<RegistryEntry<?>> registries() {
                return $$0.entrySet().stream().map(RegistryEntry::fromMapEntry);
            }

            @Override // net.minecraft.core.RegistryAccess
            public Frozen freeze() {
                return this;
            }
        };
    }

    /* JADX INFO: renamed from: net.minecraft.core.RegistryAccess$1FrozenAccess, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/RegistryAccess$1FrozenAccess.class */
    class C1FrozenAccess extends ImmutableRegistryAccess implements Frozen {
        protected C1FrozenAccess(RegistryAccess $$0, Stream<RegistryEntry<?>> $$1) {
            super($$1);
        }
    }

    default Frozen freeze() {
        return new C1FrozenAccess(this, registries().map((v0) -> {
            return v0.freeze();
        }));
    }
}
