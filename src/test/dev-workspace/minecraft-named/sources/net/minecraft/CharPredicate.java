package net.minecraft;

import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/CharPredicate.class */
@FunctionalInterface
public interface CharPredicate {
    boolean test(char c);

    default CharPredicate and(CharPredicate $$0) {
        Objects.requireNonNull($$0);
        return $$1 -> {
            return test($$1) && $$0.test($$1);
        };
    }

    default CharPredicate negate() {
        return $$0 -> {
            return !test($$0);
        };
    }

    default CharPredicate or(CharPredicate $$0) {
        Objects.requireNonNull($$0);
        return $$1 -> {
            return test($$1) || $$0.test($$1);
        };
    }
}
