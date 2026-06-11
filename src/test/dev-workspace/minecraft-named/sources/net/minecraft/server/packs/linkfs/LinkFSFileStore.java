package net.minecraft.server.packs.linkfs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/LinkFSFileStore.class */
class LinkFSFileStore extends FileStore {
    private final String name;

    public LinkFSFileStore(String $$0) {
        this.name = $$0;
    }

    @Override // java.nio.file.FileStore
    public String name() {
        return this.name;
    }

    @Override // java.nio.file.FileStore
    public String type() {
        return "index";
    }

    @Override // java.nio.file.FileStore
    public boolean isReadOnly() {
        return true;
    }

    @Override // java.nio.file.FileStore
    public long getTotalSpace() {
        return 0L;
    }

    @Override // java.nio.file.FileStore
    public long getUsableSpace() {
        return 0L;
    }

    @Override // java.nio.file.FileStore
    public long getUnallocatedSpace() {
        return 0L;
    }

    @Override // java.nio.file.FileStore
    public boolean supportsFileAttributeView(Class<? extends FileAttributeView> $$0) {
        return $$0 == BasicFileAttributeView.class;
    }

    @Override // java.nio.file.FileStore
    public boolean supportsFileAttributeView(String $$0) {
        return "basic".equals($$0);
    }

    @Override // java.nio.file.FileStore
    public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> $$0) {
        return null;
    }

    @Override // java.nio.file.FileStore
    public Object getAttribute(String $$0) throws IOException {
        throw new UnsupportedOperationException();
    }
}
