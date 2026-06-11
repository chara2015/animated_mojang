package net.labymod.api.laby3d.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/Pool.class */
@Deprecated
public abstract class Pool<T> implements AutoCloseable {
    private final List<Entry<T>> entries = new ArrayList();
    private final Set<Entry<T>> usedEntries = new HashSet();

    protected abstract Entry<T> createEntry();

    public T acquire() {
        for (Entry<T> entry : this.entries) {
            if (!this.usedEntries.contains(entry) && validateEntry(entry)) {
                this.usedEntries.add(entry);
                entry.setLastUsed();
                return entry.getValue();
            }
        }
        Entry<T> newEntry = createEntry();
        this.entries.add(newEntry);
        this.usedEntries.add(newEntry);
        return newEntry.getValue();
    }

    public void release(T value) {
        for (Entry<T> entry : this.entries) {
            if (entry.getValue().equals(value)) {
                this.usedEntries.remove(entry);
                return;
            }
        }
    }

    public void onClose(Entry<T> entry) {
    }

    protected boolean validateEntry(Entry<T> entry) {
        return true;
    }

    public void reset() {
        this.usedEntries.clear();
    }

    public List<Entry<T>> entries() {
        return this.entries;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.entries.forEach(this::onClose);
        this.entries.clear();
        this.usedEntries.clear();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/Pool$Entry.class */
    public static class Entry<T> {
        private final T value;
        private long lastUsed;

        public Entry(T value) {
            this.value = value;
            setLastUsed();
        }

        public T getValue() {
            return this.value;
        }

        public long getLastUsed() {
            return this.lastUsed;
        }

        public void setLastUsed() {
            this.lastUsed = TimeUtil.getCurrentTimeMillis();
        }
    }
}
