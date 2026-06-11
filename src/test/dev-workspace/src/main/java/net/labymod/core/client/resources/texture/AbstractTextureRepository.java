package net.labymod.core.client.resources.texture;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.ThemeResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProcessor;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.resources.AbstractResourceLocation;
import net.labymod.core.client.resources.texture.cache.ImageMemoryCache;
import net.labymod.core.client.resources.texture.loader.TextureLoaderRepository;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/AbstractTextureRepository.class */
public abstract class AbstractTextureRepository implements TextureRepository {
    protected static final Logging LOGGER = Logging.create((Class<?>) AbstractTextureRepository.class);
    private static final long TEXTURE_RELEASE_QUEUE_TIME = TimeUnit.SECONDS.toMillis(60);
    protected final LabyAPI labyAPI;
    protected final GameImageProvider gameImageProvider;
    private final TextureLoaderRepository textureLoaderRepository;
    protected TextureImage fallbackTextureImage;
    private final Map<ResourceLocation, TextureImage> fallbackTextures = new HashMap();
    private final Object2LongMap<ResourceLocation> queuedTextureReleases = new Object2LongOpenHashMap();
    private final Map<String, CompletableResourceLocation> completableMap = new HashMap();
    private final Map<ResourceLocation, RemoteTexture> remoteTextures = new ConcurrentHashMap();
    private final ImageMemoryCache imageMemoryCache = new ImageMemoryCache(new ConcurrentHashMap());

    protected AbstractTextureRepository(LabyAPI labyAPI, GameImageProvider gameImageProvider) {
        this.labyAPI = labyAPI;
        this.gameImageProvider = gameImageProvider;
        labyAPI.eventBus().registerListener(this);
        this.textureLoaderRepository = new TextureLoaderRepository(this);
    }

    @Subscribe
    public void loadFallbackTextureImage(ResourceReloadEvent event) {
        if (this.fallbackTextureImage == null && event.type() == ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS && event.phase() == Phase.PRE) {
            loadFallbackTextureImage();
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public CompletableResourceLocation getOrRegisterTexture(ResourceLocation location, ResourceLocation fallbackLocation, String url, GameImageProcessor imageModifier, Consumer<Texture> finishHandler, Consumer<CompletableResourceLocation> completableResourceLocationHandler) {
        return getOrRegisterTexture(TextureDetails.builder(location).withFallbackLocation(fallbackLocation).withUrl(url).withImageProcessor(imageModifier).withFinishHandler(finishHandler).withLocationHandler(completableResourceLocationHandler).build());
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public CompletableResourceLocation getOrRegisterTexture(TextureDetails details) {
        ResourceLocation location = details.location();
        RemoteTexture cached = details.registerStrategy() == TextureDetails.RegisterStrategy.REGISTER ? this.remoteTextures.get(location) : null;
        if (cached != null) {
            return cached.location();
        }
        CompletableResourceLocation completableLocation = new CompletableResourceLocation(details.fallbackLocation());
        details.acceptLocation(completableLocation);
        RemoteTexture texture = new RemoteTexture(details.url(), completableLocation);
        putRemoteTexture(location, texture);
        if (ThreadSafe.isRenderThread()) {
            getOrRegisterTexture(details, texture);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                getOrRegisterTexture(details, texture);
            });
        }
        return completableLocation;
    }

    private void getOrRegisterTexture(TextureDetails details, RemoteTexture remoteTexture) {
        CompletableTextureImage completableImage = new CompletableTextureImage(createFallbackTextureImage(details.fallbackLocation()));
        completableImage.addCompletableListener(() -> {
            if (completableImage.hasError()) {
                remoteTexture.location().stopLoadingOnError();
            }
            if (completableImage.isLoading()) {
                return;
            }
            TextureImage resolvedTextureImage = completableImage.getCompleted();
            if (resolvedTextureImage == null) {
                LOGGER.warn("Resolved texture image is null for {}", details.location());
                remoteTexture.location().stopLoadingOnError();
                return;
            }
            GameImage gameImage = details.processImage(resolvedTextureImage.getGameImage());
            ResourceLocation location = details.location();
            SimpleTexture texture = SimpleTexture.simple(location, gameImage);
            Metadata metadata = location.metadata();
            metadata.set("width", Integer.valueOf(gameImage.getWidth()));
            metadata.set("height", Integer.valueOf(gameImage.getHeight()));
            TextureDetails.RegisterStrategy strategy = details.registerStrategy();
            SimpleTextureRegistrationCallback callback = new SimpleTextureRegistrationCallback(texture, details, remoteTexture, this::putRemoteTexture);
            if (strategy == TextureDetails.RegisterStrategy.REGISTER) {
                register(location, texture, callback);
            } else {
                registerAndRelease(location, texture, callback);
            }
        });
        if (details.hasImageData()) {
            LabyExecutors.executeBackgroundTask(() -> {
                try {
                    TextureImage textureImage = new TextureImage(new ByteArrayInputStream(details.imageData()), "png");
                    completableImage.executeCompletableListeners(textureImage);
                } catch (IOException exception) {
                    LOGGER.warn("Failed to load image data for {}", details.location(), exception);
                    completableImage.stopLoadingOnError();
                }
            });
        } else {
            download(remoteTexture.url(), details.location(), completableImage, true);
        }
        remoteTexture.setDownloadStarted(true);
    }

    private void putRemoteTexture(ResourceLocation location, RemoteTexture texture) {
        this.remoteTextures.put(location, texture);
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public boolean hasResource(ResourceLocation location) {
        ThreadSafe.ensureRenderThread();
        boolean registeredAsRemoteTexture = this.remoteTextures.containsKey(location);
        if (registeredAsRemoteTexture) {
            return true;
        }
        for (CompletableResourceLocation crl : this.completableMap.values()) {
            if (!crl.isLoading() && crl.getCompleted().equals(location)) {
                return true;
            }
        }
        return false;
    }

    protected ResourceLocation unwrap(ResourceLocation location) {
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), ThemeResourceLocation.class, AbstractResourceLocation.class, AnimatedResourceLocation.class).dynamicInvoker().invoke(location, 0) /* invoke-custom */) {
            case -1:
            default:
                return location;
            case 0:
                ThemeResourceLocation themeResourceLocation = (ThemeResourceLocation) location;
                return unwrap(themeResourceLocation.resource());
            case 1:
                AbstractResourceLocation abstractResourceLocation = (AbstractResourceLocation) location;
                return unwrap(abstractResourceLocation.getDelegate());
            case 2:
                AnimatedResourceLocation animatedResourceLocation = (AnimatedResourceLocation) location;
                return unwrap(animatedResourceLocation.getCurrentResourceLocation());
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void unloadNamespace(String namespace) {
        ThreadSafe.ensureRenderThread();
        List<ResourceLocation> toRemove = new ArrayList<>();
        for (ResourceLocation location : this.remoteTextures.keySet()) {
            if (location.getNamespace().equals(namespace)) {
                toRemove.add(location);
            }
        }
        Map<ResourceLocation, RemoteTexture> map = new HashMap<>(this.remoteTextures);
        for (ResourceLocation resourceLocation : toRemove) {
            RemoteTexture remoteTexture = map.remove(resourceLocation);
            if (remoteTexture != null) {
                releaseTexture(resourceLocation);
            }
        }
        this.remoteTextures.putAll(map);
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void unloadAll() {
        ThreadSafe.ensureRenderThread();
        this.remoteTextures.keySet().forEach(this::releaseTexture);
        this.remoteTextures.clear();
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public CompletableResourceLocation loadCacheResourceAsync(String namespace, String hash, String url, ResourceLocation loadingResource) {
        CompletableResourceLocation completableLocation = new CompletableResourceLocation(loadingResource);
        if (ThreadSafe.isRenderThread()) {
            loadCacheResourcesAsync(namespace, hash, url, completableLocation, loadingResource);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                loadCacheResourcesAsync(namespace, hash, url, completableLocation, loadingResource);
            });
        }
        return completableLocation;
    }

    private void loadCacheResourcesAsync(String namespace, String hash, String url, CompletableResourceLocation location, ResourceLocation loadingResource) {
        CollectionHelper.removeIf(this.completableMap.entrySet(), entry -> {
            return ((CompletableResourceLocation) entry.getValue()).getCompleted() == null;
        });
        CompletableResourceLocation completableLocation = this.completableMap.get(hash);
        if (completableLocation == null) {
            completableLocation = new CompletableResourceLocation(loadingResource);
            this.completableMap.put(hash, completableLocation);
            LabyExecutors.executeBackgroundTask(() -> {
                try {
                    loadCacheResource(namespace, hash, url, completed -> {
                        completableLocation.executeCompletableListeners(completed);
                        location.executeCompletableListeners(completableLocation.getCompleted());
                    });
                } catch (IOException exception) {
                    LOGGER.error("Failed to load resource: " + url, exception);
                    completableLocation.stopLoading();
                }
            });
        }
        location.executeCompletableListeners(completableLocation.getCompleted());
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public ResourceLocation loadCacheResource(String namespace, String hash, String url, Consumer<ResourceLocation> finishHandler) throws IOException {
        Resources resources = this.labyAPI.renderPipeline().resources();
        ResourceLocation location = resources.resourceLocationFactory().create(namespace, "cached/" + hash);
        loadCacheImage(hash, url, location, image -> {
            SimpleTexture texture = SimpleTexture.simple(location, image);
            Metadata metadata = location.metadata();
            metadata.set("width", Integer.valueOf(image.getWidth()));
            metadata.set("height", Integer.valueOf(image.getHeight()));
            register(location, texture, new SimpleCachedTextureRegistrationCallback(location, texture, finishHandler));
        });
        return location;
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void loadCacheImage(String hash, String url, ResourceLocation location, Consumer<GameImage> gameImageConsumer) throws IOException {
        loadCacheImage(hash, () -> {
            return downloadGameImage(url, location);
        }, gameImageConsumer);
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void loadCacheImage(String hash, ThrowableSupplier<TextureImage, IOException> imageSupplier, Consumer<GameImage> gameImageConsumer) throws IOException {
        this.imageMemoryCache.loadCacheImage(hash, imageSupplier, gameImageConsumer);
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void purgeMemoryCache() {
        ThreadSafe.ensureRenderThread();
        this.imageMemoryCache.release();
        this.completableMap.clear();
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void purgeStorageCache() throws IOException {
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void queueTextureRelease(ResourceLocation location) {
        ThreadSafe.executeOnRenderThread(() -> {
            this.queuedTextureReleases.put(location, TimeUtil.getMillis() + TEXTURE_RELEASE_QUEUE_TIME);
        });
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void invalidateRemoteTextures(Predicate<String> urlTester) {
        invalidateRemoteTextures((url, location) -> {
            return urlTester.test(url);
        });
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void invalidateRemoteTextures(BiPredicate<String, CompletableResourceLocation> urlTester) {
        ThreadSafe.ensureRenderThread();
        CollectionHelper.removeIf(this.remoteTextures.values(), texture -> {
            String url = texture.url();
            if (url != null && urlTester.test(url, texture.location())) {
                if (!texture.location().isLoading()) {
                    releaseTexture(texture.location().getCompleted());
                    return true;
                }
                return true;
            }
            return false;
        });
    }

    private TextureImage downloadGameImage(String url, ResourceLocation location) {
        CompletableTextureImage completable = new CompletableTextureImage(useFallbackTextureImage());
        download(url, location, completable, false);
        return completable.getCompleted();
    }

    protected void download(String url, ResourceLocation location, CompletableTextureImage completableTextureImage, boolean asynchronous) {
        if (asynchronous) {
            LabyExecutors.executeBackgroundTask(() -> {
                executeTextureLoader(url, location, completableTextureImage);
            });
        } else {
            executeTextureLoader(url, location, completableTextureImage);
        }
    }

    protected TextureImage useFallbackTextureImage() {
        return this.fallbackTextureImage;
    }

    private void loadFallbackTextureImage() {
        ResourceLocation emptyLocation = Textures.EMPTY;
        try {
            this.fallbackTextureImage = new TextureImage(emptyLocation);
            this.fallbackTextures.put(emptyLocation, this.fallbackTextureImage);
        } catch (IOException e) {
            ClassLoader loader = PlatformEnvironment.getPlatformClassloader().getPlatformClassloader();
            try {
                this.fallbackTextureImage = new TextureImage(loader.getResourceAsStream(String.format(Locale.ROOT, "assets/%s/%s", emptyLocation.getNamespace(), emptyLocation.getPath())), "png");
                this.fallbackTextures.put(emptyLocation, this.fallbackTextureImage);
            } catch (IOException e2) {
                this.fallbackTextureImage = null;
            }
        }
    }

    protected TextureImage createFallbackTextureImage(ResourceLocation location) {
        try {
            if (location == null) {
                return useFallbackTextureImage();
            }
            TextureImage textureImage = this.fallbackTextures.get(location);
            if (textureImage == null) {
                textureImage = new TextureImage(location);
            } else {
                GameImage gameImage = textureImage.getGameImage();
                if (gameImage == null || gameImage.isFreed()) {
                    textureImage = new TextureImage(location);
                    this.fallbackTextures.put(location, textureImage);
                }
            }
            this.fallbackTextures.put(location, textureImage);
            return textureImage;
        } catch (IOException e) {
            return useFallbackTextureImage();
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public boolean executeTextureLoader(String url, @Nullable ResourceLocation location, CompletableTextureImage target) {
        return this.textureLoaderRepository.executeTextureLoader(url, location, target);
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void registerTextureLoader(int priority, TextureLoader loader) {
        this.textureLoaderRepository.registerTextureLoader(priority, loader);
    }

    public void releaseRemoteTexture(ResourceLocation location) {
        this.remoteTextures.remove(location);
        this.fallbackTextures.remove(location);
    }

    public void onShutdown() {
        for (TextureImage textureImage : this.fallbackTextures.values()) {
            if (textureImage != null) {
                textureImage.close();
            }
        }
        this.fallbackTextures.clear();
    }

    public void onTick() {
        ThreadSafe.ensureRenderThread();
        if (this.queuedTextureReleases.isEmpty()) {
            return;
        }
        CollectionHelper.removeIf(this.queuedTextureReleases.object2LongEntrySet(), entry -> {
            if (entry.getLongValue() <= TimeUtil.getMillis()) {
                releaseTexture((ResourceLocation) entry.getKey());
                return true;
            }
            return false;
        });
    }

    public void onDevTick() {
        ThreadSafe.ensureRenderThread();
    }

    public void unqueueTexture(ResourceLocation location) {
        ThreadSafe.ensureRenderThread();
        if (this.queuedTextureReleases.isEmpty()) {
            return;
        }
        this.queuedTextureReleases.removeLong(location);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/AbstractTextureRepository$SimpleTextureRegistrationCallback.class */
    private static class SimpleTextureRegistrationCallback implements TextureRepository.TextureRegistrationCallback {
        private final SimpleTexture texture;
        private final TextureDetails details;
        private final RemoteTexture remoteTexture;
        private final BiConsumer<ResourceLocation, RemoteTexture> textureConsumer;

        public SimpleTextureRegistrationCallback(SimpleTexture texture, TextureDetails details, RemoteTexture remoteTexture, BiConsumer<ResourceLocation, RemoteTexture> textureConsumer) {
            this.texture = texture;
            this.details = details;
            this.remoteTexture = remoteTexture;
            this.textureConsumer = textureConsumer;
        }

        @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
        public void onBeforeTextureRegistration() {
            this.texture.upload();
        }

        @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
        public void onAfterTextureRegistration() {
            this.details.finish(this.texture);
            this.remoteTexture.location().executeCompletableListeners(this.details.location());
            this.textureConsumer.accept(this.details.location(), this.remoteTexture);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/AbstractTextureRepository$SimpleCachedTextureRegistrationCallback.class */
    private static class SimpleCachedTextureRegistrationCallback implements TextureRepository.TextureRegistrationCallback {
        private final ResourceLocation location;
        private final SimpleTexture texture;
        private final Consumer<ResourceLocation> finishHandler;

        public SimpleCachedTextureRegistrationCallback(ResourceLocation location, SimpleTexture texture, Consumer<ResourceLocation> finishHandler) {
            this.location = location;
            this.texture = texture;
            this.finishHandler = finishHandler;
        }

        @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
        public void onBeforeTextureRegistration() {
            this.texture.upload();
        }

        @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
        public void onAfterTextureRegistration() {
            this.finishHandler.accept(this.location);
        }
    }
}
