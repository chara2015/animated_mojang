package net.labymod.v1_18_2.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.api.util.Color;
import net.labymod.v1_18_2.client.renderer.OverlayRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/MixinLevelRenderer.class */
@Mixin({eqq.class})
public class MixinLevelRenderer implements WorldRenderer {

    @Shadow
    private ems x;

    @Shadow
    private eqy w;

    @Shadow
    private int ao;

    @Redirect(method = {"renderLevel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    public void labyMod$renderCustomOutline(eqq instance, dtm poseStack, dtq vertexConsumer, axk entity, double x, double y, double z, gj blockPos, cov blockState) {
        RenderBlockSelectionBoxEvent event = new RenderBlockSelectionBoxEvent(blockPos.u(), blockPos.v(), blockPos.w());
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        double blockX = ((double) blockPos.u()) - x;
        double blockY = ((double) blockPos.v()) - y;
        double blockZ = ((double) blockPos.w()) - z;
        dqh voxelShape = blockState.a(this.x, blockPos, dpt.a(entity));
        if (event.getLineColor() != null) {
            voxelShape.a((minX, minY, minZ, maxX, maxY, maxZ) -> {
                drawLines(vertexConsumer, poseStack.c(), blockX, blockY, blockZ, event.getLineColor()).consume(minX, minY, minZ, maxX, maxY, maxZ);
            });
            poseStack.a();
        }
        if (event.getOverlayColor() != null) {
            dtq boxVertex = this.w.b().getBuffer(OverlayRenderType.INSTANCE);
            voxelShape.b((minX2, minY2, minZ2, maxX2, maxY2, maxZ2) -> {
                drawBoxes(boxVertex, poseStack.c(), blockX, blockY, blockZ, event.getOverlayColor()).consume(minX2, minY2, minZ2, maxX2, maxY2, maxZ2);
                poseStack.a();
            });
        }
    }

    private a drawLines(dtq vertexConsumer, a pose, double x, double y, double z, Color color) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = ajl.c((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            vertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
        };
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", shift = At.Shift.AFTER)})
    private void labyMod$beforeEntityRenderPass(dtm param0, float param1, long param2, boolean param3, dyb param4, eql param5, eqr param6, d param7, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V", shift = At.Shift.AFTER)})
    private void labyMod$afterEntityRenderPass(dtm param0, float param1, long param2, boolean param3, dyb param4, eql param5, eqr param6, d param7, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }

    private a drawBoxes(dtq vertexConsumer, a pose, double x, double y, double z, Color color) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = ajl.c((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            vertex(vertexConsumer, pose.a(), minX + x, (minY + y) - ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, (minY + y) - ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, (minY + y) - ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), minX + x, (minY + y) - ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), minX + x, minY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, minY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, minY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), minX + x, maxY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), minX + x, minY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
            vertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX2, blockY2, blockZ2).c();
        };
    }

    private dtq vertex(dtq vertexConsumer, d matrix, double x, double y, double z) {
        return vertexConsumer.a(matrix, (float) x, (float) y, (float) z);
    }

    @Override // net.labymod.api.client.world.WorldRenderer
    public int getRenderedEntities() {
        return this.ao;
    }
}
