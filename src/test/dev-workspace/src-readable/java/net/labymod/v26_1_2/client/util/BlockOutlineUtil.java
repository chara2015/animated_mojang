package net.labymod.v26_1_2.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.api.util.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/util/BlockOutlineUtil.class */
public class BlockOutlineUtil {
    public static void renderBlockOutline(Level level, MultiBufferSource bufferSource, VertexConsumer lineConsumer, PoseStack poseStack, double x, double y, double z, BlockOutlineRenderState renderState, int color, float lineWidth) {
        BlockPos blockPos = renderState.pos();
        RenderBlockSelectionBoxEvent event = (RenderBlockSelectionBoxEvent) Laby.fireEvent(new RenderBlockSelectionBoxEvent(Color.of(color), blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        if (event.isCancelled()) {
            return;
        }
        double blockX = ((double) blockPos.getX()) - x;
        double blockY = ((double) blockPos.getY()) - y;
        double blockZ = ((double) blockPos.getZ()) - z;
        VoxelShape shape = renderState.shape();
        if (event.getLineColor() != null) {
            shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
                drawLines(lineConsumer, poseStack.last(), blockX, blockY, blockZ, event.getLineColor(), lineWidth).consume(minX, minY, minZ, maxX, maxY, maxZ);
            });
        }
        Color overlayColor = event.getOverlayColor();
        if (overlayColor != null) {
            VertexConsumer overlayConsumer = bufferSource.getBuffer(RenderTypes.debugQuads());
            shape.forAllBoxes((minX2, minY2, minZ2, maxX2, maxY2, maxZ2) -> {
                drawBoxes(overlayConsumer, poseStack.last(), blockX, blockY, blockZ, overlayColor, lineWidth).consume(minX2, minY2, minZ2, maxX2, maxY2, maxZ2);
            });
        }
    }

    private static Shapes.DoubleLineConsumer drawLines(VertexConsumer vertexConsumer, PoseStack.Pose pose, double x, double y, double z, Color color, float lineWidth) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = Mth.sqrt((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            addVertex(vertexConsumer, pose.pose(), minX + x, minY + y, minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, maxY + y, maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
        };
    }

    private static Shapes.DoubleLineConsumer drawBoxes(VertexConsumer vertexConsumer, PoseStack.Pose pose, double x, double y, double z, Color color, float lineWidth) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = Mth.sqrt((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            addVertex(vertexConsumer, pose.pose(), minX + x, (minY + y) - ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, (minY + y) - ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, (minY + y) - ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), minX + x, (minY + y) - ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), minX + x, maxY + y + ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, maxY + y + ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, maxY + y + ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), minX + x, maxY + y + ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), minX + x, minY + y, (minZ + z) - ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), minX + x, maxY + y, (minZ + z) - ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, maxY + y, (minZ + z) - ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, minY + y, (minZ + z) - ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, minY + y, maxZ + z + ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x, maxY + y, maxZ + z + ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), minX + x, maxY + y, maxZ + z + ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), minX + x, minY + y, maxZ + z + ((double) 0.001f)).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
            addVertex(vertexConsumer, pose.pose(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).setColor(red, green, blue, alpha).setNormal(pose, blockX2, blockY2, blockZ2).setLineWidth(lineWidth);
        };
    }

    private static VertexConsumer addVertex(VertexConsumer vertexConsumer, Matrix4f matrix, double x, double y, double z) {
        return vertexConsumer.addVertex(matrix, (float) x, (float) y, (float) z);
    }
}
