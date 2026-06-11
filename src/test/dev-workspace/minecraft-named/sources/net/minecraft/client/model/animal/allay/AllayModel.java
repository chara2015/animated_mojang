package net.minecraft.client.model.animal.allay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.AllayRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionfc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/allay/AllayModel.class */
public class AllayModel extends EntityModel<AllayRenderState> implements ArmedModel<AllayRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private static final float FLYING_ANIMATION_X_ROT = 0.7853982f;
    private static final float MAX_HAND_HOLDING_ITEM_X_ROT_RAD = -1.134464f;
    private static final float MIN_HAND_HOLDING_ITEM_X_ROT_RAD = -1.0471976f;

    public AllayModel(ModelPart $$0) {
        super($$0.getChild("root"), RenderTypes::entityTranslucent);
        this.head = this.root.getChild(PartNames.HEAD);
        this.body = this.root.getChild(PartNames.BODY);
        this.right_arm = this.body.getChild(PartNames.RIGHT_ARM);
        this.left_arm = this.body.getChild(PartNames.LEFT_ARM);
        this.right_wing = this.body.getChild(PartNames.RIGHT_WING);
        this.left_wing = this.body.getChild(PartNames.LEFT_WING);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0f, 23.5f, 0.0f));
        $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-2.5f, -5.0f, -2.5f, 5.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -3.99f, 0.0f));
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 10).addBox(-1.5f, 0.0f, -1.0f, 3.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(0, 16).addBox(-1.5f, 0.0f, -1.0f, 3.0f, 5.0f, 2.0f, new CubeDeformation(-0.2f)), PartPose.offset(0.0f, -4.0f, 0.0f));
        $$3.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(23, 0).addBox(-0.75f, -0.5f, -1.0f, 1.0f, 4.0f, 2.0f, new CubeDeformation(-0.01f)), PartPose.offset(-1.75f, 0.5f, 0.0f));
        $$3.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(23, 6).addBox(-0.25f, -0.5f, -1.0f, 1.0f, 4.0f, 2.0f, new CubeDeformation(-0.01f)), PartPose.offset(1.75f, 0.5f, 0.0f));
        $$3.addOrReplaceChild(PartNames.RIGHT_WING, CubeListBuilder.create().texOffs(16, 14).addBox(0.0f, 1.0f, 0.0f, 0.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(-0.5f, 0.0f, 0.6f));
        $$3.addOrReplaceChild(PartNames.LEFT_WING, CubeListBuilder.create().texOffs(16, 14).addBox(0.0f, 1.0f, 0.0f, 0.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(0.5f, 0.0f, 0.6f));
        return LayerDefinition.create($$0, 32, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(AllayRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.walkAnimationSpeed;
        float $$2 = $$0.walkAnimationPos;
        float $$3 = ($$0.ageInTicks * 20.0f * 0.017453292f) + $$2;
        float $$4 = (Mth.cos($$3) * 3.1415927f * 0.15f) + $$1;
        float $$5 = $$0.ageInTicks * 9.0f * 0.017453292f;
        float $$6 = Math.min($$1 / 0.3f, 1.0f);
        float $$7 = 1.0f - $$6;
        float $$8 = $$0.holdingAnimationProgress;
        if ($$0.isDancing) {
            float $$9 = ($$0.ageInTicks * 8.0f * 0.017453292f) + $$1;
            float $$10 = Mth.cos($$9) * 16.0f * 0.017453292f;
            float $$11 = $$0.spinningProgress;
            float $$12 = Mth.cos($$9) * 14.0f * 0.017453292f;
            float $$13 = Mth.cos($$9) * 30.0f * 0.017453292f;
            this.root.yRot = $$0.isSpinning ? 12.566371f * $$11 : this.root.yRot;
            this.root.zRot = $$10 * (1.0f - $$11);
            this.head.yRot = $$13 * (1.0f - $$11);
            this.head.zRot = $$12 * (1.0f - $$11);
        } else {
            this.head.xRot = $$0.xRot * 0.017453292f;
            this.head.yRot = $$0.yRot * 0.017453292f;
        }
        this.right_wing.xRot = 0.43633232f * (1.0f - $$6);
        this.right_wing.yRot = (-0.7853982f) + $$4;
        this.left_wing.xRot = 0.43633232f * (1.0f - $$6);
        this.left_wing.yRot = FLYING_ANIMATION_X_ROT - $$4;
        this.body.xRot = $$6 * FLYING_ANIMATION_X_ROT;
        float $$14 = $$8 * Mth.lerp($$6, MIN_HAND_HOLDING_ITEM_X_ROT_RAD, MAX_HAND_HOLDING_ITEM_X_ROT_RAD);
        this.root.y += ((float) Math.cos($$5)) * 0.25f * $$7;
        this.right_arm.xRot = $$14;
        this.left_arm.xRot = $$14;
        float $$15 = $$7 * (1.0f - $$8);
        float $$16 = 0.43633232f - (((Mth.cos($$5 + 4.712389f) * 3.1415927f) * 0.075f) * $$15);
        this.left_arm.zRot = -$$16;
        this.right_arm.zRot = $$16;
        this.right_arm.yRot = 0.27925268f * $$8;
        this.left_arm.yRot = (-0.27925268f) * $$8;
    }

    @Override // net.minecraft.client.model.ArmedModel
    public void translateToHand(AllayRenderState $$0, HumanoidArm $$1, PoseStack $$2) {
        this.root.translateAndRotate($$2);
        this.body.translateAndRotate($$2);
        $$2.translate(0.0f, 0.0625f, 0.1875f);
        $$2.mulPose((Quaternionfc) Axis.XP.rotation(this.right_arm.xRot));
        $$2.scale(0.7f, 0.7f, 0.7f);
        $$2.translate(0.0625f, 0.0f, 0.0f);
    }
}
