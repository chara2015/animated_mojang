package net.labymod.api.util.io.zip;

import java.util.function.Predicate;
import net.labymod.api.util.io.zip.entry.Entry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/EntryTransformer.class */
public abstract class EntryTransformer<T extends Entry<?>> {
    private final Predicate<Entry<?>> processPredicate;

    public abstract T process(T t);

    public EntryTransformer(Predicate<Entry<?>> processPredicate) {
        this.processPredicate = processPredicate;
    }

    public boolean canProcess(T entry) {
        return this.processPredicate.test(entry);
    }
}
