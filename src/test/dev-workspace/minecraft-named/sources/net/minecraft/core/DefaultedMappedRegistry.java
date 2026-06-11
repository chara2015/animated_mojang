package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/DefaultedMappedRegistry.class */
public class DefaultedMappedRegistry<T> extends MappedRegistry<T> implements DefaultedRegistry<T> {
    private final Identifier defaultKey;
    private Holder.Reference<T> defaultValue;

    public DefaultedMappedRegistry(String $$0, ResourceKey<? extends Registry<T>> $$1, Lifecycle $$2, boolean $$3) {
        super($$1, $$2, $$3);
        this.defaultKey = Identifier.parse($$0);
    }

    @Override // net.minecraft.core.MappedRegistry, net.minecraft.core.WritableRegistry
    public Holder.Reference<T> register(ResourceKey<T> $$0, T $$1, RegistrationInfo $$2) {
        Holder.Reference<T> $$3 = super.register($$0, $$1, $$2);
        if (this.defaultKey.equals($$0.identifier())) {
            this.defaultValue = $$3;
        }
        return $$3;
    }

    @Override // net.minecraft.core.MappedRegistry, net.minecraft.core.Registry, net.minecraft.core.IdMap
    public int getId(T $$0) {
        int $$1 = super.getId($$0);
        return $$1 == -1 ? super.getId(this.defaultValue.value()) : $$1;
    }

    @Override // net.minecraft.core.MappedRegistry, net.minecraft.core.Registry, net.minecraft.core.DefaultedRegistry
    public Identifier getKey(T $$0) {
        Identifier $$1 = super.getKey($$0);
        return $$1 == null ? this.defaultKey : $$1;
    }

    @Override // net.minecraft.core.MappedRegistry, net.minecraft.core.Registry, net.minecraft.core.DefaultedRegistry
    public T getValue(Identifier identifier) {
        T t = (T) super.getValue(identifier);
        return t == null ? this.defaultValue.value() : t;
    }

    @Override // net.minecraft.core.Registry
    public Optional<T> getOptional(Identifier $$0) {
        return Optional.ofNullable(super.getValue($$0));
    }

    @Override // net.minecraft.core.MappedRegistry, net.minecraft.core.Registry
    public Optional<Holder.Reference<T>> getAny() {
        return Optional.ofNullable(this.defaultValue);
    }

    @Override // net.minecraft.core.MappedRegistry, net.minecraft.core.IdMap, net.minecraft.core.DefaultedRegistry
    public T byId(int i) {
        T t = (T) super.byId(i);
        return t == null ? this.defaultValue.value() : t;
    }

    @Override // net.minecraft.core.MappedRegistry, net.minecraft.core.Registry
    public Optional<Holder.Reference<T>> getRandom(RandomSource $$0) {
        return super.getRandom($$0).or(() -> {
            return Optional.of(this.defaultValue);
        });
    }

    @Override // net.minecraft.core.DefaultedRegistry
    public Identifier getDefaultKey() {
        return this.defaultKey;
    }
}
