package net.minecraft.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/DirectoryLock.class */
public class DirectoryLock implements AutoCloseable {
    public static final String LOCK_FILE = "session.lock";
    private final FileChannel lockFile;
    private final FileLock lock;
    private static final ByteBuffer DUMMY;

    static {
        byte[] $$0 = "☃".getBytes(StandardCharsets.UTF_8);
        DUMMY = ByteBuffer.allocateDirect($$0.length);
        DUMMY.put($$0);
        DUMMY.flip();
    }

    public static DirectoryLock create(Path $$0) throws IOException {
        Path $$1 = $$0.resolve(LOCK_FILE);
        FileUtil.createDirectoriesSafe($$0);
        FileChannel $$2 = FileChannel.open($$1, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        try {
            $$2.write(DUMMY.duplicate());
            $$2.force(true);
            FileLock $$3 = $$2.tryLock();
            if ($$3 == null) {
                throw LockException.alreadyLocked($$1);
            }
            return new DirectoryLock($$2, $$3);
        } catch (IOException $$4) {
            try {
                $$2.close();
            } catch (IOException $$5) {
                $$4.addSuppressed($$5);
            }
            throw $$4;
        }
    }

    private DirectoryLock(FileChannel $$0, FileLock $$1) {
        this.lockFile = $$0;
        this.lock = $$1;
    }

    @Override // java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            if (this.lock.isValid()) {
                this.lock.release();
            }
        } finally {
            if (this.lockFile.isOpen()) {
                this.lockFile.close();
            }
        }
    }

    public boolean isValid() {
        return this.lock.isValid();
    }

    /* JADX WARN: Unreachable blocks removed: 8, instructions: 12 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:21:0x0048
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public static boolean isLocked(java.nio.file.Path r6) throws java.io.IOException {
        /*
            r0 = r6
            java.lang.String r1 = "session.lock"
            java.nio.file.Path r0 = r0.resolve(r1)
            r7 = r0
            r0 = r7
            r1 = 1
            java.nio.file.OpenOption[] r1 = new java.nio.file.OpenOption[r1]     // Catch: java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
            r2 = r1
            r3 = 0
            java.nio.file.StandardOpenOption r4 = java.nio.file.StandardOpenOption.WRITE     // Catch: java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
            r2[r3] = r4     // Catch: java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
            java.nio.channels.FileChannel r0 = java.nio.channels.FileChannel.open(r0, r1)     // Catch: java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
            r8 = r0
            r0 = r8
            java.nio.channels.FileLock r0 = r0.tryLock()     // Catch: java.lang.Throwable -> L54 java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
            r9 = r0
            r0 = r9
            if (r0 != 0) goto L25
            r0 = 1
            goto L26
        L25:
            r0 = 0
        L26:
            r10 = r0
            r0 = r9
            if (r0 == 0) goto L30
            r0 = r9
            r0.close()     // Catch: java.lang.Throwable -> L54 java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
        L30:
            r0 = r8
            if (r0 == 0) goto L38
            r0 = r8
            r0.close()     // Catch: java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
        L38:
            r0 = r10
            return r0
        L3b:
            r10 = move-exception
            r0 = r9
            if (r0 == 0) goto L51
            r0 = r9
            r0.close()     // Catch: java.lang.Throwable -> L48 java.lang.Throwable -> L54 java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
            goto L51
        L48:
            r11 = move-exception
            r0 = r10
            r1 = r11
            r0.addSuppressed(r1)     // Catch: java.lang.Throwable -> L54 java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
        L51:
            r0 = r10
            throw r0     // Catch: java.lang.Throwable -> L54 java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
        L54:
            r9 = move-exception
            r0 = r8
            if (r0 == 0) goto L68
            r0 = r8
            r0.close()     // Catch: java.lang.Throwable -> L60 java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
            goto L68
        L60:
            r10 = move-exception
            r0 = r9
            r1 = r10
            r0.addSuppressed(r1)     // Catch: java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
        L68:
            r0 = r9
            throw r0     // Catch: java.nio.file.AccessDeniedException -> L6a java.nio.file.NoSuchFileException -> L6d
        L6a:
            r8 = move-exception
            r0 = 1
            return r0
        L6d:
            r8 = move-exception
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.minecraft.util.DirectoryLock.isLocked(java.nio.file.Path):boolean");
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/DirectoryLock$LockException.class */
    public static class LockException extends IOException {
        private LockException(Path $$0, String $$1) {
            super(String.valueOf($$0.toAbsolutePath()) + ": " + $$1);
        }

        public static LockException alreadyLocked(Path $$0) {
            return new LockException($$0, "already locked (possibly by other Minecraft instance?)");
        }
    }
}
