package net.labymod.api.client.resources.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/GameImageProvider.class */
@Referenceable
public interface GameImageProvider {
    GameImage getImage(InputStream inputStream) throws IOException;

    GameImage createImage(int i, int i2);

    GameImage getImage(BufferedImage bufferedImage);

    GameImage getImage(BufferedImage bufferedImage, BufferedImageProcessor bufferedImageProcessor);

    default GameImage getImage(ResourceLocation location) throws IOException {
        return getImage(location.openStream());
    }
}
