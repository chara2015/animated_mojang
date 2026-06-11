package net.minecraft.client.model.animal.camel;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.definitions.CamelAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.CamelRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/camel/CamelModel.class */
public class CamelModel extends EntityModel<CamelRenderState> {
    private static final float MAX_WALK_ANIMATION_SPEED = 2.0f;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5f;
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.45f);
    protected final ModelPart head;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sitPoseAnimation;
    private final KeyframeAnimation standupAnimation;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation dashAnimation;

    public CamelModel(ModelPart $$0) {
        super($$0);
        ModelPart $$1 = $$0.getChild(PartNames.BODY);
        this.head = $$1.getChild(PartNames.HEAD);
        this.walkAnimation = CamelAnimation.CAMEL_WALK.bake($$0);
        this.sitAnimation = CamelAnimation.CAMEL_SIT.bake($$0);
        this.sitPoseAnimation = CamelAnimation.CAMEL_SIT_POSE.bake($$0);
        this.standupAnimation = CamelAnimation.CAMEL_STANDUP.bake($$0);
        this.idleAnimation = CamelAnimation.CAMEL_IDLE.bake($$0);
        this.dashAnimation = CamelAnimation.CAMEL_DASH.bake($$0);
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(createBodyMesh(), 128, 128);
    }

    protected static MeshDefinition createBodyMesh() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 25).addBox(-7.5f, -12.0f, -23.5f, 15.0f, 12.0f, 27.0f), PartPose.offset(0.0f, 4.0f, 9.5f));
        $$2.addOrReplaceChild("hump", CubeListBuilder.create().texOffs(74, 0).addBox(-4.5f, -5.0f, -5.5f, 9.0f, 5.0f, 11.0f), PartPose.offset(0.0f, -12.0f, -10.0f));
        $$2.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(122, 0).addBox(-1.5f, 0.0f, 0.0f, 3.0f, 14.0f, 0.0f), PartPose.offset(0.0f, -9.0f, 3.5f));
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(60, 24).addBox(-3.5f, -7.0f, -15.0f, 7.0f, 8.0f, 19.0f).texOffs(21, 0).addBox(-3.5f, -21.0f, -15.0f, 7.0f, 14.0f, 7.0f).texOffs(50, 0).addBox(-2.5f, -21.0f, -21.0f, 5.0f, 5.0f, 6.0f), PartPose.offset(0.0f, -3.0f, -19.5f));
        $$3.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create().texOffs(45, 0).addBox(-0.5f, 0.5f, -1.0f, 3.0f, 1.0f, 2.0f), PartPose.offset(2.5f, -21.0f, -9.5f));
        $$3.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create().texOffs(67, 0).addBox(-2.5f, 0.5f, -1.0f, 3.0f, 1.0f, 2.0f), PartPose.offset(-2.5f, -21.0f, -9.5f));
        $$1.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(58, 16).addBox(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), PartPose.offset(4.9f, 1.0f, 9.5f));
        $$1.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(94, 16).addBox(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), PartPose.offset(-4.9f, 1.0f, 9.5f));
        $$1.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), PartPose.offset(4.9f, 1.0f, -10.5f));
        $$1.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 26).addBox(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), PartPose.offset(-4.9f, 1.0f, -10.5f));
        return $$0;
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(CamelRenderState $$0) {
        super.setupAnim($$0);
        applyHeadRotation($$0, $$0.yRot, $$0.xRot);
        this.walkAnimation.applyWalk($$0.walkAnimationPos, $$0.walkAnimationSpeed, 2.0f, 2.5f);
        this.sitAnimation.apply($$0.sitAnimationState, $$0.ageInTicks);
        this.sitPoseAnimation.apply($$0.sitPoseAnimationState, $$0.ageInTicks);
        this.standupAnimation.apply($$0.sitUpAnimationState, $$0.ageInTicks);
        this.idleAnimation.apply($$0.idleAnimationState, $$0.ageInTicks);
        this.dashAnimation.apply($$0.dashAnimationState, $$0.ageInTicks);
    }

    private void applyHeadRotation(CamelRenderState $$0, float $$1, float $$2) {
        float $$12 = Mth.clamp($$1, -30.0f, 30.0f);
        float $$22 = Mth.clamp($$2, -25.0f, 45.0f);
        if ($$0.jumpCooldown > 0.0f) {
            float $$3 = (45.0f * $$0.jumpCooldown) / 55.0f;
            $$22 = Mth.clamp($$22 + $$3, -25.0f, 70.0f);
        }
        this.head.yRot = $$12 * 0.017453292f;
        this.head.xRot = $$22 * 0.017453292f;
    }
}
