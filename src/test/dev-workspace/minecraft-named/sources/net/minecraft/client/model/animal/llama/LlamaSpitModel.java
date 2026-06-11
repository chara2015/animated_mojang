package net.minecraft.client.model.animal.llama;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/llama/LlamaSpitModel.class */
public class LlamaSpitModel extends EntityModel<EntityRenderState> {
    private static final String MAIN = "main";

    public LlamaSpitModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f).addBox(0.0f, -4.0f, 0.0f, 2.0f, 2.0f, 2.0f).addBox(0.0f, 0.0f, -4.0f, 2.0f, 2.0f, 2.0f).addBox(0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f).addBox(2.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f).addBox(0.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f).addBox(0.0f, 0.0f, 2.0f, 2.0f, 2.0f, 2.0f), PartPose.ZERO);
        return LayerDefinition.create($$0, 64, 32);
    }
}
