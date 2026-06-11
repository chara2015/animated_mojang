package net.minecraft.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.nbt.SnbtOperations;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/FileZipper.class */
public class FileZipper implements Closeable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path outputFile;
    private final Path tempFile;
    private final FileSystem fs;

    public FileZipper(Path $$0) {
        this.outputFile = $$0;
        this.tempFile = $$0.resolveSibling($$0.getFileName().toString() + "_tmp");
        try {
            this.fs = Util.ZIP_FILE_SYSTEM_PROVIDER.newFileSystem(this.tempFile, (Map<String, ?>) ImmutableMap.of("create", SnbtOperations.BUILTIN_TRUE));
        } catch (IOException $$1) {
            throw new UncheckedIOException($$1);
        }
    }

    public void add(Path $$0, String $$1) {
        try {
            Path $$2 = this.fs.getPath(File.separator, new String[0]);
            Path $$3 = $$2.resolve($$0.toString());
            Files.createDirectories($$3.getParent(), new FileAttribute[0]);
            Files.write($$3, $$1.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        } catch (IOException $$4) {
            throw new UncheckedIOException($$4);
        }
    }

    public void add(Path $$0, File $$1) {
        try {
            Path $$2 = this.fs.getPath(File.separator, new String[0]);
            Path $$3 = $$2.resolve($$0.toString());
            Files.createDirectories($$3.getParent(), new FileAttribute[0]);
            Files.copy($$1.toPath(), $$3, new CopyOption[0]);
        } catch (IOException $$4) {
            throw new UncheckedIOException($$4);
        }
    }

    public void add(Path $$0) {
        try {
            Path $$1 = this.fs.getPath(File.separator, new String[0]);
            if (Files.isRegularFile($$0, new LinkOption[0])) {
                Path $$2 = $$1.resolve($$0.getParent().relativize($$0).toString());
                Files.copy($$2, $$0, new CopyOption[0]);
                return;
            }
            Stream<Path> $$3 = Files.find($$0, Integer.MAX_VALUE, ($$02, $$12) -> {
                return $$12.isRegularFile();
            }, new FileVisitOption[0]);
            try {
                for (Path $$4 : (List) $$3.collect(Collectors.toList())) {
                    Path $$5 = $$1.resolve($$0.relativize($$4).toString());
                    Files.createDirectories($$5.getParent(), new FileAttribute[0]);
                    Files.copy($$4, $$5, new CopyOption[0]);
                }
                if ($$3 != null) {
                    $$3.close();
                }
            } finally {
            }
        } catch (IOException $$6) {
            throw new UncheckedIOException($$6);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            this.fs.close();
            Files.move(this.tempFile, this.outputFile, new CopyOption[0]);
            LOGGER.info("Compressed to {}", this.outputFile);
        } catch (IOException $$0) {
            throw new UncheckedIOException($$0);
        }
    }
}
