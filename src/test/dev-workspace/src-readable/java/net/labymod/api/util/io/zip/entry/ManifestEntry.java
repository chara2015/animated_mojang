package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/ManifestEntry.class */
public class ManifestEntry extends AbstractEntry<ManifestEntry> {
    public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public /* bridge */ /* synthetic */ Entry process(EntryTransformer entryTransformer) {
        return process((EntryTransformer<ManifestEntry>) entryTransformer);
    }

    public ManifestEntry(long time, byte[] data) {
        super(MANIFEST_PATH, time, data);
    }

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public ManifestEntry process(EntryTransformer<ManifestEntry> transformer) {
        return (ManifestEntry) transformer.process(this);
    }
}
