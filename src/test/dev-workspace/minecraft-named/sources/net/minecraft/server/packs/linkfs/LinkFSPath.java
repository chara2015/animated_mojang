package net.minecraft.server.packs.linkfs;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.ReadOnlyFileSystemException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import net.minecraft.server.packs.linkfs.PathContents;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/LinkFSPath.class */
class LinkFSPath implements Path {
    private static final BasicFileAttributes DIRECTORY_ATTRIBUTES = new DummyFileAttributes() { // from class: net.minecraft.server.packs.linkfs.LinkFSPath.1
        @Override // java.nio.file.attribute.BasicFileAttributes
        public boolean isRegularFile() {
            return false;
        }

        @Override // java.nio.file.attribute.BasicFileAttributes
        public boolean isDirectory() {
            return true;
        }
    };
    private static final BasicFileAttributes FILE_ATTRIBUTES = new DummyFileAttributes() { // from class: net.minecraft.server.packs.linkfs.LinkFSPath.2
        @Override // java.nio.file.attribute.BasicFileAttributes
        public boolean isRegularFile() {
            return true;
        }

        @Override // java.nio.file.attribute.BasicFileAttributes
        public boolean isDirectory() {
            return false;
        }
    };
    private static final Comparator<LinkFSPath> PATH_COMPARATOR = Comparator.comparing((v0) -> {
        return v0.pathToString();
    });
    private final String name;
    private final LinkFileSystem fileSystem;
    private final LinkFSPath parent;
    private List<String> pathToRoot;
    private String pathString;
    private final PathContents pathContents;

    public LinkFSPath(LinkFileSystem $$0, String $$1, LinkFSPath $$2, PathContents $$3) {
        this.fileSystem = $$0;
        this.name = $$1;
        this.parent = $$2;
        this.pathContents = $$3;
    }

    private LinkFSPath createRelativePath(LinkFSPath $$0, String $$1) {
        return new LinkFSPath(this.fileSystem, $$1, $$0, PathContents.RELATIVE);
    }

    @Override // java.nio.file.Path
    public LinkFileSystem getFileSystem() {
        return this.fileSystem;
    }

    @Override // java.nio.file.Path
    public boolean isAbsolute() {
        return this.pathContents != PathContents.RELATIVE;
    }

    @Override // java.nio.file.Path
    public File toFile() {
        PathContents pathContents = this.pathContents;
        if (pathContents instanceof PathContents.FileContents) {
            PathContents.FileContents $$0 = (PathContents.FileContents) pathContents;
            return $$0.contents().toFile();
        }
        throw new UnsupportedOperationException("Path " + pathToString() + " does not represent file");
    }

    @Override // java.nio.file.Path
    public LinkFSPath getRoot() {
        if (isAbsolute()) {
            return this.fileSystem.rootPath();
        }
        return null;
    }

    @Override // java.nio.file.Path
    public LinkFSPath getFileName() {
        return createRelativePath(null, this.name);
    }

    @Override // java.nio.file.Path
    public LinkFSPath getParent() {
        return this.parent;
    }

    @Override // java.nio.file.Path
    public int getNameCount() {
        return pathToRoot().size();
    }

    private List<String> pathToRoot() {
        if (this.name.isEmpty()) {
            return List.of();
        }
        if (this.pathToRoot == null) {
            ImmutableList.Builder<String> $$0 = ImmutableList.builder();
            if (this.parent != null) {
                $$0.addAll(this.parent.pathToRoot());
            }
            $$0.add(this.name);
            this.pathToRoot = $$0.build();
        }
        return this.pathToRoot;
    }

    @Override // java.nio.file.Path
    public LinkFSPath getName(int $$0) {
        List<String> $$1 = pathToRoot();
        if ($$0 < 0 || $$0 >= $$1.size()) {
            throw new IllegalArgumentException("Invalid index: " + $$0);
        }
        return createRelativePath(null, $$1.get($$0));
    }

    @Override // java.nio.file.Path
    public LinkFSPath subpath(int $$0, int $$1) {
        List<String> $$2 = pathToRoot();
        if ($$0 < 0 || $$1 > $$2.size() || $$0 >= $$1) {
            throw new IllegalArgumentException();
        }
        LinkFSPath $$3 = null;
        for (int $$4 = $$0; $$4 < $$1; $$4++) {
            $$3 = createRelativePath($$3, $$2.get($$4));
        }
        return $$3;
    }

    @Override // java.nio.file.Path
    public boolean startsWith(Path $$0) {
        if ($$0.isAbsolute() == isAbsolute() && ($$0 instanceof LinkFSPath)) {
            LinkFSPath $$1 = (LinkFSPath) $$0;
            if ($$1.fileSystem != this.fileSystem) {
                return false;
            }
            List<String> $$2 = pathToRoot();
            List<String> $$3 = $$1.pathToRoot();
            int $$4 = $$3.size();
            if ($$4 > $$2.size()) {
                return false;
            }
            for (int $$5 = 0; $$5 < $$4; $$5++) {
                if (!$$3.get($$5).equals($$2.get($$5))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.nio.file.Path
    public boolean endsWith(Path $$0) {
        if ((!$$0.isAbsolute() || isAbsolute()) && ($$0 instanceof LinkFSPath)) {
            LinkFSPath $$1 = (LinkFSPath) $$0;
            if ($$1.fileSystem != this.fileSystem) {
                return false;
            }
            List<String> $$2 = pathToRoot();
            List<String> $$3 = $$1.pathToRoot();
            int $$4 = $$3.size();
            int $$5 = $$2.size() - $$4;
            if ($$5 < 0) {
                return false;
            }
            for (int $$6 = $$4 - 1; $$6 >= 0; $$6--) {
                if (!$$3.get($$6).equals($$2.get($$5 + $$6))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.nio.file.Path
    public LinkFSPath normalize() {
        return this;
    }

    @Override // java.nio.file.Path
    public LinkFSPath resolve(Path $$0) {
        LinkFSPath $$1 = toLinkPath($$0);
        if ($$0.isAbsolute()) {
            return $$1;
        }
        return resolve($$1.pathToRoot());
    }

    private LinkFSPath resolve(List<String> $$0) {
        LinkFSPath $$1 = this;
        for (String $$2 : $$0) {
            $$1 = $$1.resolveName($$2);
        }
        return $$1;
    }

    LinkFSPath resolveName(String $$0) {
        if (isRelativeOrMissing(this.pathContents)) {
            return new LinkFSPath(this.fileSystem, $$0, this, this.pathContents);
        }
        PathContents pathContents = this.pathContents;
        if (pathContents instanceof PathContents.DirectoryContents) {
            PathContents.DirectoryContents $$1 = (PathContents.DirectoryContents) pathContents;
            LinkFSPath $$2 = $$1.children().get($$0);
            return $$2 != null ? $$2 : new LinkFSPath(this.fileSystem, $$0, this, PathContents.MISSING);
        }
        if (this.pathContents instanceof PathContents.FileContents) {
            return new LinkFSPath(this.fileSystem, $$0, this, PathContents.MISSING);
        }
        throw new AssertionError("All content types should be already handled");
    }

    private static boolean isRelativeOrMissing(PathContents $$0) {
        return $$0 == PathContents.MISSING || $$0 == PathContents.RELATIVE;
    }

    @Override // java.nio.file.Path
    public LinkFSPath relativize(Path $$0) {
        LinkFSPath $$1 = toLinkPath($$0);
        if (isAbsolute() != $$1.isAbsolute()) {
            throw new IllegalArgumentException("absolute mismatch");
        }
        List<String> $$2 = pathToRoot();
        List<String> $$3 = $$1.pathToRoot();
        if ($$2.size() >= $$3.size()) {
            throw new IllegalArgumentException();
        }
        for (int $$4 = 0; $$4 < $$2.size(); $$4++) {
            if (!$$2.get($$4).equals($$3.get($$4))) {
                throw new IllegalArgumentException();
            }
        }
        return $$1.subpath($$2.size(), $$3.size());
    }

    @Override // java.nio.file.Path
    public URI toUri() {
        try {
            return new URI(LinkFSProvider.SCHEME, this.fileSystem.store().name(), pathToString(), null);
        } catch (URISyntaxException $$0) {
            throw new AssertionError("Failed to create URI", $$0);
        }
    }

    @Override // java.nio.file.Path
    public LinkFSPath toAbsolutePath() {
        if (isAbsolute()) {
            return this;
        }
        return this.fileSystem.rootPath().resolve((Path) this);
    }

    @Override // java.nio.file.Path
    public LinkFSPath toRealPath(LinkOption... $$0) {
        return toAbsolutePath();
    }

    @Override // java.nio.file.Path, java.nio.file.Watchable
    public WatchKey register(WatchService $$0, WatchEvent.Kind<?>[] $$1, WatchEvent.Modifier... $$2) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    public int compareTo(Path $$0) {
        LinkFSPath $$1 = toLinkPath($$0);
        return PATH_COMPARATOR.compare(this, $$1);
    }

    @Override // java.nio.file.Path
    public boolean equals(Object $$0) {
        boolean $$2;
        if ($$0 == this) {
            return true;
        }
        if ($$0 instanceof LinkFSPath) {
            LinkFSPath $$1 = (LinkFSPath) $$0;
            if (this.fileSystem == $$1.fileSystem && ($$2 = hasRealContents()) == $$1.hasRealContents()) {
                return $$2 ? this.pathContents == $$1.pathContents : Objects.equals(this.parent, $$1.parent) && Objects.equals(this.name, $$1.name);
            }
            return false;
        }
        return false;
    }

    private boolean hasRealContents() {
        return !isRelativeOrMissing(this.pathContents);
    }

    @Override // java.nio.file.Path
    public int hashCode() {
        return hasRealContents() ? this.pathContents.hashCode() : this.name.hashCode();
    }

    @Override // java.nio.file.Path
    public String toString() {
        return pathToString();
    }

    private String pathToString() {
        if (this.pathString == null) {
            StringBuilder $$0 = new StringBuilder();
            if (isAbsolute()) {
                $$0.append("/");
            }
            Joiner.on("/").appendTo($$0, pathToRoot());
            this.pathString = $$0.toString();
        }
        return this.pathString;
    }

    private LinkFSPath toLinkPath(Path $$0) {
        if ($$0 == null) {
            throw new NullPointerException();
        }
        if ($$0 instanceof LinkFSPath) {
            LinkFSPath $$1 = (LinkFSPath) $$0;
            if ($$1.fileSystem == this.fileSystem) {
                return $$1;
            }
        }
        throw new ProviderMismatchException();
    }

    public boolean exists() {
        return hasRealContents();
    }

    public Path getTargetPath() {
        PathContents pathContents = this.pathContents;
        if (!(pathContents instanceof PathContents.FileContents)) {
            return null;
        }
        PathContents.FileContents $$0 = (PathContents.FileContents) pathContents;
        return $$0.contents();
    }

    public PathContents.DirectoryContents getDirectoryContents() {
        PathContents pathContents = this.pathContents;
        if (!(pathContents instanceof PathContents.DirectoryContents)) {
            return null;
        }
        PathContents.DirectoryContents $$0 = (PathContents.DirectoryContents) pathContents;
        return $$0;
    }

    public BasicFileAttributeView getBasicAttributeView() {
        return new BasicFileAttributeView() { // from class: net.minecraft.server.packs.linkfs.LinkFSPath.3
            @Override // java.nio.file.attribute.BasicFileAttributeView, java.nio.file.attribute.AttributeView
            public String name() {
                return "basic";
            }

            @Override // java.nio.file.attribute.BasicFileAttributeView
            public BasicFileAttributes readAttributes() throws IOException {
                return LinkFSPath.this.getBasicAttributes();
            }

            @Override // java.nio.file.attribute.BasicFileAttributeView
            public void setTimes(FileTime $$0, FileTime $$1, FileTime $$2) {
                throw new ReadOnlyFileSystemException();
            }
        };
    }

    public BasicFileAttributes getBasicAttributes() throws IOException {
        if (this.pathContents instanceof PathContents.DirectoryContents) {
            return DIRECTORY_ATTRIBUTES;
        }
        if (this.pathContents instanceof PathContents.FileContents) {
            return FILE_ATTRIBUTES;
        }
        throw new NoSuchFileException(pathToString());
    }
}
