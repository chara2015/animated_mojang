package net.labymod.v1_20_1.mixins.client.renderer.renderer.atlas;

import net.labymod.v1_20_1.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/renderer/atlas/MixinTextureAtlasHolder.class */
@Mixin({fvw.class})
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor {

    @Shadow
    @Final
    private fuu a;

    @Override // net.labymod.v1_20_1.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor
    public fuu getTextureAtlas() {
        return this.a;
    }
}
