package net.labymod.v1_21_1.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.api.util.Color;
import net.labymod.v1_21_1.client.renderer.OverlayRenderType;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/MixinLevelRenderer.class */
@Mixin({gex.class})
public class MixinLevelRenderer implements WorldRenderer {

    @Shadow
    private fzf u;

    @Shadow
    private gff t;

    @Shadow
    private int ag;

    @Redirect(method = {"renderLevel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    public void labyMod$renderCustomOutline(gex instance, fbi poseStack, fbm vertexConsumer, bsr entity, double x, double y, double z, jd blockPos, dtc blockState) {
        RenderBlockSelectionBoxEvent event = new RenderBlockSelectionBoxEvent(blockPos.u(), blockPos.v(), blockPos.w());
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        double blockX = ((double) blockPos.u()) - x;
        double blockY = ((double) blockPos.v()) - y;
        double blockZ = ((double) blockPos.w()) - z;
        exv voxelShape = blockState.a(this.u, blockPos, exh.a(entity));
        if (event.getLineColor() != null) {
            voxelShape.a((minX, minY, minZ, maxX, maxY, maxZ) -> {
                drawLines(vertexConsumer, poseStack.c(), blockX, blockY, blockZ, event.getLineColor()).consume(minX, minY, minZ, maxX, maxY, maxZ);
            });
            poseStack.a();
        }
        if (event.getOverlayColor() != null) {
            fbm boxVertex = this.t.c().getBuffer(OverlayRenderType.INSTANCE);
            voxelShape.b((minX2, minY2, minZ2, maxX2, maxY2, maxZ2) -> {
                drawBoxes(boxVertex, poseStack.c(), blockX, blockY, blockZ, event.getOverlayColor()).consume(minX2, minY2, minZ2, maxX2, maxY2, maxZ2);
                poseStack.a();
            });
        }
    }

    private a drawLines(fbm vertexConsumer, a pose, double x, double y, double z, Color color) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = ayo.c((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            vertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
        };
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", shift = At.Shift.AFTER)})
    private void labyMod$beforeEntityRenderPass(fgf $$0, boolean $$1, ffy $$2, ges $$3, gey $$4, Matrix4f $$5, Matrix4f $$6, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V", shift = At.Shift.AFTER)})
    private void labyMod$afterEntityRenderPass(fgf $$0, boolean $$1, ffy $$2, ges $$3, gey $$4, Matrix4f $$5, Matrix4f $$6, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }

    private a drawBoxes(fbm vertexConsumer, a pose, double x, double y, double z, Color color) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = ayo.c((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            vertex(vertexConsumer, pose.a(), minX + x, (minY + y) - ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, (minY + y) - ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, (minY + y) - ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), minX + x, (minY + y) - ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), minX + x, minY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, minY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, minY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), minX + x, minY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
        };
    }

    private fbm vertex(fbm vertexConsumer, Matrix4f matrix, double x, double y, double z) {
        return vertexConsumer.a(matrix, (float) x, (float) y, (float) z);
    }

    @Override // net.labymod.api.client.world.WorldRenderer
    public int getRenderedEntities() {
        return this.ag;
    }
}
