package net.labymod.api.client.resources.texture;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/TextureImage.class */
public class TextureImage {
    private static GameImageProvider gameImageProvider;
    private final GameImage gameImage;
    private final String type;

    public TextureImage(ResourceLocation location) throws IOException {
        this(gameImageProvider().getImage(location.openStream()));
    }

    public TextureImage(GameImage gameImage) {
        this(gameImage, "png");
    }

    public TextureImage(InputStream inputStream, String type) throws IOException {
        this(gameImageProvider().getImage(inputStream), type);
    }

    public TextureImage(GameImage gameImage, String type) {
        this.gameImage = gameImage;
        this.type = type;
    }

    public GameImage getGameImage() {
        return this.gameImage;
    }

    public String getFormat() {
        return this.type;
    }

    public void close() {
        if (this.gameImage != null) {
            this.gameImage.close();
        }
    }

    public static GameImageProvider gameImageProvider() {
        if (gameImageProvider == null) {
            gameImageProvider = Laby.references().gameImageProvider();
        }
        return gameImageProvider;
    }
}
