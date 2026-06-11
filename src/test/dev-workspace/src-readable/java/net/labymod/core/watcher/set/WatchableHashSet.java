package net.labymod.core.watcher.set;

import java.util.Collection;
import java.util.HashSet;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/watcher/set/WatchableHashSet.class */
public class WatchableHashSet<T> extends HashSet<T> {
    private final WatchableSet<T> watchableSet;

    public WatchableHashSet(WatchableSet<T> watchableSet) {
        this.watchableSet = watchableSet;
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(T t) {
        this.watchableSet.onAdd(t);
        return super.add(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean addAll(@NotNull Collection<? extends T> collection) {
        this.watchableSet.onAddAll(collection);
        return super.addAll(collection);
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object o) {
        this.watchableSet.onRemove(o);
        return super.remove(o);
    }

    @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.watchableSet.onClear();
        super.clear();
    }
}
