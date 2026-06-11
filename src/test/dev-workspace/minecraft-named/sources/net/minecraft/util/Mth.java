package net.minecraft.util;

import java.util.Locale;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.math.NumberUtils;
import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/Mth.class */
public class Mth {
    private static final long UUID_VERSION = 61440;
    private static final long UUID_VERSION_TYPE_4 = 16384;
    private static final long UUID_VARIANT = -4611686018427387904L;
    private static final long UUID_VARIANT_2 = Long.MIN_VALUE;
    public static final float PI = 3.1415927f;
    public static final float HALF_PI = 1.5707964f;
    public static final float TWO_PI = 6.2831855f;
    public static final float DEG_TO_RAD = 0.017453292f;
    public static final float RAD_TO_DEG = 57.295776f;
    public static final float EPSILON = 1.0E-5f;
    private static final int SIN_QUANTIZATION = 65536;
    private static final int SIN_MASK = 65535;
    private static final int COS_OFFSET = 16384;
    private static final double SIN_SCALE = 10430.378350470453d;
    private static final double ONE_SIXTH = 0.16666666666666666d;
    private static final int FRAC_EXP = 8;
    private static final int LUT_SIZE = 257;
    public static final float SQRT_OF_TWO = sqrt(2.0f);
    public static final Vector3f Y_AXIS = new Vector3f(0.0f, 1.0f, 0.0f);
    public static final Vector3f X_AXIS = new Vector3f(1.0f, 0.0f, 0.0f);
    public static final Vector3f Z_AXIS = new Vector3f(0.0f, 0.0f, 1.0f);
    private static final float[] SIN = (float[]) Util.make(new float[65536], $$0 -> {
        for (int $$1 = 0; $$1 < $$0.length; $$1++) {
            $$0[$$1] = (float) Math.sin(((double) $$1) / SIN_SCALE);
        }
    });
    private static final RandomSource RANDOM = RandomSource.createThreadSafe();
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = {0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] ASIN_TAB = new double[257];
    private static final double[] COS_TAB = new double[257];

    static {
        for (int $$0 = 0; $$0 < 257; $$0++) {
            double $$1 = ((double) $$0) / 256.0d;
            double $$2 = Math.asin($$1);
            COS_TAB[$$0] = Math.cos($$2);
            ASIN_TAB[$$0] = $$2;
        }
    }

    public static float sin(double $$0) {
        return SIN[(int) (((long) ($$0 * SIN_SCALE)) & 65535)];
    }

    public static float cos(double $$0) {
        return SIN[(int) (((long) (($$0 * SIN_SCALE) + 16384.0d)) & 65535)];
    }

    public static float sqrt(float $$0) {
        return (float) Math.sqrt($$0);
    }

    public static int floor(float $$0) {
        int $$1 = (int) $$0;
        return $$0 < ((float) $$1) ? $$1 - 1 : $$1;
    }

    public static int floor(double $$0) {
        int $$1 = (int) $$0;
        return $$0 < ((double) $$1) ? $$1 - 1 : $$1;
    }

    public static long lfloor(double $$0) {
        long $$1 = (long) $$0;
        return $$0 < ((double) $$1) ? $$1 - 1 : $$1;
    }

    public static float abs(float $$0) {
        return Math.abs($$0);
    }

    public static int abs(int $$0) {
        return Math.abs($$0);
    }

    public static int ceil(float $$0) {
        int $$1 = (int) $$0;
        return $$0 > ((float) $$1) ? $$1 + 1 : $$1;
    }

    public static int ceil(double $$0) {
        int $$1 = (int) $$0;
        return $$0 > ((double) $$1) ? $$1 + 1 : $$1;
    }

    public static long ceilLong(double $$0) {
        long $$1 = (long) $$0;
        return $$0 > ((double) $$1) ? $$1 + 1 : $$1;
    }

    public static int clamp(int $$0, int $$1, int $$2) {
        return Math.min(Math.max($$0, $$1), $$2);
    }

    public static long clamp(long $$0, long $$1, long $$2) {
        return Math.min(Math.max($$0, $$1), $$2);
    }

    public static float clamp(float $$0, float $$1, float $$2) {
        if ($$0 < $$1) {
            return $$1;
        }
        return Math.min($$0, $$2);
    }

    public static double clamp(double $$0, double $$1, double $$2) {
        if ($$0 < $$1) {
            return $$1;
        }
        return Math.min($$0, $$2);
    }

    public static double clampedLerp(double $$0, double $$1, double $$2) {
        if ($$0 < Density.SURFACE) {
            return $$1;
        }
        if ($$0 > 1.0d) {
            return $$2;
        }
        return lerp($$0, $$1, $$2);
    }

    public static float clampedLerp(float $$0, float $$1, float $$2) {
        if ($$0 < 0.0f) {
            return $$1;
        }
        if ($$0 > 1.0f) {
            return $$2;
        }
        return lerp($$0, $$1, $$2);
    }

    public static int absMax(int $$0, int $$1) {
        return Math.max(Math.abs($$0), Math.abs($$1));
    }

    public static float absMax(float $$0, float $$1) {
        return Math.max(Math.abs($$0), Math.abs($$1));
    }

    public static double absMax(double $$0, double $$1) {
        return Math.max(Math.abs($$0), Math.abs($$1));
    }

    public static int chessboardDistance(int $$0, int $$1, int $$2, int $$3) {
        return absMax($$2 - $$0, $$3 - $$1);
    }

    public static int floorDiv(int $$0, int $$1) {
        return Math.floorDiv($$0, $$1);
    }

    public static int nextInt(RandomSource $$0, int $$1, int $$2) {
        if ($$1 >= $$2) {
            return $$1;
        }
        return $$0.nextInt(($$2 - $$1) + 1) + $$1;
    }

    public static float nextFloat(RandomSource $$0, float $$1, float $$2) {
        if ($$1 >= $$2) {
            return $$1;
        }
        return ($$0.nextFloat() * ($$2 - $$1)) + $$1;
    }

    public static double nextDouble(RandomSource $$0, double $$1, double $$2) {
        if ($$1 >= $$2) {
            return $$1;
        }
        return ($$0.nextDouble() * ($$2 - $$1)) + $$1;
    }

    public static boolean equal(float $$0, float $$1) {
        return Math.abs($$1 - $$0) < 1.0E-5f;
    }

    public static boolean equal(double $$0, double $$1) {
        return Math.abs($$1 - $$0) < 9.999999747378752E-6d;
    }

    public static int positiveModulo(int $$0, int $$1) {
        return Math.floorMod($$0, $$1);
    }

    public static float positiveModulo(float $$0, float $$1) {
        return (($$0 % $$1) + $$1) % $$1;
    }

    public static double positiveModulo(double $$0, double $$1) {
        return (($$0 % $$1) + $$1) % $$1;
    }

    public static boolean isMultipleOf(int $$0, int $$1) {
        return $$0 % $$1 == 0;
    }

    public static byte packDegrees(float $$0) {
        return (byte) floor(($$0 * 256.0f) / 360.0f);
    }

    public static float unpackDegrees(byte $$0) {
        return ($$0 * 360) / 256.0f;
    }

    public static int wrapDegrees(int $$0) {
        int $$1 = $$0 % 360;
        if ($$1 >= 180) {
            $$1 -= 360;
        }
        if ($$1 < -180) {
            $$1 += 360;
        }
        return $$1;
    }

    public static float wrapDegrees(long $$0) {
        float $$1 = $$0 % 360;
        if ($$1 >= 180.0f) {
            $$1 -= 360.0f;
        }
        if ($$1 < -180.0f) {
            $$1 += 360.0f;
        }
        return $$1;
    }

    public static float wrapDegrees(float $$0) {
        float $$1 = $$0 % 360.0f;
        if ($$1 >= 180.0f) {
            $$1 -= 360.0f;
        }
        if ($$1 < -180.0f) {
            $$1 += 360.0f;
        }
        return $$1;
    }

    public static double wrapDegrees(double $$0) {
        double $$1 = $$0 % 360.0d;
        if ($$1 >= 180.0d) {
            $$1 -= 360.0d;
        }
        if ($$1 < -180.0d) {
            $$1 += 360.0d;
        }
        return $$1;
    }

    public static float degreesDifference(float $$0, float $$1) {
        return wrapDegrees($$1 - $$0);
    }

    public static float degreesDifferenceAbs(float $$0, float $$1) {
        return abs(degreesDifference($$0, $$1));
    }

    public static float rotateIfNecessary(float $$0, float $$1, float $$2) {
        float $$3 = degreesDifference($$0, $$1);
        float $$4 = clamp($$3, -$$2, $$2);
        return $$1 - $$4;
    }

    public static float approach(float $$0, float $$1, float $$2) {
        float $$22 = abs($$2);
        if ($$0 < $$1) {
            return clamp($$0 + $$22, $$0, $$1);
        }
        return clamp($$0 - $$22, $$1, $$0);
    }

    public static float approachDegrees(float $$0, float $$1, float $$2) {
        float $$3 = degreesDifference($$0, $$1);
        return approach($$0, $$0 + $$3, $$2);
    }

    public static int getInt(String $$0, int $$1) {
        return NumberUtils.toInt($$0, $$1);
    }

    public static int smallestEncompassingPowerOfTwo(int $$0) {
        int $$1 = $$0 - 1;
        int $$12 = $$1 | ($$1 >> 1);
        int $$13 = $$12 | ($$12 >> 2);
        int $$14 = $$13 | ($$13 >> 4);
        int $$15 = $$14 | ($$14 >> 8);
        return ($$15 | ($$15 >> 16)) + 1;
    }

    public static int smallestSquareSide(int $$0) {
        if ($$0 < 0) {
            throw new IllegalArgumentException("itemCount must be greater than or equal to zero");
        }
        return ceil(Math.sqrt($$0));
    }

    public static boolean isPowerOfTwo(int $$0) {
        return $$0 != 0 && ($$0 & ($$0 - 1)) == 0;
    }

    public static int ceillog2(int $$0) {
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[((int) ((((long) (isPowerOfTwo($$0) ? $$0 : smallestEncompassingPowerOfTwo($$0))) * 125613361) >> 27)) & 31];
    }

    public static int log2(int $$0) {
        return ceillog2($$0) - (isPowerOfTwo($$0) ? 0 : 1);
    }

    public static float frac(float $$0) {
        return $$0 - floor($$0);
    }

    public static double frac(double $$0) {
        return $$0 - lfloor($$0);
    }

    @Deprecated
    public static long getSeed(Vec3i $$0) {
        return getSeed($$0.getX(), $$0.getY(), $$0.getZ());
    }

    @Deprecated
    public static long getSeed(int $$0, int $$1, int $$2) {
        long $$3 = (((long) ($$0 * 3129871)) ^ (((long) $$2) * 116129781)) ^ ((long) $$1);
        return ((($$3 * $$3) * 42317861) + ($$3 * 11)) >> 16;
    }

    public static UUID createInsecureUUID(RandomSource $$0) {
        long $$1 = ($$0.nextLong() & (-61441)) | UUID_VERSION_TYPE_4;
        long $$2 = ($$0.nextLong() & 4611686018427387903L) | UUID_VARIANT_2;
        return new UUID($$1, $$2);
    }

    public static UUID createInsecureUUID() {
        return createInsecureUUID(RANDOM);
    }

    public static double inverseLerp(double $$0, double $$1, double $$2) {
        return ($$0 - $$1) / ($$2 - $$1);
    }

    public static float inverseLerp(float $$0, float $$1, float $$2) {
        return ($$0 - $$1) / ($$2 - $$1);
    }

    public static boolean rayIntersectsAABB(Vec3 $$0, Vec3 $$1, AABB $$2) {
        double $$3 = ($$2.minX + $$2.maxX) * 0.5d;
        double $$4 = ($$2.maxX - $$2.minX) * 0.5d;
        double $$5 = $$0.x - $$3;
        if (Math.abs($$5) > $$4 && $$5 * $$1.x >= Density.SURFACE) {
            return false;
        }
        double $$6 = ($$2.minY + $$2.maxY) * 0.5d;
        double $$7 = ($$2.maxY - $$2.minY) * 0.5d;
        double $$8 = $$0.y - $$6;
        if (Math.abs($$8) > $$7 && $$8 * $$1.y >= Density.SURFACE) {
            return false;
        }
        double $$9 = ($$2.minZ + $$2.maxZ) * 0.5d;
        double $$10 = ($$2.maxZ - $$2.minZ) * 0.5d;
        double $$11 = $$0.z - $$9;
        if (Math.abs($$11) > $$10 && $$11 * $$1.z >= Density.SURFACE) {
            return false;
        }
        double $$12 = Math.abs($$1.x);
        double $$13 = Math.abs($$1.y);
        double $$14 = Math.abs($$1.z);
        double $$15 = ($$1.y * $$11) - ($$1.z * $$8);
        if (Math.abs($$15) > ($$7 * $$14) + ($$10 * $$13)) {
            return false;
        }
        double $$152 = ($$1.z * $$5) - ($$1.x * $$11);
        if (Math.abs($$152) > ($$4 * $$14) + ($$10 * $$12)) {
            return false;
        }
        double $$153 = ($$1.x * $$8) - ($$1.y * $$5);
        return Math.abs($$153) < ($$4 * $$13) + ($$7 * $$12);
    }

    public static double atan2(double $$0, double $$1) {
        double $$2 = ($$1 * $$1) + ($$0 * $$0);
        if (Double.isNaN($$2)) {
            return Double.NaN;
        }
        boolean $$3 = $$0 < Density.SURFACE;
        if ($$3) {
            $$0 = -$$0;
        }
        boolean $$4 = $$1 < Density.SURFACE;
        if ($$4) {
            $$1 = -$$1;
        }
        boolean $$5 = $$0 > $$1;
        if ($$5) {
            double $$6 = $$1;
            $$1 = $$0;
            $$0 = $$6;
        }
        double $$7 = fastInvSqrt($$2);
        double $$12 = $$1 * $$7;
        double $$02 = $$0 * $$7;
        double $$8 = FRAC_BIAS + $$02;
        int $$9 = (int) Double.doubleToRawLongBits($$8);
        double $$10 = ASIN_TAB[$$9];
        double $$11 = COS_TAB[$$9];
        double $$122 = $$8 - FRAC_BIAS;
        double $$13 = ($$02 * $$11) - ($$12 * $$122);
        double $$14 = (6.0d + ($$13 * $$13)) * $$13 * ONE_SIXTH;
        double $$15 = $$10 + $$14;
        if ($$5) {
            $$15 = 1.5707963267948966d - $$15;
        }
        if ($$4) {
            $$15 = 3.141592653589793d - $$15;
        }
        if ($$3) {
            $$15 = -$$15;
        }
        return $$15;
    }

    public static float invSqrt(float $$0) {
        return Math.invsqrt($$0);
    }

    public static double invSqrt(double $$0) {
        return Math.invsqrt($$0);
    }

    @Deprecated
    public static double fastInvSqrt(double $$0) {
        double $$1 = 0.5d * $$0;
        long $$2 = Double.doubleToRawLongBits($$0);
        double $$02 = Double.longBitsToDouble(6910469410427058090L - ($$2 >> 1));
        return $$02 * (1.5d - (($$1 * $$02) * $$02));
    }

    public static float fastInvCubeRoot(float $$0) {
        int $$1 = Float.floatToIntBits($$0);
        float $$2 = Float.intBitsToFloat(1419967116 - ($$1 / 3));
        float $$22 = (0.6666667f * $$2) + (1.0f / (((3.0f * $$2) * $$2) * $$0));
        return (0.6666667f * $$22) + (1.0f / (((3.0f * $$22) * $$22) * $$0));
    }

    public static int hsvToRgb(float $$0, float $$1, float $$2) {
        return hsvToArgb($$0, $$1, $$2, 0);
    }

    public static int hsvToArgb(float $$0, float $$1, float $$2, int $$3) {
        float $$27;
        float $$28;
        float $$29;
        int $$4 = ((int) ($$0 * 6.0f)) % 6;
        float $$5 = ($$0 * 6.0f) - $$4;
        float $$6 = $$2 * (1.0f - $$1);
        float $$7 = $$2 * (1.0f - ($$5 * $$1));
        float $$8 = $$2 * (1.0f - ((1.0f - $$5) * $$1));
        switch ($$4) {
            case 0:
                $$27 = $$2;
                $$28 = $$8;
                $$29 = $$6;
                break;
            case 1:
                $$27 = $$7;
                $$28 = $$2;
                $$29 = $$6;
                break;
            case 2:
                $$27 = $$6;
                $$28 = $$2;
                $$29 = $$8;
                break;
            case 3:
                $$27 = $$6;
                $$28 = $$7;
                $$29 = $$2;
                break;
            case 4:
                $$27 = $$8;
                $$28 = $$6;
                $$29 = $$2;
                break;
            case 5:
                $$27 = $$2;
                $$28 = $$6;
                $$29 = $$7;
                break;
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + $$0 + ", " + $$1 + ", " + $$2);
        }
        return ARGB.color($$3, clamp((int) ($$27 * 255.0f), 0, 255), clamp((int) ($$28 * 255.0f), 0, 255), clamp((int) ($$29 * 255.0f), 0, 255));
    }

    public static int murmurHash3Mixer(int $$0) {
        int $$02 = ($$0 ^ ($$0 >>> 16)) * (-2048144789);
        int $$03 = ($$02 ^ ($$02 >>> 13)) * (-1028477387);
        return $$03 ^ ($$03 >>> 16);
    }

    public static int binarySearch(int $$0, int $$1, IntPredicate $$2) {
        int i = $$1 - $$0;
        while (true) {
            int $$3 = i;
            if ($$3 > 0) {
                int $$4 = $$3 / 2;
                int $$5 = $$0 + $$4;
                if ($$2.test($$5)) {
                    i = $$4;
                } else {
                    $$0 = $$5 + 1;
                    i = $$3 - ($$4 + 1);
                }
            } else {
                return $$0;
            }
        }
    }

    public static int lerpInt(float $$0, int $$1, int $$2) {
        return $$1 + floor($$0 * ($$2 - $$1));
    }

    public static int lerpDiscrete(float $$0, int $$1, int $$2) {
        int $$3 = $$2 - $$1;
        return $$1 + floor($$0 * ($$3 - 1)) + ($$0 > 0.0f ? 1 : 0);
    }

    public static float lerp(float $$0, float $$1, float $$2) {
        return $$1 + ($$0 * ($$2 - $$1));
    }

    public static Vec3 lerp(double $$0, Vec3 $$1, Vec3 $$2) {
        return new Vec3(lerp($$0, $$1.x, $$2.x), lerp($$0, $$1.y, $$2.y), lerp($$0, $$1.z, $$2.z));
    }

    public static double lerp(double $$0, double $$1, double $$2) {
        return $$1 + ($$0 * ($$2 - $$1));
    }

    public static double lerp2(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
        return lerp($$1, lerp($$0, $$2, $$3), lerp($$0, $$4, $$5));
    }

    public static double lerp3(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8, double $$9, double $$10) {
        return lerp($$2, lerp2($$0, $$1, $$3, $$4, $$5, $$6), lerp2($$0, $$1, $$7, $$8, $$9, $$10));
    }

    public static float catmullrom(float $$0, float $$1, float $$2, float $$3, float $$4) {
        return 0.5f * ((2.0f * $$2) + (($$3 - $$1) * $$0) + (((((2.0f * $$1) - (5.0f * $$2)) + (4.0f * $$3)) - $$4) * $$0 * $$0) + (((((3.0f * $$2) - $$1) - (3.0f * $$3)) + $$4) * $$0 * $$0 * $$0));
    }

    public static double smoothstep(double $$0) {
        return $$0 * $$0 * $$0 * (($$0 * (($$0 * 6.0d) - 15.0d)) + 10.0d);
    }

    public static double smoothstepDerivative(double $$0) {
        return 30.0d * $$0 * $$0 * ($$0 - 1.0d) * ($$0 - 1.0d);
    }

    public static int sign(double $$0) {
        if ($$0 == Density.SURFACE) {
            return 0;
        }
        return $$0 > Density.SURFACE ? 1 : -1;
    }

    public static float rotLerp(float $$0, float $$1, float $$2) {
        return $$1 + ($$0 * wrapDegrees($$2 - $$1));
    }

    public static double rotLerp(double $$0, double $$1, double $$2) {
        return $$1 + ($$0 * wrapDegrees($$2 - $$1));
    }

    public static float rotLerpRad(float $$0, float $$1, float $$2) {
        float $$3;
        float f = $$2 - $$1;
        while (true) {
            $$3 = f;
            if ($$3 >= -3.1415927f) {
                break;
            }
            f = $$3 + 6.2831855f;
        }
        while ($$3 >= 3.1415927f) {
            $$3 -= 6.2831855f;
        }
        return $$1 + ($$0 * $$3);
    }

    public static float triangleWave(float $$0, float $$1) {
        return (Math.abs(($$0 % $$1) - ($$1 * 0.5f)) - ($$1 * 0.25f)) / ($$1 * 0.25f);
    }

    public static float square(float $$0) {
        return $$0 * $$0;
    }

    public static float cube(float $$0) {
        return $$0 * $$0 * $$0;
    }

    public static double square(double $$0) {
        return $$0 * $$0;
    }

    public static int square(int $$0) {
        return $$0 * $$0;
    }

    public static long square(long $$0) {
        return $$0 * $$0;
    }

    public static double clampedMap(double $$0, double $$1, double $$2, double $$3, double $$4) {
        return clampedLerp(inverseLerp($$0, $$1, $$2), $$3, $$4);
    }

    public static float clampedMap(float $$0, float $$1, float $$2, float $$3, float $$4) {
        return clampedLerp(inverseLerp($$0, $$1, $$2), $$3, $$4);
    }

    public static double map(double $$0, double $$1, double $$2, double $$3, double $$4) {
        return lerp(inverseLerp($$0, $$1, $$2), $$3, $$4);
    }

    public static float map(float $$0, float $$1, float $$2, float $$3, float $$4) {
        return lerp(inverseLerp($$0, $$1, $$2), $$3, $$4);
    }

    public static double wobble(double $$0) {
        return $$0 + ((((2.0d * RandomSource.create(floor($$0 * 3000.0d)).nextDouble()) - 1.0d) * 1.0E-7d) / 2.0d);
    }

    public static int roundToward(int $$0, int $$1) {
        return positiveCeilDiv($$0, $$1) * $$1;
    }

    public static int positiveCeilDiv(int $$0, int $$1) {
        return -Math.floorDiv(-$$0, $$1);
    }

    public static int randomBetweenInclusive(RandomSource $$0, int $$1, int $$2) {
        return $$0.nextInt(($$2 - $$1) + 1) + $$1;
    }

    public static float randomBetween(RandomSource $$0, float $$1, float $$2) {
        return ($$0.nextFloat() * ($$2 - $$1)) + $$1;
    }

    public static float normal(RandomSource $$0, float $$1, float $$2) {
        return $$1 + (((float) $$0.nextGaussian()) * $$2);
    }

    public static double lengthSquared(double $$0, double $$1) {
        return ($$0 * $$0) + ($$1 * $$1);
    }

    public static double length(double $$0, double $$1) {
        return Math.sqrt(lengthSquared($$0, $$1));
    }

    public static float length(float $$0, float $$1) {
        return (float) Math.sqrt(lengthSquared($$0, $$1));
    }

    public static double lengthSquared(double $$0, double $$1, double $$2) {
        return ($$0 * $$0) + ($$1 * $$1) + ($$2 * $$2);
    }

    public static double length(double $$0, double $$1, double $$2) {
        return Math.sqrt(lengthSquared($$0, $$1, $$2));
    }

    public static float lengthSquared(float $$0, float $$1, float $$2) {
        return ($$0 * $$0) + ($$1 * $$1) + ($$2 * $$2);
    }

    public static int quantize(double $$0, int $$1) {
        return floor($$0 / ((double) $$1)) * $$1;
    }

    public static IntStream outFromOrigin(int $$0, int $$1, int $$2) {
        return outFromOrigin($$0, $$1, $$2, 1);
    }

    public static IntStream outFromOrigin(int $$0, int $$1, int $$2, int $$3) {
        if ($$1 > $$2) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "upperBound %d expected to be > lowerBound %d", Integer.valueOf($$2), Integer.valueOf($$1)));
        }
        if ($$3 < 1) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "step size expected to be >= 1, was %d", Integer.valueOf($$3)));
        }
        int $$4 = clamp($$0, $$1, $$2);
        return IntStream.iterate($$4, $$32 -> {
            int $$42 = Math.abs($$4 - $$32);
            return $$4 - $$42 >= $$1 || $$4 + $$42 <= $$2;
        }, $$42 -> {
            boolean $$5 = $$42 <= $$4;
            int $$6 = Math.abs($$4 - $$42);
            boolean $$7 = ($$4 + $$6) + $$3 <= $$2;
            if (!$$5 || !$$7) {
                int $$8 = ($$4 - $$6) - ($$5 ? $$3 : 0);
                if ($$8 >= $$1) {
                    return $$8;
                }
            }
            return $$4 + $$6 + $$3;
        });
    }

    public static Quaternionf rotationAroundAxis(Vector3f $$0, Quaternionf $$1, Quaternionf $$2) {
        float $$3 = $$0.dot($$1.x, $$1.y, $$1.z);
        return $$2.set($$0.x * $$3, $$0.y * $$3, $$0.z * $$3, $$1.w).normalize();
    }

    public static int mulAndTruncate(Fraction $$0, int $$1) {
        return ($$0.getNumerator() * $$1) / $$0.getDenominator();
    }
}
