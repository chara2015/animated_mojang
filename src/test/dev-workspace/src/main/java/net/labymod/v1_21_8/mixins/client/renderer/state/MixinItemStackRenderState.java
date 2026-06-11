package net.labymod.v1_21_8.mixins.client.renderer.state;

import net.labymod.v1_21_8.client.renderer.ItemStackRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/state/MixinItemStackRenderState.class */
@Mixin({hoh.class})
public class MixinItemStackRenderState implements ItemStackRenderStateAccessor {

    @Shadow
    private dct a;

    @Override // net.labymod.v1_21_8.client.renderer.ItemStackRenderStateAccessor
    public boolean labyMod$isLeftHand() {
        return this.a.d();
    }

    @Override // net.labymod.v1_21_8.client.renderer.ItemStackRenderStateAccessor
    public dct labyMod$getItemDisplayContext() {
        return this.a;
    }
}
