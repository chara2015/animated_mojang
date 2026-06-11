package net.minecraft.server.packs.linkfs;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/DummyFileAttributes.class */
abstract class DummyFileAttributes implements BasicFileAttributes {
    private static final FileTime EPOCH = FileTime.fromMillis(0);

    DummyFileAttributes() {
    }

    @Override // java.nio.file.attribute.BasicFileAttributes
    public FileTime lastModifiedTime() {
        return EPOCH;
    }

    @Override // java.nio.file.attribute.BasicFileAttributes
    public FileTime lastAccessTime() {
        return EPOCH;
    }

    @Override // java.nio.file.attribute.BasicFileAttributes
    public FileTime creationTime() {
        return EPOCH;
    }

    @Override // java.nio.file.attribute.BasicFileAttributes
    public boolean isSymbolicLink() {
        return false;
    }

    @Override // java.nio.file.attribute.BasicFileAttributes
    public boolean isOther() {
        return false;
    }

    @Override // java.nio.file.attribute.BasicFileAttributes
    public long size() {
        return 0L;
    }

    @Override // java.nio.file.attribute.BasicFileAttributes
    public Object fileKey() {
        return null;
    }
}
