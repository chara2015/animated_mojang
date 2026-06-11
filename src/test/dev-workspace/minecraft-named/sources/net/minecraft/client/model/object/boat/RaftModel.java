package net.minecraft.client.model.object.boat;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/boat/RaftModel.class */
public class RaftModel extends AbstractBoatModel {
    public RaftModel(ModelPart $$0) {
        super($$0);
    }

    private static void addCommonParts(PartDefinition $$0) {
        $$0.addOrReplaceChild(PartNames.BOTTOM, CubeListBuilder.create().texOffs(0, 0).addBox(-14.0f, -11.0f, -4.0f, 28.0f, 20.0f, 4.0f).texOffs(0, 0).addBox(-14.0f, -9.0f, -8.0f, 28.0f, 16.0f, 4.0f), PartPose.offsetAndRotation(0.0f, -2.1f, 1.0f, 1.5708f, 0.0f, 0.0f));
        $$0.addOrReplaceChild(PartNames.LEFT_PADDLE, CubeListBuilder.create().texOffs(0, 24).addBox(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f).addBox(-1.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f), PartPose.offsetAndRotation(3.0f, -4.0f, 9.0f, 0.0f, 0.0f, 0.19634955f));
        $$0.addOrReplaceChild(PartNames.RIGHT_PADDLE, CubeListBuilder.create().texOffs(40, 24).addBox(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f).addBox(0.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f), PartPose.offsetAndRotation(3.0f, -4.0f, -9.0f, 0.0f, 3.1415927f, 0.19634955f));
    }

    public static LayerDefinition createRaftModel() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        addCommonParts($$1);
        return LayerDefinition.create($$0, 128, 64);
    }

    public static LayerDefinition createChestRaftModel() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        addCommonParts($$1);
        $$1.addOrReplaceChild(PartNames.CHEST_BOTTOM, CubeListBuilder.create().texOffs(0, 76).addBox(0.0f, 0.0f, 0.0f, 12.0f, 8.0f, 12.0f), PartPose.offsetAndRotation(-2.0f, -10.1f, -6.0f, 0.0f, -1.5707964f, 0.0f));
        $$1.addOrReplaceChild(PartNames.CHEST_LID, CubeListBuilder.create().texOffs(0, 59).addBox(0.0f, 0.0f, 0.0f, 12.0f, 4.0f, 12.0f), PartPose.offsetAndRotation(-2.0f, -14.1f, -6.0f, 0.0f, -1.5707964f, 0.0f));
        $$1.addOrReplaceChild(PartNames.CHEST_LOCK, CubeListBuilder.create().texOffs(0, 59).addBox(0.0f, 0.0f, 0.0f, 2.0f, 4.0f, 1.0f), PartPose.offsetAndRotation(-1.0f, -11.1f, -1.0f, 0.0f, -1.5707964f, 0.0f));
        return LayerDefinition.create($$0, 128, 128);
    }
}
