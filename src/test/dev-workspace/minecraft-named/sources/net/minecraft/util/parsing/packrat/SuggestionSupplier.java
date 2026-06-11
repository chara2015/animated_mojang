package net.minecraft.util.parsing.packrat;

import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/SuggestionSupplier.class */
public interface SuggestionSupplier<S> {
    Stream<String> possibleValues(ParseState<S> parseState);

    static <S> SuggestionSupplier<S> empty() {
        return $$0 -> {
            return Stream.empty();
        };
    }
}
