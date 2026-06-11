package net.labymod.v1_21_11.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/resources/texture/NativeImageUtils.class */
public class NativeImageUtils {
    public static fyh read(ByteBuffer buffer) throws IOException {
        return fyh.a(buffer);
    }

    public static fyh read(InputStream inputStream) throws IOException {
        return fyh.a(inputStream);
    }

    public static fyh asNativeImage(BufferedImage image) {
        return asNativeImage(image, NativeImageUtils::processImage);
    }

    public static fyh asNativeImage(BufferedImage image, BufferedImageProcessor processor) {
        fyh result = new fyh(image.getWidth(), image.getHeight(), true);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                result.b(x, y, processor.getPixelColor(image, x, y));
            }
        }
        return result;
    }

    public static void fill(fyh image, int width, int height, int color) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.b(x, y, color);
            }
        }
    }

    public static void fillSubImage(fyh source, fyh destination, int parentX, int parentY, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                destination.b(x, y, source.a(x + parentX, y + parentY));
            }
        }
    }

    public static BufferedImage asBufferedImage(fyh image) {
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
