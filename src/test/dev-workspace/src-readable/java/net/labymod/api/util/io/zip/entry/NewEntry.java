package net.labymod.api.util.io.zip.entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.labymod.api.util.io.zip.EntryTransformer;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/entry/NewEntry.class */
public class NewEntry extends AbstractEntry<NewEntry> {
    @Override // net.labymod.api.util.io.zip.entry.Entry
    public /* bridge */ /* synthetic */ Entry process(EntryTransformer entryTransformer) {
        return process((EntryTransformer<NewEntry>) entryTransformer);
    }

    private NewEntry(String name, long time, byte[] data) {
        super(name, time, data);
    }

    public static NewEntry create(String name, Path path, long time) throws IOException {
        return new NewEntry(name, time, Files.readAllBytes(path));
    }

    public static NewEntry create(Path path, long time) throws IOException {
        return create(path.getFileName().toString(), path, time);
    }

    public static NewEntry create(String name, Path path) throws IOException {
        return create(name, path, TimeUtil.getCurrentTimeMillis());
    }

    public static NewEntry create(Path path) throws IOException {
        return create(path, TimeUtil.getCurrentTimeMillis());
    }

    public static NewEntry create(String name, long time, byte[] data) {
        return new NewEntry(name, time, data);
    }

    @Override // net.labymod.api.util.io.zip.entry.Entry
    public NewEntry process(EntryTransformer<NewEntry> transformer) {
        throw new UnsupportedOperationException("An new entry cannot be transformed");
    }
}
