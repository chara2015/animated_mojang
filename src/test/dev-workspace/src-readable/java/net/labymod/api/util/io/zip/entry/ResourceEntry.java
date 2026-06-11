package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/ResourceEntry.class */
public class ResourceEntry extends AbstractEntry<ResourceEntry> {
    @Override // net.labymod.api.util.io.zip.entry.Entry
    public /* bridge */ /* synthetic */ Entry process(EntryTransformer entryTransformer) {
        return process((EntryTransformer<ResourceEntry>) entryTransformer);
    }

    public ResourceEntry(String name, long time, byte[] data) {
        super(name, time, data);
    }

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public ResourceEntry process(EntryTransformer<ResourceEntry> transformer) {
        return (ResourceEntry) transformer.process(this);
    }
}
