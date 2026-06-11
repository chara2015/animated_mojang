package net.labymod.api.client.resources.texture;

import java.awt.image.BufferedImage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/BufferedImageProcessor.class */
@FunctionalInterface
public interface BufferedImageProcessor {
    int getPixelColor(BufferedImage bufferedImage, int i, int i2);
}
