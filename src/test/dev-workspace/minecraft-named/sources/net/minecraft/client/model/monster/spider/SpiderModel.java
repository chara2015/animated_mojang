package net.minecraft.client.model.monster.spider;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/spider/SpiderModel.class */
public class SpiderModel extends EntityModel<LivingEntityRenderState> {
    private static final String BODY_0 = "body0";
    private static final String BODY_1 = "body1";
    private static final String RIGHT_MIDDLE_FRONT_LEG = "right_middle_front_leg";
    private static final String LEFT_MIDDLE_FRONT_LEG = "left_middle_front_leg";
    private static final String RIGHT_MIDDLE_HIND_LEG = "right_middle_hind_leg";
    private static final String LEFT_MIDDLE_HIND_LEG = "left_middle_hind_leg";
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleHindLeg;
    private final ModelPart leftMiddleHindLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public SpiderModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.rightHindLeg = $$0.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = $$0.getChild(PartNames.LEFT_HIND_LEG);
        this.rightMiddleHindLeg = $$0.getChild(RIGHT_MIDDLE_HIND_LEG);
        this.leftMiddleHindLeg = $$0.getChild(LEFT_MIDDLE_HIND_LEG);
        this.rightMiddleFrontLeg = $$0.getChild(RIGHT_MIDDLE_FRONT_LEG);
        this.leftMiddleFrontLeg = $$0.getChild(LEFT_MIDDLE_FRONT_LEG);
        this.rightFrontLeg = $$0.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = $$0.getChild(PartNames.LEFT_FRONT_LEG);
    }

    public static LayerDefinition createSpiderBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(32, 4).addBox(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f), PartPose.offset(0.0f, 15.0f, -3.0f));
        $$1.addOrReplaceChild(BODY_0, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f), PartPose.offset(0.0f, 15.0f, 0.0f));
        $$1.addOrReplaceChild(BODY_1, CubeListBuilder.create().texOffs(0, 12).addBox(-5.0f, -4.0f, -6.0f, 10.0f, 8.0f, 12.0f), PartPose.offset(0.0f, 15.0f, 9.0f));
        CubeListBuilder $$3 = CubeListBuilder.create().texOffs(18, 0).addBox(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
        CubeListBuilder $$4 = CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
        $$1.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, $$3, PartPose.offsetAndRotation(-4.0f, 15.0f, 2.0f, 0.0f, 0.7853982f, -0.7853982f));
        $$1.addOrReplaceChild(PartNames.LEFT_HIND_LEG, $$4, PartPose.offsetAndRotation(4.0f, 15.0f, 2.0f, 0.0f, -0.7853982f, 0.7853982f));
        $$1.addOrReplaceChild(RIGHT_MIDDLE_HIND_LEG, $$3, PartPose.offsetAndRotation(-4.0f, 15.0f, 1.0f, 0.0f, 0.3926991f, -0.58119464f));
        $$1.addOrReplaceChild(LEFT_MIDDLE_HIND_LEG, $$4, PartPose.offsetAndRotation(4.0f, 15.0f, 1.0f, 0.0f, -0.3926991f, 0.58119464f));
        $$1.addOrReplaceChild(RIGHT_MIDDLE_FRONT_LEG, $$3, PartPose.offsetAndRotation(-4.0f, 15.0f, 0.0f, 0.0f, -0.3926991f, -0.58119464f));
        $$1.addOrReplaceChild(LEFT_MIDDLE_FRONT_LEG, $$4, PartPose.offsetAndRotation(4.0f, 15.0f, 0.0f, 0.0f, 0.3926991f, 0.58119464f));
        $$1.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, $$3, PartPose.offsetAndRotation(-4.0f, 15.0f, -1.0f, 0.0f, -0.7853982f, -0.7853982f));
        $$1.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, $$4, PartPose.offsetAndRotation(4.0f, 15.0f, -1.0f, 0.0f, 0.7853982f, 0.7853982f));
        return LayerDefinition.create($$0, 64, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(LivingEntityRenderState $$0) {
        super.setupAnim($$0);
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.head.xRot = $$0.xRot * 0.017453292f;
        float $$1 = $$0.walkAnimationPos * 0.6662f;
        float $$2 = $$0.walkAnimationSpeed;
        float $$3 = (-(Mth.cos(($$1 * 2.0f) + 0.0f) * 0.4f)) * $$2;
        float $$4 = (-(Mth.cos(($$1 * 2.0f) + 3.1415927f) * 0.4f)) * $$2;
        float $$5 = (-(Mth.cos(($$1 * 2.0f) + 1.5707964f) * 0.4f)) * $$2;
        float $$6 = (-(Mth.cos(($$1 * 2.0f) + 4.712389f) * 0.4f)) * $$2;
        float $$7 = Math.abs(Mth.sin($$1 + 0.0f) * 0.4f) * $$2;
        float $$8 = Math.abs(Mth.sin($$1 + 3.1415927f) * 0.4f) * $$2;
        float $$9 = Math.abs(Mth.sin($$1 + 1.5707964f) * 0.4f) * $$2;
        float $$10 = Math.abs(Mth.sin($$1 + 4.712389f) * 0.4f) * $$2;
        this.rightHindLeg.yRot += $$3;
        this.leftHindLeg.yRot -= $$3;
        this.rightMiddleHindLeg.yRot += $$4;
        this.leftMiddleHindLeg.yRot -= $$4;
        this.rightMiddleFrontLeg.yRot += $$5;
        this.leftMiddleFrontLeg.yRot -= $$5;
        this.rightFrontLeg.yRot += $$6;
        this.leftFrontLeg.yRot -= $$6;
        this.rightHindLeg.zRot += $$7;
        this.leftHindLeg.zRot -= $$7;
        this.rightMiddleHindLeg.zRot += $$8;
        this.leftMiddleHindLeg.zRot -= $$8;
        this.rightMiddleFrontLeg.zRot += $$9;
        this.leftMiddleFrontLeg.zRot -= $$9;
        this.rightFrontLeg.zRot += $$10;
        this.leftFrontLeg.zRot -= $$10;
    }
}
