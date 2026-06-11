package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.util.DelegateDataOutput;
import net.minecraft.util.FastBufferedInputStream;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtIo.class */
public class NbtIo {
    private static final OpenOption[] SYNC_OUTPUT_OPTIONS = {StandardOpenOption.SYNC, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

    public static CompoundTag readCompressed(Path $$0, NbtAccounter $$1) throws IOException {
        InputStream $$2 = Files.newInputStream($$0, new OpenOption[0]);
        try {
            InputStream $$3 = new FastBufferedInputStream($$2);
            try {
                CompoundTag compressed = readCompressed($$3, $$1);
                $$3.close();
                if ($$2 != null) {
                    $$2.close();
                }
                return compressed;
            } finally {
            }
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

    private static DataInputStream createDecompressorStream(InputStream $$0) throws IOException {
        return new DataInputStream(new FastBufferedInputStream(new GZIPInputStream($$0)));
    }

    private static DataOutputStream createCompressorStream(OutputStream $$0) throws IOException {
        return new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream($$0)));
    }

    public static CompoundTag readCompressed(InputStream $$0, NbtAccounter $$1) throws IOException {
        DataInputStream $$2 = createDecompressorStream($$0);
        try {
            CompoundTag compoundTag = read($$2, $$1);
            if ($$2 != null) {
                $$2.close();
            }
            return compoundTag;
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

    public static void parseCompressed(Path $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
        InputStream $$3 = Files.newInputStream($$0, new OpenOption[0]);
        try {
            InputStream $$4 = new FastBufferedInputStream($$3);
            try {
                parseCompressed($$4, $$1, $$2);
                $$4.close();
                if ($$3 != null) {
                    $$3.close();
                }
            } finally {
            }
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void parseCompressed(InputStream $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
        DataInputStream $$3 = createDecompressorStream($$0);
        try {
            parse($$3, $$1, $$2);
            if ($$3 != null) {
                $$3.close();
            }
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void writeCompressed(CompoundTag $$0, Path $$1) throws IOException {
        OutputStream $$2 = Files.newOutputStream($$1, SYNC_OUTPUT_OPTIONS);
        try {
            OutputStream $$3 = new BufferedOutputStream($$2);
            try {
                writeCompressed($$0, $$3);
                $$3.close();
                if ($$2 != null) {
                    $$2.close();
                }
            } finally {
            }
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

    public static void writeCompressed(CompoundTag $$0, OutputStream $$1) throws IOException {
        DataOutputStream $$2 = createCompressorStream($$1);
        try {
            write($$0, $$2);
            if ($$2 != null) {
                $$2.close();
            }
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

    public static void write(CompoundTag $$0, Path $$1) throws IOException {
        OutputStream $$2 = Files.newOutputStream($$1, SYNC_OUTPUT_OPTIONS);
        try {
            OutputStream $$3 = new BufferedOutputStream($$2);
            try {
                DataOutputStream $$4 = new DataOutputStream($$3);
                try {
                    write($$0, $$4);
                    $$4.close();
                    $$3.close();
                    if ($$2 != null) {
                        $$2.close();
                    }
                } catch (Throwable th) {
                    try {
                        $$4.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (Throwable th3) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    public static CompoundTag read(Path $$0) throws IOException {
        if (!Files.exists($$0, new LinkOption[0])) {
            return null;
        }
        InputStream $$1 = Files.newInputStream($$0, new OpenOption[0]);
        try {
            DataInputStream $$2 = new DataInputStream($$1);
            try {
                CompoundTag compoundTag = read($$2, NbtAccounter.unlimitedHeap());
                $$2.close();
                if ($$1 != null) {
                    $$1.close();
                }
                return compoundTag;
            } finally {
            }
        } catch (Throwable th) {
            if ($$1 != null) {
                try {
                    $$1.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static CompoundTag read(DataInput $$0) throws IOException {
        return read($$0, NbtAccounter.unlimitedHeap());
    }

    public static CompoundTag read(DataInput $$0, NbtAccounter $$1) throws IOException {
        Tag $$2 = readUnnamedTag($$0, $$1);
        if ($$2 instanceof CompoundTag) {
            return (CompoundTag) $$2;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void write(CompoundTag $$0, DataOutput $$1) throws IOException {
        writeUnnamedTagWithFallback($$0, $$1);
    }

    public static void parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
        TagType<?> $$3 = TagTypes.getType($$0.readByte());
        if ($$3 == EndTag.TYPE) {
            if ($$1.visitRootEntry(EndTag.TYPE) == StreamTagVisitor.ValueResult.CONTINUE) {
                $$1.visitEnd();
            }
            return;
        }
        switch ($$1.visitRootEntry($$3)) {
            case BREAK:
                StringTag.skipString($$0);
                $$3.skip($$0, $$2);
                break;
            case CONTINUE:
                StringTag.skipString($$0);
                $$3.parse($$0, $$1, $$2);
                break;
        }
    }

    public static Tag readAnyTag(DataInput $$0, NbtAccounter $$1) throws IOException {
        byte $$2 = $$0.readByte();
        if ($$2 == 0) {
            return EndTag.INSTANCE;
        }
        return readTagSafe($$0, $$1, $$2);
    }

    public static void writeAnyTag(Tag $$0, DataOutput $$1) throws IOException {
        $$1.writeByte($$0.getId());
        if ($$0.getId() == 0) {
            return;
        }
        $$0.write($$1);
    }

    public static void writeUnnamedTag(Tag $$0, DataOutput $$1) throws IOException {
        $$1.writeByte($$0.getId());
        if ($$0.getId() == 0) {
            return;
        }
        $$1.writeUTF("");
        $$0.write($$1);
    }

    public static void writeUnnamedTagWithFallback(Tag $$0, DataOutput $$1) throws IOException {
        writeUnnamedTag($$0, new StringFallbackDataOutput($$1));
    }

    @VisibleForTesting
    public static Tag readUnnamedTag(DataInput $$0, NbtAccounter $$1) throws IOException {
        byte $$2 = $$0.readByte();
        if ($$2 == 0) {
            return EndTag.INSTANCE;
        }
        StringTag.skipString($$0);
        return readTagSafe($$0, $$1, $$2);
    }

    private static Tag readTagSafe(DataInput $$0, NbtAccounter $$1, byte $$2) {
        try {
            return TagTypes.getType($$2).load($$0, $$1);
        } catch (IOException $$3) {
            CrashReport $$4 = CrashReport.forThrowable($$3, "Loading NBT data");
            CrashReportCategory $$5 = $$4.addCategory("NBT Tag");
            $$5.setDetail("Tag type", Byte.valueOf($$2));
            throw new ReportedNbtException($$4);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtIo$StringFallbackDataOutput.class */
    public static class StringFallbackDataOutput extends DelegateDataOutput {
        public StringFallbackDataOutput(DataOutput $$0) {
            super($$0);
        }

        @Override // net.minecraft.util.DelegateDataOutput, java.io.DataOutput
        public void writeUTF(String $$0) throws IOException {
            try {
                super.writeUTF($$0);
            } catch (UTFDataFormatException $$1) {
                Util.logAndPauseIfInIde("Failed to write NBT String", $$1);
                super.writeUTF("");
            }
        }
    }
}
