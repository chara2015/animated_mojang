package net.labymod.v1_21_11.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/util/PoseStackVisitor.class */
public class PoseStackVisitor implements ModelPart.Visitor {
    private final PoseStack stack;

    public PoseStackVisitor(PoseStack stack) {
        this.stack = stack;
    }

    public void visit(@NotNull PoseStack.Pose pose, @NotNull String partName, int index, @NotNull ModelPart.Cube cube) {
        this.stack.mulPose(pose.pose());
    }
}
