package net.labymod.v1_21_8.client.util;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.api.util.Color;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/util/BlockOutlineUtil.class */
public class BlockOutlineUtil {
    public static void renderBlockOutline(dmu level, gxn bufferSource, fog lineConsumer, fod poseStack, bzm entity, double x, double y, double z, jb blockPos, eeb blockState, int color) {
        RenderBlockSelectionBoxEvent event = (RenderBlockSelectionBoxEvent) Laby.fireEvent(new RenderBlockSelectionBoxEvent(Color.of(color), blockPos.u(), blockPos.v(), blockPos.w()));
        if (event.isCancelled()) {
            return;
        }
        double blockX = ((double) blockPos.u()) - x;
        double blockY = ((double) blockPos.v()) - y;
        double blockZ = ((double) blockPos.w()) - z;
        fjm shape = blockState.a(level, blockPos, fix.a(entity));
        if (event.getLineColor() != null) {
            shape.a((minX, minY, minZ, maxX, maxY, maxZ) -> {
                drawLines(lineConsumer, poseStack.c(), blockX, blockY, blockZ, event.getLineColor()).consume(minX, minY, minZ, maxX, maxY, maxZ);
            });
        }
        Color overlayColor = event.getOverlayColor();
        if (overlayColor != null) {
            fog overlayConsumer = bufferSource.getBuffer(gxz.z());
            shape.b((minX2, minY2, minZ2, maxX2, maxY2, maxZ2) -> {
                drawBoxes(overlayConsumer, poseStack.c(), blockX, blockY, blockZ, overlayColor).consume(minX2, minY2, minZ2, maxX2, maxY2, maxZ2);
            });
        }
    }

    private static a drawLines(fog vertexConsumer, a pose, double x, double y, double z, Color color) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = bcb.c((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
        };
    }

    private static a drawBoxes(fog vertexConsumer, a pose, double x, double y, double z, Color color) {
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float) (maxX - minX);
            float blockY = (float) (maxY - minY);
            float blockZ = (float) (maxZ - minZ);
            float offset = bcb.c((blockX * blockX) + (blockY * blockY) + (blockZ * blockZ));
            float blockX2 = blockX / offset;
            float blockY2 = blockY / offset;
            float blockZ2 = blockZ / offset;
            addVertex(vertexConsumer, pose.a(), minX + x, (minY + y) - ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, (minY + y) - ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, (minY + y) - ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), minX + x, (minY + y) - ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, minY + y, (minZ + z) - ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, minY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y, maxZ + z + ((double) 0.001f)).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), (minX + x) - ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), minZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), maxY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
            addVertex(vertexConsumer, pose.a(), maxX + x + ((double) 0.001f), minY + y + ((double) 0.001f), maxZ + z).a(red, green, blue, alpha).b(pose, blockX2, blockY2, blockZ2);
        };
    }

    private static fog addVertex(fog vertexConsumer, Matrix4f matrix, double x, double y, double z) {
        return vertexConsumer.a(matrix, (float) x, (float) y, (float) z);
    }
}
