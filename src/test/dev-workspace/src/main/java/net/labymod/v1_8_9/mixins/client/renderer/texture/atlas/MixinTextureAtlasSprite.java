package net.labymod.v1_8_9.mixins.client.renderer.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/texture/atlas/MixinTextureAtlasSprite.class */
@Mixin({bmi.class})
public class MixinTextureAtlasSprite implements TextureSprite {

    @Shadow
    private float l;

    @Shadow
    private float n;

    @Shadow
    private float m;

    @Shadow
    private float o;

    @Unique
    private TextureUV labyMod$textureUV;

    @Unique
    private SpriteScaling labyMod$spriteScaling;

    @Inject(method = {"initSprite"}, at = {@At("TAIL")})
    private void labyMod$initTextureUV(int i, int j, int originX, int originY, boolean rotated, CallbackInfo ci) {
        this.labyMod$textureUV = new DefaultTextureUV(this.l, this.n, this.m, this.o);
        this.labyMod$spriteScaling = new StretchScaling();
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite
    public TextureUV uv() {
        return this.labyMod$textureUV;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite
    public SpriteScaling scaling() {
        return this.labyMod$spriteScaling;
    }
}
