package net.minecraft.client.model.animal.sniffer;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.definitions.SnifferAnimation;
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
import net.minecraft.client.renderer.entity.state.SnifferRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/sniffer/SnifferModel.class */
public class SnifferModel extends EntityModel<SnifferRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.5f);
    private static final float WALK_ANIMATION_SPEED_MAX = 9.0f;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 100.0f;
    private final ModelPart head;
    private final KeyframeAnimation sniffSearchAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation digAnimation;
    private final KeyframeAnimation longSniffAnimation;
    private final KeyframeAnimation standUpAnimation;
    private final KeyframeAnimation happyAnimation;
    private final KeyframeAnimation sniffSniffAnimation;
    private final KeyframeAnimation babyTransform;

    public SnifferModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.BONE).getChild(PartNames.BODY).getChild(PartNames.HEAD);
        this.sniffSearchAnimation = SnifferAnimation.SNIFFER_SNIFF_SEARCH.bake($$0);
        this.walkAnimation = SnifferAnimation.SNIFFER_WALK.bake($$0);
        this.digAnimation = SnifferAnimation.SNIFFER_DIG.bake($$0);
        this.longSniffAnimation = SnifferAnimation.SNIFFER_LONGSNIFF.bake($$0);
        this.standUpAnimation = SnifferAnimation.SNIFFER_STAND_UP.bake($$0);
        this.happyAnimation = SnifferAnimation.SNIFFER_HAPPY.bake($$0);
        this.sniffSniffAnimation = SnifferAnimation.SNIFFER_SNIFFSNIFF.bake($$0);
        this.babyTransform = SnifferAnimation.BABY_TRANSFORM.bake($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BONE, CubeListBuilder.create(), PartPose.offset(0.0f, 5.0f, 0.0f));
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(62, 68).addBox(-12.5f, -14.0f, -20.0f, 25.0f, 29.0f, 40.0f, new CubeDeformation(0.0f)).texOffs(62, 0).addBox(-12.5f, -14.0f, -20.0f, 25.0f, 24.0f, 40.0f, new CubeDeformation(0.5f)).texOffs(87, 68).addBox(-12.5f, 12.0f, -20.0f, 25.0f, 0.0f, 40.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(32, 87).addBox(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(-7.5f, 10.0f, -15.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_MID_LEG, CubeListBuilder.create().texOffs(32, 105).addBox(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(-7.5f, 10.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(32, 123).addBox(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(-7.5f, 10.0f, 15.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 87).addBox(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(7.5f, 10.0f, -15.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_MID_LEG, CubeListBuilder.create().texOffs(0, 105).addBox(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(7.5f, 10.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(0, 123).addBox(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(7.5f, 10.0f, 15.0f));
        PartDefinition $$4 = $$3.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(8, 15).addBox(-6.5f, -7.5f, -11.5f, 13.0f, 18.0f, 11.0f, new CubeDeformation(0.0f)).texOffs(8, 4).addBox(-6.5f, 7.5f, -11.5f, 13.0f, 0.0f, 11.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 6.5f, -19.48f));
        $$4.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create().texOffs(2, 0).addBox(0.0f, 0.0f, -3.0f, 1.0f, 19.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offset(6.51f, -7.5f, -4.51f));
        $$4.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create().texOffs(48, 0).addBox(-1.0f, 0.0f, -3.0f, 1.0f, 19.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offset(-6.51f, -7.5f, -4.51f));
        $$4.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(10, 45).addBox(-6.5f, -2.0f, -9.0f, 13.0f, 2.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -4.5f, -11.5f));
        $$4.addOrReplaceChild("lower_beak", CubeListBuilder.create().texOffs(10, 57).addBox(-6.5f, -7.0f, -8.0f, 13.0f, 12.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 2.5f, -12.5f));
        return LayerDefinition.create($$0, 192, 192);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(SnifferRenderState $$0) {
        super.setupAnim($$0);
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = $$0.yRot * 0.017453292f;
        if ($$0.isSearching) {
            this.sniffSearchAnimation.applyWalk($$0.walkAnimationPos, $$0.walkAnimationSpeed, 9.0f, 100.0f);
        } else {
            this.walkAnimation.applyWalk($$0.walkAnimationPos, $$0.walkAnimationSpeed, 9.0f, 100.0f);
        }
        this.digAnimation.apply($$0.diggingAnimationState, $$0.ageInTicks);
        this.longSniffAnimation.apply($$0.sniffingAnimationState, $$0.ageInTicks);
        this.standUpAnimation.apply($$0.risingAnimationState, $$0.ageInTicks);
        this.happyAnimation.apply($$0.feelingHappyAnimationState, $$0.ageInTicks);
        this.sniffSniffAnimation.apply($$0.scentingAnimationState, $$0.ageInTicks);
        if ($$0.isBaby) {
            this.babyTransform.applyStatic();
        }
    }
}
