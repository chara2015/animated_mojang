package net.labymod.v1_21.mixins.client.renderer.renderer.atlas;

import net.labymod.v1_21.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/renderer/renderer/atlas/MixinTextureAtlasHolder.class */
@Mixin({gro.class})
public class MixinTextureAtlasHolder implements TextureAtlasHolderAccessor {

    @Shadow
    @Final
    private gqk a;

    @Override // net.labymod.v1_21.client.gfx.pipeline.texture.atlas.TextureAtlasHolderAccessor
    public gqk getTextureAtlas() {
        return this.a;
    }
}
