package net.labymod.api.util.color.format;

import java.awt.Color;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/color/format/ColorFormat.class */
public interface ColorFormat {
    public static final int COMPONENT_BITS = 8;
    public static final int COMPONENT_MASK = 255;
    public static final float COMPONENT_RANGE = 255.0f;
    public static final ColorFormat ARGB32 = new ARGB32ColorFormat();
    public static final ColorFormat ABGR32 = new ABGR32ColorFormat();

    int red(int i);

    int green(int i);

    int blue(int i);

    int alpha(int i);

    int pack(int i, int i2, int i3, int i4);

    int withAlpha(int i, int i2);

    default float normalizedRed(int value) {
        return normalize(red(value));
    }

    default float normalizedGreen(int value) {
        return normalize(green(value));
    }

    default float normalizedBlue(int value) {
        return normalize(blue(value));
    }

    default float normalizedAlpha(int value) {
        return normalize(alpha(value));
    }

    default int pack(@NotNull Color color, int alpha) {
        return pack(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    default int pack(@NotNull net.labymod.api.util.Color color, int alpha) {
        return pack(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    default int pack(int value, int alpha) {
        return pack(red(value), green(value), blue(value), alpha);
    }

    default int pack(int red, int green, int blue) {
        return pack(red, green, blue, 255);
    }

    default int pack(float red, float green, float blue, float alpha) {
        return pack(normalize(red), normalize(green), normalize(blue), normalize(alpha));
    }

    default int add(int sourceValue, int deltaRed, int deltaGreen, int deltaBlue, int deltaAlpha) {
        int red = red(sourceValue) + deltaRed;
        int green = green(sourceValue) + deltaGreen;
        int blue = blue(sourceValue) + deltaBlue;
        int alpha = alpha(sourceValue) + deltaAlpha;
        return pack(clamp(red), clamp(green), clamp(blue), clamp(alpha));
    }

    default int sub(int sourceValue, int deltaRed, int deltaGreen, int deltaBlue, int deltaAlpha) {
        int red = red(sourceValue) - deltaRed;
        int green = green(sourceValue) - deltaGreen;
        int blue = blue(sourceValue) - deltaBlue;
        int alpha = alpha(sourceValue) - deltaAlpha;
        return pack(clamp(red), clamp(green), clamp(blue), clamp(alpha));
    }

    default int mul(int sourceValue, float factorRed, float factorGreen, float factorBlue, float factorAlpha) {
        float red = normalizedRed(sourceValue) * factorRed;
        float green = normalizedGreen(sourceValue) * factorGreen;
        float blue = normalizedBlue(sourceValue) * factorBlue;
        float alpha = normalizedAlpha(sourceValue) * factorAlpha;
        return pack(clampNormalized(red), clampNormalized(green), clampNormalized(blue), clampNormalized(alpha));
    }

    default int scaleRGB(int value, float scale) {
        return scaleRGB(value, scale, scale, scale);
    }

    default int scaleRGB(int value, float rScale, float gScale, float bScale) {
        return pack(clampNormalized(normalizedRed(value) * rScale), clampNormalized(normalizedGreen(value) * gScale), clampNormalized(normalizedBlue(value) * bScale), normalizedAlpha(value));
    }

    default PackedColor createPackedColor(int value) {
        return new PackedColor(this, value);
    }

    default int packTo(ColorFormat destinationFormat, int value) {
        int red = red(value);
        int green = green(value);
        int blue = blue(value);
        int alpha = alpha(value);
        return destinationFormat.pack(red, green, blue, alpha);
    }

    default int clamp(int value) {
        return MathHelper.clamp(value, 0, 255);
    }

    default float clampNormalized(float value) {
        return MathHelper.clamp(value, 0.0f, 1.0f);
    }

    default int normalize(float value) {
        return ((int) (value * 255.0f)) & 255;
    }

    default float normalize(int value) {
        return value / 255.0f;
    }
}
