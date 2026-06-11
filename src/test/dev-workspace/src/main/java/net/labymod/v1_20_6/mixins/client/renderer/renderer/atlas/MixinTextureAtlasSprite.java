package net.labymod.v1_20_6.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/renderer/atlas/MixinTextureAtlasSprite.class */
@Mixin({gpb.class})
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

    @Shadow
    @Final
    private gov b;

    @Unique
    private TextureUV labyMod$textureUV;

    @Unique
    private SpriteScaling labyMod$spriteScaling;

    @Inject(method = {"<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/SpriteContents;IIII)V"}, at = {@At("TAIL")})
    private void labyMod$initTextureVanilla(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Inject(method = {"<init>(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"}, at = {@At("TAIL")})
    @Dynamic
    private void labyMod$optifine$initTextureUV0(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Inject(method = {"<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;)V"}, at = {@At("TAIL")})
    @Dynamic
    private void labyMod$optifine$initTextureUV1(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Inject(method = {"<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIILnet/minecraft/client/renderer/texture/TextureAtlas;Lnet/optifine/shaders/ShadersTextureType;)V"}, at = {@At("TAIL")})
    @Dynamic
    private void labyMod$optifine$initTextureUV2(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Unique
    private void labyMod$initialize() {
        if (this.b != null) {
            labyMod$initializeApi();
        }
    }

    @Inject(method = {"init"}, at = {@At("TAIL")}, require = 0, expect = 0)
    @Dynamic
    private void labyMod$init(alf location, gov contents, int atlasWidth, int atlasHeight, int x, int y, CallbackInfo ci) {
        labyMod$initializeApi();
    }

    @Unique
    private void labyMod$initializeApi() {
        this.labyMod$textureUV = new DefaultTextureUV(this.e, this.g, this.f, this.h);
        gqs metadataSection = (gqs) this.b.f().a(gqs.c).orElse(gqs.a);
        c cVarA = metadataSection.a();
        if (cVarA instanceof c) {
            c tile = cVarA;
            this.labyMod$spriteScaling = new TileScaling(tile.b(), tile.c());
        } else {
            if (cVarA instanceof a) {
                a nineSlice = (a) cVarA;
                a border = nineSlice.d();
                this.labyMod$spriteScaling = new NineSpliceScaling(nineSlice.b(), nineSlice.c(), new NineSpliceScaling.Border(border.a(), border.b(), border.c(), border.d()));
                return;
            }
            this.labyMod$spriteScaling = new StretchScaling();
        }
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
