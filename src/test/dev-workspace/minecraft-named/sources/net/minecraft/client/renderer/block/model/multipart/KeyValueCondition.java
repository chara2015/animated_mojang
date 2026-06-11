package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/KeyValueCondition.class */
public final class KeyValueCondition extends Record implements Condition {
    private final Map<String, Terms> tests;
    static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<KeyValueCondition> CODEC = ExtraCodecs.nonEmptyMap(Codec.unboundedMap(Codec.STRING, Terms.CODEC)).xmap(KeyValueCondition::new, (v0) -> {
        return v0.tests();
    });

    public KeyValueCondition(Map<String, Terms> $$0) {
        this.tests = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, KeyValueCondition.class), KeyValueCondition.class, "tests", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition;->tests:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, KeyValueCondition.class), KeyValueCondition.class, "tests", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition;->tests:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, KeyValueCondition.class, Object.class), KeyValueCondition.class, "tests", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition;->tests:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Map<String, Terms> tests() {
        return this.tests;
    }

    @Override // net.minecraft.client.renderer.block.model.multipart.Condition
    public <O, S extends StateHolder<O, S>> Predicate<S> instantiate(StateDefinition<O, S> $$0) {
        List<Predicate<S>> $$1 = new ArrayList<>(this.tests.size());
        this.tests.forEach(($$2, $$3) -> {
            $$1.add(instantiate($$0, $$2, $$3));
        });
        return Util.allOf($$1);
    }

    private static <O, S extends StateHolder<O, S>> Predicate<S> instantiate(StateDefinition<O, S> $$0, String $$1, Terms $$2) {
        Property<?> $$3 = $$0.getProperty($$1);
        if ($$3 == null) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Unknown property '%s' on '%s'", $$1, $$0.getOwner()));
        }
        return $$2.instantiate($$0.getOwner(), $$3);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Terms.class */
    public static final class Terms extends Record {
        private final List<Term> entries;
        private static final char SEPARATOR = '|';
        private static final Joiner JOINER = Joiner.on('|');
        private static final Splitter SPLITTER = Splitter.on('|');
        private static final Codec<String> LEGACY_REPRESENTATION_CODEC = Codec.either(Codec.INT, Codec.BOOL).flatComapMap($$0 -> {
            return (String) $$0.map((v0) -> {
                return String.valueOf(v0);
            }, (v0) -> {
                return String.valueOf(v0);
            });
        }, $$02 -> {
            return DataResult.error(() -> {
                return "This codec can't be used for encoding";
            });
        });
        public static final Codec<Terms> CODEC = Codec.withAlternative(Codec.STRING, LEGACY_REPRESENTATION_CODEC).comapFlatMap(Terms::parse, (v0) -> {
            return v0.toString();
        });

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Terms.class), Terms.class, "entries", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Terms;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Terms.class, Object.class), Terms.class, "entries", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Terms;->entries:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<Term> entries() {
            return this.entries;
        }

        public Terms(List<Term> $$0) {
            if (!$$0.isEmpty()) {
                this.entries = $$0;
                return;
            }
            throw new IllegalArgumentException("Empty value for property");
        }

        public static DataResult<Terms> parse(String $$0) {
            List<Term> $$1 = SPLITTER.splitToStream($$0).map(Term::parse).toList();
            if ($$1.isEmpty()) {
                return DataResult.error(() -> {
                    return "Empty value for property";
                });
            }
            for (Term $$2 : $$1) {
                if ($$2.value.isEmpty()) {
                    return DataResult.error(() -> {
                        return "Empty term in value '" + $$0 + "'";
                    });
                }
            }
            return DataResult.success(new Terms($$1));
        }

        @Override // java.lang.Record
        public String toString() {
            return JOINER.join(this.entries);
        }

        public <O, S extends StateHolder<O, S>, T extends Comparable<T>> Predicate<S> instantiate(O $$0, Property<T> $$1) {
            boolean $$9;
            List<T> $$11;
            Predicate<T> $$2 = Util.anyOf(Lists.transform(this.entries, $$22 -> {
                return instantiate($$0, $$1, $$22);
            }));
            List<T> $$3 = new ArrayList<>($$1.getPossibleValues());
            int $$4 = $$3.size();
            $$3.removeIf($$2.negate());
            int $$5 = $$3.size();
            if ($$5 == 0) {
                KeyValueCondition.LOGGER.warn("Condition {} for property {} on {} is always false", new Object[]{this, $$1.getName(), $$0});
                return $$02 -> {
                    return false;
                };
            }
            int $$6 = $$4 - $$5;
            if ($$6 == 0) {
                KeyValueCondition.LOGGER.warn("Condition {} for property {} on {} is always true", new Object[]{this, $$1.getName(), $$0});
                return $$03 -> {
                    return true;
                };
            }
            if ($$5 <= $$6) {
                $$9 = false;
                $$11 = $$3;
            } else {
                $$9 = true;
                List<T> $$10 = new ArrayList<>($$1.getPossibleValues());
                $$10.removeIf($$2);
                $$11 = $$10;
            }
            if ($$11.size() == 1) {
                Comparable comparable = (Comparable) $$11.getFirst();
                boolean z = $$9;
                return $$32 -> {
                    return comparable.equals($$32.getValue($$1)) ^ z;
                };
            }
            List<T> list = $$11;
            boolean z2 = $$9;
            return $$33 -> {
                return list.contains($$33.getValue($$1)) ^ z2;
            };
        }

        private <T extends Comparable<T>> T getValueOrThrow(Object $$0, Property<T> $$1, String $$2) {
            Optional<T> $$3 = $$1.getValue($$2);
            if ($$3.isEmpty()) {
                throw new RuntimeException(String.format(Locale.ROOT, "Unknown value '%s' for property '%s' on '%s' in '%s'", $$2, $$1, $$0, this));
            }
            return $$3.get();
        }

        private <T extends Comparable<T>> Predicate<T> instantiate(Object $$0, Property<T> $$1, Term $$2) {
            Comparable valueOrThrow = getValueOrThrow($$0, $$1, $$2.value);
            if ($$2.negated) {
                return $$12 -> {
                    return !$$12.equals(valueOrThrow);
                };
            }
            return $$13 -> {
                return $$13.equals(valueOrThrow);
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Term.class */
    public static final class Term extends Record {
        private final String value;
        private final boolean negated;
        private static final String NEGATE = "!";

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Term.class), Term.class, "value;negated", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Term;->value:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Term;->negated:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Term.class, Object.class), Term.class, "value;negated", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Term;->value:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/KeyValueCondition$Term;->negated:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String value() {
            return this.value;
        }

        public boolean negated() {
            return this.negated;
        }

        public Term(String $$0, boolean $$1) {
            if (!$$0.isEmpty()) {
                this.value = $$0;
                this.negated = $$1;
                return;
            }
            throw new IllegalArgumentException("Empty term");
        }

        public static Term parse(String $$0) {
            if ($$0.startsWith(NEGATE)) {
                return new Term($$0.substring(1), true);
            }
            return new Term($$0, false);
        }

        @Override // java.lang.Record
        public String toString() {
            return this.negated ? "!" + this.value : this.value;
        }
    }
}
