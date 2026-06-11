package net.minecraft.core;

import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlagSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/HolderLookup.class */
public interface HolderLookup<T> extends HolderGetter<T> {
    Stream<Holder.Reference<T>> listElements();

    Stream<HolderSet.Named<T>> listTags();

    default Stream<ResourceKey<T>> listElementIds() {
        return (Stream<ResourceKey<T>>) listElements().map((v0) -> {
            return v0.key();
        });
    }

    default Stream<TagKey<T>> listTagIds() {
        return (Stream<TagKey<T>>) listTags().map((v0) -> {
            return v0.key();
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/HolderLookup$RegistryLookup.class */
    public interface RegistryLookup<T> extends HolderLookup<T>, HolderOwner<T> {
        ResourceKey<? extends Registry<? extends T>> key();

        Lifecycle registryLifecycle();

        default RegistryLookup<T> filterFeatures(FeatureFlagSet $$0) {
            if (FeatureElement.FILTERED_REGISTRIES.contains(key())) {
                return filterElements($$1 -> {
                    return ((FeatureElement) $$1).isEnabled($$0);
                });
            }
            return this;
        }

        default RegistryLookup<T> filterElements(final Predicate<T> $$0) {
            return new Delegate<T>() { // from class: net.minecraft.core.HolderLookup.RegistryLookup.1
                @Override // net.minecraft.core.HolderLookup.RegistryLookup.Delegate
                public RegistryLookup<T> parent() {
                    return RegistryLookup.this;
                }

                @Override // net.minecraft.core.HolderLookup.RegistryLookup.Delegate, net.minecraft.core.HolderGetter
                public Optional<Holder.Reference<T>> get(ResourceKey<T> $$02) {
                    Optional<Holder.Reference<T>> optional = parent().get($$02);
                    Predicate predicate = $$0;
                    return optional.filter($$1 -> {
                        return predicate.test($$1.value());
                    });
                }

                @Override // net.minecraft.core.HolderLookup.RegistryLookup.Delegate, net.minecraft.core.HolderLookup
                public Stream<Holder.Reference<T>> listElements() {
                    Stream<Holder.Reference<T>> streamListElements = parent().listElements();
                    Predicate predicate = $$0;
                    return streamListElements.filter($$1 -> {
                        return predicate.test($$1.value());
                    });
                }
            };
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/HolderLookup$RegistryLookup$Delegate.class */
        public interface Delegate<T> extends RegistryLookup<T> {
            RegistryLookup<T> parent();

            @Override // net.minecraft.core.HolderLookup.RegistryLookup
            default ResourceKey<? extends Registry<? extends T>> key() {
                return parent().key();
            }

            @Override // net.minecraft.core.HolderLookup.RegistryLookup
            default Lifecycle registryLifecycle() {
                return parent().registryLifecycle();
            }

            @Override // net.minecraft.core.HolderGetter
            default Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
                return parent().get($$0);
            }

            @Override // net.minecraft.core.HolderLookup
            default Stream<Holder.Reference<T>> listElements() {
                return parent().listElements();
            }

            @Override // net.minecraft.core.HolderGetter
            default Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
                return parent().get($$0);
            }

            @Override // net.minecraft.core.HolderLookup
            default Stream<HolderSet.Named<T>> listTags() {
                return parent().listTags();
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/HolderLookup$Provider.class */
    public interface Provider extends HolderGetter.Provider {
        Stream<ResourceKey<? extends Registry<?>>> listRegistryKeys();

        @Override // net.minecraft.core.HolderGetter.Provider
        <T> Optional<? extends RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> resourceKey);

        default Stream<RegistryLookup<?>> listRegistries() {
            return listRegistryKeys().map(this::lookupOrThrow);
        }

        @Override // net.minecraft.core.HolderGetter.Provider
        default <T> RegistryLookup<T> lookupOrThrow(ResourceKey<? extends Registry<? extends T>> $$0) {
            return lookup($$0).orElseThrow(() -> {
                return new IllegalStateException("Registry " + String.valueOf($$0.identifier()) + " not found");
            });
        }

        default <V> RegistryOps<V> createSerializationContext(DynamicOps<V> $$0) {
            return RegistryOps.create($$0, this);
        }

        static Provider create(Stream<RegistryLookup<?>> $$0) {
            final Map<ResourceKey<? extends Registry<?>>, RegistryLookup<?>> $$1 = (Map) $$0.collect(Collectors.toUnmodifiableMap((v0) -> {
                return v0.key();
            }, $$02 -> {
                return $$02;
            }));
            return new Provider() { // from class: net.minecraft.core.HolderLookup.Provider.1
                @Override // net.minecraft.core.HolderLookup.Provider
                public Stream<ResourceKey<? extends Registry<?>>> listRegistryKeys() {
                    return $$1.keySet().stream();
                }

                @Override // net.minecraft.core.HolderLookup.Provider, net.minecraft.core.HolderGetter.Provider
                public <T> Optional<RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$03) {
                    return Optional.ofNullable((RegistryLookup) $$1.get($$03));
                }
            };
        }

        default Lifecycle allRegistriesLifecycle() {
            return (Lifecycle) listRegistries().map((v0) -> {
                return v0.registryLifecycle();
            }).reduce(Lifecycle.stable(), (v0, v1) -> {
                return v0.add(v1);
            });
        }
    }
}
