package net.minecraft.client.model.monster.shulker;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.ShulkerRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/shulker/ShulkerModel.class */
public class ShulkerModel extends EntityModel<ShulkerRenderState> {
    public static final String LID = "lid";
    private static final String BASE = "base";
    private final ModelPart lid;
    private final ModelPart head;

    public ShulkerModel(ModelPart $$0) {
        super($$0, RenderTypes::entityCutoutNoCullZOffset);
        this.lid = $$0.getChild(LID);
        this.head = $$0.getChild(PartNames.HEAD);
    }

    private static MeshDefinition createShellMesh() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -16.0f, -8.0f, 16.0f, 12.0f, 16.0f), PartPose.offset(0.0f, 24.0f, 0.0f));
        $$1.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 28).addBox(-8.0f, -8.0f, -8.0f, 16.0f, 8.0f, 16.0f), PartPose.offset(0.0f, 24.0f, 0.0f));
        return $$0;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = createShellMesh();
        $$0.getRoot().addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 52).addBox(-3.0f, 0.0f, -3.0f, 6.0f, 6.0f, 6.0f), PartPose.offset(0.0f, 12.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    public static LayerDefinition createBoxLayer() {
        MeshDefinition $$0 = createShellMesh();
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(ShulkerRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = (0.5f + $$0.peekAmount) * 3.1415927f;
        float $$2 = (-1.0f) + Mth.sin($$1);
        float $$3 = 0.0f;
        if ($$1 > 3.1415927f) {
            $$3 = Mth.sin($$0.ageInTicks * 0.1f) * 0.7f;
        }
        this.lid.setPos(0.0f, 16.0f + (Mth.sin($$1) * 8.0f) + $$3, 0.0f);
        if ($$0.peekAmount > 0.3f) {
            this.lid.yRot = $$2 * $$2 * $$2 * $$2 * 3.1415927f * 0.125f;
        } else {
            this.lid.yRot = 0.0f;
        }
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = (($$0.yHeadRot - 180.0f) - $$0.yBodyRot) * 0.017453292f;
    }
}
