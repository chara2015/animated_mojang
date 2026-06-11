package net.labymod.v1_21_1.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Singleton;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.texture.AbstractGameImageProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/resources/texture/NativeGameImageProvider.class */
@Singleton
@Implements(GameImageProvider.class)
public class NativeGameImageProvider extends AbstractGameImageProvider {
    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage getImage(InputStream stream) throws IOException {
        return new NativeGameImage(faj.a(stream));
    }

    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage createImage(int width, int height) {
        return new NativeGameImage(new faj(width, height, true));
    }

    @Override // net.labymod.core.client.resources.texture.AbstractGameImageProvider
    protected GameImage create(int channels, int width, int height) {
        return new NativeGameImage(new faj(channels == 3 ? a.b : a.a, width, height, false));
    }

    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage getImage(BufferedImage bufferedImage) {
        return new NativeGameImage(NativeImageUtils.asNativeImage(bufferedImage));
    }

    @Override // net.labymod.api.client.resources.texture.GameImageProvider
    public GameImage getImage(BufferedImage bufferedImage, BufferedImageProcessor processor) {
        return new NativeGameImage(NativeImageUtils.asNativeImage(bufferedImage, processor));
    }
}
