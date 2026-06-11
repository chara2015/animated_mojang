package net.minecraft.client.model.animal.bee;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.BeeRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/bee/BeeModel.class */
public class BeeModel extends EntityModel<BeeRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.5f);
    private static final String BONE = "bone";
    private static final String STINGER = "stinger";
    private static final String LEFT_ANTENNA = "left_antenna";
    private static final String RIGHT_ANTENNA = "right_antenna";
    private static final String FRONT_LEGS = "front_legs";
    private static final String MIDDLE_LEGS = "middle_legs";
    private static final String BACK_LEGS = "back_legs";
    private final ModelPart bone;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart frontLeg;
    private final ModelPart midLeg;
    private final ModelPart backLeg;
    private final ModelPart stinger;
    private final ModelPart leftAntenna;
    private final ModelPart rightAntenna;
    private float rollAmount;

    public BeeModel(ModelPart $$0) {
        super($$0);
        this.bone = $$0.getChild("bone");
        ModelPart $$1 = this.bone.getChild(PartNames.BODY);
        this.stinger = $$1.getChild(STINGER);
        this.leftAntenna = $$1.getChild(LEFT_ANTENNA);
        this.rightAntenna = $$1.getChild(RIGHT_ANTENNA);
        this.rightWing = this.bone.getChild(PartNames.RIGHT_WING);
        this.leftWing = this.bone.getChild(PartNames.LEFT_WING);
        this.frontLeg = this.bone.getChild(FRONT_LEGS);
        this.midLeg = this.bone.getChild(MIDDLE_LEGS);
        this.backLeg = this.bone.getChild(BACK_LEGS);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0f, 19.0f, 0.0f));
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-3.5f, -4.0f, -5.0f, 7.0f, 7.0f, 10.0f), PartPose.ZERO);
        $$3.addOrReplaceChild(STINGER, CubeListBuilder.create().texOffs(26, 7).addBox(0.0f, -1.0f, 5.0f, 0.0f, 1.0f, 2.0f), PartPose.ZERO);
        $$3.addOrReplaceChild(LEFT_ANTENNA, CubeListBuilder.create().texOffs(2, 0).addBox(1.5f, -2.0f, -3.0f, 1.0f, 2.0f, 3.0f), PartPose.offset(0.0f, -2.0f, -5.0f));
        $$3.addOrReplaceChild(RIGHT_ANTENNA, CubeListBuilder.create().texOffs(2, 3).addBox(-2.5f, -2.0f, -3.0f, 1.0f, 2.0f, 3.0f), PartPose.offset(0.0f, -2.0f, -5.0f));
        CubeDeformation $$4 = new CubeDeformation(0.001f);
        $$2.addOrReplaceChild(PartNames.RIGHT_WING, CubeListBuilder.create().texOffs(0, 18).addBox(-9.0f, 0.0f, 0.0f, 9.0f, 0.0f, 6.0f, $$4), PartPose.offsetAndRotation(-1.5f, -4.0f, -3.0f, 0.0f, -0.2618f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_WING, CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.0f, 0.0f, 0.0f, 9.0f, 0.0f, 6.0f, $$4), PartPose.offsetAndRotation(1.5f, -4.0f, -3.0f, 0.0f, 0.2618f, 0.0f));
        $$2.addOrReplaceChild(FRONT_LEGS, CubeListBuilder.create().addBox(FRONT_LEGS, -5.0f, 0.0f, 0.0f, 7, 2, 0, 26, 1), PartPose.offset(1.5f, 3.0f, -2.0f));
        $$2.addOrReplaceChild(MIDDLE_LEGS, CubeListBuilder.create().addBox(MIDDLE_LEGS, -5.0f, 0.0f, 0.0f, 7, 2, 0, 26, 3), PartPose.offset(1.5f, 3.0f, 0.0f));
        $$2.addOrReplaceChild(BACK_LEGS, CubeListBuilder.create().addBox(BACK_LEGS, -5.0f, 0.0f, 0.0f, 7, 2, 0, 26, 5), PartPose.offset(1.5f, 3.0f, 2.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(BeeRenderState $$0) {
        super.setupAnim($$0);
        this.rollAmount = $$0.rollAmount;
        this.stinger.visible = $$0.hasStinger;
        if (!$$0.isOnGround) {
            float $$1 = $$0.ageInTicks * 120.32113f * 0.017453292f;
            this.rightWing.yRot = 0.0f;
            this.rightWing.zRot = Mth.cos($$1) * 3.1415927f * 0.15f;
            this.leftWing.xRot = this.rightWing.xRot;
            this.leftWing.yRot = this.rightWing.yRot;
            this.leftWing.zRot = -this.rightWing.zRot;
            this.frontLeg.xRot = 0.7853982f;
            this.midLeg.xRot = 0.7853982f;
            this.backLeg.xRot = 0.7853982f;
        }
        if (!$$0.isAngry && !$$0.isOnGround) {
            float $$2 = Mth.cos($$0.ageInTicks * 0.18f);
            this.bone.xRot = 0.1f + ($$2 * 3.1415927f * 0.025f);
            this.leftAntenna.xRot = $$2 * 3.1415927f * 0.03f;
            this.rightAntenna.xRot = $$2 * 3.1415927f * 0.03f;
            this.frontLeg.xRot = ((-$$2) * 3.1415927f * 0.1f) + 0.3926991f;
            this.backLeg.xRot = ((-$$2) * 3.1415927f * 0.05f) + 0.7853982f;
            this.bone.y -= Mth.cos($$0.ageInTicks * 0.18f) * 0.9f;
        }
        if (this.rollAmount > 0.0f) {
            this.bone.xRot = Mth.rotLerpRad(this.rollAmount, this.bone.xRot, 3.0915928f);
        }
    }
}
