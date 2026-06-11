package net.minecraft.client.model.animal.dolphin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.DolphinRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/dolphin/DolphinModel.class */
public class DolphinModel extends EntityModel<DolphinRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.5f);
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart tailFin;

    public DolphinModel(ModelPart $$0) {
        super($$0);
        this.body = $$0.getChild(PartNames.BODY);
        this.tail = this.body.getChild(PartNames.TAIL);
        this.tailFin = this.tail.getChild(PartNames.TAIL_FIN);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$4 = $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(22, 0).addBox(-4.0f, -7.0f, 0.0f, 8.0f, 7.0f, 13.0f), PartPose.offset(0.0f, 22.0f, -5.0f));
        $$4.addOrReplaceChild(PartNames.BACK_FIN, CubeListBuilder.create().texOffs(51, 0).addBox(-0.5f, 0.0f, 8.0f, 1.0f, 4.0f, 5.0f), PartPose.rotation(1.0471976f, 0.0f, 0.0f));
        $$4.addOrReplaceChild(PartNames.LEFT_FIN, CubeListBuilder.create().texOffs(48, 20).mirror().addBox(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f), PartPose.offsetAndRotation(2.0f, -2.0f, 4.0f, 1.0471976f, 0.0f, 2.0943952f));
        $$4.addOrReplaceChild(PartNames.RIGHT_FIN, CubeListBuilder.create().texOffs(48, 20).addBox(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f), PartPose.offsetAndRotation(-2.0f, -2.0f, 4.0f, 1.0471976f, 0.0f, -2.0943952f));
        PartDefinition $$5 = $$4.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(0, 19).addBox(-2.0f, -2.5f, 0.0f, 4.0f, 5.0f, 11.0f), PartPose.offsetAndRotation(0.0f, -2.5f, 11.0f, -0.10471976f, 0.0f, 0.0f));
        $$5.addOrReplaceChild(PartNames.TAIL_FIN, CubeListBuilder.create().texOffs(19, 20).addBox(-5.0f, -0.5f, 0.0f, 10.0f, 1.0f, 6.0f), PartPose.offset(0.0f, 0.0f, 9.0f));
        PartDefinition $$6 = $$4.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -3.0f, -3.0f, 8.0f, 7.0f, 6.0f), PartPose.offset(0.0f, -4.0f, -3.0f));
        $$6.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(0, 13).addBox(-1.0f, 2.0f, -7.0f, 2.0f, 2.0f, 4.0f), PartPose.ZERO);
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(DolphinRenderState $$0) {
        super.setupAnim($$0);
        this.body.xRot = $$0.xRot * 0.017453292f;
        this.body.yRot = $$0.yRot * 0.017453292f;
        if ($$0.isMoving) {
            this.body.xRot += (-0.05f) - (0.05f * Mth.cos($$0.ageInTicks * 0.3f));
            this.tail.xRot = (-0.1f) * Mth.cos($$0.ageInTicks * 0.3f);
            this.tailFin.xRot = (-0.2f) * Mth.cos($$0.ageInTicks * 0.3f);
        }
    }
}
