package net.minecraft.client.model.animal.pig;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/pig/ColdPigModel.class */
public class ColdPigModel extends PigModel {
    public ColdPigModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
        MeshDefinition $$1 = createBasePigModel($$0);
        PartDefinition $$2 = $$1.getRoot();
        $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(28, 8).addBox(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f).texOffs(28, 32).addBox(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.offsetAndRotation(0.0f, 11.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        return LayerDefinition.create($$1, 64, 64);
    }
}
