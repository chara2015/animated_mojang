package net.minecraft.client.model.npc;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.VillagerLikeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/npc/VillagerModel.class */
public class VillagerModel extends EntityModel<VillagerRenderState> implements HeadedModel, VillagerLikeModel<VillagerRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.5f);
    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart arms;

    public VillagerModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.rightLeg = $$0.getChild(PartNames.RIGHT_LEG);
        this.leftLeg = $$0.getChild(PartNames.LEFT_LEG);
        this.arms = $$0.getChild(PartNames.ARMS);
    }

    public static MeshDefinition createBodyModel() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$3 = $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.ZERO);
        PartDefinition $$4 = $$3.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, new CubeDeformation(0.51f)), PartPose.ZERO);
        $$4.addOrReplaceChild(PartNames.HAT_RIM, CubeListBuilder.create().texOffs(30, 47).addBox(-8.0f, -8.0f, -6.0f, 16.0f, 16.0f, 1.0f), PartPose.rotation(-1.5707964f, 0.0f, 0.0f));
        $$3.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), PartPose.offset(0.0f, -2.0f, 0.0f));
        PartDefinition $$5 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f), PartPose.ZERO);
        $$5.addOrReplaceChild(PartNames.JACKET, CubeListBuilder.create().texOffs(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.ZERO);
        $$1.addOrReplaceChild(PartNames.ARMS, CubeListBuilder.create().texOffs(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).texOffs(44, 22).addBox(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, true).texOffs(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), PartPose.offsetAndRotation(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 22).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(-2.0f, 12.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(2.0f, 12.0f, 0.0f));
        return $$0;
    }

    public static MeshDefinition createNoHatModel() {
        MeshDefinition $$0 = createBodyModel();
        $$0.getRoot().clearChild(PartNames.HEAD).clearRecursively();
        return $$0;
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(VillagerRenderState $$0) {
        super.setupAnim($$0);
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.head.xRot = $$0.xRot * 0.017453292f;
        if ($$0.isUnhappy) {
            this.head.zRot = 0.3f * Mth.sin(0.45f * $$0.ageInTicks);
            this.head.xRot = 0.4f;
        } else {
            this.head.zRot = 0.0f;
        }
        this.rightLeg.xRot = Mth.cos($$0.walkAnimationPos * 0.6662f) * 1.4f * $$0.walkAnimationSpeed * 0.5f;
        this.leftLeg.xRot = Mth.cos(($$0.walkAnimationPos * 0.6662f) + 3.1415927f) * 1.4f * $$0.walkAnimationSpeed * 0.5f;
        this.rightLeg.yRot = 0.0f;
        this.leftLeg.yRot = 0.0f;
    }

    @Override // net.minecraft.client.model.HeadedModel
    public ModelPart getHead() {
        return this.head;
    }

    @Override // net.minecraft.client.model.VillagerLikeModel
    public void translateToArms(VillagerRenderState $$0, PoseStack $$1) {
        this.root.translateAndRotate($$1);
        this.arms.translateAndRotate($$1);
    }
}
