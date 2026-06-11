package net.labymod.api.client.resources.transform;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/transform/ResourceTransformerRegistry.class */
@Referenceable
public interface ResourceTransformerRegistry {
    void register(ResourceLocation resourceLocation, ResourceTransformer resourceTransformer);

    default void register(String namespace, String path, ResourceTransformer transformer) {
        register(ResourceLocation.create(namespace, path), transformer);
    }
}
