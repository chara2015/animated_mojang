package net.labymod.v1_18_2.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/renderer/atlas/MixinTextureAtlasSprite.class */
@Mixin({fay.class})
public class MixinTextureAtlasSprite implements TextureSprite {

    @Shadow
    @Final
    private float j;

    @Shadow
    @Final
    private float l;

    @Shadow
    @Final
    private float k;

    @Shadow
    @Final
    private float m;

    @Unique
    private TextureUV labyMod$textureUV;

    @Unique
    private SpriteScaling labyMod$spriteScaling;

    @Inject(method = {"<init>*"}, at = {@At("TAIL")})
    private void labyMod$initTextureUV(CallbackInfo ci) {
        this.labyMod$textureUV = new DefaultTextureUV(this.j, this.l, this.k, this.m);
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
