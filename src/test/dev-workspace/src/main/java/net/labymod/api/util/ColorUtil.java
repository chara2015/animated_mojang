package net.labymod.api.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.function.Functional;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/ColorUtil.class */
public class ColorUtil {
    private static final String HEX_FORMAT = "#%02x%02x%02x";
    private static final String HEX_ALPHA_FORMAT = "#%02x%02x%02x%02x";
    public static final char LEGACY_COLOR_CHAR_PREFIX = 167;
    public static final TextColor[] DEFAULT_COLORS = {NamedTextColor.BLACK, NamedTextColor.DARK_BLUE, NamedTextColor.DARK_GREEN, NamedTextColor.DARK_AQUA, NamedTextColor.DARK_RED, NamedTextColor.DARK_PURPLE, NamedTextColor.GOLD, NamedTextColor.GRAY, NamedTextColor.DARK_GRAY, NamedTextColor.BLUE, NamedTextColor.GREEN, NamedTextColor.AQUA, NamedTextColor.RED, NamedTextColor.LIGHT_PURPLE, NamedTextColor.YELLOW, NamedTextColor.WHITE};
    public static final int WHITE_ARGB = ColorFormat.ARGB32.pack(255, 255, 255, 255);

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static final int WHITE_RGBA = toValue(NamedTextColor.WHITE.getValue());
    private static final Int2ObjectMap<TextColor> DEFAULT_COLOR_LOOKUP = (Int2ObjectMap) Functional.of(new Int2ObjectOpenHashMap(DEFAULT_COLORS.length), map -> {
        TextColor[] arr$ = DEFAULT_COLORS;
        for (TextColor defaultColor : arr$) {
            map.put(defaultColor.getValue(), defaultColor);
        }
    });

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/ColorUtil$ColorChannel.class */
    @FunctionalInterface
    public interface ColorChannel {
        void channel(int i, int i2, int i3, int i4);
    }

    private ColorUtil() {
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(int hex) {
        return packARGB(hex, 255);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(int hex, int alpha) {
        return toValue((hex >> 16) & 255, (hex >> 8) & 255, hex & 255, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(int red, int green, int blue) {
        return toValue(red, green, blue, 255);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(int red, int green, int blue, int alpha) {
        return packARGB(red, green, blue, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(float red, float green, float blue, float alpha) {
        return packARGB(red, green, blue, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(java.awt.Color color, int alpha) {
        return toValue(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(int hex, int alpha) {
        return toValue((hex >> 16) & 255, (hex >> 8) & 255, hex & 255, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(int red, int green, int blue) {
        return packARGB(red, green, blue, 255);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(int red, int green, int blue, int alpha) {
        return ColorFormat.ARGB32.pack(red, green, blue, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(float red, float green, float blue, float alpha) {
        return ColorFormat.ARGB32.pack(red, green, blue, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packABGR(int red, int green, int blue, int alpha) {
        return ColorFormat.ABGR32.pack(red, green, blue, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packABGR(float red, float green, float blue, float alpha) {
        return ColorFormat.ABGR32.pack(red, green, blue, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getRed(int color) {
        return ColorFormat.ARGB32.normalizedRed(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getRedInt(int color) {
        return ColorFormat.ARGB32.red(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getGreen(int color) {
        return ColorFormat.ARGB32.normalizedGreen(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getGreenInt(int color) {
        return ColorFormat.ARGB32.green(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getBlue(int color) {
        return ColorFormat.ARGB32.normalizedBlue(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getBlueInt(int color) {
        return ColorFormat.ARGB32.blue(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getAlpha(int color) {
        return ColorFormat.ARGB32.normalizedAlpha(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getAlphaInt(int color) {
        return ColorFormat.ARGB32.alpha(color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int mapARGBtoABGR(int color) {
        return ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int mapABGRtoARGB(int color) {
        return ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, color);
    }

    public static float[] toRGBA(int color) {
        ColorFormat format = ColorFormat.ARGB32;
        return new float[]{format.normalizedRed(color), format.normalizedGreen(color), format.normalizedBlue(color), format.normalizedAlpha(color)};
    }

    public static void fillArray(int[] channels, int color) {
        channels[0] = (color >> 16) & 255;
        channels[1] = (color >> 8) & 255;
        channels[2] = color & 255;
        if (channels.length == 4) {
            channels[3] = (color >> 24) & 255;
        }
    }

    public static int rgbToRgba(int rgb) {
        return (-16777216) | rgb;
    }

    public static boolean isNoColor(int color) {
        return color == 0;
    }

    public static boolean isNoColor(float red, float green, float blue, float alpha) {
        return red == 0.0f && green == 0.0f && blue == 0.0f && alpha == 0.0f;
    }

    public static String removeLegacyColors(String text) {
        return text.replaceAll("§[a-z0-9]", "");
    }

    public static String rgbToHex(int color) {
        String[] hex = {null};
        splitIntoChannel(color, (red, green, blue, alpha) -> {
            hex[0] = rgbToHex(red, green, blue);
        });
        return hex[0];
    }

    public static String rgbToHex(int red, int green, int blue) {
        return String.format(Locale.ROOT, HEX_FORMAT, Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue));
    }

    public static String rgbToHex(int red, int green, int blue, int alpha) {
        return String.format(Locale.ROOT, HEX_ALPHA_FORMAT, Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue), Integer.valueOf(alpha));
    }

    public static void splitIntoChannel(int color, @NotNull ColorChannel colorChannel) {
        int red = (color >> 16) & 255;
        int green = (color >> 8) & 255;
        int blue = color & 255;
        int alpha = (color >> 24) & 255;
        colorChannel.channel(red, green, blue, alpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int add(int color, int deltaRed, int deltaGreen, int deltaBlue, int deltaAlpha) {
        return ColorFormat.ARGB32.add(color, deltaRed, deltaGreen, deltaBlue, deltaAlpha);
    }

    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int multiply(int color, float factorRed, float factorGreen, float factorBlue, float factorAlpha) {
        return ColorFormat.ARGB32.mul(color, factorRed, factorGreen, factorBlue, factorAlpha);
    }

    public static int combineAlpha(int color) {
        return combineAlpha(color, Laby.labyAPI().renderPipeline().getAlpha());
    }

    public static int combineAlpha(int color, ColorFormat format) {
        return combineAlpha(color, Laby.labyAPI().renderPipeline().getAlpha(), format);
    }

    public static int combineAlpha(int color, float alpha) {
        return combineAlpha(color, alpha, ColorFormat.ARGB32);
    }

    public static int combineAlpha(int color, float alpha, ColorFormat format) {
        if (color == -1) {
            color = WHITE_ARGB;
        }
        if (alpha == 1.0f) {
            return color;
        }
        if (alpha == 0.0f) {
            return format.pack(color, 0);
        }
        float res = format.normalizedAlpha(color) * alpha;
        return format.pack(color, MathHelper.ceil(res * 255.0f));
    }

    public static int adjustedColor(int color) {
        return (color & (-67108864)) == 0 ? color | (-16777216) : color;
    }

    public static int getRainbowColor(float speed) {
        return getRainbowColor(speed, Laby.labyAPI().renderPipeline().gameTickProvider().get());
    }

    public static int getRainbowColor(float speed, float delta) {
        float rainbowSpeed = (speed / 100.0f) * delta;
        int red = (int) ((Math.sin(rainbowSpeed + 0.0f) * 127.0d) + 128.0d);
        int green = (int) ((Math.sin(rainbowSpeed + 2.0f) * 127.0d) + 128.0d);
        int blue = (int) ((Math.sin(rainbowSpeed + 4.0f) * 127.0d) + 128.0d);
        return ColorFormat.ARGB32.pack(red, green, blue);
    }

    public static int blendColors(int destination, int source) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        float sourceAlpha = colorFormat.normalizedAlpha(source);
        float destinationAlpha = colorFormat.normalizedAlpha(destination);
        float outAlpha = sourceAlpha + (destinationAlpha * (1.0f - sourceAlpha));
        if (outAlpha < 0.001f) {
            return 0;
        }
        int sourceRed = colorFormat.red(source);
        int sourceGreen = colorFormat.green(source);
        int sourceBlue = colorFormat.blue(source);
        int destinationRed = colorFormat.red(destination);
        int destinationGreen = colorFormat.green(destination);
        int destinationBlue = colorFormat.blue(destination);
        int outRed = (int) (((sourceRed * sourceAlpha) + ((destinationRed * destinationAlpha) * (1.0f - sourceAlpha))) / outAlpha);
        int outGreen = (int) (((sourceGreen * sourceAlpha) + ((destinationGreen * destinationAlpha) * (1.0f - sourceAlpha))) / outAlpha);
        int outBlue = (int) (((sourceBlue * sourceAlpha) + ((destinationBlue * destinationAlpha) * (1.0f - sourceAlpha))) / outAlpha);
        return colorFormat.pack(outRed, outGreen, outBlue, colorFormat.normalize(outAlpha));
    }

    public static TextColor getClosestDefaultTextColor(TextColor color) {
        return getClosestDefaultTextColor(color.color());
    }

    public static TextColor getClosestDefaultTextColor(Color color) {
        TextColor textColor = (TextColor) DEFAULT_COLOR_LOOKUP.get(color.getValue());
        if (textColor != null) {
            return textColor;
        }
        float matchedDistance = Float.MAX_VALUE;
        TextColor match = DEFAULT_COLORS[0];
        for (TextColor potential : DEFAULT_COLORS) {
            float distance = distance(color, potential.color());
            if (distance < matchedDistance) {
                match = potential;
                matchedDistance = distance;
            }
            if (distance == 0.0f) {
                break;
            }
        }
        return match;
    }

    public static int lerp(int color, int previousColor, float delta) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        boolean colorPresent = color != 0;
        int alpha = colorPresent ? colorFormat.alpha(color) : 0;
        int red = colorFormat.red(colorPresent ? color : previousColor);
        int green = colorFormat.green(colorPresent ? color : previousColor);
        int blue = colorFormat.blue(colorPresent ? color : previousColor);
        int previousAlpha = colorFormat.alpha(previousColor);
        int previousRed = colorFormat.red(previousColor);
        int previousGreen = colorFormat.green(previousColor);
        int previousBlue = colorFormat.blue(previousColor);
        int newAlpha = MathHelper.lerp(alpha, previousAlpha, delta);
        int newRed = MathHelper.lerp(red, previousRed, delta);
        int newGreen = MathHelper.lerp(green, previousGreen, delta);
        int newBlue = MathHelper.lerp(blue, previousBlue, delta);
        return colorFormat.pack(newRed, newGreen, newBlue, newAlpha);
    }

    public static int hsvToRgb(float hue, float saturation, float value) {
        float red;
        float green;
        float blue;
        int sector = ((int) (hue * 6.0f)) % 6;
        float fractionalPart = (hue * 6.0f) - sector;
        float p = value * (1.0f - saturation);
        float q = value * (1.0f - (fractionalPart * saturation));
        float t = value * (1.0f - ((1.0f - fractionalPart) * saturation));
        switch (sector) {
            case 0:
                red = value;
                green = t;
                blue = p;
                break;
            case 1:
                red = q;
                green = value;
                blue = p;
                break;
            case 2:
                red = p;
                green = value;
                blue = t;
                break;
            case 3:
                red = p;
                green = q;
                blue = value;
                break;
            case 4:
                red = t;
                green = p;
                blue = value;
                break;
            case 5:
                red = value;
                green = p;
                blue = q;
                break;
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
        return ColorFormat.ARGB32.pack(MathHelper.clamp((int) (red * 255.0f), 0, 255), MathHelper.clamp((int) (green * 255.0f), 0, 255), MathHelper.clamp((int) (blue * 255.0f), 0, 255), 255);
    }

    private static float distance(Color self, Color other) {
        float selfHue = self.getHue() / 360.0f;
        float otherHue = other.getHue() / 360.0f;
        float hueDistance = 3.0f * Math.min(Math.abs(selfHue - otherHue), 1.0f - Math.abs(selfHue - otherHue));
        float saturationDiff = (self.getSaturation() / 100.0f) - (other.getSaturation() / 100.0f);
        float brightnessDiff = (self.getBrightness() / 100.0f) - (other.getBrightness() / 100.0f);
        return (hueDistance * hueDistance) + (saturationDiff * saturationDiff) + (brightnessDiff * brightnessDiff);
    }

    public static int lerpedColor(long transitionDuration, float delta, LssProperty<Integer> colorProperty) {
        int color;
        if (transitionDuration <= 0) {
            return colorProperty.get().intValue();
        }
        int currentColor = colorProperty.get().intValue();
        int previousColor = colorProperty.getPreviousLerpedAccessedValue().intValue();
        float progress = colorProperty.getProgress(delta, transitionDuration);
        if (progress == 1.0f) {
            color = currentColor;
        } else {
            color = lerp(currentColor, previousColor, progress);
        }
        colorProperty.setLastLerpedAccessedValue(Integer.valueOf(color));
        return color;
    }

    public static int invertColor(int argb) {
        int r = (argb >> 16) & 255;
        int g = (argb >> 8) & 255;
        int b = argb & 255;
        int a = (argb >> 24) & 255;
        double luminance = (((0.299d * ((double) r)) + (0.587d * ((double) g))) + (0.114d * ((double) b))) / 255.0d;
        int invertedColor = luminance > 0.5d ? -16777216 : -1;
        return (a << 24) | (invertedColor & 16777215);
    }
}
