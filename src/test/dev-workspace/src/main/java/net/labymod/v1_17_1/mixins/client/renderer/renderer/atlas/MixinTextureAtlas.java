package net.labymod.v1_17_1.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/renderer/renderer/atlas/MixinTextureAtlas.class */
@Mixin({exl.class})
public abstract class MixinTextureAtlas implements TextureAtlas {

    @Shadow
    @Final
    private ww l;

    @Unique
    private int labyMod$width;

    @Unique
    private int labyMod$height;

    @Shadow
    public abstract exm a(ww wwVar);

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public void register(ResourceLocation id, TextureSprite sprite) {
        throw new UnsupportedOperationException();
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public TextureSprite findSprite(ResourceLocation location) {
        TextureSprite textureSpriteA = a((ww) location.getMinecraftLocation());
        if (textureSpriteA == null) {
            return null;
        }
        return textureSpriteA;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public ResourceLocation resource() {
        return this.l;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasWidth() {
        return this.labyMod$width;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasHeight() {
        return this.labyMod$height;
    }

    @Inject(method = {"reload"}, at = {@At("HEAD")})
    private void labyMod$setAtlasDimension(a preparations, CallbackInfo ci) {
        this.labyMod$width = preparations.b;
        this.labyMod$height = preparations.c;
    }
}
