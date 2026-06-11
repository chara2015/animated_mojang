package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.monster.phantom.PhantomModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.PhantomRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/PhantomEyesLayer.class */
public class PhantomEyesLayer extends EyesLayer<PhantomRenderState, PhantomModel> {
    private static final RenderType PHANTOM_EYES = RenderTypes.eyes(Identifier.withDefaultNamespace("textures/entity/phantom_eyes.png"));

    public PhantomEyesLayer(RenderLayerParent<PhantomRenderState, PhantomModel> $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.entity.layers.EyesLayer
    public RenderType renderType() {
        return PHANTOM_EYES;
    }
}
