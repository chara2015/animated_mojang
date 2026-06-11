package net.minecraft.client.model.ambient;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.definitions.BatAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/ambient/BatModel.class */
public class BatModel extends EntityModel<BatRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightWingTip;
    private final ModelPart leftWingTip;
    private final ModelPart feet;
    private final KeyframeAnimation flyingAnimation;
    private final KeyframeAnimation restingAnimation;

    public BatModel(ModelPart $$0) {
        super($$0, RenderTypes::entityCutout);
        this.body = $$0.getChild(PartNames.BODY);
        this.head = $$0.getChild(PartNames.HEAD);
        this.rightWing = this.body.getChild(PartNames.RIGHT_WING);
        this.rightWingTip = this.rightWing.getChild(PartNames.RIGHT_WING_TIP);
        this.leftWing = this.body.getChild(PartNames.LEFT_WING);
        this.leftWingTip = this.leftWing.getChild(PartNames.LEFT_WING_TIP);
        this.feet = this.body.getChild(PartNames.FEET);
        this.flyingAnimation = BatAnimation.BAT_FLYING.bake($$0);
        this.restingAnimation = BatAnimation.BAT_RESTING.bake($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-1.5f, 0.0f, -1.0f, 3.0f, 5.0f, 2.0f), PartPose.offset(0.0f, 17.0f, 0.0f));
        PartDefinition $$3 = $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 7).addBox(-2.0f, -3.0f, -1.0f, 4.0f, 3.0f, 2.0f), PartPose.offset(0.0f, 17.0f, 0.0f));
        $$3.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create().texOffs(1, 15).addBox(-2.5f, -4.0f, 0.0f, 3.0f, 5.0f, 0.0f), PartPose.offset(-1.5f, -2.0f, 0.0f));
        $$3.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create().texOffs(8, 15).addBox(-0.1f, -3.0f, 0.0f, 3.0f, 5.0f, 0.0f), PartPose.offset(1.1f, -3.0f, 0.0f));
        PartDefinition $$4 = $$2.addOrReplaceChild(PartNames.RIGHT_WING, CubeListBuilder.create().texOffs(12, 0).addBox(-2.0f, -2.0f, 0.0f, 2.0f, 7.0f, 0.0f), PartPose.offset(-1.5f, 0.0f, 0.0f));
        $$4.addOrReplaceChild(PartNames.RIGHT_WING_TIP, CubeListBuilder.create().texOffs(16, 0).addBox(-6.0f, -2.0f, 0.0f, 6.0f, 8.0f, 0.0f), PartPose.offset(-2.0f, 0.0f, 0.0f));
        PartDefinition $$5 = $$2.addOrReplaceChild(PartNames.LEFT_WING, CubeListBuilder.create().texOffs(12, 7).addBox(0.0f, -2.0f, 0.0f, 2.0f, 7.0f, 0.0f), PartPose.offset(1.5f, 0.0f, 0.0f));
        $$5.addOrReplaceChild(PartNames.LEFT_WING_TIP, CubeListBuilder.create().texOffs(16, 8).addBox(0.0f, -2.0f, 0.0f, 6.0f, 8.0f, 0.0f), PartPose.offset(2.0f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.FEET, CubeListBuilder.create().texOffs(16, 16).addBox(-1.5f, 0.0f, 0.0f, 3.0f, 2.0f, 0.0f), PartPose.offset(0.0f, 5.0f, 0.0f));
        return LayerDefinition.create($$0, 32, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(BatRenderState $$0) {
        super.setupAnim($$0);
        if ($$0.isResting) {
            applyHeadRotation($$0.yRot);
        }
        this.flyingAnimation.apply($$0.flyAnimationState, $$0.ageInTicks);
        this.restingAnimation.apply($$0.restAnimationState, $$0.ageInTicks);
    }

    private void applyHeadRotation(float $$0) {
        this.head.yRot = $$0 * 0.017453292f;
    }
}
