package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;
import net.labymod.api.util.io.zip.entry.Entry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/Entry.class */
public interface Entry<T extends Entry<T>> {
    String getName();

    long getTime();

    byte[] getData();

    T process(EntryTransformer<T> entryTransformer);
}
