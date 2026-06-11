package net.minecraft.world.level.block.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/StateDefinition.class */
public class StateDefinition<O, S extends StateHolder<O, S>> {
    static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
    private final O owner;
    private final ImmutableSortedMap<String, Property<?>> propertiesByName;
    private final ImmutableList<S> states;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/StateDefinition$Factory.class */
    public interface Factory<O, S> {
        S create(O o, Reference2ObjectArrayMap<Property<?>, Comparable<?>> reference2ObjectArrayMap, MapCodec<S> mapCodec);
    }

    protected StateDefinition(Function<O, S> $$0, O $$1, Factory<O, S> $$2, Map<String, Property<?>> $$3) {
        this.owner = $$1;
        this.propertiesByName = ImmutableSortedMap.copyOf($$3);
        Supplier<S> $$4 = () -> {
            return (StateHolder) $$0.apply($$1);
        };
        MapCodec<S> $$5 = MapCodec.of(Encoder.empty(), Decoder.unit($$4));
        UnmodifiableIterator it = this.propertiesByName.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Property<?>> $$6 = (Map.Entry) it.next();
            $$5 = appendPropertyCodec($$5, $$4, $$6.getKey(), $$6.getValue());
        }
        MapCodec<S> $$7 = $$5;
        LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        List<S> $$9 = Lists.newArrayList();
        Stream<List<Pair<Property<?>, Comparable<?>>>> $$10 = Stream.of(Collections.emptyList());
        UnmodifiableIterator it2 = this.propertiesByName.values().iterator();
        while (it2.hasNext()) {
            Property<?> $$11 = (Property) it2.next();
            $$10 = $$10.flatMap($$12 -> {
                return $$11.getPossibleValues().stream().map($$22 -> {
                    List<Pair<Property<?>, Comparable<?>>> $$32 = Lists.newArrayList($$12);
                    $$32.add(Pair.of($$11, $$22));
                    return $$32;
                });
            });
        }
        $$10.forEach($$52 -> {
            Reference2ObjectArrayMap<Property<?>, Comparable<?>> $$62 = new Reference2ObjectArrayMap<>($$52.size());
            Iterator it3 = $$52.iterator();
            while (it3.hasNext()) {
                Pair<Property<?>, Comparable<?>> $$72 = (Pair) it3.next();
                $$62.put((Property) $$72.getFirst(), (Comparable) $$72.getSecond());
            }
            StateHolder stateHolder = (StateHolder) $$2.create($$1, $$62, $$7);
            linkedHashMapNewLinkedHashMap.put($$62, stateHolder);
            $$9.add(stateHolder);
        });
        for (S $$122 : $$9) {
            $$122.populateNeighbours(linkedHashMapNewLinkedHashMap);
        }
        this.states = ImmutableList.copyOf($$9);
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> MapCodec<S> appendPropertyCodec(MapCodec<S> $$0, Supplier<S> $$1, String $$2, Property<T> $$3) {
        return Codec.mapPair($$0, $$3.valueCodec().fieldOf($$2).orElseGet($$02 -> {
        }, () -> {
            return $$3.value((StateHolder<?, ?>) $$1.get());
        })).xmap($$12 -> {
            return (StateHolder) ((StateHolder) $$12.getFirst()).setValue($$3, ((Property.Value) $$12.getSecond()).value());
        }, $$13 -> {
            return Pair.of($$13, $$3.value((StateHolder<?, ?>) $$13));
        });
    }

    public ImmutableList<S> getPossibleStates() {
        return this.states;
    }

    public S any() {
        return (S) this.states.get(0);
    }

    public O getOwner() {
        return this.owner;
    }

    public Collection<Property<?>> getProperties() {
        return this.propertiesByName.values();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("block", this.owner).add("properties", this.propertiesByName.values().stream().map((v0) -> {
            return v0.getName();
        }).collect(Collectors.toList())).toString();
    }

    public Property<?> getProperty(String $$0) {
        return (Property) this.propertiesByName.get($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/StateDefinition$Builder.class */
    public static class Builder<O, S extends StateHolder<O, S>> {
        private final O owner;
        private final Map<String, Property<?>> properties = Maps.newHashMap();

        public Builder(O $$0) {
            this.owner = $$0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder<O, S> add(Property<?>... propertyArr) {
            for (IntegerProperty integerProperty : propertyArr) {
                validateProperty(integerProperty);
                this.properties.put(integerProperty.getName(), integerProperty);
            }
            return this;
        }

        private <T extends Comparable<T>> void validateProperty(Property<T> $$0) {
            String $$1 = $$0.getName();
            if (!StateDefinition.NAME_PATTERN.matcher($$1).matches()) {
                throw new IllegalArgumentException(String.valueOf(this.owner) + " has invalidly named property: " + $$1);
            }
            Collection<T> $$2 = $$0.getPossibleValues();
            if ($$2.size() <= 1) {
                throw new IllegalArgumentException(String.valueOf(this.owner) + " attempted use property " + $$1 + " with <= 1 possible values");
            }
            for (T $$3 : $$2) {
                String $$4 = $$0.getName($$3);
                if (!StateDefinition.NAME_PATTERN.matcher($$4).matches()) {
                    throw new IllegalArgumentException(String.valueOf(this.owner) + " has property: " + $$1 + " with invalidly named value: " + $$4);
                }
            }
            if (this.properties.containsKey($$1)) {
                throw new IllegalArgumentException(String.valueOf(this.owner) + " has duplicate property: " + $$1);
            }
        }

        public StateDefinition<O, S> create(Function<O, S> $$0, Factory<O, S> $$1) {
            return new StateDefinition<>($$0, this.owner, $$1, this.properties);
        }
    }
}
