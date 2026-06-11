package net.labymod.v1_21_4.mixins.client.renderer.state;

import net.labymod.v1_21_4.client.renderer.ItemStackRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/state/MixinItemStackRenderState.class */
@Mixin({hbp.class})
public class MixinItemStackRenderState implements ItemStackRenderStateAccessor {

    @Shadow
    private boolean b;

    @Shadow
    private cwo a;

    @Override // net.labymod.v1_21_4.client.renderer.ItemStackRenderStateAccessor
    public boolean labyMod$isLeftHand() {
        return this.b;
    }

    @Override // net.labymod.v1_21_4.client.renderer.ItemStackRenderStateAccessor
    public cwo labyMod$getItemDisplayContext() {
        return this.a;
    }
}
