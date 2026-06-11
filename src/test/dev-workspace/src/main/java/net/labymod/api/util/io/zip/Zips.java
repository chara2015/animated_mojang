package net.labymod.api.util.io.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import net.labymod.api.util.function.ThrowableBiFunction;
import net.labymod.api.util.io.IOUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/Zips.class */
public final class Zips {
    private Zips() {
    }

    public static ZipTransformer createDefaultTransformer(File source, File destination) {
        return ZipTransformer.createDefault(source.toPath(), destination.toPath());
    }

    public static ZipTransformer createDefaultTransformer(Path source, Path destination) {
        return ZipTransformer.createDefault(source, destination);
    }

    public static ZipTransformer createDefaultTransformer(File source, File destination, Consumer<ZipTransformer> consumer) {
        return ZipTransformer.createDefault(source.toPath(), destination.toPath(), consumer);
    }

    public static ZipTransformer createDefaultTransformer(Path source, Path destination, Consumer<ZipTransformer> consumer) {
        return ZipTransformer.createDefault(source, destination, consumer);
    }

    public static void readStream(InputStream stream, ThrowableBiFunction<ZipEntry, byte[], Boolean, IOException> consumer) throws ZipException {
        Objects.requireNonNull(consumer, "consumer must not be null");
        try {
            ZipInputStream zipInputStream = new ZipInputStream(stream);
            while (true) {
                try {
                    ZipEntry entry = zipInputStream.getNextEntry();
                    if (entry == null || consumer.apply(entry, zipInputStream.readAllBytes()).booleanValue()) {
                        break;
                    } else {
                        zipInputStream.closeEntry();
                    }
                } finally {
                }
            }
            zipInputStream.close();
        } catch (IOException exception) {
            throw new ZipException(null, exception);
        }
    }

    public static void read(Path source, ThrowableBiFunction<ZipEntry, byte[], Boolean, IOException> consumer) throws ZipException {
        Objects.requireNonNull(consumer, "consumer must not be null");
        try {
            ZipFile zipFile = new ZipFile(IOUtil.toFile(source));
            try {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    if (consumer.apply(entry, IOUtil.readBytes(zipFile.getInputStream(entry))).booleanValue()) {
                        break;
                    }
                }
                zipFile.close();
            } finally {
            }
        } catch (IOException exception) {
            throw new ZipException(null, exception);
        }
    }
}
