package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.hoglin.HoglinModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HoglinRenderState;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/AbstractHoglinRenderer.class */
public abstract class AbstractHoglinRenderer<T extends Mob & HoglinBase> extends AgeableMobRenderer<T, HoglinRenderState, HoglinModel> {
    public AbstractHoglinRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1, ModelLayerLocation $$2, float $$3) {
        super($$0, new HoglinModel($$0.bakeLayer($$1)), new HoglinModel($$0.bakeLayer($$2)), $$3);
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public HoglinRenderState createRenderState() {
        return new HoglinRenderState();
    }

    @Override // net.minecraft.client.renderer.entity.LivingEntityRenderer, net.minecraft.client.renderer.entity.EntityRenderer
    public void extractRenderState(T $$0, HoglinRenderState $$1, float $$2) {
        super.extractRenderState($$0, $$1, $$2);
        $$1.attackAnimationRemainingTicks = $$0.getAttackAnimationRemainingTicks();
    }
}
