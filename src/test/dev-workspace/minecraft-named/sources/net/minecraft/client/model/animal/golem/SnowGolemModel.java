package net.minecraft.client.model.animal.golem;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/golem/SnowGolemModel.class */
public class SnowGolemModel extends EntityModel<LivingEntityRenderState> {
    private static final String UPPER_BODY = "upper_body";
    private final ModelPart upperBody;
    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public SnowGolemModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.leftArm = $$0.getChild(PartNames.LEFT_ARM);
        this.rightArm = $$0.getChild(PartNames.RIGHT_ARM);
        this.upperBody = $$0.getChild("upper_body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        CubeDeformation $$3 = new CubeDeformation(-0.5f);
        $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, $$3), PartPose.offset(0.0f, 4.0f, 0.0f));
        CubeListBuilder $$4 = CubeListBuilder.create().texOffs(32, 0).addBox(-1.0f, 0.0f, -1.0f, 12.0f, 2.0f, 2.0f, $$3);
        $$1.addOrReplaceChild(PartNames.LEFT_ARM, $$4, PartPose.offsetAndRotation(5.0f, 6.0f, 1.0f, 0.0f, 0.0f, 1.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_ARM, $$4, PartPose.offsetAndRotation(-5.0f, 6.0f, -1.0f, 0.0f, 3.1415927f, -1.0f));
        $$1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0f, -10.0f, -5.0f, 10.0f, 10.0f, 10.0f, $$3), PartPose.offset(0.0f, 13.0f, 0.0f));
        $$1.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(0, 36).addBox(-6.0f, -12.0f, -6.0f, 12.0f, 12.0f, 12.0f, $$3), PartPose.offset(0.0f, 24.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(LivingEntityRenderState $$0) {
        super.setupAnim($$0);
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.upperBody.yRot = $$0.yRot * 0.017453292f * 0.25f;
        float $$1 = Mth.sin(this.upperBody.yRot);
        float $$2 = Mth.cos(this.upperBody.yRot);
        this.leftArm.yRot = this.upperBody.yRot;
        this.rightArm.yRot = this.upperBody.yRot + 3.1415927f;
        this.leftArm.x = $$2 * 5.0f;
        this.leftArm.z = (-$$1) * 5.0f;
        this.rightArm.x = (-$$2) * 5.0f;
        this.rightArm.z = $$1 * 5.0f;
    }

    public ModelPart getHead() {
        return this.head;
    }
}
