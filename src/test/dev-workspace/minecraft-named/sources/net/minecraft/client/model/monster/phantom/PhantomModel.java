package net.minecraft.client.model.monster.phantom;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.PhantomRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/phantom/PhantomModel.class */
public class PhantomModel extends EntityModel<PhantomRenderState> {
    private static final String TAIL_BASE = "tail_base";
    private static final String TAIL_TIP = "tail_tip";
    private final ModelPart leftWingBase;
    private final ModelPart leftWingTip;
    private final ModelPart rightWingBase;
    private final ModelPart rightWingTip;
    private final ModelPart tailBase;
    private final ModelPart tailTip;

    public PhantomModel(ModelPart $$0) {
        super($$0);
        ModelPart $$1 = $$0.getChild(PartNames.BODY);
        this.tailBase = $$1.getChild(TAIL_BASE);
        this.tailTip = this.tailBase.getChild(TAIL_TIP);
        this.leftWingBase = $$1.getChild(PartNames.LEFT_WING_BASE);
        this.leftWingTip = this.leftWingBase.getChild(PartNames.LEFT_WING_TIP);
        this.rightWingBase = $$1.getChild(PartNames.RIGHT_WING_BASE);
        this.rightWingTip = this.rightWingBase.getChild(PartNames.RIGHT_WING_TIP);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 8).addBox(-3.0f, -2.0f, -8.0f, 5.0f, 3.0f, 9.0f), PartPose.rotation(-0.1f, 0.0f, 0.0f));
        PartDefinition $$3 = $$2.addOrReplaceChild(TAIL_BASE, CubeListBuilder.create().texOffs(3, 20).addBox(-2.0f, 0.0f, 0.0f, 3.0f, 2.0f, 6.0f), PartPose.offset(0.0f, -2.0f, 1.0f));
        $$3.addOrReplaceChild(TAIL_TIP, CubeListBuilder.create().texOffs(4, 29).addBox(-1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 6.0f), PartPose.offset(0.0f, 0.5f, 6.0f));
        PartDefinition $$4 = $$2.addOrReplaceChild(PartNames.LEFT_WING_BASE, CubeListBuilder.create().texOffs(23, 12).addBox(0.0f, 0.0f, 0.0f, 6.0f, 2.0f, 9.0f), PartPose.offsetAndRotation(2.0f, -2.0f, -8.0f, 0.0f, 0.0f, 0.1f));
        $$4.addOrReplaceChild(PartNames.LEFT_WING_TIP, CubeListBuilder.create().texOffs(16, 24).addBox(0.0f, 0.0f, 0.0f, 13.0f, 1.0f, 9.0f), PartPose.offsetAndRotation(6.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f));
        PartDefinition $$5 = $$2.addOrReplaceChild(PartNames.RIGHT_WING_BASE, CubeListBuilder.create().texOffs(23, 12).mirror().addBox(-6.0f, 0.0f, 0.0f, 6.0f, 2.0f, 9.0f), PartPose.offsetAndRotation(-3.0f, -2.0f, -8.0f, 0.0f, 0.0f, -0.1f));
        $$5.addOrReplaceChild(PartNames.RIGHT_WING_TIP, CubeListBuilder.create().texOffs(16, 24).mirror().addBox(-13.0f, 0.0f, 0.0f, 13.0f, 1.0f, 9.0f), PartPose.offsetAndRotation(-6.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.1f));
        $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -2.0f, -5.0f, 7.0f, 3.0f, 5.0f), PartPose.offsetAndRotation(0.0f, 1.0f, -7.0f, 0.2f, 0.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(PhantomRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.flapTime * 7.448451f * 0.017453292f;
        this.leftWingBase.zRot = Mth.cos($$1) * 16.0f * 0.017453292f;
        this.leftWingTip.zRot = Mth.cos($$1) * 16.0f * 0.017453292f;
        this.rightWingBase.zRot = -this.leftWingBase.zRot;
        this.rightWingTip.zRot = -this.leftWingTip.zRot;
        this.tailBase.xRot = (-(5.0f + (Mth.cos($$1 * 2.0f) * 5.0f))) * 0.017453292f;
        this.tailTip.xRot = (-(5.0f + (Mth.cos($$1 * 2.0f) * 5.0f))) * 0.017453292f;
    }
}
