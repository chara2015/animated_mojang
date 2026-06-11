package net.minecraft.client.model.object.projectile;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/projectile/ArrowModel.class */
public class ArrowModel extends EntityModel<ArrowRenderState> {
    public ArrowModel(ModelPart $$0) {
        super($$0, RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f), PartPose.offsetAndRotation(-11.0f, 0.0f, 0.0f, 0.7853982f, 0.0f, 0.0f).withScale(0.8f));
        CubeListBuilder $$2 = CubeListBuilder.create().texOffs(0, 0).addBox(-12.0f, -2.0f, 0.0f, 16.0f, 4.0f, 0.0f, CubeDeformation.NONE, 1.0f, 0.8f);
        $$1.addOrReplaceChild("cross_1", $$2, PartPose.rotation(0.7853982f, 0.0f, 0.0f));
        $$1.addOrReplaceChild("cross_2", $$2, PartPose.rotation(2.3561945f, 0.0f, 0.0f));
        return LayerDefinition.create($$0.transformed($$02 -> {
            return $$02.scaled(0.9f);
        }), 32, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(ArrowRenderState $$0) {
        super.setupAnim($$0);
        if ($$0.shake > 0.0f) {
            float $$1 = (-Mth.sin($$0.shake * 3.0f)) * $$0.shake;
            this.root.zRot += $$1 * 0.017453292f;
        }
    }
}
