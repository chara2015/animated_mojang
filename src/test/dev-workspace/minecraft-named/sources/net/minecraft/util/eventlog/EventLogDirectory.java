package net.minecraft.util.eventlog;

import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/eventlog/EventLogDirectory.class */
public class EventLogDirectory {
    static final Logger LOGGER = LogUtils.getLogger();
    private static final int COMPRESS_BUFFER_SIZE = 4096;
    private static final String COMPRESSED_EXTENSION = ".gz";
    private final Path root;
    private final String extension;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/eventlog/EventLogDirectory$File.class */
    public interface File {
        Path path();

        FileId id();

        Reader openReader() throws IOException;

        CompressedFile compress() throws IOException;
    }

    private EventLogDirectory(Path $$0, String $$1) {
        this.root = $$0;
        this.extension = $$1;
    }

    public static EventLogDirectory open(Path $$0, String $$1) throws IOException {
        Files.createDirectories($$0, new FileAttribute[0]);
        return new EventLogDirectory($$0, $$1);
    }

    public FileList listFiles() throws IOException {
        Stream<Path> $$0 = Files.list(this.root);
        try {
            FileList fileList = new FileList($$0.filter($$02 -> {
                return Files.isRegularFile($$02, new LinkOption[0]);
            }).map(this::parseFile).filter((v0) -> {
                return Objects.nonNull(v0);
            }).toList());
            if ($$0 != null) {
                $$0.close();
            }
            return fileList;
        } catch (Throwable th) {
            if ($$0 != null) {
                try {
                    $$0.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private File parseFile(Path $$0) {
        FileId $$3;
        String $$1 = $$0.getFileName().toString();
        int $$2 = $$1.indexOf(46);
        if ($$2 != -1 && ($$3 = FileId.parse($$1.substring(0, $$2))) != null) {
            String $$4 = $$1.substring($$2);
            if ($$4.equals(this.extension)) {
                return new RawFile($$0, $$3);
            }
            if ($$4.equals(this.extension + ".gz")) {
                return new CompressedFile($$0, $$3);
            }
            return null;
        }
        return null;
    }

    static void tryCompress(Path $$0, Path $$1) throws IOException {
        if (Files.exists($$1, new LinkOption[0])) {
            throw new IOException("Compressed target file already exists: " + String.valueOf($$1));
        }
        FileChannel $$2 = FileChannel.open($$0, StandardOpenOption.WRITE, StandardOpenOption.READ);
        try {
            FileLock $$3 = $$2.tryLock();
            if ($$3 == null) {
                throw new IOException("Raw log file is already locked, cannot compress: " + String.valueOf($$0));
            }
            writeCompressed($$2, $$1);
            $$2.truncate(0L);
            if ($$2 != null) {
                $$2.close();
            }
            Files.delete($$0);
        } catch (Throwable th) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void writeCompressed(ReadableByteChannel $$0, Path $$1) throws IOException {
        OutputStream $$2 = new GZIPOutputStream(Files.newOutputStream($$1, new OpenOption[0]));
        try {
            byte[] $$3 = new byte[4096];
            ByteBuffer $$4 = ByteBuffer.wrap($$3);
            while ($$0.read($$4) >= 0) {
                $$4.flip();
                $$2.write($$3, 0, $$4.limit());
                $$4.clear();
            }
            $$2.close();
        } catch (Throwable th) {
            try {
                $$2.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public RawFile createNewFile(LocalDate $$0) throws IOException {
        FileId $$3;
        int $$1 = 1;
        Set<FileId> $$2 = listFiles().ids();
        do {
            int i = $$1;
            $$1++;
            $$3 = new FileId($$0, i);
        } while ($$2.contains($$3));
        RawFile $$4 = new RawFile(this.root.resolve($$3.toFileName(this.extension)), $$3);
        Files.createFile($$4.path(), new FileAttribute[0]);
        return $$4;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/eventlog/EventLogDirectory$FileList.class */
    public static class FileList implements Iterable<File> {
        private final List<File> files;

        FileList(List<File> $$0) {
            this.files = new ArrayList($$0);
        }

        public FileList prune(LocalDate $$0, int $$1) {
            this.files.removeIf($$2 -> {
                FileId $$3 = $$2.id();
                LocalDate $$4 = $$3.date().plusDays($$1);
                if (!$$0.isBefore($$4)) {
                    try {
                        Files.delete($$2.path());
                        return true;
                    } catch (IOException $$5) {
                        EventLogDirectory.LOGGER.warn("Failed to delete expired event log file: {}", $$2.path(), $$5);
                        return false;
                    }
                }
                return false;
            });
            return this;
        }

        public FileList compressAll() {
            ListIterator<File> $$0 = this.files.listIterator();
            while ($$0.hasNext()) {
                File $$1 = $$0.next();
                try {
                    $$0.set($$1.compress());
                } catch (IOException $$2) {
                    EventLogDirectory.LOGGER.warn("Failed to compress event log file: {}", $$1.path(), $$2);
                }
            }
            return this;
        }

        @Override // java.lang.Iterable
        public Iterator<File> iterator() {
            return this.files.iterator();
        }

        public Stream<File> stream() {
            return this.files.stream();
        }

        public Set<FileId> ids() {
            return (Set) this.files.stream().map((v0) -> {
                return v0.id();
            }).collect(Collectors.toSet());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/eventlog/EventLogDirectory$RawFile.class */
    public static final class RawFile extends Record implements File {
        private final Path path;
        private final FileId id;

        public RawFile(Path $$0, FileId $$1) {
            this.path = $$0;
            this.id = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RawFile.class), RawFile.class, "path;id", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;->path:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;->id:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RawFile.class), RawFile.class, "path;id", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;->path:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;->id:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RawFile.class, Object.class), RawFile.class, "path;id", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;->path:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;->id:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public Path path() {
            return this.path;
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public FileId id() {
            return this.id;
        }

        public FileChannel openChannel() throws IOException {
            return FileChannel.open(this.path, StandardOpenOption.WRITE, StandardOpenOption.READ);
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public Reader openReader() throws IOException {
            if (Files.exists(this.path, new LinkOption[0])) {
                return Files.newBufferedReader(this.path);
            }
            return null;
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public CompressedFile compress() throws IOException {
            Path $$0 = this.path.resolveSibling(this.path.getFileName().toString() + ".gz");
            EventLogDirectory.tryCompress(this.path, $$0);
            return new CompressedFile($$0, this.id);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/eventlog/EventLogDirectory$CompressedFile.class */
    public static final class CompressedFile extends Record implements File {
        private final Path path;
        private final FileId id;

        public CompressedFile(Path $$0, FileId $$1) {
            this.path = $$0;
            this.id = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CompressedFile.class), CompressedFile.class, "path;id", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;->path:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;->id:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CompressedFile.class), CompressedFile.class, "path;id", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;->path:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;->id:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CompressedFile.class, Object.class), CompressedFile.class, "path;id", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;->path:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;->id:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public Path path() {
            return this.path;
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public FileId id() {
            return this.id;
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public Reader openReader() throws IOException {
            if (!Files.exists(this.path, new LinkOption[0])) {
                return null;
            }
            return new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(this.path, new OpenOption[0])), StandardCharsets.UTF_8));
        }

        @Override // net.minecraft.util.eventlog.EventLogDirectory.File
        public CompressedFile compress() {
            return this;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/eventlog/EventLogDirectory$FileId.class */
    public static final class FileId extends Record {
        private final LocalDate date;
        private final int index;
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

        public FileId(LocalDate $$0, int $$1) {
            this.date = $$0;
            this.index = $$1;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FileId.class), FileId.class, "date;index", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;->date:Ljava/time/LocalDate;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;->index:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FileId.class, Object.class), FileId.class, "date;index", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;->date:Ljava/time/LocalDate;", "FIELD:Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;->index:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public LocalDate date() {
            return this.date;
        }

        public int index() {
            return this.index;
        }

        public static FileId parse(String $$0) {
            int $$1 = $$0.indexOf("-");
            if ($$1 == -1) {
                return null;
            }
            String $$2 = $$0.substring(0, $$1);
            String $$3 = $$0.substring($$1 + 1);
            try {
                return new FileId(LocalDate.parse($$2, DATE_FORMATTER), Integer.parseInt($$3));
            } catch (NumberFormatException | DateTimeParseException e) {
                return null;
            }
        }

        @Override // java.lang.Record
        public String toString() {
            return DATE_FORMATTER.format(this.date) + "-" + this.index;
        }

        public String toFileName(String $$0) {
            return String.valueOf(this) + $$0;
        }
    }
}
