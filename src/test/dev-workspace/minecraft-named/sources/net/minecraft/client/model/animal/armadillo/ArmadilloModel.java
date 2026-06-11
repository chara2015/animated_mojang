package net.minecraft.client.model.animal.armadillo;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.definitions.ArmadilloAnimation;
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
import net.minecraft.client.renderer.entity.state.ArmadilloRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/armadillo/ArmadilloModel.class */
public class ArmadilloModel extends EntityModel<ArmadilloRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.6f);
    private static final float MAX_DOWN_HEAD_ROTATION_EXTENT = 25.0f;
    private static final float MAX_UP_HEAD_ROTATION_EXTENT = 22.5f;
    private static final float MAX_WALK_ANIMATION_SPEED = 16.5f;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5f;
    private static final String HEAD_CUBE = "head_cube";
    private static final String RIGHT_EAR_CUBE = "right_ear_cube";
    private static final String LEFT_EAR_CUBE = "left_ear_cube";
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart cube;
    private final ModelPart head;
    private final ModelPart tail;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation rollOutAnimation;
    private final KeyframeAnimation rollUpAnimation;
    private final KeyframeAnimation peekAnimation;

    public ArmadilloModel(ModelPart $$0) {
        super($$0);
        this.body = $$0.getChild(PartNames.BODY);
        this.rightHindLeg = $$0.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = $$0.getChild(PartNames.LEFT_HIND_LEG);
        this.head = this.body.getChild(PartNames.HEAD);
        this.tail = this.body.getChild(PartNames.TAIL);
        this.cube = $$0.getChild(PartNames.CUBE);
        this.walkAnimation = ArmadilloAnimation.ARMADILLO_WALK.bake($$0);
        this.rollOutAnimation = ArmadilloAnimation.ARMADILLO_ROLL_OUT.bake($$0);
        this.rollUpAnimation = ArmadilloAnimation.ARMADILLO_ROLL_UP.bake($$0);
        this.peekAnimation = ArmadilloAnimation.ARMADILLO_PEEK.bake($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 20).addBox(-4.0f, -7.0f, -10.0f, 8.0f, 8.0f, 12.0f, new CubeDeformation(0.3f)).texOffs(0, 40).addBox(-4.0f, -7.0f, -10.0f, 8.0f, 8.0f, 12.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 21.0f, 4.0f));
        $$2.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(44, 53).addBox(-0.5f, -0.0865f, 0.0933f, 1.0f, 6.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -3.0f, 1.0f, 0.5061f, 0.0f, 0.0f));
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0f, -2.0f, -11.0f));
        $$3.addOrReplaceChild(HEAD_CUBE, CubeListBuilder.create().texOffs(43, 15).addBox(-1.5f, -1.0f, -1.0f, 3.0f, 5.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, -0.3927f, 0.0f, 0.0f));
        PartDefinition $$4 = $$3.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create(), PartPose.offset(-1.0f, -1.0f, 0.0f));
        $$4.addOrReplaceChild(RIGHT_EAR_CUBE, CubeListBuilder.create().texOffs(43, 10).addBox(-2.0f, -3.0f, 0.0f, 2.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, -0.6f, 0.1886f, -0.3864f, -0.0718f));
        PartDefinition $$5 = $$3.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create(), PartPose.offset(1.0f, -2.0f, 0.0f));
        $$5.addOrReplaceChild(LEFT_EAR_CUBE, CubeListBuilder.create().texOffs(47, 10).addBox(0.0f, -3.0f, 0.0f, 2.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.5f, 1.0f, -0.6f, 0.1886f, 0.3864f, 0.0718f));
        $$1.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(51, 31).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(-2.0f, 21.0f, 4.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(42, 31).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(2.0f, 21.0f, 4.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(51, 43).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(-2.0f, 21.0f, -4.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(42, 43).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(2.0f, 21.0f, -4.0f));
        $$1.addOrReplaceChild(PartNames.CUBE, CubeListBuilder.create().texOffs(0, 0).addBox(-5.0f, -10.0f, -6.0f, 10.0f, 10.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 24.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(ArmadilloRenderState $$0) {
        super.setupAnim($$0);
        if ($$0.isHidingInShell) {
            this.body.skipDraw = true;
            this.leftHindLeg.visible = false;
            this.rightHindLeg.visible = false;
            this.tail.visible = false;
            this.cube.visible = true;
        } else {
            this.body.skipDraw = false;
            this.leftHindLeg.visible = true;
            this.rightHindLeg.visible = true;
            this.tail.visible = true;
            this.cube.visible = false;
            this.head.xRot = Mth.clamp($$0.xRot, -22.5f, 25.0f) * 0.017453292f;
            this.head.yRot = Mth.clamp($$0.yRot, -32.5f, 32.5f) * 0.017453292f;
        }
        this.walkAnimation.applyWalk($$0.walkAnimationPos, $$0.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, 2.5f);
        this.rollOutAnimation.apply($$0.rollOutAnimationState, $$0.ageInTicks);
        this.rollUpAnimation.apply($$0.rollUpAnimationState, $$0.ageInTicks);
        this.peekAnimation.apply($$0.peekAnimationState, $$0.ageInTicks);
    }
}
