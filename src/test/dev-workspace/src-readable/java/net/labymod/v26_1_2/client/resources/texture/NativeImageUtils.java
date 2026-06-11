package net.labymod.v26_1_2.client.resources.texture;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/resources/texture/NativeImageUtils.class */
public class NativeImageUtils {
    public static NativeImage read(ByteBuffer buffer) throws IOException {
        return NativeImage.read(buffer);
    }

    public static NativeImage read(InputStream inputStream) throws IOException {
        return NativeImage.read(inputStream);
    }

    public static NativeImage asNativeImage(BufferedImage image) {
        return asNativeImage(image, NativeImageUtils::processImage);
    }

    public static NativeImage asNativeImage(BufferedImage image, BufferedImageProcessor processor) {
        NativeImage result = new NativeImage(image.getWidth(), image.getHeight(), true);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                result.setPixel(x, y, processor.getPixelColor(image, x, y));
            }
        }
        return result;
    }

    public static void fill(NativeImage image, int width, int height, int color) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, color);
            }
        }
    }

    public static void fillSubImage(NativeImage source, NativeImage destination, int parentX, int parentY, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                destination.setPixel(x, y, source.getPixel(x + parentX, y + parentY));
            }
        }
    }

    public static BufferedImage asBufferedImage(NativeImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                result.setRGB(x, y, image.getPixel(x, y));
            }
        }
        return result;
    }

    private static int processImage(BufferedImage image, int x, int y) {
        return image.getRGB(x, y);
    }
}
