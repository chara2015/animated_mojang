package net.minecraft.client.model.animal.golem;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.IronGolemRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/golem/IronGolemModel.class */
public class IronGolemModel extends EntityModel<IronGolemRenderState> {
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public IronGolemModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.rightArm = $$0.getChild(PartNames.RIGHT_ARM);
        this.leftArm = $$0.getChild(PartNames.LEFT_ARM);
        this.rightLeg = $$0.getChild(PartNames.RIGHT_LEG);
        this.leftLeg = $$0.getChild(PartNames.LEFT_LEG);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -12.0f, -5.5f, 8.0f, 10.0f, 8.0f).texOffs(24, 0).addBox(-1.0f, -5.0f, -7.5f, 2.0f, 4.0f, 2.0f), PartPose.offset(0.0f, -7.0f, -2.0f));
        $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 40).addBox(-9.0f, -2.0f, -6.0f, 18.0f, 12.0f, 11.0f).texOffs(0, 70).addBox(-4.5f, 10.0f, -3.0f, 9.0f, 5.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, -7.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(60, 21).addBox(-13.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f), PartPose.offset(0.0f, -7.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(60, 58).addBox(9.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f), PartPose.offset(0.0f, -7.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(37, 0).addBox(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f), PartPose.offset(-4.0f, 11.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f), PartPose.offset(5.0f, 11.0f, 0.0f));
        return LayerDefinition.create($$0, 128, 128);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(IronGolemRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.attackTicksRemaining;
        float $$2 = $$0.walkAnimationSpeed;
        float $$3 = $$0.walkAnimationPos;
        if ($$1 > 0.0f) {
            this.rightArm.xRot = (-2.0f) + (1.5f * Mth.triangleWave($$1, 10.0f));
            this.leftArm.xRot = (-2.0f) + (1.5f * Mth.triangleWave($$1, 10.0f));
        } else {
            int $$4 = $$0.offerFlowerTick;
            if ($$4 > 0) {
                this.rightArm.xRot = (-0.8f) + (0.025f * Mth.triangleWave($$4, 70.0f));
                this.leftArm.xRot = 0.0f;
            } else {
                this.rightArm.xRot = ((-0.2f) + (1.5f * Mth.triangleWave($$3, 13.0f))) * $$2;
                this.leftArm.xRot = ((-0.2f) - (1.5f * Mth.triangleWave($$3, 13.0f))) * $$2;
            }
        }
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.rightLeg.xRot = (-1.5f) * Mth.triangleWave($$3, 13.0f) * $$2;
        this.leftLeg.xRot = 1.5f * Mth.triangleWave($$3, 13.0f) * $$2;
        this.rightLeg.yRot = 0.0f;
        this.leftLeg.yRot = 0.0f;
    }

    public ModelPart getFlowerHoldingArm() {
        return this.rightArm;
    }
}
