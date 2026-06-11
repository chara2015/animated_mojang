package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.HumanoidArm;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/ArmedModel.class */
public interface ArmedModel<T extends EntityRenderState> {
    void translateToHand(T t, HumanoidArm humanoidArm, PoseStack poseStack);
}
