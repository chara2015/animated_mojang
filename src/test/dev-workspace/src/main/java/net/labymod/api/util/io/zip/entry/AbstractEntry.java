package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.entry.Entry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/AbstractEntry.class */
public abstract class AbstractEntry<T extends Entry<T>> implements Entry<T> {
    private final String name;
    private final long time;
    private final byte[] data;

    public AbstractEntry(String name, long time, byte[] data) {
        this.name = name;
        this.time = time;
        this.data = data;
    }

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public String getName() {
        return this.name;
    }

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public long getTime() {
        return this.time;
    }

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public byte[] getData() {
        return this.data;
    }
}
