package net.labymod.v26_1_2.mixins.client.renderer.renderer.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/renderer/atlas/MixinTextureAtlas.class */
@Mixin({TextureAtlas.class})
public abstract class MixinTextureAtlas implements net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas {

    @Shadow
    @Final
    private Identifier location;

    @Shadow
    private int width;

    @Shadow
    private int height;

    @Shadow
    public abstract TextureAtlasSprite getSprite(Identifier identifier);

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public void register(ResourceLocation id, TextureSprite sprite) {
        throw new UnsupportedOperationException();
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public TextureSprite findSprite(ResourceLocation location) {
        TextureSprite sprite = getSprite((Identifier) location.getMinecraftLocation());
        if (sprite == null) {
            return null;
        }
        return sprite;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public ResourceLocation resource() {
        return this.location;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasWidth() {
        return this.width;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas
    public int getAtlasHeight() {
        return this.height;
    }
}
