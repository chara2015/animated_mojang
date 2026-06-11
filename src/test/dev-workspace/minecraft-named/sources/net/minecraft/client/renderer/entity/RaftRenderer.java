package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.RaftModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/RaftRenderer.class */
public class RaftRenderer extends AbstractBoatRenderer {
    private final EntityModel<BoatRenderState> model;
    private final Identifier texture;

    public RaftRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1) {
        super($$0);
        this.texture = $$1.model().withPath($$02 -> {
            return "textures/entity/" + $$02 + ".png";
        });
        this.model = new RaftModel($$0.bakeLayer($$1));
    }

    @Override // net.minecraft.client.renderer.entity.AbstractBoatRenderer
    protected EntityModel<BoatRenderState> model() {
        return this.model;
    }

    @Override // net.minecraft.client.renderer.entity.AbstractBoatRenderer
    protected RenderType renderType() {
        return this.model.renderType(this.texture);
    }
}
