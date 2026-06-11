package net.labymod.api.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.Sets;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/CollectionHelper.class */
public class CollectionHelper {
    private static final Supplier<Collection<?>> ARRAY_LIST_SUPPLIER = ArrayList::new;

    private CollectionHelper() {
    }

    public static <T> List<T> asUnmodifiableList(T... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements));
    }

    public static <T> Set<T> asUnmodifiableSet(T... elements) {
        return Collections.unmodifiableSet(Sets.newHashSet(elements));
    }

    public static <T> void copyOfRange(T[] source, T[] destination, int from, int to) {
        int length = to - from;
        if (length < 0) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Provided range has negative length (from: %d, to: %d, length: %d)", Integer.valueOf(from), Integer.valueOf(to), Integer.valueOf(length)));
        }
        System.arraycopy(source, from, destination, 0, length);
    }

    public static <T> boolean contains(T[] array, T filter) {
        for (T t : array) {
            if (Objects.equals(t, filter)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean containsNonNull(T[] array) {
        for (T t : array) {
            if (t != null) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static <T> T get(Collection<T> collection, Predicate<T> predicate) {
        return (T) get(collection, predicate, null);
    }

    public static <T, R> R[] mapArray(T[] tArr, Class<R> cls, @NotNull Function<T, R> function) {
        R[] rArr = (R[]) ((Object[]) Array.newInstance((Class<?>) cls, tArr.length));
        for (int i = 0; i < tArr.length; i++) {
            rArr[i] = function.apply(tArr[i]);
        }
        return rArr;
    }

    @NotNull
    public static <T, R> Collection<R> map(T[] array, @NotNull Function<T, R> mapFunction) {
        return map(Arrays.asList(array), mapFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static <T, R, C extends Collection<T>> Collection<R> map(@NotNull C collection, @NotNull Function<T, R> function) {
        Objects.requireNonNull(function, "mapFunction must not be null");
        ArrayList arrayList = new ArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(function.apply(it.next()));
        }
        return arrayList;
    }

    public static <T> T get(Collection<T> collection, Predicate<T> predicate, T defaultValue) {
        for (T entry : collection) {
            if (predicate.test(entry)) {
                return entry;
            }
        }
        return defaultValue;
    }

    @Nullable
    public static <T> T removeFirstIf(Collection<T> collection, Predicate<T> predicate) {
        T t = (T) get(collection, predicate);
        if (t != null) {
            collection.remove(t);
        }
        return t;
    }

    public static <T> boolean removeIf(Collection<T> collection, Predicate<T> predicate) {
        boolean match = false;
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T entry = iterator.next();
            if (predicate.test(entry)) {
                iterator.remove();
                match = true;
            }
        }
        return match;
    }

    public static <T> boolean anyMatch(Collection<T> entries, Predicate<T> predicate) {
        for (T entry : entries) {
            if (predicate.test(entry)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean anyMatch(T[] entries, Predicate<T> predicate) {
        for (T entry : entries) {
            if (predicate.test(entry)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean allMatch(Collection<T> entries, Predicate<T> predicate) {
        for (T entry : entries) {
            if (!predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean allMatch(T[] entries, Predicate<T> predicate) {
        for (T entry : entries) {
            if (!predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean noneMatch(Collection<T> entries, Predicate<T> predicate) {
        for (T entry : entries) {
            if (predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean noneMatch(T[] entries, Predicate<T> predicate) {
        for (T entry : entries) {
            if (predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }

    public static <T> int indexOf(List<T> entries, Predicate<T> predicate) {
        for (int i = 0; i < entries.size(); i++) {
            if (predicate.test(entries.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static <T> void removeRange(List<T> entries, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("From Index: " + fromIndex + " > To Index: " + toIndex);
        }
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("From Index: " + fromIndex + " < 0");
        }
        if (toIndex > entries.size()) {
            throw new IndexOutOfBoundsException("To Index: " + toIndex + " > Size: " + entries.size());
        }
        if (toIndex > fromIndex) {
            entries.subList(fromIndex, toIndex).clear();
        }
    }

    public static <T> Collection<T> filter(Collection<T> collection, Predicate<T> filter) {
        return filter(collection, ARRAY_LIST_SUPPLIER, filter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <L extends Collection<T>, T> L filter(L collection, Supplier<L> supplier, Predicate<T> predicate) {
        L result = supplier.get();
        for (Object obj : collection) {
            if (predicate.test(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    @ApiStatus.Internal
    @Deprecated
    public static <T> List<T> sortEntriesByType(List<T> entries, Function<T, Boolean> typeChecker) {
        List<T> sortedEntries = new ArrayList<>(entries);
        sortedEntries.sort((o1, o2) -> {
            if (((Boolean) typeChecker.apply(o1)).booleanValue() && !((Boolean) typeChecker.apply(o2)).booleanValue()) {
                return 1;
            }
            if (!((Boolean) typeChecker.apply(o1)).booleanValue() && ((Boolean) typeChecker.apply(o2)).booleanValue()) {
                return -1;
            }
            return 0;
        });
        return sortedEntries;
    }
}
