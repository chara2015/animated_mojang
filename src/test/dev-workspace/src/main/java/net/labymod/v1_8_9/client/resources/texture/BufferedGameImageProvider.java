package net.labymod.v1_8_9.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Singleton;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.BufferedGameImage;
import net.labymod.core.client.resources.texture.AbstractGameImageProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/resources/texture/BufferedGameImageProvider.class */
@Singleton
@Implements(GameImageProvider.class)
public class BufferedGameImageProvider extends AbstractGameImageProvider {
    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage getImage(InputStream stream) throws IOException {
        BufferedGameImage image = new BufferedGameImage(stream);
        stream.close();
        return image;
    }

    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage createImage(int width, int height) {
        return new BufferedGameImage(new BufferedImage(width, height, 2));
    }

    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage getImage(BufferedImage bufferedImage) {
        return new BufferedGameImage(bufferedImage);
    }

    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage getImage(BufferedImage bufferedImage, BufferedImageProcessor processor) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                bufferedImage.setRGB(x, y, processor.getPixelColor(bufferedImage, x, y));
            }
        }
        return new BufferedGameImage(bufferedImage);
    }

    @Override // net.labymod.core.client.resources.texture.AbstractGameImageProvider
    protected GameImage create(int channels, int width, int height) {
        return new BufferedGameImage(new BufferedImage(width, height, channels == 3 ? 1 : 2));
    }
}
