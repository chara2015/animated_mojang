package net.labymod.v1_12_2.mixins.cosmetics;

import net.labymod.v1_12_2.client.renderer.GlowAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/cosmetics/MixinRenderManagerGlowing.class */
@Mixin({bzf.class})
public class MixinRenderManagerGlowing implements GlowAccessor {

    @Shadow
    private boolean r;

    @Override // net.labymod.v1_12_2.client.renderer.GlowAccessor
    public boolean isGlowing() {
        return this.r;
    }
}
