package net.minecraft.client.model.animal.feline;

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
import net.minecraft.client.renderer.entity.state.FelineRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/feline/FelineModel.class */
public class FelineModel<T extends FelineRenderState> extends EntityModel<T> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 10.0f, 4.0f, Set.of(PartNames.HEAD));
    private static final float XO = 0.0f;
    private static final float YO = 16.0f;
    private static final float ZO = -9.0f;
    protected static final float BACK_LEG_Y = 18.0f;
    protected static final float BACK_LEG_Z = 5.0f;
    protected static final float FRONT_LEG_Y = 14.1f;
    private static final float FRONT_LEG_Z = -5.0f;
    private static final String TAIL_1 = "tail1";
    private static final String TAIL_2 = "tail2";
    protected final ModelPart leftHindLeg;
    protected final ModelPart rightHindLeg;
    protected final ModelPart leftFrontLeg;
    protected final ModelPart rightFrontLeg;
    protected final ModelPart tail1;
    protected final ModelPart tail2;
    protected final ModelPart head;
    protected final ModelPart body;

    public FelineModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.body = $$0.getChild(PartNames.BODY);
        this.tail1 = $$0.getChild(TAIL_1);
        this.tail2 = $$0.getChild(TAIL_2);
        this.leftHindLeg = $$0.getChild(PartNames.LEFT_HIND_LEG);
        this.rightHindLeg = $$0.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftFrontLeg = $$0.getChild(PartNames.LEFT_FRONT_LEG);
        this.rightFrontLeg = $$0.getChild(PartNames.RIGHT_FRONT_LEG);
    }

    public static MeshDefinition createBodyMesh(CubeDeformation $$0) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.getRoot();
        CubeDeformation $$3 = new CubeDeformation(-0.02f);
        $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().addBox("main", -2.5f, -2.0f, -3.0f, 5.0f, 4.0f, 5.0f, $$0).addBox(PartNames.NOSE, -1.5f, -0.001f, -4.0f, 3, 2, 2, $$0, 0, 24).addBox("ear1", -2.0f, -3.0f, 0.0f, 1, 1, 2, $$0, 0, 10).addBox("ear2", 1.0f, -3.0f, 0.0f, 1, 1, 2, $$0, 6, 10), PartPose.offset(0.0f, 15.0f, ZO));
        $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(20, 0).addBox(-2.0f, 3.0f, -8.0f, 4.0f, 16.0f, 6.0f, $$0), PartPose.offsetAndRotation(0.0f, 12.0f, -10.0f, 1.5707964f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(TAIL_1, CubeListBuilder.create().texOffs(0, 15).addBox(-0.5f, 0.0f, 0.0f, 1.0f, 8.0f, 1.0f, $$0), PartPose.offsetAndRotation(0.0f, 15.0f, 8.0f, 0.9f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(TAIL_2, CubeListBuilder.create().texOffs(4, 15).addBox(-0.5f, 0.0f, 0.0f, 1.0f, 8.0f, 1.0f, $$3), PartPose.offset(0.0f, 20.0f, 14.0f));
        CubeListBuilder $$4 = CubeListBuilder.create().texOffs(8, 13).addBox(-1.0f, 0.0f, 1.0f, 2.0f, 6.0f, 2.0f, $$0);
        $$2.addOrReplaceChild(PartNames.LEFT_HIND_LEG, $$4, PartPose.offset(1.1f, BACK_LEG_Y, 5.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, $$4, PartPose.offset(-1.1f, BACK_LEG_Y, 5.0f));
        CubeListBuilder $$5 = CubeListBuilder.create().texOffs(40, 0).addBox(-1.0f, 0.0f, 0.0f, 2.0f, 10.0f, 2.0f, $$0);
        $$2.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, $$5, PartPose.offset(1.2f, FRONT_LEG_Y, FRONT_LEG_Z));
        $$2.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, $$5, PartPose.offset(-1.2f, FRONT_LEG_Y, FRONT_LEG_Z));
        return $$1;
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(T $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.ageScale;
        if ($$0.isCrouching) {
            this.body.y += 1.0f * $$1;
            this.head.y += 2.0f * $$1;
            this.tail1.y += 1.0f * $$1;
            this.tail2.y += (-4.0f) * $$1;
            this.tail2.z += 2.0f * $$1;
            this.tail1.xRot = 1.5707964f;
            this.tail2.xRot = 1.5707964f;
        } else if ($$0.isSprinting) {
            this.tail2.y = this.tail1.y;
            this.tail2.z += 2.0f * $$1;
            this.tail1.xRot = 1.5707964f;
            this.tail2.xRot = 1.5707964f;
        }
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = $$0.yRot * 0.017453292f;
        if (!$$0.isSitting) {
            this.body.xRot = 1.5707964f;
            float $$2 = $$0.walkAnimationSpeed;
            float $$3 = $$0.walkAnimationPos;
            if ($$0.isSprinting) {
                this.leftHindLeg.xRot = Mth.cos($$3 * 0.6662f) * $$2;
                this.rightHindLeg.xRot = Mth.cos(($$3 * 0.6662f) + 0.3f) * $$2;
                this.leftFrontLeg.xRot = Mth.cos(($$3 * 0.6662f) + 3.1415927f + 0.3f) * $$2;
                this.rightFrontLeg.xRot = Mth.cos(($$3 * 0.6662f) + 3.1415927f) * $$2;
                this.tail2.xRot = 1.7278761f + (0.31415927f * Mth.cos($$3) * $$2);
            } else {
                this.leftHindLeg.xRot = Mth.cos($$3 * 0.6662f) * $$2;
                this.rightHindLeg.xRot = Mth.cos(($$3 * 0.6662f) + 3.1415927f) * $$2;
                this.leftFrontLeg.xRot = Mth.cos(($$3 * 0.6662f) + 3.1415927f) * $$2;
                this.rightFrontLeg.xRot = Mth.cos($$3 * 0.6662f) * $$2;
                if (!$$0.isCrouching) {
                    this.tail2.xRot = 1.7278761f + (0.7853982f * Mth.cos($$3) * $$2);
                } else {
                    this.tail2.xRot = 1.7278761f + (0.47123894f * Mth.cos($$3) * $$2);
                }
            }
        }
        if ($$0.isSitting) {
            this.body.xRot = 0.7853982f;
            this.body.y += (-4.0f) * $$1;
            this.body.z += 5.0f * $$1;
            this.head.y += (-3.3f) * $$1;
            this.head.z += 1.0f * $$1;
            this.tail1.y += 8.0f * $$1;
            this.tail1.z += (-2.0f) * $$1;
            this.tail2.y += 2.0f * $$1;
            this.tail2.z += (-0.8f) * $$1;
            this.tail1.xRot = 1.7278761f;
            this.tail2.xRot = 2.670354f;
            this.leftFrontLeg.xRot = -0.15707964f;
            this.leftFrontLeg.y += 2.0f * $$1;
            this.leftFrontLeg.z -= 2.0f * $$1;
            this.rightFrontLeg.xRot = -0.15707964f;
            this.rightFrontLeg.y += 2.0f * $$1;
            this.rightFrontLeg.z -= 2.0f * $$1;
            this.leftHindLeg.xRot = -1.5707964f;
            this.leftHindLeg.y += 3.0f * $$1;
            this.leftHindLeg.z -= 4.0f * $$1;
            this.rightHindLeg.xRot = -1.5707964f;
            this.rightHindLeg.y += 3.0f * $$1;
            this.rightHindLeg.z -= 4.0f * $$1;
        }
        if ($$0.lieDownAmount > 0.0f) {
            this.head.zRot = Mth.rotLerp($$0.lieDownAmount, this.head.zRot, -1.2707963f);
            this.head.yRot = Mth.rotLerp($$0.lieDownAmount, this.head.yRot, 1.2707963f);
            this.leftFrontLeg.xRot = -1.2707963f;
            this.rightFrontLeg.xRot = -0.47079635f;
            this.rightFrontLeg.zRot = -0.2f;
            this.rightFrontLeg.x += $$1;
            this.leftHindLeg.xRot = -0.4f;
            this.rightHindLeg.xRot = 0.5f;
            this.rightHindLeg.zRot = -0.5f;
            this.rightHindLeg.x += 0.8f * $$1;
            this.rightHindLeg.y += 2.0f * $$1;
            this.tail1.xRot = Mth.rotLerp($$0.lieDownAmountTail, this.tail1.xRot, 0.8f);
            this.tail2.xRot = Mth.rotLerp($$0.lieDownAmountTail, this.tail2.xRot, -0.4f);
        }
        if ($$0.relaxStateOneAmount > 0.0f) {
            this.head.xRot = Mth.rotLerp($$0.relaxStateOneAmount, this.head.xRot, -0.58177644f);
        }
    }
}
