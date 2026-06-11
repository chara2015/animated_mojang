package net.minecraft.client.model.animal.fish;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/fish/PufferfishSmallModel.class */
public class PufferfishSmallModel extends EntityModel<EntityRenderState> {
    private final ModelPart leftFin;
    private final ModelPart rightFin;

    public PufferfishSmallModel(ModelPart $$0) {
        super($$0);
        this.leftFin = $$0.getChild(PartNames.LEFT_FIN);
        this.rightFin = $$0.getChild(PartNames.RIGHT_FIN);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 27).addBox(-1.5f, -2.0f, -1.5f, 3.0f, 2.0f, 3.0f), PartPose.offset(0.0f, 23.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_EYE, CubeListBuilder.create().texOffs(24, 6).addBox(-1.5f, 0.0f, -1.5f, 1.0f, 1.0f, 1.0f), PartPose.offset(0.0f, 20.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_EYE, CubeListBuilder.create().texOffs(28, 6).addBox(0.5f, 0.0f, -1.5f, 1.0f, 1.0f, 1.0f), PartPose.offset(0.0f, 20.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.BACK_FIN, CubeListBuilder.create().texOffs(-3, 0).addBox(-1.5f, 0.0f, 0.0f, 3.0f, 0.0f, 3.0f), PartPose.offset(0.0f, 22.0f, 1.5f));
        $$1.addOrReplaceChild(PartNames.RIGHT_FIN, CubeListBuilder.create().texOffs(25, 0).addBox(-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f), PartPose.offset(-1.5f, 22.0f, -1.5f));
        $$1.addOrReplaceChild(PartNames.LEFT_FIN, CubeListBuilder.create().texOffs(25, 0).addBox(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f), PartPose.offset(1.5f, 22.0f, -1.5f));
        return LayerDefinition.create($$0, 32, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(EntityRenderState $$0) {
        super.setupAnim($$0);
        this.rightFin.zRot = (-0.2f) + (0.4f * Mth.sin($$0.ageInTicks * 0.2f));
        this.leftFin.zRot = 0.2f - (0.4f * Mth.sin($$0.ageInTicks * 0.2f));
    }
}
