package net.labymod.v1_20_1.client.resources.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.client.resources.PathResourceLocation;
import net.labymod.core.client.resources.texture.AbstractTextureRepository;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.core.event.client.resources.ReleaseTextureEventCaller;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/resources/texture/VersionedTextureRepository.class */
@Singleton
@Implements(TextureRepository.class)
public class VersionedTextureRepository extends AbstractTextureRepository implements TextureRepository {
    private fuw textureManager;

    @Inject
    public VersionedTextureRepository(LabyAPI labyAPI, GameImageProvider gameImageProvider) {
        super(labyAPI, gameImageProvider);
    }

    @Override // net.labymod.core.client.resources.texture.AbstractTextureRepository, net.labymod.api.client.resources.texture.TextureRepository
    public boolean hasResource(ResourceLocation location) {
        ResourceLocation location2 = unwrap(location);
        return getTextureManager().hasResource((ResourceLocation) location2.getMinecraftLocation()) || super.hasResource(location2);
    }

    @Override // net.labymod.core.client.resources.texture.AbstractTextureRepository
    public void onDevTick() {
        super.onDevTick();
        for (ResourceLocation resourceLocation : this.labyAPI.renderPipeline().resources().resourceLocationFactory().getCachedResourceLocations().values()) {
            if (resourceLocation instanceof PathResourceLocation) {
                PathResourceLocation path = (PathResourceLocation) resourceLocation;
                GameTextureManager textureManager = getTextureManager();
                acq minecraftLocation = (acq) resourceLocation.getMinecraftLocation();
                boolean hasResource = textureManager.hasResource((ResourceLocation) resourceLocation.getMinecraftLocation());
                if (hasResource && path.isModified()) {
                    textureManager.c(minecraftLocation);
                    textureManager.a(minecraftLocation);
                    LOGGER.info("Texture " + String.valueOf(minecraftLocation) + " has been reloaded", new Object[0]);
                }
            }
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void register(ResourceLocation location, Texture texture, TextureRepository.TextureRegistrationCallback callback) {
        fug minecraftTexture = MinecraftUtil.toMinecraft(texture);
        if (ThreadSafe.isRenderThread()) {
            registerTexture(location, minecraftTexture, callback);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                registerTexture(location, minecraftTexture, callback);
            });
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void preloadTexture(ResourceLocation location) {
        registerTexture(location, new fuo((acq) location.getMinecraftLocation()));
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void releaseTexture(ResourceLocation location, Runnable releaseHandler) {
        if (RenderSystem.isOnRenderThreadOrInit()) {
            release(location, releaseHandler);
        } else {
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                release(location, releaseHandler);
            });
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public Texture getTexture(ResourceLocation location) {
        if (!hasResource(location)) {
            return null;
        }
        unqueueTexture(location);
        Texture textureB = getTextureManager().b((acq) location.getMinecraftLocation());
        if (textureB instanceof VersionedLabyTexture) {
            VersionedLabyTexture labyTexture = (VersionedLabyTexture) textureB;
            return labyTexture.getDelegate();
        }
        if (!(textureB instanceof Texture)) {
            return createTexture(textureB);
        }
        Texture labyTexture2 = textureB;
        return labyTexture2;
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public Texture getOrCreateTexture(ResourceLocation location) {
        if (!location.exists()) {
            return null;
        }
        unqueueTexture(location);
        Texture textureB = getTextureManager().b((acq) location.getMinecraftLocation());
        if (textureB instanceof VersionedLabyTexture) {
            VersionedLabyTexture labyTexture = (VersionedLabyTexture) textureB;
            return labyTexture.getDelegate();
        }
        if (!(textureB instanceof Texture)) {
            return createTexture(textureB);
        }
        Texture labyTexture2 = textureB;
        return labyTexture2;
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void registerAndRelease(ResourceLocation location, Texture texture, TextureRepository.TextureRegistrationCallback callback) {
        if (ThreadSafe.isRenderThread()) {
            _registerAndRelease(location, texture, callback);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                _registerAndRelease(location, texture, callback);
            });
        }
    }

    private void release(ResourceLocation location, Runnable releaseHandler) {
        getTextureManager().c((acq) location.getMinecraftLocation());
        releaseRemoteTexture(location);
        ReleaseTextureEventCaller.call(location);
        if (releaseHandler != null) {
            releaseHandler.run();
        }
    }

    private void registerTexture(@NotNull ResourceLocation location, fug texture) {
        getTextureManager().a((acq) location.getMinecraftLocation(), texture);
    }

    private void registerTexture(@NotNull ResourceLocation location, fug texture, TextureRepository.TextureRegistrationCallback callback) {
        TextureRepository.TextureRegistrationCallback callback2 = (TextureRepository.TextureRegistrationCallback) Objects.requireNonNullElse(callback, TextureRepository.TextureRegistrationCallback.EMPTY);
        callback2.onBeforeTextureRegistration();
        registerTexture(location, texture);
        callback2.onAfterTextureRegistration();
    }

    private void _registerAndRelease(ResourceLocation location, Object texture, TextureRepository.TextureRegistrationCallback callback) {
        TextureRepository.TextureRegistrationCallback callback2 = (TextureRepository.TextureRegistrationCallback) Objects.requireNonNullElse(callback, TextureRepository.TextureRegistrationCallback.EMPTY);
        callback2.onBeforeTextureRegistration();
        getTextureManager().registerAndRelease(location, texture);
        callback2.onAfterTextureRegistration();
    }

    private fuw getTextureManager() {
        if (this.textureManager == null) {
            this.textureManager = enn.N().X();
        }
        return this.textureManager;
    }

    private Texture createTexture(fug texture) {
        return new VersionedTextureResourceWrapper(texture);
    }
}
