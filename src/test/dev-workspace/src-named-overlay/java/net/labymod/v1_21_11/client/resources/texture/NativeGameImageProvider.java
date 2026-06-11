package net.labymod.v1_21_11.client.resources.texture;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Singleton;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.texture.AbstractGameImageProvider;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/texture/NativeGameImageProvider.class */
@Singleton
@Implements(GameImageProvider.class)
public class NativeGameImageProvider extends AbstractGameImageProvider {
    public GameImage getImage(InputStream stream) throws IOException {
        return new NativeGameImage(NativeImage.read(stream));
    }

    public GameImage createImage(int width, int height) {
        return new NativeGameImage(new NativeImage(width, height, true));
    }

    protected GameImage create(int channels, int width, int height) {
        return new NativeGameImage(new NativeImage(channels == 3 ? NativeImage.Format.RGB : NativeImage.Format.RGBA, width, height, false));
    }

    public GameImage getImage(BufferedImage bufferedImage) {
        return new NativeGameImage(NativeImageUtils.asNativeImage(bufferedImage));
    }

    public GameImage getImage(BufferedImage bufferedImage, BufferedImageProcessor processor) {
        return new NativeGameImage(NativeImageUtils.asNativeImage(bufferedImage, processor));
    }
}
