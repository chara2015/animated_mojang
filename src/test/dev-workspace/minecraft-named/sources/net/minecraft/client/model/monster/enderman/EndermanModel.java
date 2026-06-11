package net.minecraft.client.model.monster.enderman;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EndermanRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/enderman/EndermanModel.class */
public class EndermanModel<T extends EndermanRenderState> extends HumanoidModel<T> {
    public EndermanModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$1 = HumanoidModel.createMesh(CubeDeformation.NONE, -14.0f);
        PartDefinition $$2 = $$1.getRoot();
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.offset(0.0f, -13.0f, 0.0f));
        $$3.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 16).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(-0.5f)), PartPose.ZERO);
        $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(32, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f), PartPose.offset(0.0f, -14.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(56, 0).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.offset(-5.0f, -12.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.offset(5.0f, -12.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(56, 0).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.offset(-2.0f, -5.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f), PartPose.offset(2.0f, -5.0f, 0.0f));
        return LayerDefinition.create($$1, 64, 32);
    }

    @Override // net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.Model
    public void setupAnim(T $$0) {
        super.setupAnim($$0);
        this.head.visible = true;
        this.rightArm.xRot *= 0.5f;
        this.leftArm.xRot *= 0.5f;
        this.rightLeg.xRot *= 0.5f;
        this.leftLeg.xRot *= 0.5f;
        this.rightArm.xRot = Mth.clamp(this.rightArm.xRot, -0.4f, 0.4f);
        this.leftArm.xRot = Mth.clamp(this.leftArm.xRot, -0.4f, 0.4f);
        this.rightLeg.xRot = Mth.clamp(this.rightLeg.xRot, -0.4f, 0.4f);
        this.leftLeg.xRot = Mth.clamp(this.leftLeg.xRot, -0.4f, 0.4f);
        if ($$0.carriedBlock != null) {
            this.rightArm.xRot = -0.5f;
            this.leftArm.xRot = -0.5f;
            this.rightArm.zRot = 0.05f;
            this.leftArm.zRot = -0.05f;
        }
        if ($$0.isCreepy) {
            this.head.y -= 5.0f;
            this.hat.y += 5.0f;
        }
    }
}
