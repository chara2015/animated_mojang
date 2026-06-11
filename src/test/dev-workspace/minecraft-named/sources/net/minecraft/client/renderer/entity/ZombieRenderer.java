package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.entity.monster.zombie.Zombie;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/ZombieRenderer.class */
public class ZombieRenderer extends AbstractZombieRenderer<Zombie, ZombieRenderState, ZombieModel<ZombieRenderState>> {
    public ZombieRenderer(EntityRendererProvider.Context $$0) {
        this($$0, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_BABY, ModelLayers.ZOMBIE_ARMOR, ModelLayers.ZOMBIE_BABY_ARMOR);
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    public ZombieRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1, ModelLayerLocation $$2, ArmorModelSet<ModelLayerLocation> $$3, ArmorModelSet<ModelLayerLocation> $$4) {
        super($$0, new ZombieModel($$0.bakeLayer($$1)), new ZombieModel($$0.bakeLayer($$2)), ArmorModelSet.bake($$3, $$0.getModelSet(), ZombieModel::new), ArmorModelSet.bake($$4, $$0.getModelSet(), ZombieModel::new));
    }
}
