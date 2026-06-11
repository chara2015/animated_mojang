package net.labymod.api.util.io.zip.entry.factory;

import java.util.zip.ZipEntry;
import net.labymod.api.util.io.zip.entry.ClassEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/factory/ClassEntryFactory.class */
public class ClassEntryFactory extends EntryFactory<ClassEntry> {
    public ClassEntryFactory() {
        super(name -> {
            return name.endsWith(".class");
        });
    }

    @Override // net.labymod.api.util.io.zip.entry.factory.EntryFactory
    public ClassEntry create(ZipEntry entry, byte[] data) {
        return new ClassEntry(entry.getName(), entry.getTime(), data);
    }
}
