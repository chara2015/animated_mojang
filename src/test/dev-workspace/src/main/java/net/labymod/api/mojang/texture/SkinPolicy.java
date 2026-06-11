package net.labymod.api.mojang.texture;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mojang/texture/SkinPolicy.class */
public class SkinPolicy {
    private static final boolean[][] POLICY = new boolean[64][64];

    static {
        updatePolicyMap(true);
    }

    public static GameImage applyPolicy(GameImage texture) {
        return applyPolicy(texture, false);
    }

    public static GameImage applyPolicy(GameImage texture, boolean allowHigherResolution) {
        int width = texture.getWidth();
        int height = texture.getHeight();
        boolean resolutionValid = (width == height || width == height * 2) && (width == 64 || (allowHigherResolution && width % 64 == 0));
        if (!resolutionValid) {
            throw new IllegalStateException("Discarding incorrectly sized (" + width + "x" + height + ") skin texture");
        }
        int f = width / 64;
        boolean isLegacy = width != height;
        if (isLegacy) {
            GameImage converted = Laby.references().gameImageProvider().createImage(width, width);
            converted.copyFrom(texture);
            converted.fillRect(0 * f, 32 * f, 64 * f, 32 * f, 0);
            converted.copyRect(4 * f, 16 * f, 16 * f, 32 * f, 4 * f, 4 * f, true, false);
            converted.copyRect(8 * f, 16 * f, 16 * f, 32 * f, 4 * f, 4 * f, true, false);
            converted.copyRect(0 * f, 20 * f, 24 * f, 32 * f, 4 * f, 12 * f, true, false);
            converted.copyRect(4 * f, 20 * f, 16 * f, 32 * f, 4 * f, 12 * f, true, false);
            converted.copyRect(8 * f, 20 * f, 8 * f, 32 * f, 4 * f, 12 * f, true, false);
            converted.copyRect(12 * f, 20 * f, 16 * f, 32 * f, 4 * f, 12 * f, true, false);
            converted.copyRect(44 * f, 16 * f, (-8) * f, 32 * f, 4 * f, 4 * f, true, false);
            converted.copyRect(48 * f, 16 * f, (-8) * f, 32 * f, 4 * f, 4 * f, true, false);
            converted.copyRect(40 * f, 20 * f, 0 * f, 32 * f, 4 * f, 12 * f, true, false);
            converted.copyRect(44 * f, 20 * f, (-8) * f, 32 * f, 4 * f, 12 * f, true, false);
            converted.copyRect(48 * f, 20 * f, (-16) * f, 32 * f, 4 * f, 12 * f, true, false);
            converted.copyRect(52 * f, 20 * f, (-8) * f, 32 * f, 4 * f, 12 * f, true, false);
            texture = converted;
        }
        setNoAlpha(texture, 8 * f, 0 * f, 24 * f, 8 * f);
        setNoAlpha(texture, 0 * f, 8 * f, 32 * f, 16 * f);
        if (isLegacy) {
            doNotchTransparencyHack(texture, 32 * f, 0 * f, 64 * f, 32 * f);
        }
        setNoAlpha(texture, 4 * f, 16 * f, 12 * f, 20 * f);
        setNoAlpha(texture, 20 * f, 16 * f, 36 * f, 20 * f);
        setNoAlpha(texture, 44 * f, 16 * f, 52 * f, 20 * f);
        setNoAlpha(texture, 0 * f, 20 * f, 64 * f, 32 * f);
        setNoAlpha(texture, 20 * f, 48 * f, 24 * f, 52 * f);
        setNoAlpha(texture, 36 * f, 48 * f, 40 * f, 52 * f);
        setNoAlpha(texture, 16 * f, 52 * f, 48 * f, 64 * f);
        return texture;
    }

    public static boolean isValidFormat(GameImage texture) {
        int width = texture.getWidth();
        int height = texture.getHeight();
        if (width != 64) {
            return false;
        }
        if (height != 32 && height != 64) {
            return false;
        }
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < height; y++) {
                if (!POLICY[x][y] && hasTransparency(texture, x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static MinecraftServices.SkinVariant guessVariant(GameImage texture) {
        if (texture.getWidth() != texture.getHeight()) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        int f = texture.getWidth() / 64;
        if (texture.getARGB(54 * f, 19 * f) == texture.getARGB(54 * f, 20 * f)) {
            return MinecraftServices.SkinVariant.SLIM;
        }
        return MinecraftServices.SkinVariant.CLASSIC;
    }

    private static void setNoAlpha(GameImage image, int x1, int y1, int x2, int y2) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                image.setARGB(x, y, image.getARGB(x, y) | (-16777216));
            }
        }
    }

    private static void doNotchTransparencyHack(GameImage image, int x1, int y1, int x2, int y2) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                int rgba = image.getARGB(x, y);
                if (((rgba >> 24) & 255) < 128) {
                    return;
                }
            }
        }
        for (int x3 = x1; x3 < x2; x3++) {
            for (int y3 = y1; y3 < y2; y3++) {
                image.setARGB(x3, y3, image.getARGB(x3, y3) & 16777215);
            }
        }
    }

    private static boolean hasTransparency(GameImage texture, int x, int y) {
        int rgba = texture.getARGB(x, y);
        return ColorFormat.ARGB32.alpha(rgba) < 255;
    }

    private static void updatePolicyMap(boolean slim) {
        updatePolicyMap(0, 0, 64, 64, true);
        updatePolicyMap(0, 8, 32, 8, false);
        updatePolicyMap(8, 0, 16, 8, false);
        updatePolicyMap(0, 20, 56 - (slim ? 2 : 0), 12, false);
        updatePolicyMap(4, 16, 8, 4, false);
        updatePolicyMap(20, 16, 16, 4, false);
        updatePolicyMap(44, 16, 8 - (slim ? 2 : 0), 4, false);
        updatePolicyMap(16, 52, 32 - (slim ? 2 : 0), 12, false);
        updatePolicyMap(20, 48, 8, 4, false);
        updatePolicyMap(36, 48, 8 - (slim ? 2 : 0), 4, false);
    }

    private static void updatePolicyMap(int x, int y, int width, int height, boolean alpha) {
        for (int relX = x; relX < x + width; relX++) {
            for (int relY = y; relY < y + height; relY++) {
                if (POLICY[relX] == null) {
                    POLICY[relX] = new boolean[64];
                }
                POLICY[relX][relY] = alpha;
            }
        }
    }
}
