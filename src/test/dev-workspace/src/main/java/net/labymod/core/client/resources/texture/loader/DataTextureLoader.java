package net.labymod.core.client.resources.texture.loader;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/DataTextureLoader.class */
public class DataTextureLoader implements TextureLoader {
    public static GameImage loadGifFirstFrame(@NotNull InputStream inputStream, @NotNull GameImageProvider provider) throws IOException {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
            try {
                Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
                if (!readers.hasNext()) {
                    throw new IOException("No GIF ImageReader available");
                }
                ImageReader reader = readers.next();
                try {
                    reader.setInput(iis, false, false);
                    BufferedImage firstFrame = reader.read(0);
                    GameImage image = provider.getImage(firstFrame);
                    reader.dispose();
                    if (iis != null) {
                        iis.close();
                    }
                    return image;
                } catch (Throwable th) {
                    reader.dispose();
                    throw th;
                }
            } finally {
            }
        } catch (Exception e) {
            throw new IOException("Failed to load GIF first frame", e);
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public boolean canLoad(URI uri) {
        return uri.getScheme().equals("data");
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public void loadTexture(URI uri, @Nullable ResourceLocation location, CompletableTextureImage target) throws IOException {
        String[] base64Parts = uri.getSchemeSpecificPart().split(",", 2);
        if (base64Parts.length >= 2) {
            String imageFormat = base64Parts[0];
            String imageFormat2 = imageFormat.substring("image/".length(), imageFormat.indexOf(59));
            byte[] encodedBase64 = base64Parts[1].replace("\n", "").getBytes(StandardCharsets.UTF_8);
            byte[] data = Base64.getDecoder().decode(encodedBase64);
            if ("gif".equalsIgnoreCase(imageFormat2)) {
                try {
                    InputStream gifStream = new ByteArrayInputStream(data);
                    try {
                        GameImage gameImage = loadGifFirstFrame(gifStream, TextureImage.gameImageProvider());
                        TextureImage textureImage = new TextureImage(gameImage);
                        target.executeCompletableListeners(textureImage);
                        gifStream.close();
                        return;
                    } finally {
                    }
                } catch (Exception e) {
                }
            }
            InputStream inputStream = new ByteArrayInputStream(data);
            try {
                TextureImage textureImage2 = new TextureImage(inputStream, imageFormat2);
                target.executeCompletableListeners(textureImage2);
                inputStream.close();
                return;
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        target.stopLoadingOnError();
    }
}
