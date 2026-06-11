package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.monster.spider.SpiderModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/SpiderEyesLayer.class */
public class SpiderEyesLayer<M extends SpiderModel> extends EyesLayer<LivingEntityRenderState, M> {
    private static final RenderType SPIDER_EYES = RenderTypes.eyes(Identifier.withDefaultNamespace("textures/entity/spider_eyes.png"));

    public SpiderEyesLayer(RenderLayerParent<LivingEntityRenderState, M> $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.entity.layers.EyesLayer
    public RenderType renderType() {
        return SPIDER_EYES;
    }
}
