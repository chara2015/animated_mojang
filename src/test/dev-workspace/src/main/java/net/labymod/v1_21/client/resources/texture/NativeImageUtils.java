package net.labymod.v1_21.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/resources/texture/NativeImageUtils.class */
public class NativeImageUtils {
    public static faj read(ByteBuffer buffer) throws IOException {
        return faj.a(buffer);
    }

    public static faj read(InputStream inputStream) throws IOException {
        return faj.a(inputStream);
    }

    public static faj asNativeImage(BufferedImage image) {
        return asNativeImage(image, NativeImageUtils::processImage);
    }

    public static faj asNativeImage(BufferedImage image, BufferedImageProcessor processor) {
        faj result = new faj(image.getWidth(), image.getHeight(), true);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                result.a(x, y, processor.getPixelColor(image, x, y));
            }
        }
        return result;
    }

    public static void fill(faj image, int width, int height, int color) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.a(x, y, color);
            }
        }
    }

    public static void fillSubImage(faj source, faj destination, int parentX, int parentY, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                destination.a(x, y, source.a(x + parentX, y + parentY));
            }
        }
    }

    public static BufferedImage asBufferedImage(faj image) {
        BufferedImage result = new BufferedImage(image.a(), image.b(), 2);
        for (int x = 0; x < image.a(); x++) {
            for (int y = 0; y < image.b(); y++) {
                result.setRGB(x, y, ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, image.a(x, y)));
            }
        }
        return result;
    }

    private static int processImage(BufferedImage image, int x, int y) {
        return ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, image.getRGB(x, y));
    }
}
