package net.labymod.v1_21_11.mixins.client.renderer.state;

import net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/state/MixinItemStackRenderState.class */
@Mixin({ihm.class})
public class MixinItemStackRenderState implements ItemStackRenderStateAccessor {

    @Shadow
    private dlr a;
    private chl labyMod$livingEntity;
    private dlt labyMod$itemStack;

    @Override // net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor
    public boolean labyMod$isLeftHand() {
        return this.a.d();
    }

    @Override // net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor
    public dlr labyMod$getItemDisplayContext() {
        return this.a;
    }

    @Override // net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor
    public void labyMod$setLivingEntity(chl entity) {
        this.labyMod$livingEntity = entity;
    }

    @Override // net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor
    public chl labyMod$getLivingEntity() {
        return this.labyMod$livingEntity;
    }

    @Override // net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor
    public void labyMod$setItemStack(dlt stack) {
        this.labyMod$itemStack = stack;
    }

    @Override // net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor
    public dlt labyMod$getItemStack() {
        return this.labyMod$itemStack;
    }
}
