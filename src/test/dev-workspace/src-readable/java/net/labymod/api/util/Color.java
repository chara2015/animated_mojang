package net.labymod.api.util;

import net.labymod.api.Laby;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Color.class */
public class Color {
    private static final float DEFAULT_CHROMA_SPEED = 1.0f;
    private final int value;
    private final int alpha;
    private final int hue;
    private final int saturation;
    private final int brightness;
    private boolean rainbow;
    private float rainbowSpeed;
    private float lastRainbowValueAt;
    private int lastRainbowValue;
    private static final GameTickProvider TICK_PROVIDER = Laby.references().gameTickProvider();
    public static final Color WHITE = ofRGB(255, 255, 255);
    public static final Color LIGHT_GRAY = ofRGB(192, 192, 192);
    public static final Color GRAY = ofRGB(128, 128, 128);
    public static final Color DARK_GRAY = ofRGB(64, 64, 64);
    public static final Color BLACK = ofRGB(0, 0, 0);
    public static final Color RED = ofRGB(255, 0, 0);
    public static final Color PINK = ofRGB(255, 175, 175);
    public static final Color ORANGE = ofRGB(255, 200, 0);
    public static final Color YELLOW = ofRGB(255, 255, 0);
    public static final Color GREEN = ofRGB(0, 255, 0);
    public static final Color MAGENTA = ofRGB(255, 0, 255);
    public static final Color CYAN = ofRGB(0, 255, 255);
    public static final Color BLUE = ofRGB(0, 0, 255);

    private Color(int value, int alpha, int hue, int saturation, int brightness) {
        this.rainbowSpeed = DEFAULT_CHROMA_SPEED;
        this.value = value & 16777215;
        this.alpha = alpha;
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    private Color(int value, int alpha, int[] hsb) {
        this(value, alpha, hsb[0], hsb[1], hsb[2]);
    }

    private Color(int alpha, int hue, int saturation, int brightness) {
        this(HSBtoRGB(hue, saturation, brightness), alpha, hue, saturation, brightness);
    }

    public static Color of(int value) {
        return of(value, ColorFormat.ARGB32.alpha(value));
    }

    public static Color of(int value, int alpha) {
        int strippedValue = value & 16777215;
        return new Color(strippedValue, alpha, RGBtoHSB(strippedValue));
    }

    public static Color of(String hex) {
        return of(Integer.decode(hex).intValue() & 16777215, 255);
    }

    public static Color of(java.awt.Color color) {
        return ofRGB(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color ofHSB(float hue, float saturation, float brightness) {
        return ofHSB(hue, saturation, brightness, 255);
    }

    public static Color ofHSB(float hue, float saturation, float brightness, int alpha) {
        return ofHSB((int) (hue * 360.0f), (int) (saturation * 100.0f), (int) (brightness * 100.0f), alpha);
    }

    public static Color ofHSB(int hue, int saturation, int brightness) {
        return ofHSB(hue, saturation, brightness, 255);
    }

    public static Color ofHSB(int hue, int saturation, int brightness, int alpha) {
        int value = HSBtoRGB(hue, saturation, brightness);
        return new Color(value, alpha, hue, saturation, brightness);
    }

    public static Color ofRGB(int red, int green, int blue) {
        return of(ColorFormat.ARGB32.pack(red, green, blue, 255));
    }

    public static Color ofRGB(int red, int green, int blue, int alpha) {
        return of(ColorFormat.ARGB32.pack(red, green, blue, alpha));
    }

    public static Color ofRGB(float red, float green, float blue) {
        return ofRGB(red, green, blue, 255);
    }

    public static Color ofRGB(float red, float green, float blue, int alpha) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        return ofRGB(colorFormat.normalize(red), colorFormat.normalize(green), colorFormat.normalize(blue), alpha);
    }

    public int getRed() {
        return ColorFormat.ARGB32.red(get());
    }

    public int getGreen() {
        return ColorFormat.ARGB32.green(get());
    }

    public int getBlue() {
        return ColorFormat.ARGB32.blue(get());
    }

    public int getAlpha() {
        return this.alpha;
    }

    public int get() {
        if (this.rainbow) {
            float currentTick = TICK_PROVIDER.get();
            if (currentTick != this.lastRainbowValueAt) {
                this.lastRainbowValueAt = currentTick;
                int rainbowSaturation = this.saturation == 0 ? 75 : this.saturation;
                int rainbowBrightness = this.brightness == 0 ? 75 : this.brightness;
                int rainbowValue = HSBtoRGB(getHue(), rainbowSaturation, rainbowBrightness) & 16777215;
                this.lastRainbowValue = ColorFormat.ARGB32.withAlpha(rainbowValue, this.alpha);
            }
            return this.lastRainbowValue;
        }
        return ColorFormat.ARGB32.withAlpha(this.value, this.alpha);
    }

    public int getHue() {
        return getHue(TICK_PROVIDER.get());
    }

    private int getHue(float tick) {
        if (this.rainbow) {
            float rainbowSpeed = ((this.rainbowSpeed * 2.0f) / 100.0f) * tick;
            return (int) ((MathHelper.sin(rainbowSpeed + this.hue) * 179.0f) + 180.0f);
        }
        return this.hue;
    }

    public int getSaturation() {
        return this.saturation;
    }

    public int getBrightness() {
        return this.brightness;
    }

    public boolean isChroma() {
        return this.rainbow;
    }

    public float getChromaSpeed() {
        return this.rainbowSpeed;
    }

    public boolean isDefaultChromaSpeed() {
        return this.rainbowSpeed == DEFAULT_CHROMA_SPEED;
    }

    public Color withAlpha(int alpha) {
        if (this.alpha == alpha) {
            return this;
        }
        Color color = new Color(this.value, alpha, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (isChroma()) {
            color.rainbow = true;
        }
        return color;
    }

    public Color withAlpha(float alpha) {
        return withAlpha((int) (alpha * 255.0f));
    }

    public Color withHue(int hue) {
        if (this.hue == hue) {
            return this;
        }
        Color color = new Color(this.alpha, hue, this.saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (isChroma()) {
            color.rainbow = true;
        }
        return color;
    }

    public Color withSaturation(int saturation) {
        if (this.saturation == saturation) {
            return this;
        }
        Color color = new Color(this.alpha, this.hue, saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (isChroma()) {
            color.rainbow = true;
        }
        return color;
    }

    public Color withBrightness(int brightness) {
        if (this.brightness == brightness) {
            return this;
        }
        Color color = new Color(this.alpha, this.hue, this.saturation, brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (isChroma()) {
            color.rainbow = true;
        }
        return color;
    }

    public Color withoutAlpha() {
        if (this.alpha == 255) {
            return this;
        }
        Color color = new Color(this.value, 255, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (isChroma()) {
            color.rainbow = true;
        }
        return color;
    }

    public Color withChroma() {
        return withChroma(this.rainbowSpeed);
    }

    public Color withChroma(float speed) {
        if (this.rainbow && this.rainbowSpeed == speed) {
            return this;
        }
        Color color = new Color(this.value, this.alpha, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = speed;
        color.rainbow = true;
        return color;
    }

    public Color withoutChroma() {
        return withoutChroma(this.rainbowSpeed);
    }

    public Color withoutChroma(float speed) {
        if (!this.rainbow && this.rainbowSpeed == speed) {
            return this;
        }
        Color color = new Color(this.value, this.alpha, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = speed;
        color.rainbow = false;
        return color;
    }

    public Color resetChromaSpeed() {
        return this.rainbow ? withChroma(DEFAULT_CHROMA_SPEED) : withoutChroma(DEFAULT_CHROMA_SPEED);
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return "Color{value=" + this.value + ", alpha=" + this.alpha + ", rainbow=" + this.rainbow + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Color color = (Color) o;
        return this.value == color.value && this.alpha == color.alpha && this.rainbow == color.rainbow && this.rainbowSpeed == color.rainbowSpeed;
    }

    public int hashCode() {
        int result = this.value;
        return (31 * ((31 * ((31 * result) + this.alpha)) + (this.rainbow ? 1 : 0))) + (this.rainbowSpeed != 0.0f ? Float.floatToIntBits(this.rainbowSpeed) : 0);
    }

    private static int getViableColor(int color) {
        return Math.max(0, Math.min(255, color));
    }

    public static int[] RGBtoHSB(int red, int green, int blue) {
        return RGBtoHSB(ColorFormat.ARGB32.pack(red, green, blue));
    }

    public static int[] RGBtoHSB(int rgb) {
        float h;
        float h2;
        ColorFormat colorFormat = ColorFormat.ARGB32;
        float r = colorFormat.normalizedRed(rgb);
        float g = colorFormat.normalizedGreen(rgb);
        float b = colorFormat.normalizedBlue(rgb);
        float M = r > g ? Math.max(r, b) : Math.max(g, b);
        float m = r < g ? Math.min(r, b) : Math.min(g, b);
        float c = M - m;
        if (M == r) {
            float f = (g - b) / c;
            while (true) {
                h2 = f;
                if (h2 >= 0.0f) {
                    break;
                }
                f = h2 + 6.0f;
            }
            h = h2 % 6.0f;
        } else if (M == g) {
            h = ((b - r) / c) + 2.0f;
        } else {
            h = ((r - g) / c) + 4.0f;
        }
        float s = c / M;
        int[] iArr = new int[3];
        iArr[0] = c == 0.0f ? 0 : (int) (h * 60.0f);
        iArr[1] = (int) (s * 100.0f);
        iArr[2] = (int) (M * 100.0f);
        return iArr;
    }

    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        return HSBtoRGB((int) (hue * 360.0f), (int) (saturation * 100.0f), (int) (brightness * 100.0f));
    }

    public static int HSBtoRGB(int hue, int saturation, int brightness) {
        float r;
        float g;
        float b;
        int hue2 = hue % 360;
        float s = saturation / 100.0f;
        float v = brightness / 100.0f;
        float c = v * s;
        float h = hue2 / 60.0f;
        float x = c * (DEFAULT_CHROMA_SPEED - Math.abs((h % 2.0f) - DEFAULT_CHROMA_SPEED));
        switch (hue2 / 60) {
            case 0:
                r = c;
                g = x;
                b = 0.0f;
                break;
            case 1:
                r = x;
                g = c;
                b = 0.0f;
                break;
            case 2:
                r = 0.0f;
                g = c;
                b = x;
                break;
            case 3:
                r = 0.0f;
                g = x;
                b = c;
                break;
            case 4:
                r = x;
                g = 0.0f;
                b = c;
                break;
            case 5:
                r = c;
                g = 0.0f;
                b = x;
                break;
            default:
                return 0;
        }
        float m = v - c;
        return ColorFormat.ARGB32.pack((int) ((r + m) * 255.0f), (int) ((g + m) * 255.0f), (int) ((b + m) * 255.0f));
    }

    public static int withAlpha(int color, int alpha) {
        return ColorFormat.ARGB32.withAlpha(color, alpha);
    }
}
