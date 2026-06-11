package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/random/WeightedList.class */
public final class WeightedList<E> {
    private static final int FLAT_THRESHOLD = 64;
    private final int totalWeight;
    private final List<Weighted<E>> items;
    private final Selector<E> selector;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/random/WeightedList$Selector.class */
    interface Selector<E> {
        E get(int i);
    }

    WeightedList(List<? extends Weighted<E>> $$0) {
        this.items = List.copyOf($$0);
        this.totalWeight = WeightedRandom.getTotalWeight($$0, (v0) -> {
            return v0.weight();
        });
        if (this.totalWeight == 0) {
            this.selector = null;
        } else if (this.totalWeight < 64) {
            this.selector = new Flat(this.items, this.totalWeight);
        } else {
            this.selector = new Compact(this.items);
        }
    }

    public static <E> WeightedList<E> of() {
        return new WeightedList<>(List.of());
    }

    public static <E> WeightedList<E> of(E $$0) {
        return new WeightedList<>(List.of(new Weighted($$0, 1)));
    }

    @SafeVarargs
    public static <E> WeightedList<E> of(Weighted<E>... $$0) {
        return new WeightedList<>(List.of((Object[]) $$0));
    }

    public static <E> WeightedList<E> of(List<Weighted<E>> $$0) {
        return new WeightedList<>($$0);
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public <T> WeightedList<T> map(Function<E, T> $$0) {
        return new WeightedList<>(Lists.transform(this.items, $$1 -> {
            return $$1.map($$0);
        }));
    }

    public Optional<E> getRandom(RandomSource $$0) {
        if (this.selector == null) {
            return Optional.empty();
        }
        int $$1 = $$0.nextInt(this.totalWeight);
        return Optional.of(this.selector.get($$1));
    }

    public E getRandomOrThrow(RandomSource $$0) {
        if (this.selector == null) {
            throw new IllegalStateException("Weighted list has no elements");
        }
        int $$1 = $$0.nextInt(this.totalWeight);
        return this.selector.get($$1);
    }

    public List<Weighted<E>> unwrap() {
        return this.items;
    }

    public static <E> Codec<WeightedList<E>> codec(Codec<E> $$0) {
        return Weighted.codec($$0).listOf().xmap(WeightedList::of, (v0) -> {
            return v0.unwrap();
        });
    }

    public static <E> Codec<WeightedList<E>> codec(MapCodec<E> $$0) {
        return Weighted.codec($$0).listOf().xmap(WeightedList::of, (v0) -> {
            return v0.unwrap();
        });
    }

    public static <E> Codec<WeightedList<E>> nonEmptyCodec(Codec<E> $$0) {
        return ExtraCodecs.nonEmptyList(Weighted.codec($$0).listOf()).xmap(WeightedList::of, (v0) -> {
            return v0.unwrap();
        });
    }

    public static <E> Codec<WeightedList<E>> nonEmptyCodec(MapCodec<E> $$0) {
        return ExtraCodecs.nonEmptyList(Weighted.codec($$0).listOf()).xmap(WeightedList::of, (v0) -> {
            return v0.unwrap();
        });
    }

    public static <E, B extends ByteBuf> StreamCodec<B, WeightedList<E>> streamCodec(StreamCodec<B, E> $$0) {
        return Weighted.streamCodec($$0).apply(ByteBufCodecs.list()).map(WeightedList::of, (v0) -> {
            return v0.unwrap();
        });
    }

    public boolean contains(E $$0) {
        for (Weighted<E> $$1 : this.items) {
            if ($$1.value().equals($$0)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof WeightedList)) {
            return false;
        }
        WeightedList<?> $$1 = (WeightedList) $$0;
        return this.totalWeight == $$1.totalWeight && Objects.equals(this.items, $$1.items);
    }

    public int hashCode() {
        int $$0 = this.totalWeight;
        return (31 * $$0) + this.items.hashCode();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/random/WeightedList$Builder.class */
    public static class Builder<E> {
        private final ImmutableList.Builder<Weighted<E>> result = ImmutableList.builder();

        public Builder<E> add(E $$0) {
            return add($$0, 1);
        }

        public Builder<E> add(E $$0, int $$1) {
            this.result.add(new Weighted($$0, $$1));
            return this;
        }

        public WeightedList<E> build() {
            return new WeightedList<>(this.result.build());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/random/WeightedList$Flat.class */
    static class Flat<E> implements Selector<E> {
        private final Object[] entries;

        Flat(List<Weighted<E>> $$0, int $$1) {
            this.entries = new Object[$$1];
            int $$2 = 0;
            for (Weighted<E> $$3 : $$0) {
                int $$4 = $$3.weight();
                Arrays.fill(this.entries, $$2, $$2 + $$4, $$3.value());
                $$2 += $$4;
            }
        }

        @Override // net.minecraft.util.random.WeightedList.Selector
        public E get(int i) {
            return (E) this.entries[i];
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/random/WeightedList$Compact.class */
    static class Compact<E> implements Selector<E> {
        private final Weighted<?>[] entries;

        Compact(List<Weighted<E>> $$0) {
            this.entries = (Weighted[]) $$0.toArray($$02 -> {
                return new Weighted[$$02];
            });
        }

        @Override // net.minecraft.util.random.WeightedList.Selector
        public E get(int i) {
            for (Weighted<?> weighted : this.entries) {
                i -= weighted.weight();
                if (i < 0) {
                    return (E) weighted.value();
                }
            }
            throw new IllegalStateException(i + " exceeded total weight");
        }
    }
}
