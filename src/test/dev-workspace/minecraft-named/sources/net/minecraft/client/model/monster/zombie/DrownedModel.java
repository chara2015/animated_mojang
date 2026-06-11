package net.minecraft.client.model.monster.zombie;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/zombie/DrownedModel.class */
public class DrownedModel extends ZombieModel<ZombieRenderState> {
    public DrownedModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
        MeshDefinition $$1 = HumanoidModel.createMesh($$0, 0.0f);
        PartDefinition $$2 = $$1.getRoot();
        $$2.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0), PartPose.offset(5.0f, 2.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0), PartPose.offset(1.9f, 12.0f, 0.0f));
        return LayerDefinition.create($$1, 64, 64);
    }

    @Override // net.minecraft.client.model.monster.zombie.AbstractZombieModel, net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.Model
    public void setupAnim(ZombieRenderState $$0) {
        super.setupAnim($$0);
        if ($$0.leftArmPose == HumanoidModel.ArmPose.THROW_TRIDENT) {
            this.leftArm.xRot = (this.leftArm.xRot * 0.5f) - 3.1415927f;
            this.leftArm.yRot = 0.0f;
        }
        if ($$0.rightArmPose == HumanoidModel.ArmPose.THROW_TRIDENT) {
            this.rightArm.xRot = (this.rightArm.xRot * 0.5f) - 3.1415927f;
            this.rightArm.yRot = 0.0f;
        }
        float $$1 = $$0.swimAmount;
        if ($$1 > 0.0f) {
            this.rightArm.xRot = Mth.rotLerpRad($$1, this.rightArm.xRot, -2.5132742f) + ($$1 * 0.35f * Mth.sin(0.1f * $$0.ageInTicks));
            this.leftArm.xRot = Mth.rotLerpRad($$1, this.leftArm.xRot, -2.5132742f) - (($$1 * 0.35f) * Mth.sin(0.1f * $$0.ageInTicks));
            this.rightArm.zRot = Mth.rotLerpRad($$1, this.rightArm.zRot, -0.15f);
            this.leftArm.zRot = Mth.rotLerpRad($$1, this.leftArm.zRot, 0.15f);
            this.leftLeg.xRot -= ($$1 * 0.55f) * Mth.sin(0.1f * $$0.ageInTicks);
            this.rightLeg.xRot += $$1 * 0.55f * Mth.sin(0.1f * $$0.ageInTicks);
            this.head.xRot = 0.0f;
        }
    }
}
