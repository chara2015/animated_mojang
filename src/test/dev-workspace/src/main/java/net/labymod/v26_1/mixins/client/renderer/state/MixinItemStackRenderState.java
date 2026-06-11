package net.labymod.v26_1.mixins.client.renderer.state;

import net.labymod.v26_1.client.renderer.ItemStackRenderStateAccessor;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/renderer/state/MixinItemStackRenderState.class */
@Mixin({ItemStackRenderState.class})
public class MixinItemStackRenderState implements ItemStackRenderStateAccessor {

    @Shadow
    private ItemDisplayContext displayContext;
    private LivingEntity labyMod$livingEntity;
    private ItemStack labyMod$itemStack;

    @Override // net.labymod.v26_1.client.renderer.ItemStackRenderStateAccessor
    public boolean labyMod$isLeftHand() {
        return this.displayContext.leftHand();
    }

    @Override // net.labymod.v26_1.client.renderer.ItemStackRenderStateAccessor
    public ItemDisplayContext labyMod$getItemDisplayContext() {
        return this.displayContext;
    }

    @Override // net.labymod.v26_1.client.renderer.ItemStackRenderStateAccessor
    public void labyMod$setLivingEntity(LivingEntity entity) {
        this.labyMod$livingEntity = entity;
    }

    @Override // net.labymod.v26_1.client.renderer.ItemStackRenderStateAccessor
    public LivingEntity labyMod$getLivingEntity() {
        return this.labyMod$livingEntity;
    }

    @Override // net.labymod.v26_1.client.renderer.ItemStackRenderStateAccessor
    public void labyMod$setItemStack(ItemStack stack) {
        this.labyMod$itemStack = stack;
    }

    @Override // net.labymod.v26_1.client.renderer.ItemStackRenderStateAccessor
    public ItemStack labyMod$getItemStack() {
        return this.labyMod$itemStack;
    }
}
