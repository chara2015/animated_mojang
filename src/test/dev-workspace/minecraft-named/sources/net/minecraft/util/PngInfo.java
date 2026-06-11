package net.minecraft.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HexFormat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/PngInfo.class */
public final class PngInfo extends Record {
    private final int width;
    private final int height;
    private static final HexFormat FORMAT = HexFormat.of().withUpperCase().withPrefix("0x");
    private static final long PNG_HEADER = -8552249625308161526L;
    private static final int IHDR_TYPE = 1229472850;
    private static final int IHDR_SIZE = 13;

    public PngInfo(int $$0, int $$1) {
        this.width = $$0;
        this.height = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PngInfo.class), PngInfo.class, "width;height", "FIELD:Lnet/minecraft/util/PngInfo;->width:I", "FIELD:Lnet/minecraft/util/PngInfo;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PngInfo.class), PngInfo.class, "width;height", "FIELD:Lnet/minecraft/util/PngInfo;->width:I", "FIELD:Lnet/minecraft/util/PngInfo;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PngInfo.class, Object.class), PngInfo.class, "width;height", "FIELD:Lnet/minecraft/util/PngInfo;->width:I", "FIELD:Lnet/minecraft/util/PngInfo;->height:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public static PngInfo fromStream(InputStream $$0) throws IOException {
        DataInputStream $$1 = new DataInputStream($$0);
        long $$2 = $$1.readLong();
        if ($$2 != PNG_HEADER) {
            throw new IOException("Bad PNG Signature: " + FORMAT.toHexDigits($$2));
        }
        int $$3 = $$1.readInt();
        if ($$3 != 13) {
            throw new IOException("Bad length for IHDR chunk: " + $$3);
        }
        int $$4 = $$1.readInt();
        if ($$4 != IHDR_TYPE) {
            throw new IOException("Bad type for IHDR chunk: " + FORMAT.toHexDigits($$4));
        }
        int $$5 = $$1.readInt();
        int $$6 = $$1.readInt();
        return new PngInfo($$5, $$6);
    }

    public static PngInfo fromBytes(byte[] $$0) throws IOException {
        return fromStream(new ByteArrayInputStream($$0));
    }

    public static void validateHeader(ByteBuffer $$0) throws IOException {
        ByteOrder $$1 = $$0.order();
        $$0.order(ByteOrder.BIG_ENDIAN);
        if ($$0.limit() < 16) {
            throw new IOException("PNG header missing");
        }
        if ($$0.getLong(0) != PNG_HEADER) {
            throw new IOException("Bad PNG Signature");
        }
        if ($$0.getInt(8) != 13) {
            throw new IOException("Bad length for IHDR chunk!");
        }
        if ($$0.getInt(12) != IHDR_TYPE) {
            throw new IOException("Bad type for IHDR chunk!");
        }
        $$0.order($$1);
    }
}
