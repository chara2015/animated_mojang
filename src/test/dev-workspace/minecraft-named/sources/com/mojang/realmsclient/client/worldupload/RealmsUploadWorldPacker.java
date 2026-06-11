package com.mojang.realmsclient.client.worldupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.BooleanSupplier;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/worldupload/RealmsUploadWorldPacker.class */
public class RealmsUploadWorldPacker {
    private static final long SIZE_LIMIT = 5368709120L;
    private static final String WORLD_FOLDER_NAME = "world";
    private final BooleanSupplier isCanceled;
    private final Path directoryToPack;

    public static File pack(Path $$0, BooleanSupplier $$1) throws IOException {
        return new RealmsUploadWorldPacker($$0, $$1).tarGzipArchive();
    }

    private RealmsUploadWorldPacker(Path $$0, BooleanSupplier $$1) {
        this.isCanceled = $$1;
        this.directoryToPack = $$0;
    }

    private File tarGzipArchive() throws IOException {
        TarArchiveOutputStream $$0 = null;
        try {
            File $$1 = File.createTempFile("realms-upload-file", ".tar.gz");
            TarArchiveOutputStream $$02 = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream($$1)));
            $$02.setLongFileMode(3);
            addFileToTarGz($$02, this.directoryToPack, WORLD_FOLDER_NAME, true);
            if (this.isCanceled.getAsBoolean()) {
                throw new RealmsUploadCanceledException();
            }
            $$02.finish();
            verifyBelowSizeLimit($$1.length());
            if ($$02 != null) {
                $$02.close();
            }
            return $$1;
        } catch (Throwable th) {
            if (0 != 0) {
                $$0.close();
            }
            throw th;
        }
    }

    private void addFileToTarGz(TarArchiveOutputStream $$0, Path $$1, String $$2, boolean $$3) throws IOException {
        if (this.isCanceled.getAsBoolean()) {
            throw new RealmsUploadCanceledException();
        }
        verifyBelowSizeLimit($$0.getBytesWritten());
        File $$4 = $$1.toFile();
        String $$5 = $$3 ? $$2 : $$2 + $$4.getName();
        TarArchiveEntry $$6 = new TarArchiveEntry($$4, $$5);
        $$0.putArchiveEntry($$6);
        if ($$4.isFile()) {
            InputStream $$7 = new FileInputStream($$4);
            try {
                $$7.transferTo($$0);
                $$7.close();
                $$0.closeArchiveEntry();
                return;
            } catch (Throwable th) {
                try {
                    $$7.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        $$0.closeArchiveEntry();
        File[] $$8 = $$4.listFiles();
        if ($$8 != null) {
            for (File $$9 : $$8) {
                addFileToTarGz($$0, $$9.toPath(), $$5 + "/", false);
            }
        }
    }

    private void verifyBelowSizeLimit(long $$0) {
        if ($$0 > SIZE_LIMIT) {
            throw new RealmsUploadTooLargeException(SIZE_LIMIT);
        }
    }
}
