package net.minecraft.client.model.object.projectile;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/projectile/WindChargeModel.class */
public class WindChargeModel extends EntityModel<EntityRenderState> {
    private static final int ROTATION_SPEED = 16;
    private final ModelPart bone;
    private final ModelPart windCharge;
    private final ModelPart wind;

    public WindChargeModel(ModelPart $$0) {
        super($$0, RenderTypes::entityTranslucent);
        this.bone = $$0.getChild(PartNames.BONE);
        this.wind = this.bone.getChild("wind");
        this.windCharge = this.bone.getChild("wind_charge");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BONE, CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
        $$2.addOrReplaceChild("wind", CubeListBuilder.create().texOffs(15, 20).addBox(-4.0f, -1.0f, -4.0f, 8.0f, 2.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(0, 9).addBox(-3.0f, -2.0f, -3.0f, 6.0f, 4.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, -0.7854f, 0.0f));
        $$2.addOrReplaceChild("wind_charge", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0f, -2.0f, -2.0f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(EntityRenderState $$0) {
        super.setupAnim($$0);
        this.windCharge.yRot = (-$$0.ageInTicks) * 16.0f * 0.017453292f;
        this.wind.yRot = $$0.ageInTicks * 16.0f * 0.017453292f;
    }
}
