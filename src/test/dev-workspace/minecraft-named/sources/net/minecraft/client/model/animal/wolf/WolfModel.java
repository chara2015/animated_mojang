package net.minecraft.client.model.animal.wolf;

import java.util.Set;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/wolf/WolfModel.class */
public class WolfModel extends EntityModel<WolfRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(Set.of(PartNames.HEAD));
    private static final String REAL_HEAD = "real_head";
    private static final String UPPER_BODY = "upper_body";
    private static final String REAL_TAIL = "real_tail";
    private final ModelPart head;
    private final ModelPart realHead;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    private final ModelPart realTail;
    private final ModelPart upperBody;
    private static final int LEG_SIZE = 8;

    public WolfModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.realHead = this.head.getChild(REAL_HEAD);
        this.body = $$0.getChild(PartNames.BODY);
        this.upperBody = $$0.getChild("upper_body");
        this.rightHindLeg = $$0.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = $$0.getChild(PartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = $$0.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = $$0.getChild(PartNames.LEFT_FRONT_LEG);
        this.tail = $$0.getChild(PartNames.TAIL);
        this.realTail = this.tail.getChild(REAL_TAIL);
    }

    public static MeshDefinition createMeshDefinition(CubeDeformation $$0) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.getRoot();
        PartDefinition $$4 = $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(-1.0f, 13.5f, -7.0f));
        $$4.addOrReplaceChild(REAL_HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0f, -3.0f, -2.0f, 6.0f, 6.0f, 4.0f, $$0).texOffs(16, 14).addBox(-2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f, $$0).texOffs(16, 14).addBox(2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f, $$0).texOffs(0, 10).addBox(-0.5f, -0.001f, -5.0f, 3.0f, 3.0f, 4.0f, $$0), PartPose.ZERO);
        $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(18, 14).addBox(-3.0f, -2.0f, -3.0f, 6.0f, 9.0f, 6.0f, $$0), PartPose.offsetAndRotation(0.0f, 14.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        $$2.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0f, -3.0f, -3.0f, 8.0f, 6.0f, 7.0f, $$0), PartPose.offsetAndRotation(-1.0f, 14.0f, -3.0f, 1.5707964f, 0.0f, 0.0f));
        CubeListBuilder $$5 = CubeListBuilder.create().texOffs(0, 18).addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, $$0);
        CubeListBuilder $$6 = CubeListBuilder.create().mirror().texOffs(0, 18).addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, $$0);
        $$2.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, $$6, PartPose.offset(-2.5f, 16.0f, 7.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_HIND_LEG, $$5, PartPose.offset(0.5f, 16.0f, 7.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, $$6, PartPose.offset(-2.5f, 16.0f, -4.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, $$5, PartPose.offset(0.5f, 16.0f, -4.0f));
        PartDefinition $$7 = $$2.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0f, 12.0f, 8.0f, 0.62831855f, 0.0f, 0.0f));
        $$7.addOrReplaceChild(REAL_TAIL, CubeListBuilder.create().texOffs(9, 18).addBox(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, $$0), PartPose.ZERO);
        return $$1;
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(WolfRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.walkAnimationPos;
        float $$2 = $$0.walkAnimationSpeed;
        if ($$0.isAngry) {
            this.tail.yRot = 0.0f;
        } else {
            this.tail.yRot = Mth.cos($$1 * 0.6662f) * 1.4f * $$2;
        }
        if ($$0.isSitting) {
            float $$3 = $$0.ageScale;
            this.upperBody.y += 2.0f * $$3;
            this.upperBody.xRot = 1.2566371f;
            this.upperBody.yRot = 0.0f;
            this.body.y += 4.0f * $$3;
            this.body.z -= 2.0f * $$3;
            this.body.xRot = 0.7853982f;
            this.tail.y += 9.0f * $$3;
            this.tail.z -= 2.0f * $$3;
            this.rightHindLeg.y += 6.7f * $$3;
            this.rightHindLeg.z -= 5.0f * $$3;
            this.rightHindLeg.xRot = 4.712389f;
            this.leftHindLeg.y += 6.7f * $$3;
            this.leftHindLeg.z -= 5.0f * $$3;
            this.leftHindLeg.xRot = 4.712389f;
            this.rightFrontLeg.xRot = 5.811947f;
            this.rightFrontLeg.x += 0.01f * $$3;
            this.rightFrontLeg.y += 1.0f * $$3;
            this.leftFrontLeg.xRot = 5.811947f;
            this.leftFrontLeg.x -= 0.01f * $$3;
            this.leftFrontLeg.y += 1.0f * $$3;
        } else {
            this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662f) * 1.4f * $$2;
            this.leftHindLeg.xRot = Mth.cos(($$1 * 0.6662f) + 3.1415927f) * 1.4f * $$2;
            this.rightFrontLeg.xRot = Mth.cos(($$1 * 0.6662f) + 3.1415927f) * 1.4f * $$2;
            this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662f) * 1.4f * $$2;
        }
        this.realHead.zRot = $$0.headRollAngle + $$0.getBodyRollAngle(0.0f);
        this.upperBody.zRot = $$0.getBodyRollAngle(-0.08f);
        this.body.zRot = $$0.getBodyRollAngle(-0.16f);
        this.realTail.zRot = $$0.getBodyRollAngle(-0.2f);
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.tail.xRot = $$0.tailAngle;
    }
}
