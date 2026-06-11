package net.labymod.api.client.screenshot;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/screenshot/ScreenshotUtil.class */
public class ScreenshotUtil {
    public static FloatVector2 maxSize(FloatVector2 dimension, int maxWidth, int maxHeight) {
        int width = (int) dimension.getX();
        int height = (int) dimension.getY();
        if (width <= maxWidth && height <= maxHeight) {
            return dimension;
        }
        double scale = Math.min(((double) maxWidth) / ((double) width), ((double) maxHeight) / ((double) height));
        int newWidth = (int) (((double) width) * scale);
        int newHeight = (int) (((double) height) * scale);
        return new FloatVector2(newWidth, newHeight);
    }

    public static GameImage maxSize(GameImage image, int maxWidth, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width <= maxWidth && height <= maxHeight) {
            return image;
        }
        double scale = Math.min(((double) maxWidth) / ((double) width), ((double) maxHeight) / ((double) height));
        int newWidth = (int) (((double) width) * scale);
        int newHeight = (int) (((double) height) * scale);
        return image.resize(0, 0, newWidth, newHeight);
    }

    public static BufferedImage maxSize(BufferedImage image, int maxWidth, int maxHeight) {
        GameImage resizedImage = maxSize(GameImage.IMAGE_PROVIDER.getImage(image), maxWidth, maxHeight);
        return resizedImage.getImage();
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, image.getType());
        resized.createGraphics().drawImage(image, 0, 0, width, height, (ImageObserver) null);
        return resized;
    }

    public static GameImage resize(GameImage image, int width, int height) {
        return image.resize(0, 0, width, height);
    }
}
