package net.labymod.api.util.math;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Pose;
import net.labymod.api.util.function.Functional;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatVector3;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/MathHelper.class */
public final class MathHelper {
    private static final float FULL_ROT_RADIANS = 6.2831855f;
    private static final float MAGIC_PIXEL_NUMBER = 3.0f;
    private static final float POINT_SIZE_NUMBER = 1.333f;
    private static final FloatVector3 NORMAL_TRANSFORM = new FloatVector3();
    private static final FloatVector3 MATRIX_TRANSFORM = new FloatVector3();
    public static final int[] POWER_OF_TWO = (int[]) Functional.of(() -> {
        IntArrayList intArrayList = new IntArrayList();
        int prev = 1;
        for (int i = 0; i < 16; i++) {
            int value = prev * 2;
            intArrayList.add(value);
            prev = value;
        }
        return intArrayList.toIntArray();
    });
    private static final float[] MATRIX4x4_BUFFER = new float[16];

    private MathHelper() {
    }

    public static float applyFloatingPointPosition(boolean useFloatingPointPosition, float value) {
        return useFloatingPointPosition ? value : (int) value;
    }

    public static long clamp(long value, long minimum, long maximum) {
        return value < minimum ? minimum : Math.min(value, maximum);
    }

    public static byte clamp(byte value, byte minimum, byte maximum) {
        return value < minimum ? minimum : value > maximum ? maximum : value;
    }

    public static int clamp(int value, int minimum, int maximum) {
        return value < minimum ? minimum : Math.min(value, maximum);
    }

    public static float clamp(float value, float minimum, float maximum) {
        return value < minimum ? minimum : Math.min(value, maximum);
    }

    public static double clamp(double value, double minimum, double maximum) {
        return value < minimum ? minimum : Math.min(value, maximum);
    }

    public static int ceil(float value) {
        int tmpValue = (int) value;
        return value > ((float) tmpValue) ? tmpValue + 1 : tmpValue;
    }

    public static int ceil(double value) {
        int tmpValue = (int) value;
        return value > ((double) tmpValue) ? tmpValue + 1 : tmpValue;
    }

    public static int ceilOrFloor(float value) {
        return ceilOrFloor(value, 0.5f);
    }

    public static int ceilOrFloor(float value, float average) {
        int flooredValue = floor(value);
        int ceiledValue = ceil(value);
        float averageValue = flooredValue + clamp(average, 0.0f, 1.0f);
        return value < averageValue ? flooredValue : ceiledValue;
    }

    public static float interpolateRotation(float previousOffset, float offset, float delta) {
        float rotated;
        float f = offset - previousOffset;
        while (true) {
            rotated = f;
            if (rotated >= -180.0f) {
                break;
            }
            f = rotated + 360.0f;
        }
        while (rotated >= 180.0f) {
            rotated -= 360.0f;
        }
        return previousOffset + (delta * rotated);
    }

    public static float rotateTowards(float currentAngle, float targetAngle, float maxRotation) {
        float angleDiff = degreesDifference(currentAngle, targetAngle);
        float clampedAngleDiff = clamp(angleDiff, -maxRotation, maxRotation);
        return currentAngle + clampedAngleDiff;
    }

    public static float degreesDifference(float a, float b) {
        return wrapDegrees(b - a);
    }

    public static float wrapDegrees(float angle) {
        float rotated = angle % 360.0f;
        if (rotated >= 180.0f) {
            rotated -= 360.0f;
        }
        if (rotated < -180.0f) {
            rotated += 360.0f;
        }
        return rotated;
    }

    public static double wrapDegrees(double angle) {
        double rotated = angle % 360.0d;
        if (rotated >= 180.0d) {
            rotated -= 360.0d;
        }
        if (rotated < -180.0d) {
            rotated += 360.0d;
        }
        return rotated;
    }

    public static float wrapRadians(float angle) {
        float rotated = angle % FULL_ROT_RADIANS;
        if (rotated < 0.0f) {
            rotated += FULL_ROT_RADIANS;
        }
        return rotated;
    }

    public static float angleDifference(float angle1, float angle2) {
        float angleDifference = (angle1 - angle2) % 360.0f;
        float shortestDistance = 180.0f - Math.abs(Math.abs(angleDifference) - 180.0f);
        return (angleDifference + 360.0f) % 360.0f < 180.0f ? shortestDistance : shortestDistance * (-1.0f);
    }

    public static float normalizeRadians(float angle) {
        return angle % FULL_ROT_RADIANS;
    }

    public static float roundRadians(float angle) {
        return Math.round(angle / FULL_ROT_RADIANS) * FULL_ROT_RADIANS;
    }

    public static float convertRadians(float sourceAngle, float targetAngle) {
        float normalizedDistanceAngle = wrapRadians(targetAngle - sourceAngle);
        float invertedDistanceAngle = normalizedDistanceAngle - FULL_ROT_RADIANS;
        float distanceAngle = Math.abs(normalizedDistanceAngle) < Math.abs(invertedDistanceAngle) ? normalizedDistanceAngle : invertedDistanceAngle;
        return sourceAngle + distanceAngle;
    }

    public static float convertDegrees(float sourceAngle, float targetAngle) {
        return toDegreesFloat(convertRadians(toRadiansFloat(sourceAngle), toRadiansFloat(targetAngle)));
    }

    public static int floor(float value) {
        int intValue = (int) value;
        return value < ((float) intValue) ? intValue - 1 : intValue;
    }

    public static int floor(double value) {
        int intValue = (int) value;
        return value < ((double) intValue) ? intValue - 1 : intValue;
    }

    public static boolean isAngleBetween(double angle, double startAngle, double endAngle, double fullRot) {
        return (((angle - startAngle) > 0.0d ? 1 : ((angle - startAngle) == 0.0d ? 0 : -1)) < 0 ? (angle - startAngle) + fullRot : angle - startAngle) < (((endAngle - startAngle) > 0.0d ? 1 : ((endAngle - startAngle) == 0.0d ? 0 : -1)) < 0 ? (endAngle - startAngle) + fullRot : endAngle - startAngle);
    }

    public static double lerp(double value, double previousValue) {
        return lerp(value, previousValue, Laby.labyAPI().minecraft().getPartialTicks());
    }

    public static double lerp(double value, double previousValue, float partialTicks) {
        return previousValue + ((value - previousValue) * ((double) partialTicks));
    }

    public static float lerp(float value, float previousValue) {
        return lerp(value, previousValue, Laby.labyAPI().minecraft().getPartialTicks());
    }

    public static float lerp(float value, float previousValue, float partialTicks) {
        return previousValue + ((value - previousValue) * partialTicks);
    }

    public static int lerp(int value, int previousValue) {
        return lerp(value, previousValue, Laby.labyAPI().minecraft().getPartialTicks());
    }

    public static int lerp(int value, int previousValue, float partialTicks) {
        return (int) (previousValue + ((value - previousValue) * partialTicks));
    }

    public static float rotLerp(float value, float previousValue, float t) {
        return previousValue + (t * wrapDegrees(value - previousValue));
    }

    public static double toDegreesDouble(double angrad) {
        return Math.toDegrees(angrad);
    }

    public static float toDegreesFloat(float angrad) {
        return (float) Math.toDegrees(angrad);
    }

    public static double toRadiansDouble(double angle) {
        return Math.toRadians(angle);
    }

    public static float toRadiansFloat(float angle) {
        return (float) Math.toRadians(angle);
    }

    public static float radiansToTarget(float cameraYaw, double cameraX, double cameraZ, double targetX, double targetZ) {
        double xDifference = targetX - cameraX;
        double zDifference = targetZ - cameraZ;
        double distance = Math.sqrt((xDifference * xDifference) + (zDifference * zDifference));
        if (distance == 0.0d) {
            return 1.5707964f;
        }
        float rotationRadians = (float) Math.toRadians(wrapDegrees(cameraYaw - 90.0f) + 180.0f);
        float positionRadians = ((float) Math.acos(xDifference / distance)) / 2.0f;
        if (zDifference < 0.0d) {
            positionRadians = 3.1415927f - positionRadians;
        }
        float fullRadians = ((positionRadians * 2.0f) - rotationRadians) + 1.5707964f;
        float degrees = wrapDegrees((float) Math.toDegrees(fullRadians));
        return (float) Math.toRadians((-degrees) + 180.0f);
    }

    public static float distanceSquared(float startX, float startY, float startZ, float endX, float endY, float endZ) {
        return square(startX - endX) + square(startY - endY) + square(startZ - endZ);
    }

    public static double distanceSquared(Player startPlayer, Player endPlayer) {
        return startPlayer.position().distanceSquared(endPlayer.position());
    }

    public static double distanceSquared(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        return square(startX - endX) + square(startY - endY) + square(startZ - endZ);
    }

    public static float square(float number) {
        return number * number;
    }

    public static double square(double number) {
        return number * number;
    }

    public static float sigmoid(float value) {
        if (value <= 0.0f || value >= 1.0f) {
            return value;
        }
        return (float) (1.0d / (1.0d + Math.pow(2.718281828459045d, ((-1.0d) * (((double) value) - 0.5d)) * 16.0d)));
    }

    public static float catmullrom(float t, float p0, float p1, float p2, float p3) {
        float v0 = (p2 - p0) * 0.5f;
        float v1 = (p3 - p1) * 0.5f;
        float t2 = t * t;
        float t3 = t * t2;
        return ((((2.0f * p1) - (2.0f * p2)) + v0 + v1) * t3) + ((((((-3.0f) * p1) + (MAGIC_PIXEL_NUMBER * p2)) - (2.0f * v0)) - v1) * t2) + (v0 * t) + p1;
    }

    public static float fastInverseSqrt(float value) {
        float v = 0.5f * value;
        int bits = Float.floatToIntBits(value);
        float value2 = Float.intBitsToFloat(1597463007 - (bits >> 1));
        return value2 * (1.5f - ((v * value2) * value2));
    }

    public static float fastInverseCubeRoot(float value) {
        int intBits = Float.floatToIntBits(value);
        float floatBits = Float.intBitsToFloat(1419967116 - (intBits / 3));
        float floatBits2 = (0.6666667f * floatBits) + (1.0f / (((MAGIC_PIXEL_NUMBER * floatBits) * floatBits) * value));
        return (0.6666667f * floatBits2) + (1.0f / (((MAGIC_PIXEL_NUMBER * floatBits2) * floatBits2) * value));
    }

    public static float linearSin(float value) {
        float factor = (float) (0.6366197723675814d * (((double) value) % 6.283185307179586d));
        return factor < -1.0f ? (-2.0f) - factor : factor > 1.0f ? 2.0f - factor : factor;
    }

    public static float toPixel(float value) {
        return value / MAGIC_PIXEL_NUMBER;
    }

    public static float pixelToPointSize(float value) {
        return value / POINT_SIZE_NUMBER;
    }

    public static float pointSizeToPixel(float value) {
        return value * POINT_SIZE_NUMBER;
    }

    public static float scaleToFit(float parentWidth, float componentWidth) {
        return scaleToFit(parentWidth, componentWidth, 1.0f);
    }

    public static float scaleToFit(float parentWidth, float componentWidth, float scale) {
        float componentWidth2 = componentWidth * scale;
        if (parentWidth != 0.0f && componentWidth2 > parentWidth) {
            return (parentWidth / componentWidth2) - 0.05f;
        }
        return scale;
    }

    public static byte normalIntValue(float value) {
        return (byte) (((int) (clamp(value, -1.0f, 1.0f) * 127.0f)) & 255);
    }

    public static int roundToward(int a, int b) {
        return positiveCeilDiv(a, b) * b;
    }

    public static int positiveCeilDiv(int a, int b) {
        return -Math.floorDiv(-a, b);
    }

    public static float sin(double value) {
        return sin((float) value);
    }

    public static float sin(float value) {
        return (float) Math.sin(value);
    }

    public static float cos(double value) {
        return cos((float) value);
    }

    public static float cos(float value) {
        return (float) Math.cos(value);
    }

    public static float fma(float a, float b, float c) {
        return (a * b) + c;
    }

    public static int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public static int max(int a, int b, int c, int d) {
        return Math.max(a, Math.max(b, Math.max(c, d)));
    }

    public static FloatVector3 transform(FloatMatrix3 matrix, float srcX, float srcY, float srcZ) {
        NORMAL_TRANSFORM.set(fma(matrix.getM00(), srcX, fma(matrix.getM10(), srcY, matrix.getM20() * srcZ)), fma(matrix.getM01(), srcX, fma(matrix.getM11(), srcY, matrix.getM21() * srcZ)), fma(matrix.getM02(), srcX, fma(matrix.getM12(), srcY, matrix.getM22() * srcZ)));
        return NORMAL_TRANSFORM;
    }

    public static FloatVector3 transform(FloatMatrix4 matrix, float srcX, float srcY, float srcZ) {
        MATRIX_TRANSFORM.set(fma(matrix.getM00(), srcX, fma(matrix.getM10(), srcY, fma(matrix.getM20(), srcZ, matrix.getM30()))), fma(matrix.getM01(), srcX, fma(matrix.getM11(), srcY, fma(matrix.getM21(), srcZ, matrix.getM31()))), fma(matrix.getM02(), srcX, fma(matrix.getM12(), srcY, fma(matrix.getM22(), srcZ, matrix.getM32()))));
        return MATRIX_TRANSFORM;
    }

    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    public static GameMathMapper mapper() {
        return Laby.references().gameMathMapper();
    }

    public static boolean isBox(AxisAlignedBoundingBox boundingBox) {
        return boundingBox.getMinX() == 0.0d && boundingBox.getMinY() == 0.0d && boundingBox.getMinZ() == 0.0d && boundingBox.getMaxX() == 1.0d && boundingBox.getMaxY() == 1.0d && boundingBox.getMaxZ() == 1.0d;
    }

    public static void rotateDegrees(Pose entry, float angle, float x, float y, float z) {
        if (x == 0.0f && y == 0.0f && z == 0.0f) {
            return;
        }
        entry.rotate(toRadiansFloat(angle), x, y, z);
    }

    public static void rotateRadians(Pose entry, float rad, float x, float y, float z) {
        if (x == 0.0f && y == 0.0f && z == 0.0f) {
            return;
        }
        entry.rotate(rad, x, y, z);
    }

    public static void setMat3x2(Matrix3x2fc source, Matrix4f destination) {
        source.get4x4(MATRIX4x4_BUFFER);
        destination.set(MATRIX4x4_BUFFER);
    }
}
