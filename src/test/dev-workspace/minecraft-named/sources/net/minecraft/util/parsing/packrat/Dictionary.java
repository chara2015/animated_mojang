package net.minecraft.util.parsing.packrat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.util.parsing.packrat.Rule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Dictionary.class */
public class Dictionary<S> {
    private final Map<Atom<?>, Entry<S, ?>> terms = new IdentityHashMap();

    public <T> NamedRule<S, T> put(Atom<T> $$0, Rule<S, T> $$1) {
        Entry<S, ?> entryComputeIfAbsent = this.terms.computeIfAbsent($$0, Entry::new);
        if (entryComputeIfAbsent.value != null) {
            throw new IllegalArgumentException("Trying to override rule: " + String.valueOf($$0));
        }
        entryComputeIfAbsent.value = $$1;
        return entryComputeIfAbsent;
    }

    public <T> NamedRule<S, T> putComplex(Atom<T> $$0, Term<S> $$1, Rule.RuleAction<S, T> $$2) {
        return put($$0, Rule.fromTerm($$1, $$2));
    }

    public <T> NamedRule<S, T> put(Atom<T> $$0, Term<S> $$1, Rule.SimpleRuleAction<S, T> $$2) {
        return put($$0, Rule.fromTerm((Term) $$1, (Rule.SimpleRuleAction) $$2));
    }

    public void checkAllBound() {
        List<? extends Atom<?>> $$0 = this.terms.entrySet().stream().filter($$02 -> {
            return ((Entry) $$02.getValue()).value == null;
        }).map((v0) -> {
            return v0.getKey();
        }).toList();
        if (!$$0.isEmpty()) {
            throw new IllegalStateException("Unbound names: " + String.valueOf($$0));
        }
    }

    public <T> NamedRule<S, T> getOrThrow(Atom<T> $$0) {
        return (NamedRule) Objects.requireNonNull(this.terms.get($$0), (Supplier<String>) () -> {
            return "No rule called " + String.valueOf($$0);
        });
    }

    public <T> NamedRule<S, T> forward(Atom<T> $$0) {
        return getOrCreateEntry($$0);
    }

    private <T> Entry<S, T> getOrCreateEntry(Atom<T> atom) {
        return this.terms.computeIfAbsent(atom, Entry::new);
    }

    public <T> Term<S> named(Atom<T> $$0) {
        return new Reference(getOrCreateEntry($$0), $$0);
    }

    public <T> Term<S> namedWithAlias(Atom<T> $$0, Atom<T> $$1) {
        return new Reference(getOrCreateEntry($$0), $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Dictionary$Reference.class */
    static final class Reference<S, T> extends Record implements Term<S> {
        private final Entry<S, T> ruleToParse;
        private final Atom<T> nameToStore;

        Reference(Entry<S, T> $$0, Atom<T> $$1) {
            this.ruleToParse = $$0;
            this.nameToStore = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Reference.class), Reference.class, "ruleToParse;nameToStore", "FIELD:Lnet/minecraft/util/parsing/packrat/Dictionary$Reference;->ruleToParse:Lnet/minecraft/util/parsing/packrat/Dictionary$Entry;", "FIELD:Lnet/minecraft/util/parsing/packrat/Dictionary$Reference;->nameToStore:Lnet/minecraft/util/parsing/packrat/Atom;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Reference.class), Reference.class, "ruleToParse;nameToStore", "FIELD:Lnet/minecraft/util/parsing/packrat/Dictionary$Reference;->ruleToParse:Lnet/minecraft/util/parsing/packrat/Dictionary$Entry;", "FIELD:Lnet/minecraft/util/parsing/packrat/Dictionary$Reference;->nameToStore:Lnet/minecraft/util/parsing/packrat/Atom;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Reference.class, Object.class), Reference.class, "ruleToParse;nameToStore", "FIELD:Lnet/minecraft/util/parsing/packrat/Dictionary$Reference;->ruleToParse:Lnet/minecraft/util/parsing/packrat/Dictionary$Entry;", "FIELD:Lnet/minecraft/util/parsing/packrat/Dictionary$Reference;->nameToStore:Lnet/minecraft/util/parsing/packrat/Atom;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Entry<S, T> ruleToParse() {
            return this.ruleToParse;
        }

        public Atom<T> nameToStore() {
            return this.nameToStore;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.minecraft.util.parsing.packrat.Term
        public boolean parse(ParseState<S> $$0, Scope scope, Control $$2) {
            Object obj = $$0.parse(this.ruleToParse);
            if (obj == null) {
                return false;
            }
            scope.put(this.nameToStore, obj);
            return true;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Dictionary$Entry.class */
    static class Entry<S, T> implements NamedRule<S, T>, Supplier<String> {
        private final Atom<T> name;
        Rule<S, T> value;

        private Entry(Atom<T> $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.parsing.packrat.NamedRule
        public Atom<T> name() {
            return this.name;
        }

        @Override // net.minecraft.util.parsing.packrat.NamedRule
        public Rule<S, T> value() {
            return (Rule) Objects.requireNonNull(this.value, this);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.function.Supplier
        public String get() {
            return "Unbound rule " + String.valueOf(this.name);
        }
    }
}
