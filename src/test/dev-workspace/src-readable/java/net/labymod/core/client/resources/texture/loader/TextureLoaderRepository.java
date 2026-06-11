package net.labymod.core.client.resources.texture.loader;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/TextureLoaderRepository.class */
public final class TextureLoaderRepository {
    private static final Logging LOGGER = Logging.create((Class<?>) TextureLoaderRepository.class);
    private final List<LoaderEntry> textureLoaders = new ArrayList();
    private final TextureRepository textureRepository;

    public TextureLoaderRepository(TextureRepository textureRepository) {
        this.textureRepository = textureRepository;
        registerDefaults();
    }

    private void registerDefaults() {
        registerInternalTextureLoader(0, new DataTextureLoader());
        registerInternalTextureLoader(0, new FaceTextureLoader());
        registerInternalTextureLoader(0, new McServerTextureLoader(this.textureRepository));
        registerInternalTextureLoader(0, new ResourceTextureLoader());
        registerInternalTextureLoader(0, new ProxiedHttpTextureLoader(this.textureRepository));
        registerInternalTextureLoader(ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_DURATION, new HttpTextureLoader());
        sortTextureLoaders();
    }

    public void registerTextureLoader(int priority, TextureLoader loader) {
        registerTextureLoader(priority, loader, true);
    }

    public boolean executeTextureLoader(String url, @Nullable ResourceLocation location, CompletableTextureImage target) {
        URI uri;
        try {
            uri = URI.create(url.replace("\n", ""));
        } catch (Throwable exception) {
            LOGGER.error(exception.getMessage(), new Object[0]);
        }
        for (LoaderEntry entry : this.textureLoaders) {
            TextureLoader textureLoader = entry.getTextureLoader();
            if (textureLoader.canLoad(uri)) {
                textureLoader.loadTexture(uri, location, target);
                return true;
            }
            target.stopLoadingOnError();
            return false;
        }
        target.stopLoadingOnError();
        return false;
    }

    private void registerInternalTextureLoader(int priority, TextureLoader loader) {
        registerTextureLoader(priority, loader, false);
    }

    private void registerTextureLoader(int priority, TextureLoader loader, boolean sort) {
        this.textureLoaders.add(new LoaderEntry(loader, priority));
        if (sort) {
            sortTextureLoaders();
        }
    }

    private void sortTextureLoaders() {
        this.textureLoaders.sort(Comparator.comparingInt((v0) -> {
            return v0.getPriority();
        }));
    }
}
