package net.labymod.api.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/Lists.class */
public final class Lists {
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... entries) {
        ArrayList<T> list = new ArrayList<>(entries.length);
        Collections.addAll(list, entries);
        return list;
    }

    public static <T> ArrayList<T> newArrayListWithCapacity(int capacity) {
        return new ArrayList<>(capacity);
    }

    public static <T> ArrayList<T> newArrayList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    @NotNull
    public static <T> ArrayList<T> newDistinctArrayList(@NotNull Collection<T> collection, boolean originalOrder) {
        Set<T> set = originalOrder ? new LinkedHashSet<>(collection) : new HashSet<>(collection);
        return new ArrayList<>(set);
    }
}
