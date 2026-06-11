package net.labymod.v1_19_4.mixins.client.renderer.renderer.atlas;

import net.labymod.v1_19_4.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/renderer/renderer/atlas/MixinTextureAtlasHolder.class */
@Mixin({fud.class})
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor {

    @Shadow
    @Final
    private ftb a;

    @Override // net.labymod.v1_19_4.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor
    public ftb getTextureAtlas() {
        return this.a;
    }
}
