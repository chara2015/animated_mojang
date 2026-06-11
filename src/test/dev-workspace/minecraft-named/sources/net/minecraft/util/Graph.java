package net.minecraft.util;

import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/Graph.class */
public final class Graph {
    private Graph() {
    }

    public static <T> boolean depthFirstSearch(Map<T, Set<T>> $$0, Set<T> $$1, Set<T> $$2, Consumer<T> $$3, T $$4) {
        if ($$1.contains($$4)) {
            return false;
        }
        if ($$2.contains($$4)) {
            return true;
        }
        $$2.add($$4);
        for (T $$5 : $$0.getOrDefault($$4, ImmutableSet.of())) {
            if (depthFirstSearch($$0, $$1, $$2, $$3, $$5)) {
                return true;
            }
        }
        $$2.remove($$4);
        $$1.add($$4);
        $$3.accept($$4);
        return false;
    }
}
