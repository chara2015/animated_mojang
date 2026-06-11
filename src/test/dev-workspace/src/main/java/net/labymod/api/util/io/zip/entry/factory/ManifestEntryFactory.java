package net.labymod.api.util.io.zip.entry.factory;

import java.util.Objects;
import java.util.zip.ZipEntry;
import net.labymod.api.util.io.zip.entry.ManifestEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/factory/ManifestEntryFactory.class */
public class ManifestEntryFactory extends EntryFactory<ManifestEntry> {
    public ManifestEntryFactory() {
        super(name -> {
            return Objects.equals(name, ManifestEntry.MANIFEST_PATH);
        });
    }

    @Override // net.labymod.api.util.io.zip.entry.factory.EntryFactory
    public ManifestEntry create(ZipEntry entry, byte[] data) {
        return new ManifestEntry(entry.getTime(), data);
    }
}
