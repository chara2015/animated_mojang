package net.minecraft.world.flag;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/flag/FeatureFlagRegistry.class */
public class FeatureFlagRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final FeatureFlagUniverse universe;
    private final Map<Identifier, FeatureFlag> names;
    private final FeatureFlagSet allFlags;

    FeatureFlagRegistry(FeatureFlagUniverse $$0, FeatureFlagSet $$1, Map<Identifier, FeatureFlag> $$2) {
        this.universe = $$0;
        this.names = $$2;
        this.allFlags = $$1;
    }

    public boolean isSubset(FeatureFlagSet $$0) {
        return $$0.isSubsetOf(this.allFlags);
    }

    public FeatureFlagSet allFlags() {
        return this.allFlags;
    }

    public FeatureFlagSet fromNames(Iterable<Identifier> $$0) {
        return fromNames($$0, $$02 -> {
            LOGGER.warn("Unknown feature flag: {}", $$02);
        });
    }

    public FeatureFlagSet subset(FeatureFlag... $$0) {
        return FeatureFlagSet.create(this.universe, Arrays.asList($$0));
    }

    public FeatureFlagSet fromNames(Iterable<Identifier> $$0, Consumer<Identifier> $$1) {
        Set<FeatureFlag> $$2 = Sets.newIdentityHashSet();
        for (Identifier $$3 : $$0) {
            FeatureFlag $$4 = this.names.get($$3);
            if ($$4 == null) {
                $$1.accept($$3);
            } else {
                $$2.add($$4);
            }
        }
        return FeatureFlagSet.create(this.universe, $$2);
    }

    public Set<Identifier> toNames(FeatureFlagSet $$0) {
        Set<Identifier> $$1 = new HashSet<>();
        this.names.forEach(($$2, $$3) -> {
            if ($$0.contains($$3)) {
                $$1.add($$2);
            }
        });
        return $$1;
    }

    public Codec<FeatureFlagSet> codec() {
        return Identifier.CODEC.listOf().comapFlatMap($$0 -> {
            Set<Identifier> $$1 = new HashSet<>();
            Objects.requireNonNull($$1);
            FeatureFlagSet $$2 = fromNames($$0, (v1) -> {
                r2.add(v1);
            });
            if (!$$1.isEmpty()) {
                return DataResult.error(() -> {
                    return "Unknown feature ids: " + String.valueOf($$1);
                }, $$2);
            }
            return DataResult.success($$2);
        }, $$02 -> {
            return List.copyOf(toNames($$02));
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/flag/FeatureFlagRegistry$Builder.class */
    public static class Builder {
        private final FeatureFlagUniverse universe;
        private int id;
        private final Map<Identifier, FeatureFlag> flags = new LinkedHashMap();

        public Builder(String $$0) {
            this.universe = new FeatureFlagUniverse($$0);
        }

        public FeatureFlag createVanilla(String $$0) {
            return create(Identifier.withDefaultNamespace($$0));
        }

        public FeatureFlag create(Identifier $$0) {
            if (this.id >= 64) {
                throw new IllegalStateException("Too many feature flags");
            }
            FeatureFlagUniverse featureFlagUniverse = this.universe;
            int i = this.id;
            this.id = i + 1;
            FeatureFlag $$1 = new FeatureFlag(featureFlagUniverse, i);
            FeatureFlag $$2 = this.flags.put($$0, $$1);
            if ($$2 != null) {
                throw new IllegalStateException("Duplicate feature flag " + String.valueOf($$0));
            }
            return $$1;
        }

        public FeatureFlagRegistry build() {
            FeatureFlagSet $$0 = FeatureFlagSet.create(this.universe, this.flags.values());
            return new FeatureFlagRegistry(this.universe, $$0, Map.copyOf(this.flags));
        }
    }
}
