package net.minecraft.commands;

import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/CommandBuildContext.class */
public interface CommandBuildContext extends HolderLookup.Provider {
    FeatureFlagSet enabledFeatures();

    static CommandBuildContext simple(final HolderLookup.Provider $$0, final FeatureFlagSet $$1) {
        return new CommandBuildContext() { // from class: net.minecraft.commands.CommandBuildContext.1
            @Override // net.minecraft.core.HolderLookup.Provider
            public Stream<ResourceKey<? extends Registry<?>>> listRegistryKeys() {
                return $$0.listRegistryKeys();
            }

            @Override // net.minecraft.core.HolderLookup.Provider, net.minecraft.core.HolderGetter.Provider
            public <T> Optional<HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> resourceKey) {
                Optional<? extends HolderLookup.RegistryLookup<T>> optionalLookup = $$0.lookup(resourceKey);
                FeatureFlagSet featureFlagSet = $$1;
                return (Optional<HolderLookup.RegistryLookup<T>>) optionalLookup.map($$12 -> {
                    return $$12.filterFeatures(featureFlagSet);
                });
            }

            @Override // net.minecraft.commands.CommandBuildContext
            public FeatureFlagSet enabledFeatures() {
                return $$1;
            }
        };
    }
}
