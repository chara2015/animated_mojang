package net.minecraft.util.parsing.packrat;

import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/ParseState.class */
public interface ParseState<S> {
    Scope scope();

    ErrorCollector<S> errorCollector();

    <T> T parse(NamedRule<S, T> namedRule);

    S input();

    int mark();

    void restore(int i);

    Control acquireControl();

    void releaseControl();

    ParseState<S> silent();

    default <T> Optional<T> parseTopRule(NamedRule<S, T> $$0) {
        Object obj = parse($$0);
        if (obj != null) {
            errorCollector().finish(mark());
        }
        if (!scope().hasOnlySingleFrame()) {
            throw new IllegalStateException("Malformed scope: " + String.valueOf(scope()));
        }
        return Optional.ofNullable(obj);
    }
}
