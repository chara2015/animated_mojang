package net.labymod.api.util.io.zip.entry.factory;

import java.util.zip.ZipEntry;
import net.labymod.api.util.io.zip.entry.ResourceEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/factory/ResourceEntryFactory.class */
public class ResourceEntryFactory extends EntryFactory<ResourceEntry> {
    public ResourceEntryFactory() {
        super(name -> {
            return true;
        });
    }

    @Override // net.labymod.api.util.io.zip.entry.factory.EntryFactory
    public ResourceEntry create(ZipEntry entry, byte[] data) {
        return new ResourceEntry(entry.getName(), entry.getTime(), data);
    }
}
