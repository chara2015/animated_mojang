package net.labymod.v1_12_2.mixins.client.renderer.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/texture/atlas/MixinTextureAtlas.class */
@Mixin({cdp.class})
public abstract class MixinTextureAtlas implements TextureAtlas {

    @Unique
    private int labyMod$width;

    @Unique
    private int labyMod$height;

    @Shadow
    public abstract cdq a(String str);

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public void register(ResourceLocation id, TextureSprite sprite) {
        throw new UnsupportedOperationException();
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public TextureSprite findSprite(ResourceLocation location) {
        TextureSprite textureSpriteA = a(location.getPath());
        if (textureSpriteA == null) {
            return null;
        }
        return textureSpriteA;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public ResourceLocation resource() {
        return cdp.g;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasWidth() {
        return this.labyMod$width;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasHeight() {
        return this.labyMod$height;
    }

    @Redirect(method = {"loadTextureAtlas"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/Stitcher;doStitch()V"))
    private void labyMod$setAtlasDimension(cdn stitcher) {
        stitcher.c();
        this.labyMod$width = stitcher.a();
        this.labyMod$height = stitcher.b();
    }
}
