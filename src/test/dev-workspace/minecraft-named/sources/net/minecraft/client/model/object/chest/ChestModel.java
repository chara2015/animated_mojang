package net.minecraft.client.model.object.chest;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/chest/ChestModel.class */
public class ChestModel extends Model<Float> {
    private static final String BOTTOM = "bottom";
    private static final String LID = "lid";
    private static final String LOCK = "lock";
    private final ModelPart lid;
    private final ModelPart lock;

    public ChestModel(ModelPart $$0) {
        super($$0, RenderTypes::entitySolid);
        this.lid = $$0.getChild("lid");
        this.lock = $$0.getChild("lock");
    }

    public static LayerDefinition createSingleBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(1.0f, 0.0f, 1.0f, 14.0f, 10.0f, 14.0f), PartPose.ZERO);
        $$1.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(1.0f, 0.0f, 0.0f, 14.0f, 5.0f, 14.0f), PartPose.offset(0.0f, 9.0f, 1.0f));
        $$1.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(0, 0).addBox(7.0f, -2.0f, 14.0f, 2.0f, 4.0f, 1.0f), PartPose.offset(0.0f, 9.0f, 1.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    public static LayerDefinition createDoubleBodyRightLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(1.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f), PartPose.ZERO);
        $$1.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(1.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f), PartPose.offset(0.0f, 9.0f, 1.0f));
        $$1.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(0, 0).addBox(15.0f, -2.0f, 14.0f, 1.0f, 4.0f, 1.0f), PartPose.offset(0.0f, 9.0f, 1.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    public static LayerDefinition createDoubleBodyLeftLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(0.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f), PartPose.ZERO);
        $$1.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(0.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f), PartPose.offset(0.0f, 9.0f, 1.0f));
        $$1.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(0, 0).addBox(0.0f, -2.0f, 14.0f, 1.0f, 4.0f, 1.0f), PartPose.offset(0.0f, 9.0f, 1.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(Float $$0) {
        super.setupAnim($$0);
        this.lid.xRot = -($$0.floatValue() * 1.5707964f);
        this.lock.xRot = this.lid.xRot;
    }
}
