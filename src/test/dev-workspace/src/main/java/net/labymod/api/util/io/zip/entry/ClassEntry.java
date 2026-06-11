package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/ClassEntry.class */
public class ClassEntry extends AbstractEntry<ClassEntry> {
    private static final int CLASS_LENGTH = ".class".length();
    private final String className;

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public /* bridge */ /* synthetic */ Entry process(EntryTransformer entryTransformer) {
        return process((EntryTransformer<ClassEntry>) entryTransformer);
    }

    public ClassEntry(String name, long time, byte[] data) {
        super(name, time, data);
        this.className = name.substring(0, name.length() - CLASS_LENGTH);
    }

    public String getClassName() {
        return this.className;
    }

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public ClassEntry process(EntryTransformer<ClassEntry> transformer) {
        return (ClassEntry) transformer.process(this);
    }
}
