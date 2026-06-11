package net.minecraft.client.model.animal.axolotl;

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
import net.minecraft.client.renderer.entity.state.AxolotlRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/axolotl/AxolotlModel.class */
public class AxolotlModel extends EntityModel<AxolotlRenderState> {
    public static final float SWIMMING_LEG_XROT = 1.8849558f;
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.5f);
    private final ModelPart tail;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart topGills;
    private final ModelPart leftGills;
    private final ModelPart rightGills;

    public AxolotlModel(ModelPart $$0) {
        super($$0);
        this.body = $$0.getChild(PartNames.BODY);
        this.head = this.body.getChild(PartNames.HEAD);
        this.rightHindLeg = this.body.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = this.body.getChild(PartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = this.body.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = this.body.getChild(PartNames.LEFT_FRONT_LEG);
        this.tail = this.body.getChild(PartNames.TAIL);
        this.topGills = this.head.getChild(PartNames.TOP_GILLS);
        this.leftGills = this.head.getChild(PartNames.LEFT_GILLS);
        this.rightGills = this.head.getChild(PartNames.RIGHT_GILLS);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 11).addBox(-4.0f, -2.0f, -9.0f, 8.0f, 4.0f, 10.0f).texOffs(2, 17).addBox(0.0f, -3.0f, -8.0f, 0.0f, 5.0f, 9.0f), PartPose.offset(0.0f, 20.0f, 5.0f));
        CubeDeformation $$3 = new CubeDeformation(0.001f);
        PartDefinition $$4 = $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 1).addBox(-4.0f, -3.0f, -5.0f, 8.0f, 5.0f, 5.0f, $$3), PartPose.offset(0.0f, 0.0f, -9.0f));
        CubeListBuilder $$5 = CubeListBuilder.create().texOffs(3, 37).addBox(-4.0f, -3.0f, 0.0f, 8.0f, 3.0f, 0.0f, $$3);
        CubeListBuilder $$6 = CubeListBuilder.create().texOffs(0, 40).addBox(-3.0f, -5.0f, 0.0f, 3.0f, 7.0f, 0.0f, $$3);
        CubeListBuilder $$7 = CubeListBuilder.create().texOffs(11, 40).addBox(0.0f, -5.0f, 0.0f, 3.0f, 7.0f, 0.0f, $$3);
        $$4.addOrReplaceChild(PartNames.TOP_GILLS, $$5, PartPose.offset(0.0f, -3.0f, -1.0f));
        $$4.addOrReplaceChild(PartNames.LEFT_GILLS, $$6, PartPose.offset(-4.0f, 0.0f, -1.0f));
        $$4.addOrReplaceChild(PartNames.RIGHT_GILLS, $$7, PartPose.offset(4.0f, 0.0f, -1.0f));
        CubeListBuilder $$8 = CubeListBuilder.create().texOffs(2, 13).addBox(-1.0f, 0.0f, 0.0f, 3.0f, 5.0f, 0.0f, $$3);
        CubeListBuilder $$9 = CubeListBuilder.create().texOffs(2, 13).addBox(-2.0f, 0.0f, 0.0f, 3.0f, 5.0f, 0.0f, $$3);
        $$2.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, $$9, PartPose.offset(-3.5f, 1.0f, -1.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_HIND_LEG, $$8, PartPose.offset(3.5f, 1.0f, -1.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, $$9, PartPose.offset(-3.5f, 1.0f, -8.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, $$8, PartPose.offset(3.5f, 1.0f, -8.0f));
        $$2.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(2, 19).addBox(0.0f, -3.0f, 0.0f, 0.0f, 5.0f, 12.0f), PartPose.offset(0.0f, 0.0f, 1.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(AxolotlRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.playingDeadFactor;
        float $$2 = $$0.inWaterFactor;
        float $$3 = $$0.onGroundFactor;
        float $$4 = $$0.movingFactor;
        float $$5 = 1.0f - $$4;
        float $$6 = 1.0f - Math.min($$3, $$4);
        this.body.yRot += $$0.yRot * 0.017453292f;
        setupSwimmingAnimation($$0.ageInTicks, $$0.xRot, Math.min($$4, $$2));
        setupWaterHoveringAnimation($$0.ageInTicks, Math.min($$5, $$2));
        setupGroundCrawlingAnimation($$0.ageInTicks, Math.min($$4, $$3));
        setupLayStillOnGroundAnimation($$0.ageInTicks, Math.min($$5, $$3));
        setupPlayDeadAnimation($$1);
        applyMirrorLegRotations($$6);
    }

    private void setupLayStillOnGroundAnimation(float $$0, float $$1) {
        if ($$1 <= 1.0E-5f) {
            return;
        }
        float $$2 = $$0 * 0.09f;
        float $$3 = Mth.sin($$2);
        float $$4 = Mth.cos($$2);
        float $$5 = ($$3 * $$3) - (2.0f * $$3);
        float $$6 = ($$4 * $$4) - (3.0f * $$3);
        this.head.xRot += (-0.09f) * $$5 * $$1;
        this.head.zRot += (-0.2f) * $$1;
        this.tail.yRot += ((-0.1f) + (0.1f * $$5)) * $$1;
        float $$7 = (0.6f + (0.05f * $$6)) * $$1;
        this.topGills.xRot += $$7;
        this.leftGills.yRot -= $$7;
        this.rightGills.yRot += $$7;
        this.leftHindLeg.xRot += 1.1f * $$1;
        this.leftHindLeg.yRot += 1.0f * $$1;
        this.leftFrontLeg.xRot += 0.8f * $$1;
        this.leftFrontLeg.yRot += 2.3f * $$1;
        this.leftFrontLeg.zRot -= 0.5f * $$1;
    }

    private void setupGroundCrawlingAnimation(float $$0, float $$1) {
        if ($$1 <= 1.0E-5f) {
            return;
        }
        float $$2 = $$0 * 0.11f;
        float $$3 = Mth.cos($$2);
        float $$4 = (($$3 * $$3) - (2.0f * $$3)) / 5.0f;
        float $$5 = 0.7f * $$3;
        float $$6 = 0.09f * $$3 * $$1;
        this.head.yRot += $$6;
        this.tail.yRot += $$6;
        float $$7 = (0.6f - (0.08f * (($$3 * $$3) + (2.0f * Mth.sin($$2))))) * $$1;
        this.topGills.xRot += $$7;
        this.leftGills.yRot -= $$7;
        this.rightGills.yRot += $$7;
        float $$8 = 0.9424779f * $$1;
        float $$9 = 1.0995574f * $$1;
        this.leftHindLeg.xRot += $$8;
        this.leftHindLeg.yRot += (1.5f - $$4) * $$1;
        this.leftHindLeg.zRot += (-0.1f) * $$1;
        this.leftFrontLeg.xRot += $$9;
        this.leftFrontLeg.yRot += (1.5707964f - $$5) * $$1;
        this.rightHindLeg.xRot += $$8;
        this.rightHindLeg.yRot += ((-1.0f) - $$4) * $$1;
        this.rightFrontLeg.xRot += $$9;
        this.rightFrontLeg.yRot += ((-1.5707964f) - $$5) * $$1;
    }

    private void setupWaterHoveringAnimation(float $$0, float $$1) {
        if ($$1 <= 1.0E-5f) {
            return;
        }
        float $$2 = $$0 * 0.075f;
        float $$3 = Mth.cos($$2);
        float $$4 = Mth.sin($$2) * 0.15f;
        float $$5 = ((-0.15f) + (0.075f * $$3)) * $$1;
        this.body.xRot += $$5;
        this.body.y -= $$4 * $$1;
        this.head.xRot -= $$5;
        this.topGills.xRot += 0.2f * $$3 * $$1;
        float $$6 = (((-0.3f) * $$3) - 0.19f) * $$1;
        this.leftGills.yRot += $$6;
        this.rightGills.yRot -= $$6;
        this.leftHindLeg.xRot += (2.3561945f - ($$3 * 0.11f)) * $$1;
        this.leftHindLeg.yRot += 0.47123894f * $$1;
        this.leftHindLeg.zRot += 1.7278761f * $$1;
        this.leftFrontLeg.xRot += (0.7853982f - ($$3 * 0.2f)) * $$1;
        this.leftFrontLeg.yRot += 2.042035f * $$1;
        this.tail.yRot += 0.5f * $$3 * $$1;
    }

    private void setupSwimmingAnimation(float $$0, float $$1, float $$2) {
        if ($$2 <= 1.0E-5f) {
            return;
        }
        float $$3 = $$0 * 0.33f;
        float $$4 = Mth.sin($$3);
        float $$5 = Mth.cos($$3);
        float $$6 = 0.13f * $$4;
        this.body.xRot += (($$1 * 0.017453292f) + $$6) * $$2;
        this.head.xRot -= ($$6 * 1.8f) * $$2;
        this.body.y -= (0.45f * $$5) * $$2;
        this.topGills.xRot += (((-0.5f) * $$4) - 0.8f) * $$2;
        float $$7 = ((0.3f * $$4) + 0.9f) * $$2;
        this.leftGills.yRot += $$7;
        this.rightGills.yRot -= $$7;
        this.tail.yRot += 0.3f * Mth.cos($$3 * 0.9f) * $$2;
        this.leftHindLeg.xRot += 1.8849558f * $$2;
        this.leftHindLeg.yRot += (-0.4f) * $$4 * $$2;
        this.leftHindLeg.zRot += 1.5707964f * $$2;
        this.leftFrontLeg.xRot += 1.8849558f * $$2;
        this.leftFrontLeg.yRot += (((-0.2f) * $$5) - 0.1f) * $$2;
        this.leftFrontLeg.zRot += 1.5707964f * $$2;
    }

    private void setupPlayDeadAnimation(float $$0) {
        if ($$0 <= 1.0E-5f) {
            return;
        }
        this.leftHindLeg.xRot += 1.4137167f * $$0;
        this.leftHindLeg.yRot += 1.0995574f * $$0;
        this.leftHindLeg.zRot += 0.7853982f * $$0;
        this.leftFrontLeg.xRot += 0.7853982f * $$0;
        this.leftFrontLeg.yRot += 2.042035f * $$0;
        this.body.xRot += (-0.15f) * $$0;
        this.body.zRot += 0.35f * $$0;
    }

    private void applyMirrorLegRotations(float $$0) {
        if ($$0 <= 1.0E-5f) {
            return;
        }
        this.rightHindLeg.xRot += this.leftHindLeg.xRot * $$0;
        this.rightHindLeg.yRot += (-this.leftHindLeg.yRot) * $$0;
        this.rightHindLeg.zRot += (-this.leftHindLeg.zRot) * $$0;
        this.rightFrontLeg.xRot += this.leftFrontLeg.xRot * $$0;
        this.rightFrontLeg.yRot += (-this.leftFrontLeg.yRot) * $$0;
        this.rightFrontLeg.zRot += (-this.leftFrontLeg.zRot) * $$0;
    }
}
