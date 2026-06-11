package net.minecraft.network;

import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/VarInt.class */
public class VarInt {
    public static final int MAX_VARINT_SIZE = 5;
    private static final int DATA_BITS_MASK = 127;
    private static final int CONTINUATION_BIT_MASK = 128;
    private static final int DATA_BITS_PER_BYTE = 7;

    public static int getByteSize(int $$0) {
        for (int $$1 = 1; $$1 < 5; $$1++) {
            if (($$0 & ((-1) << ($$1 * 7))) == 0) {
                return $$1;
            }
        }
        return 5;
    }

    public static boolean hasContinuationBit(byte $$0) {
        return ($$0 & 128) == 128;
    }

    public static int read(ByteBuf $$0) {
        byte $$3;
        int $$1 = 0;
        int $$2 = 0;
        do {
            $$3 = $$0.readByte();
            int i = $$2;
            $$2++;
            $$1 |= ($$3 & 127) << (i * 7);
            if ($$2 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while (hasContinuationBit($$3));
        return $$1;
    }

    public static ByteBuf write(ByteBuf $$0, int $$1) {
        while (($$1 & (-128)) != 0) {
            $$0.writeByte(($$1 & 127) | 128);
            $$1 >>>= 7;
        }
        $$0.writeByte($$1);
        return $$0;
    }
}
