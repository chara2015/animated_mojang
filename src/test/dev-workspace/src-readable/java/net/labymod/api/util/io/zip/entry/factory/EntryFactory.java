package net.labymod.api.util.io.zip.entry.factory;

import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import net.labymod.api.util.io.zip.entry.Entry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/factory/EntryFactory.class */
public abstract class EntryFactory<T extends Entry<T>> {
    private final Predicate<String> condition;

    public abstract T create(ZipEntry zipEntry, byte[] bArr);

    protected EntryFactory(Predicate<String> condition) {
        this.condition = condition;
    }

    public boolean shouldCreate(String name) {
        return this.condition.test(name);
    }
}
