package net.labymod.v1_12_2.client.resources.texture;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
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
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/texture/VersionedTextureRepository.class */
@Singleton
@Implements(TextureRepository.class)
public class VersionedTextureRepository extends AbstractTextureRepository implements TextureRepository {
    private cdr textureManager;

    @Inject
    public VersionedTextureRepository(LabyAPI labyAPI, GameImageProvider gameImageProvider) {
        super(labyAPI, gameImageProvider);
    }

    @Override // net.labymod.core.client.resources.texture.AbstractTextureRepository, net.labymod.api.client.resources.texture.TextureRepository
    public boolean hasResource(ResourceLocation location) {
        ResourceLocation location2 = unwrap(location);
        return getTextureManager().hasResource((ResourceLocation) location2.getMinecraftLocation()) || super.hasResource(location2);
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void register(ResourceLocation location, Texture texture, TextureRepository.TextureRegistrationCallback callback) {
        cds minecraftTexture = MinecraftUtil.toMinecraft(texture);
        if (ThreadSafe.isRenderThread()) {
            registerTexture(location, minecraftTexture, callback);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                registerTexture(location, minecraftTexture, callback);
            });
        }
    }

    @Override // net.labymod.core.client.resources.texture.AbstractTextureRepository
    public void onDevTick() {
        super.onDevTick();
        for (ResourceLocation resourceLocation : this.labyAPI.renderPipeline().resources().resourceLocationFactory().getCachedResourceLocations().values()) {
            if (resourceLocation instanceof PathResourceLocation) {
                PathResourceLocation path = (PathResourceLocation) resourceLocation;
                GameTextureManager textureManager = getTextureManager();
                nf minecraftLocation = (nf) resourceLocation.getMinecraftLocation();
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
    public void preloadTexture(ResourceLocation location) {
        Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft.isOnRenderThread()) {
            registerTexture(location, new cdm((nf) location.getMinecraftLocation()));
        } else {
            minecraft.executeOnRenderThread(() -> {
                registerTexture(location, new cdm((nf) location.getMinecraftLocation()));
            });
        }
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void releaseTexture(ResourceLocation location, Runnable releaseHandler) {
        if (bib.z().aF()) {
            release(location, releaseHandler);
        } else {
            bib.z().a(() -> {
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
        VersionedLabyTexture versionedLabyTextureB = getTextureManager().b((nf) location.getMinecraftLocation());
        if (versionedLabyTextureB instanceof VersionedLabyTexture) {
            VersionedLabyTexture labyTexture = versionedLabyTextureB;
            return labyTexture.getDelegate();
        }
        if (!(versionedLabyTextureB instanceof Texture)) {
            return createTexture(versionedLabyTextureB);
        }
        return versionedLabyTextureB;
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public Texture getOrCreateTexture(ResourceLocation location) {
        if (!location.exists()) {
            return null;
        }
        unqueueTexture(location);
        cds texture = getTextureManager().b((nf) location.getMinecraftLocation());
        if (texture == null) {
            texture = new cdm((nf) location.getMinecraftLocation());
            getTextureManager().a((nf) location.getMinecraftLocation(), texture);
        }
        if (texture instanceof VersionedLabyTexture) {
            VersionedLabyTexture labyTexture = (VersionedLabyTexture) texture;
            return labyTexture.getDelegate();
        }
        if (!(texture instanceof Texture)) {
            return createTexture(texture);
        }
        return (Texture) texture;
    }

    @Override // net.labymod.api.client.resources.texture.TextureRepository
    public void registerAndRelease(ResourceLocation location, Texture texture, TextureRepository.TextureRegistrationCallback callback) {
        if (bib.z().aF()) {
            _registerAndRelease(location, texture, callback);
        } else {
            bib.z().a(() -> {
                _registerAndRelease(location, texture, callback);
            });
        }
    }

    private void release(ResourceLocation location, Runnable releaseHandler) {
        getTextureManager().c((nf) location.getMinecraftLocation());
        releaseRemoteTexture(location);
        ReleaseTextureEventCaller.call(location);
        if (releaseHandler != null) {
            releaseHandler.run();
        }
    }

    private void registerTexture(@NotNull ResourceLocation location, cds texture) {
        getTextureManager().a((nf) location.getMinecraftLocation(), texture);
    }

    private void registerTexture(@NotNull ResourceLocation location, cds texture, TextureRepository.TextureRegistrationCallback callback) {
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

    private cdr getTextureManager() {
        if (this.textureManager == null) {
            this.textureManager = bib.z().N();
        }
        return this.textureManager;
    }

    private Texture createTexture(cds texture) {
        return new VersionedTextureResourceWrapper(texture);
    }
}
