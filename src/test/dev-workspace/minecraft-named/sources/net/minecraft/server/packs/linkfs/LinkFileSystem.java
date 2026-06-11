package net.minecraft.server.packs.linkfs;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.packs.linkfs.PathContents;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/LinkFileSystem.class */
public class LinkFileSystem extends FileSystem {
    public static final String PATH_SEPARATOR = "/";
    private final FileStore store;
    private final FileSystemProvider provider = new LinkFSProvider();
    private final LinkFSPath root;
    private static final Set<String> VIEWS = Set.of("basic");
    private static final Splitter PATH_SPLITTER = Splitter.on('/');

    LinkFileSystem(String $$0, DirectoryEntry $$1) {
        this.store = new LinkFSFileStore($$0);
        this.root = buildPath($$1, this, "", null);
    }

    private static LinkFSPath buildPath(DirectoryEntry $$0, LinkFileSystem $$1, String $$2, LinkFSPath $$3) {
        Object2ObjectOpenHashMap<String, LinkFSPath> $$4 = new Object2ObjectOpenHashMap<>();
        LinkFSPath $$5 = new LinkFSPath($$1, $$2, $$3, new PathContents.DirectoryContents($$4));
        $$0.files.forEach(($$32, $$42) -> {
            $$4.put($$32, new LinkFSPath($$1, $$32, $$5, new PathContents.FileContents($$42)));
        });
        $$0.children.forEach(($$33, $$43) -> {
            $$4.put($$33, buildPath($$43, $$1, $$33, $$5));
        });
        $$4.trim();
        return $$5;
    }

    @Override // java.nio.file.FileSystem
    public FileSystemProvider provider() {
        return this.provider;
    }

    @Override // java.nio.file.FileSystem, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.nio.file.FileSystem
    public boolean isOpen() {
        return true;
    }

    @Override // java.nio.file.FileSystem
    public boolean isReadOnly() {
        return true;
    }

    @Override // java.nio.file.FileSystem
    public String getSeparator() {
        return "/";
    }

    @Override // java.nio.file.FileSystem
    public Iterable<Path> getRootDirectories() {
        return List.of(this.root);
    }

    @Override // java.nio.file.FileSystem
    public Iterable<FileStore> getFileStores() {
        return List.of(this.store);
    }

    @Override // java.nio.file.FileSystem
    public Set<String> supportedFileAttributeViews() {
        return VIEWS;
    }

    @Override // java.nio.file.FileSystem
    public Path getPath(String $$0, String... $$1) {
        Stream<String> $$2 = Stream.of($$0);
        if ($$1.length > 0) {
            $$2 = Stream.concat($$2, Stream.of((Object[]) $$1));
        }
        String $$3 = (String) $$2.collect(Collectors.joining("/"));
        if ($$3.equals("/")) {
            return this.root;
        }
        if ($$3.startsWith("/")) {
            LinkFSPath $$4 = this.root;
            for (String $$5 : PATH_SPLITTER.split($$3.substring(1))) {
                if ($$5.isEmpty()) {
                    throw new IllegalArgumentException("Empty paths not allowed");
                }
                $$4 = $$4.resolveName($$5);
            }
            return $$4;
        }
        LinkFSPath $$6 = null;
        for (String $$7 : PATH_SPLITTER.split($$3)) {
            if ($$7.isEmpty()) {
                throw new IllegalArgumentException("Empty paths not allowed");
            }
            $$6 = new LinkFSPath(this, $$7, $$6, PathContents.RELATIVE);
        }
        if ($$6 == null) {
            throw new IllegalArgumentException("Empty paths not allowed");
        }
        return $$6;
    }

    @Override // java.nio.file.FileSystem
    public PathMatcher getPathMatcher(String $$0) {
        throw new UnsupportedOperationException();
    }

    @Override // java.nio.file.FileSystem
    public UserPrincipalLookupService getUserPrincipalLookupService() {
        throw new UnsupportedOperationException();
    }

    @Override // java.nio.file.FileSystem
    public WatchService newWatchService() {
        throw new UnsupportedOperationException();
    }

    public FileStore store() {
        return this.store;
    }

    public LinkFSPath rootPath() {
        return this.root;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry.class */
    static final class DirectoryEntry extends Record {
        private final Map<String, DirectoryEntry> children;
        private final Map<String, Path> files;

        private DirectoryEntry(Map<String, DirectoryEntry> $$0, Map<String, Path> $$1) {
            this.children = $$0;
            this.files = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DirectoryEntry.class), DirectoryEntry.class, "children;files", "FIELD:Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;->children:Ljava/util/Map;", "FIELD:Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;->files:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DirectoryEntry.class), DirectoryEntry.class, "children;files", "FIELD:Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;->children:Ljava/util/Map;", "FIELD:Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;->files:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DirectoryEntry.class, Object.class), DirectoryEntry.class, "children;files", "FIELD:Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;->children:Ljava/util/Map;", "FIELD:Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;->files:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<String, DirectoryEntry> children() {
            return this.children;
        }

        public Map<String, Path> files() {
            return this.files;
        }

        public DirectoryEntry() {
            this(new HashMap(), new HashMap());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/LinkFileSystem$Builder.class */
    public static class Builder {
        private final DirectoryEntry root = new DirectoryEntry();

        public Builder put(List<String> $$0, String $$1, Path $$2) {
            DirectoryEntry $$3 = this.root;
            for (String $$4 : $$0) {
                $$3 = $$3.children.computeIfAbsent($$4, $$02 -> {
                    return new DirectoryEntry();
                });
            }
            $$3.files.put($$1, $$2);
            return this;
        }

        public Builder put(List<String> $$0, Path $$1) {
            if ($$0.isEmpty()) {
                throw new IllegalArgumentException("Path can't be empty");
            }
            int $$2 = $$0.size() - 1;
            return put($$0.subList(0, $$2), $$0.get($$2), $$1);
        }

        public FileSystem build(String $$0) {
            return new LinkFileSystem($$0, this.root);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
