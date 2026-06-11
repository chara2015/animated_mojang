package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/MobRenderer.class */
public abstract class MobRenderer<T extends Mob, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends LivingEntityRenderer<T, S, M> {
    public MobRenderer(EntityRendererProvider.Context $$0, M $$1, float $$2) {
        super($$0, $$1, $$2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.renderer.entity.LivingEntityRenderer, net.minecraft.client.renderer.entity.EntityRenderer
    public boolean shouldShowName(T $$0, double $$1) {
        return super.shouldShowName($$0, $$1) && ($$0.shouldShowName() || ($$0.hasCustomName() && $$0 == this.entityRenderDispatcher.crosshairPickEntity));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.renderer.entity.LivingEntityRenderer, net.minecraft.client.renderer.entity.EntityRenderer
    public float getShadowRadius(S $$0) {
        return super.getShadowRadius((LivingEntityRenderState) $$0) * $$0.ageScale;
    }

    protected static boolean checkMagicName(Entity $$0, String $$1) {
        Component $$2 = $$0.getCustomName();
        return $$2 != null && $$1.equals($$2.getString());
    }
}
