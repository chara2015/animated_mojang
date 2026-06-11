package net.labymod.core.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/DefaultTextureSprite.class */
public class DefaultTextureSprite implements TextureSprite {
    private final TextureUV uv;
    protected final float width;
    protected final float height;
    protected final SpriteScaling scaling;

    public DefaultTextureSprite(float minU, float minV, float maxU, float maxV, SpriteScaling.Factory spriteScalingFactory) {
        this(minU, minV, maxU, maxV, (maxU - minU) * 256.0f, (maxV - minV) * 256.0f, spriteScalingFactory);
    }

    public DefaultTextureSprite(float minU, float minV, float maxU, float maxV, float width, float height, SpriteScaling.Factory spriteScalingFactory) {
        this(new DefaultTextureUV(minU, minV, maxU, maxV), width, height, spriteScalingFactory);
    }

    public DefaultTextureSprite(TextureUV uv, float width, float height, SpriteScaling.Factory spriteScalingFactory) {
        this.uv = uv;
        this.width = width;
        this.height = height;
        this.scaling = spriteScalingFactory.create(width, height);
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite
    public TextureUV uv() {
        return this.uv;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite
    public SpriteScaling scaling() {
        return this.scaling;
    }
}
