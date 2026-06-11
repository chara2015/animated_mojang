package net.minecraft.resources;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/RegistryOps.class */
public class RegistryOps<T> extends DelegatingOps<T> {
    private final RegistryInfoLookup lookupProvider;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/RegistryOps$RegistryInfoLookup.class */
    public interface RegistryInfoLookup {
        <T> Optional<RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> resourceKey);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/RegistryOps$RegistryInfo.class */
    public static final class RegistryInfo<T> extends Record {
        private final HolderOwner<T> owner;
        private final HolderGetter<T> getter;
        private final Lifecycle elementsLifecycle;

        public RegistryInfo(HolderOwner<T> $$0, HolderGetter<T> $$1, Lifecycle $$2) {
            this.owner = $$0;
            this.getter = $$1;
            this.elementsLifecycle = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RegistryInfo.class), RegistryInfo.class, "owner;getter;elementsLifecycle", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->owner:Lnet/minecraft/core/HolderOwner;", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->getter:Lnet/minecraft/core/HolderGetter;", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->elementsLifecycle:Lcom/mojang/serialization/Lifecycle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RegistryInfo.class), RegistryInfo.class, "owner;getter;elementsLifecycle", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->owner:Lnet/minecraft/core/HolderOwner;", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->getter:Lnet/minecraft/core/HolderGetter;", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->elementsLifecycle:Lcom/mojang/serialization/Lifecycle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RegistryInfo.class, Object.class), RegistryInfo.class, "owner;getter;elementsLifecycle", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->owner:Lnet/minecraft/core/HolderOwner;", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->getter:Lnet/minecraft/core/HolderGetter;", "FIELD:Lnet/minecraft/resources/RegistryOps$RegistryInfo;->elementsLifecycle:Lcom/mojang/serialization/Lifecycle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public HolderOwner<T> owner() {
            return this.owner;
        }

        public HolderGetter<T> getter() {
            return this.getter;
        }

        public Lifecycle elementsLifecycle() {
            return this.elementsLifecycle;
        }

        public static <T> RegistryInfo<T> fromRegistryLookup(HolderLookup.RegistryLookup<T> $$0) {
            return new RegistryInfo<>($$0, $$0, $$0.registryLifecycle());
        }
    }

    public static <T> RegistryOps<T> create(DynamicOps<T> $$0, HolderLookup.Provider $$1) {
        return create($$0, new HolderLookupAdapter($$1));
    }

    public static <T> RegistryOps<T> create(DynamicOps<T> $$0, RegistryInfoLookup $$1) {
        return new RegistryOps<>($$0, $$1);
    }

    public static <T> Dynamic<T> injectRegistryContext(Dynamic<T> $$0, HolderLookup.Provider $$1) {
        return new Dynamic<>($$1.createSerializationContext($$0.getOps()), $$0.getValue());
    }

    private RegistryOps(DynamicOps<T> $$0, RegistryInfoLookup $$1) {
        super($$0);
        this.lookupProvider = $$1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <U> RegistryOps<U> withParent(DynamicOps<U> $$0) {
        if ($$0 == this.delegate) {
            return this;
        }
        return new RegistryOps<>($$0, this.lookupProvider);
    }

    public <E> Optional<HolderOwner<E>> owner(ResourceKey<? extends Registry<? extends E>> resourceKey) {
        return (Optional<HolderOwner<E>>) this.lookupProvider.lookup(resourceKey).map((v0) -> {
            return v0.owner();
        });
    }

    public <E> Optional<HolderGetter<E>> getter(ResourceKey<? extends Registry<? extends E>> resourceKey) {
        return (Optional<HolderGetter<E>>) this.lookupProvider.lookup(resourceKey).map((v0) -> {
            return v0.getter();
        });
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        RegistryOps<?> $$1 = (RegistryOps) $$0;
        return this.delegate.equals($$1.delegate) && this.lookupProvider.equals($$1.lookupProvider);
    }

    public int hashCode() {
        return (this.delegate.hashCode() * 31) + this.lookupProvider.hashCode();
    }

    public static <E, O> RecordCodecBuilder<O, HolderGetter<E>> retrieveGetter(ResourceKey<? extends Registry<? extends E>> $$0) {
        return ExtraCodecs.retrieveContext($$1 -> {
            if ($$1 instanceof RegistryOps) {
                RegistryOps<?> $$2 = (RegistryOps) $$1;
                return (DataResult) ((RegistryOps) $$2).lookupProvider.lookup($$0).map($$02 -> {
                    return DataResult.success($$02.getter(), $$02.elementsLifecycle());
                }).orElseGet(() -> {
                    return DataResult.error(() -> {
                        return "Unknown registry: " + String.valueOf($$0);
                    });
                });
            }
            return DataResult.error(() -> {
                return "Not a registry ops";
            });
        }).forGetter($$02 -> {
            return null;
        });
    }

    public static <E, O> RecordCodecBuilder<O, Holder.Reference<E>> retrieveElement(ResourceKey<E> $$0) {
        ResourceKey<? extends Registry<E>> $$1 = ResourceKey.createRegistryKey($$0.registry());
        return ExtraCodecs.retrieveContext($$2 -> {
            if ($$2 instanceof RegistryOps) {
                RegistryOps<?> $$3 = (RegistryOps) $$2;
                return (DataResult) ((RegistryOps) $$3).lookupProvider.lookup($$1).flatMap($$12 -> {
                    return $$12.getter().get($$0);
                }).map((v0) -> {
                    return DataResult.success(v0);
                }).orElseGet(() -> {
                    return DataResult.error(() -> {
                        return "Can't find value: " + String.valueOf($$0);
                    });
                });
            }
            return DataResult.error(() -> {
                return "Not a registry ops";
            });
        }).forGetter($$02 -> {
            return null;
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/RegistryOps$HolderLookupAdapter.class */
    static final class HolderLookupAdapter implements RegistryInfoLookup {
        private final HolderLookup.Provider lookupProvider;
        private final Map<ResourceKey<? extends Registry<?>>, Optional<? extends RegistryInfo<?>>> lookups = new ConcurrentHashMap();

        public HolderLookupAdapter(HolderLookup.Provider $$0) {
            this.lookupProvider = $$0;
        }

        @Override // net.minecraft.resources.RegistryOps.RegistryInfoLookup
        public <E> Optional<RegistryInfo<E>> lookup(ResourceKey<? extends Registry<? extends E>> $$0) {
            return (Optional) this.lookups.computeIfAbsent($$0, this::createLookup);
        }

        private Optional<RegistryInfo<Object>> createLookup(ResourceKey<? extends Registry<?>> $$0) {
            return this.lookupProvider.lookup($$0).map(RegistryInfo::fromRegistryLookup);
        }

        public boolean equals(Object $$0) {
            if (this == $$0) {
                return true;
            }
            if ($$0 instanceof HolderLookupAdapter) {
                HolderLookupAdapter $$1 = (HolderLookupAdapter) $$0;
                if (this.lookupProvider.equals($$1.lookupProvider)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.lookupProvider.hashCode();
        }
    }
}
