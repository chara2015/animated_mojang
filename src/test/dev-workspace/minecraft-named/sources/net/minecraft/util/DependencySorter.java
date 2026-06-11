package net.minecraft.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.util.DependencySorter.Entry;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/DependencySorter.class */
public class DependencySorter<K, V extends Entry<K>> {
    private final Map<K, V> contents = new HashMap();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/DependencySorter$Entry.class */
    public interface Entry<K> {
        void visitRequiredDependencies(Consumer<K> consumer);

        void visitOptionalDependencies(Consumer<K> consumer);
    }

    public DependencySorter<K, V> addEntry(K $$0, V $$1) {
        this.contents.put($$0, $$1);
        return this;
    }

    private void visitDependenciesAndElement(Multimap<K, K> $$0, Set<K> $$1, K $$2, BiConsumer<K, V> $$3) {
        if (!$$1.add($$2)) {
            return;
        }
        $$0.get($$2).forEach(obj -> {
            visitDependenciesAndElement($$0, $$1, obj, $$3);
        });
        V $$4 = this.contents.get($$2);
        if ($$4 != null) {
            $$3.accept($$2, $$4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K> boolean isCyclic(Multimap<K, K> $$0, K $$1, K $$2) {
        Collection<K> $$3 = $$0.get($$2);
        if ($$3.contains($$1)) {
            return true;
        }
        return $$3.stream().anyMatch($$22 -> {
            return isCyclic($$0, $$1, $$22);
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K> void addDependencyIfNotCyclic(Multimap<K, K> $$0, K $$1, K $$2) {
        if (!isCyclic($$0, $$1, $$2)) {
            $$0.put($$1, $$2);
        }
    }

    public void orderByDependencies(BiConsumer<K, V> $$0) {
        HashMultimap hashMultimapCreate = HashMultimap.create();
        this.contents.forEach(($$1, $$2) -> {
            $$2.visitRequiredDependencies($$2 -> {
                addDependencyIfNotCyclic(hashMultimapCreate, $$1, $$2);
            });
        });
        this.contents.forEach(($$12, $$22) -> {
            $$22.visitOptionalDependencies($$22 -> {
                addDependencyIfNotCyclic(hashMultimapCreate, $$12, $$22);
            });
        });
        Set<K> $$23 = new HashSet<>();
        this.contents.keySet().forEach(obj -> {
            visitDependenciesAndElement(hashMultimapCreate, $$23, obj, $$0);
        });
    }
}
