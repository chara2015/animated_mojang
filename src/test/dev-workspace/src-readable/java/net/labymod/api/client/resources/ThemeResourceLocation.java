package net.labymod.api.client.resources;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.Laby;
import net.labymod.api.metadata.Metadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/ThemeResourceLocation.class */
public interface ThemeResourceLocation extends ResourceLocation {
    public static final ResourceLocationFactory FACTORY = Laby.references().resourceLocationFactory();

    ResourceLocation resource();

    @Override // net.labymod.api.client.resources.ResourceLocation
    default <T> T getMinecraftLocation() {
        return (T) resource().getMinecraftLocation();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    default String getNamespace() {
        return resource().getNamespace();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    default String getPath() {
        return resource().getPath();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    default InputStream openStream() throws IOException {
        return resource().openStream();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    default boolean exists() {
        return resource().exists();
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    default void metadata(Metadata metadata) {
        resource().metadata(metadata);
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    default Metadata metadata() {
        return resource().metadata();
    }
}
