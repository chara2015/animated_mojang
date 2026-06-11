package net.minecraft.client.model.animal.chicken;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/chicken/ColdChickenModel.class */
public class ColdChickenModel extends ChickenModel {
    public ColdChickenModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = createBaseChickenModel();
        $$0.getRoot().addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 9).addBox(-3.0f, -4.0f, -3.0f, 6.0f, 8.0f, 6.0f).texOffs(38, 9).addBox(0.0f, 3.0f, -1.0f, 0.0f, 3.0f, 5.0f), PartPose.offsetAndRotation(0.0f, 16.0f, 0.0f, 1.5707964f, 0.0f, 0.0f));
        $$0.getRoot().addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0f, -6.0f, -2.0f, 4.0f, 6.0f, 3.0f).texOffs(44, 0).addBox(-3.0f, -7.0f, -2.015f, 6.0f, 3.0f, 4.0f), PartPose.offset(0.0f, 15.0f, -4.0f));
        return LayerDefinition.create($$0, 64, 32);
    }
}
