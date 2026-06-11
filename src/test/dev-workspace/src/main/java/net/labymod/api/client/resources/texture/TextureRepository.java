package net.labymod.api.client.resources.texture;

import java.io.IOException;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.function.ThrowableSupplier;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/TextureRepository.class */
@Referenceable
public interface TextureRepository {
    @Deprecated(forRemoval = true, since = "4.2.66")
    CompletableResourceLocation getOrRegisterTexture(ResourceLocation resourceLocation, ResourceLocation resourceLocation2, String str, GameImageProcessor gameImageProcessor, Consumer<Texture> consumer, Consumer<CompletableResourceLocation> consumer2);

    CompletableResourceLocation getOrRegisterTexture(TextureDetails textureDetails);

    boolean hasResource(ResourceLocation resourceLocation);

    void unloadNamespace(String str);

    void unloadAll();

    CompletableResourceLocation loadCacheResourceAsync(String str, String str2, String str3, ResourceLocation resourceLocation);

    ResourceLocation loadCacheResource(String str, String str2, String str3, Consumer<ResourceLocation> consumer) throws IOException;

    void loadCacheImage(String str, String str2, ResourceLocation resourceLocation, Consumer<GameImage> consumer) throws IOException;

    void loadCacheImage(String str, ThrowableSupplier<TextureImage, IOException> throwableSupplier, Consumer<GameImage> consumer) throws IOException;

    void purgeMemoryCache();

    void purgeStorageCache() throws IOException;

    void invalidateRemoteTextures(Predicate<String> predicate);

    void invalidateRemoteTextures(BiPredicate<String, CompletableResourceLocation> biPredicate);

    void register(ResourceLocation resourceLocation, Texture texture, TextureRegistrationCallback textureRegistrationCallback);

    void preloadTexture(ResourceLocation resourceLocation);

    @ApiStatus.Experimental
    void registerAndRelease(ResourceLocation resourceLocation, Texture texture, TextureRegistrationCallback textureRegistrationCallback);

    void releaseTexture(ResourceLocation resourceLocation, Runnable runnable);

    void queueTextureRelease(ResourceLocation resourceLocation);

    boolean executeTextureLoader(String str, ResourceLocation resourceLocation, CompletableTextureImage completableTextureImage);

    void registerTextureLoader(int i, TextureLoader textureLoader);

    Texture getTexture(ResourceLocation resourceLocation);

    Texture getOrCreateTexture(ResourceLocation resourceLocation);

    @Deprecated(forRemoval = true, since = "4.2.66")
    default CompletableResourceLocation getOrRegisterTexture(ResourceLocation location, ResourceLocation fallbackLocation, String url, GameImageProcessor imageModifier, Consumer<Texture> finishHandler) {
        return getOrRegisterTexture(location, fallbackLocation, url, imageModifier, finishHandler, null);
    }

    default ResourceLocation loadCacheResource(String namespace, String hash, String url) throws IOException {
        return loadCacheResource(namespace, hash, url, loadedLocation -> {
        });
    }

    default void register(ResourceLocation location, Texture texture) {
        register(location, texture, null);
    }

    @ApiStatus.Experimental
    default void registerAndRelease(ResourceLocation location, Texture texture) {
        registerAndRelease(location, texture, null);
    }

    default void releaseTexture(ResourceLocation location) {
        releaseTexture(location, null);
    }

    default boolean executeTextureLoader(String url, CompletableTextureImage target) {
        return executeTextureLoader(url, null, target);
    }

    default GameImage getImageFromTexture(ResourceLocation location) {
        return getImageFromTexture(getTexture(location));
    }

    default GameImage getImageFromTexture(Texture texture) {
        if (texture instanceof SimpleTexture) {
            SimpleTexture simpleTexture = (SimpleTexture) texture;
            return simpleTexture.getImage();
        }
        if (texture instanceof TextureResourceWrapper) {
            TextureResourceWrapper<?> textureResourceWrapper = (TextureResourceWrapper) texture;
            return textureResourceWrapper.getImage();
        }
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/TextureRepository$TextureRegistrationCallback.class */
    @FunctionalInterface
    public interface TextureRegistrationCallback {
        public static final TextureRegistrationCallback EMPTY = () -> {
        };

        void onAfterTextureRegistration();

        default void onBeforeTextureRegistration() {
        }
    }
}
