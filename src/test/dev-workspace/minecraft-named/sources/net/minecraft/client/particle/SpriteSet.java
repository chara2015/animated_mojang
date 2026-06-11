package net.minecraft.client.particle;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/particle/SpriteSet.class */
public interface SpriteSet {
    TextureAtlasSprite get(int i, int i2);

    TextureAtlasSprite get(RandomSource randomSource);

    TextureAtlasSprite first();
}
