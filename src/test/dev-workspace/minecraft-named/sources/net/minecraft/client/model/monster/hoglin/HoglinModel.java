package net.minecraft.client.model.monster.hoglin;

import java.util.Set;
import net.minecraft.client.model.BabyModelTransform;
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
import net.minecraft.client.renderer.entity.state.HoglinRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/hoglin/HoglinModel.class */
public class HoglinModel extends EntityModel<HoglinRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 8.0f, 6.0f, 1.9f, 2.0f, 24.0f, Set.of(PartNames.HEAD));
    private static final float DEFAULT_HEAD_X_ROT = 0.87266463f;
    private static final float ATTACK_HEAD_X_ROT_END = -0.34906584f;
    private final ModelPart head;
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private final ModelPart body;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart mane;

    public HoglinModel(ModelPart $$0) {
        super($$0);
        this.body = $$0.getChild(PartNames.BODY);
        this.mane = this.body.getChild(PartNames.MANE);
        this.head = $$0.getChild(PartNames.HEAD);
        this.rightEar = this.head.getChild(PartNames.RIGHT_EAR);
        this.leftEar = this.head.getChild(PartNames.LEFT_EAR);
        this.rightFrontLeg = $$0.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = $$0.getChild(PartNames.LEFT_FRONT_LEG);
        this.rightHindLeg = $$0.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = $$0.getChild(PartNames.LEFT_HIND_LEG);
    }

    private static MeshDefinition createMesh() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(1, 1).addBox(-8.0f, -7.0f, -13.0f, 16.0f, 14.0f, 26.0f), PartPose.offset(0.0f, 7.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.MANE, CubeListBuilder.create().texOffs(90, 33).addBox(0.0f, 0.0f, -9.0f, 0.0f, 10.0f, 19.0f, new CubeDeformation(0.001f)), PartPose.offset(0.0f, -14.0f, -7.0f));
        PartDefinition $$3 = $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(61, 1).addBox(-7.0f, -3.0f, -19.0f, 14.0f, 6.0f, 19.0f), PartPose.offsetAndRotation(0.0f, 2.0f, -12.0f, DEFAULT_HEAD_X_ROT, 0.0f, 0.0f));
        $$3.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create().texOffs(1, 1).addBox(-6.0f, -1.0f, -2.0f, 6.0f, 1.0f, 4.0f), PartPose.offsetAndRotation(-6.0f, -2.0f, -3.0f, 0.0f, 0.0f, -0.6981317f));
        $$3.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create().texOffs(1, 6).addBox(0.0f, -1.0f, -2.0f, 6.0f, 1.0f, 4.0f), PartPose.offsetAndRotation(6.0f, -2.0f, -3.0f, 0.0f, 0.0f, 0.6981317f));
        $$3.addOrReplaceChild(PartNames.RIGHT_HORN, CubeListBuilder.create().texOffs(10, 13).addBox(-1.0f, -11.0f, -1.0f, 2.0f, 11.0f, 2.0f), PartPose.offset(-7.0f, 2.0f, -12.0f));
        $$3.addOrReplaceChild(PartNames.LEFT_HORN, CubeListBuilder.create().texOffs(1, 13).addBox(-1.0f, -11.0f, -1.0f, 2.0f, 11.0f, 2.0f), PartPose.offset(7.0f, 2.0f, -12.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(66, 42).addBox(-3.0f, 0.0f, -3.0f, 6.0f, 14.0f, 6.0f), PartPose.offset(-4.0f, 10.0f, -8.5f));
        $$1.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(41, 42).addBox(-3.0f, 0.0f, -3.0f, 6.0f, 14.0f, 6.0f), PartPose.offset(4.0f, 10.0f, -8.5f));
        $$1.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(21, 45).addBox(-2.5f, 0.0f, -2.5f, 5.0f, 11.0f, 5.0f), PartPose.offset(-5.0f, 13.0f, 10.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(0, 45).addBox(-2.5f, 0.0f, -2.5f, 5.0f, 11.0f, 5.0f), PartPose.offset(5.0f, 13.0f, 10.0f));
        return $$0;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = createMesh();
        return LayerDefinition.create($$0, 128, 64);
    }

    public static LayerDefinition createBabyLayer() {
        MeshDefinition $$0 = createMesh();
        PartDefinition $$1 = $$0.getRoot().getChild(PartNames.BODY);
        $$1.addOrReplaceChild(PartNames.MANE, CubeListBuilder.create().texOffs(90, 33).addBox(0.0f, 0.0f, -9.0f, 0.0f, 10.0f, 19.0f, new CubeDeformation(0.001f)), PartPose.offset(0.0f, -14.0f, -3.0f));
        return LayerDefinition.create($$0, 128, 64).apply(BABY_TRANSFORMER);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(HoglinRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.walkAnimationSpeed;
        float $$2 = $$0.walkAnimationPos;
        this.rightEar.zRot = (-0.6981317f) - ($$1 * Mth.sin($$2));
        this.leftEar.zRot = 0.6981317f + ($$1 * Mth.sin($$2));
        this.head.yRot = $$0.yRot * 0.017453292f;
        float $$3 = 1.0f - (Mth.abs(10 - (2 * $$0.attackAnimationRemainingTicks)) / 10.0f);
        this.head.xRot = Mth.lerp($$3, DEFAULT_HEAD_X_ROT, ATTACK_HEAD_X_ROT_END);
        if ($$0.isBaby) {
            this.head.y += $$3 * 2.5f;
        }
        this.rightFrontLeg.xRot = Mth.cos($$2) * 1.2f * $$1;
        this.leftFrontLeg.xRot = Mth.cos($$2 + 3.1415927f) * 1.2f * $$1;
        this.rightHindLeg.xRot = this.leftFrontLeg.xRot;
        this.leftHindLeg.xRot = this.rightFrontLeg.xRot;
    }
}
