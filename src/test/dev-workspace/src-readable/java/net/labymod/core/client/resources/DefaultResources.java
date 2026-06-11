package net.labymod.core.client.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/DefaultResources.class */
@Singleton
@Implements(Resources.class)
public class DefaultResources implements Resources {
    private final ResourceLocationFactory resourceLocationFactory;
    private final TextureRepository textureRepository;

    @Inject
    public DefaultResources(ResourceLocationFactory resourceLocationFactory, TextureRepository textureRepository) {
        this.resourceLocationFactory = resourceLocationFactory;
        this.textureRepository = textureRepository;
    }

    @Override // net.labymod.api.client.resources.Resources
    public ResourceLocationFactory resourceLocationFactory() {
        return this.resourceLocationFactory;
    }

    @Override // net.labymod.api.client.resources.Resources
    public TextureRepository textureRepository() {
        return this.textureRepository;
    }
}
