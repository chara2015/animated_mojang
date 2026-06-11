package net.labymod.core.client.gfx.pipeline.texture.atlas;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/DefaultAnimatedTextureSprite.class */
public class DefaultAnimatedTextureSprite implements AnimatedTextureSprite {
    private final Int2ObjectMap<TextureSprite> sprites;
    private final int frames;

    public DefaultAnimatedTextureSprite(Int2ObjectMap<TextureSprite> sprites) {
        this.sprites = sprites;
        this.frames = sprites.size();
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite
    public TextureUV uv(int frame) {
        TextureSprite sprite = (TextureSprite) this.sprites.get(frame);
        if (sprite == null) {
            return null;
        }
        return sprite.uv();
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite
    public int getFrames() {
        return this.frames;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite
    public SpriteScaling scaling() {
        return null;
    }
}
