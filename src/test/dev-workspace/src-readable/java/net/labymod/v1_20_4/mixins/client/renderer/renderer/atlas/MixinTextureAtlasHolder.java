package net.labymod.v1_20_4.mixins.client.renderer.renderer.atlas;

import net.labymod.v1_20_4.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/renderer/atlas/MixinTextureAtlasHolder.class */
@Mixin({gfp.class})
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor {

    @Shadow
    @Final
    private gem a;

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor
    public gem getTextureAtlas() {
        return this.a;
    }
}
