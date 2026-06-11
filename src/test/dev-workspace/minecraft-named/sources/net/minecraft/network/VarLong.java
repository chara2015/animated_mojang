package net.minecraft.network;

import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/VarLong.class */
public class VarLong {
    private static final int MAX_VARLONG_SIZE = 10;
    private static final int DATA_BITS_MASK = 127;
    private static final int CONTINUATION_BIT_MASK = 128;
    private static final int DATA_BITS_PER_BYTE = 7;

    public static int getByteSize(long $$0) {
        for (int $$1 = 1; $$1 < 10; $$1++) {
            if (($$0 & ((-1) << ($$1 * 7))) == 0) {
                return $$1;
            }
        }
        return 10;
    }

    public static boolean hasContinuationBit(byte $$0) {
        return ($$0 & 128) == 128;
    }

    public static long read(ByteBuf $$0) {
        byte $$3;
        long $$1 = 0;
        int $$2 = 0;
        do {
            $$3 = $$0.readByte();
            int i = $$2;
            $$2++;
            $$1 |= ((long) ($$3 & 127)) << (i * 7);
            if ($$2 > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while (hasContinuationBit($$3));
        return $$1;
    }

    public static ByteBuf write(ByteBuf $$0, long $$1) {
        while (($$1 & (-128)) != 0) {
            $$0.writeByte(((int) ($$1 & 127)) | 128);
            $$1 >>>= 7;
        }
        $$0.writeByte((int) $$1);
        return $$0;
    }
}
