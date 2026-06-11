package net.minecraft.client.searchtree;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/searchtree/SearchTree.class */
@FunctionalInterface
public interface SearchTree<T> {
    List<T> search(String str);

    static <T> SearchTree<T> empty() {
        return $$0 -> {
            return List.of();
        };
    }

    static <T> SearchTree<T> plainText(List<T> $$0, Function<T, Stream<String>> $$1) {
        if ($$0.isEmpty()) {
            return empty();
        }
        SuffixArray<T> $$2 = new SuffixArray<>();
        for (T $$3 : $$0) {
            $$1.apply($$3).forEach($$22 -> {
                $$2.add($$3, $$22.toLowerCase(Locale.ROOT));
            });
        }
        $$2.generate();
        Objects.requireNonNull($$2);
        return $$2::search;
    }
}
