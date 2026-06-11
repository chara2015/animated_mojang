package net.labymod.core.client.resources.texture.cache;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Consumer;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.util.function.Consumers;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/cache/ImageMemoryCache.class */
public final class ImageMemoryCache {
    private static final Logging LOGGER = Logging.create((Class<?>) ImageMemoryCache.class);
    private final Map<String, GameImage> cache;
    private final GameImageProvider imageProvider = Laby.references().gameImageProvider();

    public ImageMemoryCache(Map<String, GameImage> cache) {
        this.cache = cache;
    }

    public void loadCacheImage(String hash, ThrowableSupplier<TextureImage, IOException> imageSupplier, Consumer<GameImage> consumer) throws IOException {
        GameImage image = this.cache.get(hash);
        if (image != null) {
            Consumers.accept(consumer, image);
            return;
        }
        Path cachedImage = Constants.Files.CACHE.resolve(hash.substring(0, 2) + "/" + hash);
        GameImage gameImage = readCachedImageFromLocalStorage(hash, cachedImage);
        if (gameImage != null) {
            Consumers.accept(consumer, gameImage);
            return;
        }
        Path directory = cachedImage.getParent();
        if (!IOUtil.exists(directory)) {
            IOUtil.createDirectories(directory);
        }
        TextureImage textureImage = imageSupplier.get();
        writeImageToLocalStorage(hash, consumer, cachedImage, textureImage);
    }

    public void release() {
        this.cache.clear();
    }

    private void writeImageToLocalStorage(String hash, Consumer<GameImage> consumer, Path cachedImage, TextureImage textureImage) throws IOException {
        GameImage gameImage = textureImage.getGameImage();
        gameImage.write(textureImage.getFormat(), cachedImage);
        this.cache.put(hash, gameImage);
        Consumers.accept(consumer, gameImage);
    }

    private GameImage readCachedImageFromLocalStorage(String hash, Path path) throws IOException {
        if (!IOUtil.exists(path)) {
            return null;
        }
        try {
            InputStream stream = IOUtil.newInputStream(path);
            try {
                GameImage image = this.imageProvider.getImage(stream);
                this.cache.put(hash, image);
                if (stream != null) {
                    stream.close();
                }
                return image;
            } finally {
            }
        } catch (Throwable throwable) {
            LOGGER.warn("Failed to load image \"{}\" from cache (probably corrupt), deleting it: {}: {}", path, throwable.getClass().getSimpleName(), throwable.getMessage());
            IOUtil.delete(path);
            return null;
        }
    }
}
