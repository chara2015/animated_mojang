package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/SpriteGetter.class */
public interface SpriteGetter {
    TextureAtlasSprite get(Material material, ModelDebugName modelDebugName);

    TextureAtlasSprite reportMissingReference(String str, ModelDebugName modelDebugName);

    default TextureAtlasSprite resolveSlot(TextureSlots $$0, String $$1, ModelDebugName $$2) {
        Material $$3 = $$0.getMaterial($$1);
        return $$3 != null ? get($$3, $$2) : reportMissingReference($$1, $$2);
    }
}
