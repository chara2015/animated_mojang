package net.minecraft.server.packs.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/IoSupplier.class */
@FunctionalInterface
public interface IoSupplier<T> {
    T get() throws IOException;

    static IoSupplier<InputStream> create(Path $$0) {
        return () -> {
            return Files.newInputStream($$0, new OpenOption[0]);
        };
    }

    static IoSupplier<InputStream> create(ZipFile $$0, ZipEntry $$1) {
        return () -> {
            return $$0.getInputStream($$1);
        };
    }
}
