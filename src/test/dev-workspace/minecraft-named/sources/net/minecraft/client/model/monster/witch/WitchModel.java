package net.minecraft.client.model.monster.witch;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.VillagerLikeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.npc.VillagerModel;
import net.minecraft.client.renderer.entity.state.WitchRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/witch/WitchModel.class */
public class WitchModel extends EntityModel<WitchRenderState> implements HeadedModel, VillagerLikeModel<WitchRenderState> {
    protected final ModelPart nose;
    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart arms;

    public WitchModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.nose = this.head.getChild(PartNames.NOSE);
        this.rightLeg = $$0.getChild(PartNames.RIGHT_LEG);
        this.leftLeg = $$0.getChild(PartNames.LEFT_LEG);
        this.arms = $$0.getChild(PartNames.ARMS);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = VillagerModel.createBodyModel();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.ZERO);
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 64).addBox(0.0f, 0.0f, 0.0f, 10.0f, 2.0f, 10.0f), PartPose.offset(-5.0f, -10.03125f, -5.0f));
        PartDefinition $$4 = $$3.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(0, 76).addBox(0.0f, 0.0f, 0.0f, 7.0f, 4.0f, 7.0f), PartPose.offsetAndRotation(1.75f, -4.0f, 2.0f, -0.05235988f, 0.0f, 0.02617994f));
        PartDefinition $$5 = $$4.addOrReplaceChild("hat3", CubeListBuilder.create().texOffs(0, 87).addBox(0.0f, 0.0f, 0.0f, 4.0f, 4.0f, 4.0f), PartPose.offsetAndRotation(1.75f, -4.0f, 2.0f, -0.10471976f, 0.0f, 0.05235988f));
        $$5.addOrReplaceChild("hat4", CubeListBuilder.create().texOffs(0, 95).addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f, new CubeDeformation(0.25f)), PartPose.offsetAndRotation(1.75f, -2.0f, 2.0f, -0.20943952f, 0.0f, 0.10471976f));
        PartDefinition $$6 = $$2.getChild(PartNames.NOSE);
        $$6.addOrReplaceChild("mole", CubeListBuilder.create().texOffs(0, 0).addBox(0.0f, 3.0f, -6.75f, 1.0f, 1.0f, 1.0f, new CubeDeformation(-0.25f)), PartPose.offset(0.0f, -2.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 128);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(WitchRenderState $$0) {
        super.setupAnim($$0);
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.rightLeg.xRot = Mth.cos($$0.walkAnimationPos * 0.6662f) * 1.4f * $$0.walkAnimationSpeed * 0.5f;
        this.leftLeg.xRot = Mth.cos(($$0.walkAnimationPos * 0.6662f) + 3.1415927f) * 1.4f * $$0.walkAnimationSpeed * 0.5f;
        float $$1 = 0.01f * ($$0.entityId % 10);
        this.nose.xRot = Mth.sin($$0.ageInTicks * $$1) * 4.5f * 0.017453292f;
        this.nose.zRot = Mth.cos($$0.ageInTicks * $$1) * 2.5f * 0.017453292f;
        if ($$0.isHoldingItem) {
            this.nose.setPos(0.0f, 1.0f, -1.5f);
            this.nose.xRot = -0.9f;
        }
    }

    public ModelPart getNose() {
        return this.nose;
    }

    @Override // net.minecraft.client.model.HeadedModel
    public ModelPart getHead() {
        return this.head;
    }

    @Override // net.minecraft.client.model.VillagerLikeModel
    public void translateToArms(WitchRenderState $$0, PoseStack $$1) {
        this.root.translateAndRotate($$1);
        this.arms.translateAndRotate($$1);
    }
}
