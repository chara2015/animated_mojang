package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/ShapeRenderer.class */
public class ShapeRenderer {
    public static void renderShape(PoseStack $$0, VertexConsumer $$1, VoxelShape $$2, double $$3, double $$4, double $$5, int $$6, float $$7) {
        PoseStack.Pose $$8 = $$0.last();
        $$2.forAllEdges(($$72, $$82, $$9, $$10, $$11, $$12) -> {
            Vector3f $$13 = new Vector3f((float) ($$10 - $$72), (float) ($$11 - $$82), (float) ($$12 - $$9)).normalize();
            $$1.addVertex($$8, (float) ($$72 + $$3), (float) ($$82 + $$4), (float) ($$9 + $$5)).setColor($$6).setNormal($$8, $$13).setLineWidth($$7);
            $$1.addVertex($$8, (float) ($$10 + $$3), (float) ($$11 + $$4), (float) ($$12 + $$5)).setColor($$6).setNormal($$8, $$13).setLineWidth($$7);
        });
    }
}
