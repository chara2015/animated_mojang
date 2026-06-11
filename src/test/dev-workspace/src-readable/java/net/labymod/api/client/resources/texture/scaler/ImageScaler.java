package net.labymod.api.client.resources.texture.scaler;

import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/scaler/ImageScaler.class */
public interface ImageScaler {
    public static final ImageScaler NEAREST_NEIGHBOR = new NearestNeighbor();
    public static final ImageScaler BILINEAR = new Bilinear();

    GameImage scale(GameImage gameImage, int i, int i2);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/scaler/ImageScaler$NearestNeighbor.class */
    public static class NearestNeighbor implements ImageScaler {
        @Override // net.labymod.api.client.resources.texture.scaler.ImageScaler
        public GameImage scale(GameImage source, int newWidth, int newHeight) {
            if (source.getWidth() == newWidth && source.getHeight() == newHeight) {
                return source;
            }
            GameImage result = GameImage.IMAGE_PROVIDER.createImage(newWidth, newHeight);
            float sx = source.getWidth() / newWidth;
            float sy = source.getHeight() / newHeight;
            for (int x = 0; x < newWidth; x++) {
                int sourceX = (int) (x * sx);
                for (int y = 0; y < newHeight; y++) {
                    int sourceY = (int) (y * sy);
                    result.setARGB(x, y, source.getARGB(sourceX, sourceY));
                }
            }
            return result;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/scaler/ImageScaler$Bilinear.class */
    public static class Bilinear implements ImageScaler {
        @Override // net.labymod.api.client.resources.texture.scaler.ImageScaler
        public GameImage scale(GameImage source, int width, int height) {
            if (source.getWidth() == width && source.getHeight() == height) {
                return source;
            }
            GameImage out = GameImage.IMAGE_PROVIDER.createImage(width, height);
            int srcW = source.getWidth();
            int srcH = source.getHeight();
            float sx = (srcW - 1) / width;
            float sy = (srcH - 1) / height;
            for (int x = 0; x < width; x++) {
                float gx = x * sx;
                int x0 = (int) gx;
                int x1 = Math.min(x0 + 1, srcW - 1);
                float tx = gx - x0;
                for (int y = 0; y < height; y++) {
                    float gy = y * sy;
                    int y0 = (int) gy;
                    int y1 = Math.min(y0 + 1, srcH - 1);
                    float ty = gy - y0;
                    int c00 = source.getARGB(x0, y0);
                    int c10 = source.getARGB(x1, y0);
                    int c01 = source.getARGB(x0, y1);
                    int c11 = source.getARGB(x1, y1);
                    int color = lerpARGB(c00, c10, c01, c11, tx, ty);
                    out.setARGB(x, y, color);
                }
            }
            return out;
        }

        private int lerpARGB(int c00, int c10, int c01, int c11, float tx, float ty) {
            ColorFormat format = ColorFormat.ARGB32;
            int alpha = bilerp(format.alpha(c00), format.alpha(c10), format.alpha(c01), format.alpha(c11), tx, ty);
            int red = bilerp(format.red(c00), format.red(c10), format.red(c01), format.red(c11), tx, ty);
            int green = bilerp(format.green(c00), format.green(c10), format.green(c01), format.green(c11), tx, ty);
            int blue = bilerp(format.blue(c00), format.blue(c10), format.blue(c01), format.blue(c11), tx, ty);
            return format.pack(red, green, blue, alpha);
        }

        private int bilerp(int c00, int c10, int c01, int c11, float tx, float ty) {
            float top = MathHelper.lerp(c00, c10, tx);
            float bottom = MathHelper.lerp(c01, c11, tx);
            return (int) (MathHelper.lerp(top, bottom, ty) + 0.5f);
        }
    }
}
