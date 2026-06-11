package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.TextureSlots;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/UnbakedGeometry.class */
@FunctionalInterface
public interface UnbakedGeometry {
    public static final UnbakedGeometry EMPTY = ($$0, $$1, $$2, $$3) -> {
        return QuadCollection.EMPTY;
    };

    QuadCollection bake(TextureSlots textureSlots, ModelBaker modelBaker, ModelState modelState, ModelDebugName modelDebugName);
}
