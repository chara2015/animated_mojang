package net.labymod.v26_2_snapshot_8.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiMetadataSection;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/renderer/atlas/MixinTextureAtlasSprite.class */
@Mixin({TextureAtlasSprite.class})
public class MixinTextureAtlasSprite implements TextureSprite {

    @Shadow
    @Final
    private float u0;

    @Shadow
    @Final
    private float v0;

    @Shadow
    @Final
    private float u1;

    @Shadow
    @Final
    private float v1;

    @Shadow
    @Final
    private SpriteContents contents;

    @Unique
    private TextureUV labyMod$textureUV;

    @Unique
    private SpriteScaling labyMod$spriteScaling;

    @Inject(method = {"<init>(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIII)V"}, at = {@At("TAIL")})
    private void labyMod$initTextureVanilla(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Inject(method = {"<init>(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"}, at = {@At("TAIL")})
    @Dynamic
    private void labyMod$optifine$initTextureUV0(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Inject(method = {"<init>(Lnet/minecraft/resources/Identifier;Lnet/minecraft/resources/Identifier;)V"}, at = {@At("TAIL")})
    @Dynamic
    private void labyMod$optifine$initTextureUV1(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Inject(method = {"<init>(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIILnet/minecraft/client/renderer/texture/TextureAtlas;Lnet/optifine/shaders/ShadersTextureType;)V"}, at = {@At("TAIL")})
    @Dynamic
    private void labyMod$optifine$initTextureUV2(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Inject(method = {"Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;<init>(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIIILnet/minecraft/client/renderer/texture/TextureAtlas;Lnet/optifine/shaders/ShadersTextureType;)V"}, at = {@At("TAIL")})
    @Dynamic
    private void labyMod$optifine$initTextureUV3(CallbackInfo ci) {
        labyMod$initialize();
    }

    @Unique
    private void labyMod$initialize() {
        if (this.contents != null) {
            labyMod$initializeApi();
        }
    }

    @Inject(method = {"init"}, at = {@At("TAIL")}, require = 0, expect = 0)
    @Dynamic
    private void labyMod$init(CallbackInfo ci) {
        labyMod$initializeApi();
    }

    @Unique
    private void labyMod$initializeApi() {
        this.labyMod$textureUV = new DefaultTextureUV(this.u0, this.v0, this.u1, this.v1);
        GuiMetadataSection metadataSection = (GuiMetadataSection) this.contents.getAdditionalMetadata(GuiMetadataSection.TYPE).orElse(GuiMetadataSection.DEFAULT);
        GuiSpriteScaling.Tile tileScaling = metadataSection.scaling();
        if (tileScaling instanceof GuiSpriteScaling.Tile) {
            GuiSpriteScaling.Tile tile = tileScaling;
            this.labyMod$spriteScaling = new TileScaling(tile.width(), tile.height());
        } else {
            if (tileScaling instanceof GuiSpriteScaling.NineSlice) {
                GuiSpriteScaling.NineSlice nineSlice = (GuiSpriteScaling.NineSlice) tileScaling;
                GuiSpriteScaling.NineSlice.Border border = nineSlice.border();
                this.labyMod$spriteScaling = new NineSpliceScaling(nineSlice.width(), nineSlice.height(), new NineSpliceScaling.Border(border.left(), border.top(), border.right(), border.bottom()));
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
