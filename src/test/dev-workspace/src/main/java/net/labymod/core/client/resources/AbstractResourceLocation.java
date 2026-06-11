package net.labymod.core.client.resources;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.metadata.Metadata;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/AbstractResourceLocation.class */
public abstract class AbstractResourceLocation implements ResourceLocation {
    private final ResourceLocation delegate;

    public AbstractResourceLocation(String namespace, String path) {
        this.delegate = ResourceLocation.create(namespace, path);
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public String getNamespace() {
        return getDelegate().getNamespace();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public String getPath() {
        return getDelegate().getPath();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public <T> T getMinecraftLocation() {
        return (T) getDelegate().getMinecraftLocation();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public InputStream openStream() throws IOException {
        return getDelegate().openStream();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public boolean exists() {
        return getDelegate().exists();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    @ApiStatus.Internal
    @ApiStatus.Experimental
    public CompletableResourceLocation toCompletableResourceLocation() {
        return getDelegate().toCompletableResourceLocation();
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        getDelegate().metadata(metadata);
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return getDelegate().metadata();
    }

    public ResourceLocation getDelegate() {
        return this.delegate;
    }
}
