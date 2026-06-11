package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/RenderLayerParent.class */
public interface RenderLayerParent<S extends EntityRenderState, M extends EntityModel<? super S>> {
    M getModel();
}
