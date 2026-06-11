package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JavaOps;
import com.mojang.serialization.Lifecycle;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/PlaceholderLookupProvider.class */
public class PlaceholderLookupProvider implements HolderGetter.Provider {
    final HolderLookup.Provider context;
    final UniversalLookup lookup = new UniversalLookup();
    final Map<ResourceKey<Object>, Holder.Reference<Object>> holders = new HashMap();
    final Map<TagKey<Object>, HolderSet.Named<Object>> holderSets = new HashMap();

    public PlaceholderLookupProvider(HolderLookup.Provider $$0) {
        this.context = $$0;
    }

    @Override // net.minecraft.core.HolderGetter.Provider
    public <T> Optional<? extends HolderGetter<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) {
        return Optional.of(this.lookup.castAsLookup());
    }

    public <V> RegistryOps<V> createSerializationContext(DynamicOps<V> $$0) {
        return RegistryOps.create($$0, new RegistryOps.RegistryInfoLookup() { // from class: net.minecraft.util.PlaceholderLookupProvider.1
            @Override // net.minecraft.resources.RegistryOps.RegistryInfoLookup
            public <T> Optional<RegistryOps.RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$02) {
                return PlaceholderLookupProvider.this.context.lookup($$02).map(RegistryOps.RegistryInfo::fromRegistryLookup).or(() -> {
                    return Optional.of(new RegistryOps.RegistryInfo(PlaceholderLookupProvider.this.lookup.castAsOwner(), PlaceholderLookupProvider.this.lookup.castAsLookup(), Lifecycle.experimental()));
                });
            }
        });
    }

    public RegistryContextSwapper createSwapper() {
        return new RegistryContextSwapper() { // from class: net.minecraft.util.PlaceholderLookupProvider.2
            @Override // net.minecraft.util.RegistryContextSwapper
            public <T> DataResult<T> swapTo(Codec<T> $$0, T $$1, HolderLookup.Provider $$2) {
                return $$0.encodeStart(PlaceholderLookupProvider.this.createSerializationContext(JavaOps.INSTANCE), $$1).flatMap($$22 -> {
                    return $$0.parse($$2.createSerializationContext(JavaOps.INSTANCE), $$22);
                });
            }
        };
    }

    public boolean hasRegisteredPlaceholders() {
        return (this.holders.isEmpty() && this.holderSets.isEmpty()) ? false : true;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/PlaceholderLookupProvider$UniversalLookup.class */
    class UniversalLookup implements HolderGetter<Object>, HolderOwner<Object> {
        UniversalLookup() {
        }

        @Override // net.minecraft.core.HolderGetter
        public Optional<Holder.Reference<Object>> get(ResourceKey<Object> $$0) {
            return Optional.of(getOrCreate($$0));
        }

        @Override // net.minecraft.core.HolderGetter
        public Holder.Reference<Object> getOrThrow(ResourceKey<Object> $$0) {
            return getOrCreate($$0);
        }

        private Holder.Reference<Object> getOrCreate(ResourceKey<Object> $$0) {
            return PlaceholderLookupProvider.this.holders.computeIfAbsent($$0, $$02 -> {
                return Holder.Reference.createStandAlone(this, $$02);
            });
        }

        @Override // net.minecraft.core.HolderGetter
        public Optional<HolderSet.Named<Object>> get(TagKey<Object> $$0) {
            return Optional.of(getOrCreate($$0));
        }

        @Override // net.minecraft.core.HolderGetter
        public HolderSet.Named<Object> getOrThrow(TagKey<Object> $$0) {
            return getOrCreate($$0);
        }

        private HolderSet.Named<Object> getOrCreate(TagKey<Object> $$0) {
            return PlaceholderLookupProvider.this.holderSets.computeIfAbsent($$0, $$02 -> {
                return HolderSet.emptyNamed(this, $$02);
            });
        }

        public <T> HolderGetter<T> castAsLookup() {
            return this;
        }

        public <T> HolderOwner<T> castAsOwner() {
            return this;
        }
    }
}
