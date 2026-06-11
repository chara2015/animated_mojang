package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/BlockEntityRenderer.class */
public interface BlockEntityRenderer<T extends BlockEntity, S extends BlockEntityRenderState> {
    S createRenderState();

    void submit(S s, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState);

    default void extractRenderState(T $$0, S $$1, float $$2, Vec3 $$3, ModelFeatureRenderer.CrumblingOverlay $$4) {
        BlockEntityRenderState.extractBase($$0, $$1, $$4);
    }

    default boolean shouldRenderOffScreen() {
        return false;
    }

    default int getViewDistance() {
        return 64;
    }

    default boolean shouldRender(T $$0, Vec3 $$1) {
        return Vec3.atCenterOf($$0.getBlockPos()).closerThan($$1, getViewDistance());
    }
}
