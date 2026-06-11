package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/WitherSkeletonRenderer.class */
public class WitherSkeletonRenderer extends AbstractSkeletonRenderer<WitherSkeleton, SkeletonRenderState> {
    private static final Identifier WITHER_SKELETON_LOCATION = Identifier.withDefaultNamespace("textures/entity/skeleton/wither_skeleton.png");

    public WitherSkeletonRenderer(EntityRendererProvider.Context $$0) {
        super($$0, ModelLayers.WITHER_SKELETON, ModelLayers.WITHER_SKELETON_ARMOR);
    }

    @Override // net.minecraft.client.renderer.entity.LivingEntityRenderer
    public Identifier getTextureLocation(SkeletonRenderState $$0) {
        return WITHER_SKELETON_LOCATION;
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }
}
