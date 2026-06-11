package net.minecraft.client.renderer.entity.state;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/HoldingEntityRenderState.class */
public class HoldingEntityRenderState extends LivingEntityRenderState {
    public final ItemStackRenderState heldItem = new ItemStackRenderState();

    public static void extractHoldingEntityRenderState(LivingEntity $$0, HoldingEntityRenderState $$1, ItemModelResolver $$2) {
        $$2.updateForLiving($$1.heldItem, $$0.getMainHandItem(), ItemDisplayContext.GROUND, $$0);
    }
}
