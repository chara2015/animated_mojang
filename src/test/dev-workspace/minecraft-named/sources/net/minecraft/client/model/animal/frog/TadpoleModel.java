package net.minecraft.client.model.animal.frog;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/frog/TadpoleModel.class */
public class TadpoleModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart tail;

    public TadpoleModel(ModelPart $$0) {
        super($$0, RenderTypes::entityCutoutNoCull);
        this.tail = $$0.getChild(PartNames.TAIL);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-1.5f, -1.0f, 0.0f, 3.0f, 2.0f, 3.0f), PartPose.offset(0.0f, 22.0f, -3.0f));
        $$1.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(0, 0).addBox(0.0f, -1.0f, 0.0f, 0.0f, 2.0f, 7.0f), PartPose.offset(0.0f, 22.0f, 0.0f));
        return LayerDefinition.create($$0, 16, 16);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(LivingEntityRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.isInWater ? 1.0f : 1.5f;
        this.tail.yRot = (-$$1) * 0.25f * Mth.sin(0.3f * $$0.ageInTicks);
    }
}
