package net.labymod.api.component.data;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/component/data/DataComponentContainer.class */
public interface DataComponentContainer extends Iterable<TypedDataComponent<?>> {
    public static final DataComponentContainer EMPTY = new DataComponentContainer() { // from class: net.labymod.api.component.data.DataComponentContainer.1
        @Override // java.lang.Iterable
        @NotNull
        public Iterator<TypedDataComponent<?>> iterator() {
            return Collections.emptyIterator();
        }

        @Override // net.labymod.api.component.data.DataComponentContainer
        public Set<DataComponentKey> keySet() {
            return Set.of();
        }

        @Override // net.labymod.api.component.data.DataComponentContainer
        public boolean has(DataComponentKey key) {
            return false;
        }

        @Override // net.labymod.api.component.data.DataComponentContainer
        public <T> T get(DataComponentKey key) {
            return null;
        }

        @Override // net.labymod.api.component.data.DataComponentContainer
        public <T> T getOrDefault(DataComponentKey key, T defaultValue) {
            return null;
        }

        @Override // net.labymod.api.component.data.DataComponentContainer
        public <T> void set(DataComponentKey key, T value) {
        }

        @Override // net.labymod.api.component.data.DataComponentContainer
        public void apply(DataComponentPatch patch) {
        }
    };

    Set<DataComponentKey> keySet();

    boolean has(DataComponentKey dataComponentKey);

    <T> T get(DataComponentKey dataComponentKey);

    <T> T getOrDefault(DataComponentKey dataComponentKey, T t);

    <T> void set(DataComponentKey dataComponentKey, T t);

    void apply(DataComponentPatch dataComponentPatch);

    default int size() {
        return keySet().size();
    }

    default boolean isEmpty() {
        return size() == 0;
    }
}
