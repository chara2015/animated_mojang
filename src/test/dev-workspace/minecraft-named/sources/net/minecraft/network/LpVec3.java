package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/LpVec3.class */
public class LpVec3 {
    private static final int DATA_BITS = 15;
    private static final int DATA_BITS_MASK = 32767;
    private static final double MAX_QUANTIZED_VALUE = 32766.0d;
    private static final int SCALE_BITS = 2;
    private static final int SCALE_BITS_MASK = 3;
    private static final int CONTINUATION_FLAG = 4;
    private static final int X_OFFSET = 3;
    private static final int Y_OFFSET = 18;
    private static final int Z_OFFSET = 33;
    public static final double ABS_MAX_VALUE = 1.7179869183E10d;
    public static final double ABS_MIN_VALUE = 3.051944088384301E-5d;

    public static boolean hasContinuationBit(int $$0) {
        return ($$0 & 4) == 4;
    }

    public static Vec3 read(ByteBuf $$0) {
        int $$1 = $$0.readUnsignedByte();
        if ($$1 == 0) {
            return Vec3.ZERO;
        }
        int $$2 = $$0.readUnsignedByte();
        long $$3 = $$0.readUnsignedInt();
        long $$4 = ($$3 << 16) | ((long) ($$2 << 8)) | ((long) $$1);
        long $$5 = $$1 & 3;
        if (hasContinuationBit($$1)) {
            $$5 |= (((long) VarInt.read($$0)) & 4294967295L) << 2;
        }
        return new Vec3(unpack($$4 >> 3) * $$5, unpack($$4 >> 18) * $$5, unpack($$4 >> 33) * $$5);
    }

    public static void write(ByteBuf $$0, Vec3 $$1) {
        double $$2 = sanitize($$1.x);
        double $$3 = sanitize($$1.y);
        double $$4 = sanitize($$1.z);
        double $$5 = Mth.absMax($$2, Mth.absMax($$3, $$4));
        if ($$5 < 3.051944088384301E-5d) {
            $$0.writeByte(0);
            return;
        }
        long $$6 = Mth.ceilLong($$5);
        boolean $$7 = ($$6 & 3) != $$6;
        long $$8 = $$7 ? ($$6 & 3) | 4 : $$6;
        long $$9 = pack($$2 / $$6) << 3;
        long $$10 = pack($$3 / $$6) << 18;
        long $$11 = pack($$4 / $$6) << 33;
        long $$12 = $$8 | $$9 | $$10 | $$11;
        $$0.writeByte((byte) $$12);
        $$0.writeByte((byte) ($$12 >> 8));
        $$0.writeInt((int) ($$12 >> 16));
        if ($$7) {
            VarInt.write($$0, (int) ($$6 >> 2));
        }
    }

    private static double sanitize(double $$0) {
        return Double.isNaN($$0) ? Density.SURFACE : Math.clamp($$0, -1.7179869183E10d, 1.7179869183E10d);
    }

    private static long pack(double $$0) {
        return Math.round((($$0 * 0.5d) + 0.5d) * MAX_QUANTIZED_VALUE);
    }

    private static double unpack(long $$0) {
        return ((Math.min($$0 & 32767, MAX_QUANTIZED_VALUE) * 2.0d) / MAX_QUANTIZED_VALUE) - 1.0d;
    }
}
