package net.labymod.v1_21_3.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/resources/texture/NativeImageUtils.class */
public class NativeImageUtils {
    public static ffs read(ByteBuffer buffer) throws IOException {
        return ffs.a(buffer);
    }

    public static ffs read(InputStream inputStream) throws IOException {
        return ffs.a(inputStream);
    }

    public static ffs asNativeImage(BufferedImage image) {
        return asNativeImage(image, NativeImageUtils::processImage);
    }

    public static ffs asNativeImage(BufferedImage image, BufferedImageProcessor processor) {
        ffs result = new ffs(image.getWidth(), image.getHeight(), true);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                result.a(x, y, processor.getPixelColor(image, x, y));
            }
        }
        return result;
    }

    public static void fill(ffs image, int width, int height, int color) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.a(x, y, color);
            }
        }
    }

    public static void fillSubImage(ffs source, ffs destination, int parentX, int parentY, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                destination.a(x, y, source.a(x + parentX, y + parentY));
            }
        }
    }

    public static BufferedImage asBufferedImage(ffs image) {
        BufferedImage result = new BufferedImage(image.a(), image.b(), 2);
        for (int x = 0; x < image.a(); x++) {
            for (int y = 0; y < image.b(); y++) {
                result.setRGB(x, y, image.a(x, y));
            }
        }
        return result;
    }

    private static int processImage(BufferedImage image, int x, int y) {
        return image.getRGB(x, y);
    }
}
