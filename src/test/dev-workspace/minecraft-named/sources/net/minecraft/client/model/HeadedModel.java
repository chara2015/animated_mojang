package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/HeadedModel.class */
public interface HeadedModel {
    ModelPart getHead();

    default void translateToHead(PoseStack $$0) {
        getHead().translateAndRotate($$0);
    }
}
