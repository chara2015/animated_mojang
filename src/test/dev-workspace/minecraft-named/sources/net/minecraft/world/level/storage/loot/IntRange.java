package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/IntRange.class */
public class IntRange {
    private static final Codec<IntRange> RECORD_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(NumberProviders.CODEC.optionalFieldOf("min").forGetter($$0 -> {
            return Optional.ofNullable($$0.min);
        }), NumberProviders.CODEC.optionalFieldOf("max").forGetter($$02 -> {
            return Optional.ofNullable($$02.max);
        })).apply($$0, IntRange::new);
    });
    public static final Codec<IntRange> CODEC = Codec.either(Codec.INT, RECORD_CODEC).xmap($$0 -> {
        return (IntRange) $$0.map((v0) -> {
            return exact(v0);
        }, Function.identity());
    }, $$02 -> {
        OptionalInt $$1 = $$02.unpackExact();
        if ($$1.isPresent()) {
            return Either.left(Integer.valueOf($$1.getAsInt()));
        }
        return Either.right($$02);
    });
    private final NumberProvider min;
    private final NumberProvider max;
    private final IntLimiter limiter;
    private final IntChecker predicate;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/IntRange$IntChecker.class */
    @FunctionalInterface
    interface IntChecker {
        boolean test(LootContext lootContext, int i);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/IntRange$IntLimiter.class */
    @FunctionalInterface
    interface IntLimiter {
        int apply(LootContext lootContext, int i);
    }

    public Set<ContextKey<?>> getReferencedContextParams() {
        ImmutableSet.Builder<ContextKey<?>> $$0 = ImmutableSet.builder();
        if (this.min != null) {
            $$0.addAll(this.min.getReferencedContextParams());
        }
        if (this.max != null) {
            $$0.addAll(this.max.getReferencedContextParams());
        }
        return $$0.build();
    }

    private IntRange(Optional<NumberProvider> $$0, Optional<NumberProvider> $$1) {
        this($$0.orElse(null), $$1.orElse(null));
    }

    private IntRange(NumberProvider $$0, NumberProvider $$1) {
        this.min = $$0;
        this.max = $$1;
        if ($$0 == null) {
            if ($$1 == null) {
                this.limiter = ($$02, $$12) -> {
                    return $$12;
                };
                this.predicate = ($$03, $$13) -> {
                    return true;
                };
                return;
            } else {
                this.limiter = ($$14, $$2) -> {
                    return Math.min($$1.getInt($$14), $$2);
                };
                this.predicate = ($$15, $$22) -> {
                    return $$22 <= $$1.getInt($$15);
                };
                return;
            }
        }
        if ($$1 == null) {
            this.limiter = ($$16, $$23) -> {
                return Math.max($$0.getInt($$16), $$23);
            };
            this.predicate = ($$17, $$24) -> {
                return $$24 >= $$0.getInt($$17);
            };
        } else {
            this.limiter = ($$25, $$3) -> {
                return Mth.clamp($$3, $$0.getInt($$25), $$1.getInt($$25));
            };
            this.predicate = ($$26, $$32) -> {
                return $$32 >= $$0.getInt($$26) && $$32 <= $$1.getInt($$26);
            };
        }
    }

    public static IntRange exact(int $$0) {
        ConstantValue $$1 = ConstantValue.exactly($$0);
        return new IntRange((Optional<NumberProvider>) Optional.of($$1), (Optional<NumberProvider>) Optional.of($$1));
    }

    public static IntRange range(int $$0, int $$1) {
        return new IntRange((Optional<NumberProvider>) Optional.of(ConstantValue.exactly($$0)), (Optional<NumberProvider>) Optional.of(ConstantValue.exactly($$1)));
    }

    public static IntRange lowerBound(int $$0) {
        return new IntRange((Optional<NumberProvider>) Optional.of(ConstantValue.exactly($$0)), (Optional<NumberProvider>) Optional.empty());
    }

    public static IntRange upperBound(int $$0) {
        return new IntRange((Optional<NumberProvider>) Optional.empty(), (Optional<NumberProvider>) Optional.of(ConstantValue.exactly($$0)));
    }

    public int clamp(LootContext $$0, int $$1) {
        return this.limiter.apply($$0, $$1);
    }

    public boolean test(LootContext $$0, int $$1) {
        return this.predicate.test($$0, $$1);
    }

    private OptionalInt unpackExact() {
        if (Objects.equals(this.min, this.max)) {
            NumberProvider numberProvider = this.min;
            if (numberProvider instanceof ConstantValue) {
                ConstantValue $$0 = (ConstantValue) numberProvider;
                if (Math.floor($$0.value()) == $$0.value()) {
                    return OptionalInt.of((int) $$0.value());
                }
            }
        }
        return OptionalInt.empty();
    }
}
