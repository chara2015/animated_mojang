package net.labymod.v1_20_1.mixins.client.renderer.renderer.atlas;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/renderer/atlas/MixinTextureAtlasSprite.class */
@Mixin({fuv.class})
public class MixinTextureAtlasSprite implements TextureSprite {

    @Shadow
    @Final
    private float e;

    @Shadow
    @Final
    private float g;

    @Shadow
    @Final
    private float f;

    @Shadow
    @Final
    private float h;

    @Unique
    private TextureUV labyMod$textureUV;

    @Unique
    private SpriteScaling labyMod$spriteScaling;

    @Inject(method = {"<init>*"}, at = {@At("TAIL")})
    private void labyMod$initTextureUV(CallbackInfo ci) {
        this.labyMod$textureUV = new DefaultTextureUV(this.e, this.g, this.f, this.h);
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
