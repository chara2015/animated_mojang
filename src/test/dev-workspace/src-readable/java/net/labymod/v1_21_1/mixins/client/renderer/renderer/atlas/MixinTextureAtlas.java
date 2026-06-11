package net.labymod.v1_21_1.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/renderer/renderer/atlas/MixinTextureAtlas.class */
@Mixin({gqk.class})
public abstract class MixinTextureAtlas implements TextureAtlas {

    @Shadow
    @Final
    private akr l;

    @Shadow
    private int n;

    @Shadow
    private int o;

    @Shadow
    public abstract gql a(akr akrVar);

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public void register(ResourceLocation id, TextureSprite sprite) {
        throw new UnsupportedOperationException();
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public TextureSprite findSprite(ResourceLocation location) {
        TextureSprite textureSpriteA = a((akr) location.getMinecraftLocation());
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
        return this.n;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasHeight() {
        return this.o;
    }
}
