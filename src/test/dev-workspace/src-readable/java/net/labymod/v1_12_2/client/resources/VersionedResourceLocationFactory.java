package net.labymod.v1_12_2.client.resources;

import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.AbstractResourceLocationFactory;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/VersionedResourceLocationFactory.class */
@Singleton
@Implements(ResourceLocationFactory.class)
public class VersionedResourceLocationFactory extends AbstractResourceLocationFactory implements ResourceLocationFactory {
    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public ResourceLocation create(@NotNull String namespace, @NotNull String path) {
        return apply(namespace, path, this::createLocation);
    }

    private ResourceLocation createLocation(@NotNull String namespace, @NotNull String path) {
        return new nf(namespace, path);
    }
}
