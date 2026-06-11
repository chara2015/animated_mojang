package net.labymod.api.client.gfx.pipeline.texture.atlas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/atlas/AnimatedTextureSprite.class */
public interface AnimatedTextureSprite extends TextureSprite {
    TextureUV uv(int i);

    int getFrames();

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite
    default TextureUV uv() {
        return uv(0);
    }
}
