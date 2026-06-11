package net.minecraft.client.model.monster.skeleton;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/skeleton/SkeletonModel.class */
public class SkeletonModel<S extends SkeletonRenderState> extends HumanoidModel<S> {
    public SkeletonModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f);
        PartDefinition $$1 = $$0.getRoot();
        createDefaultSkeletonMesh($$1);
        return LayerDefinition.create($$0, 64, 32);
    }

    protected static void createDefaultSkeletonMesh(PartDefinition $$0) {
        $$0.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(40, 16).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.offset(-5.0f, 2.0f, 0.0f));
        $$0.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.offset(5.0f, 2.0f, 0.0f));
        $$0.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.offset(-2.0f, 12.0f, 0.0f));
        $$0.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f), PartPose.offset(2.0f, 12.0f, 0.0f));
    }

    public static LayerDefinition createSingleModelDualBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(16, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f).texOffs(28, 0).addBox(-4.0f, 10.0f, -2.0f, 8.0f, 1.0f, 4.0f).texOffs(16, 48).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.025f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f).texOffs(0, 32).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.2f)), PartPose.offset(0.0f, 0.0f, 0.0f)).addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.ZERO);
        $$1.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(40, 16).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f).texOffs(42, 33).addBox(-1.55f, -2.025f, -1.5f, 3.0f, 12.0f, 3.0f), PartPose.offset(-5.5f, 2.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(56, 16).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f).texOffs(40, 48).addBox(-1.45f, -2.025f, -1.5f, 3.0f, 12.0f, 3.0f), PartPose.offset(5.5f, 2.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f).texOffs(0, 49).addBox(-1.5f, -0.0f, -1.5f, 3.0f, 12.0f, 3.0f), PartPose.offset(-2.0f, 12.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f).texOffs(4, 49).addBox(-1.5f, 0.0f, -1.5f, 3.0f, 12.0f, 3.0f), PartPose.offset(2.0f, 12.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.Model
    public void setupAnim(S $$0) {
        super.setupAnim($$0);
        if ($$0.isAggressive && !$$0.isHoldingBow) {
            float $$1 = $$0.attackTime;
            float $$2 = Mth.sin($$1 * 3.1415927f);
            float $$3 = Mth.sin((1.0f - ((1.0f - $$1) * (1.0f - $$1))) * 3.1415927f);
            this.rightArm.zRot = 0.0f;
            this.leftArm.zRot = 0.0f;
            this.rightArm.yRot = -(0.1f - ($$2 * 0.6f));
            this.leftArm.yRot = 0.1f - ($$2 * 0.6f);
            this.rightArm.xRot = -1.5707964f;
            this.leftArm.xRot = -1.5707964f;
            this.rightArm.xRot -= ($$2 * 1.2f) - ($$3 * 0.4f);
            this.leftArm.xRot -= ($$2 * 1.2f) - ($$3 * 0.4f);
            AnimationUtils.bobArms(this.rightArm, this.leftArm, $$0.ageInTicks);
        }
    }

    @Override // net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.ArmedModel
    public void translateToHand(SkeletonRenderState $$0, HumanoidArm $$1, PoseStack $$2) {
        root().translateAndRotate($$2);
        float $$3 = $$1 == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        ModelPart $$4 = getArm($$1);
        $$4.x += $$3;
        $$4.translateAndRotate($$2);
        $$4.x -= $$3;
    }
}
