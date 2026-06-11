package net.labymod.v1_21_10.mixins.client.renderer.state;

import net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/state/MixinItemStackRenderState.class */
@Mixin({hys.class})
public class MixinItemStackRenderState implements ItemStackRenderStateAccessor {

    @Shadow
    private dhn a;
    private cew labyMod$livingEntity;
    private dhp labyMod$itemStack;

    @Override // net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor
    public boolean labyMod$isLeftHand() {
        return this.a.d();
    }

    @Override // net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor
    public dhn labyMod$getItemDisplayContext() {
        return this.a;
    }

    @Override // net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor
    public void labyMod$setLivingEntity(cew entity) {
        this.labyMod$livingEntity = entity;
    }

    @Override // net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor
    public cew labyMod$getLivingEntity() {
        return this.labyMod$livingEntity;
    }

    @Override // net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor
    public void labyMod$setItemStack(dhp stack) {
        this.labyMod$itemStack = stack;
    }

    @Override // net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor
    public dhp labyMod$getItemStack() {
        return this.labyMod$itemStack;
    }
}
