package net.minecraft.client.model.player;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/player/PlayerEarsModel.class */
public class PlayerEarsModel extends PlayerModel {
    public PlayerEarsModel(ModelPart $$0) {
        super($$0, false);
    }

    public static LayerDefinition createEarsLayer() {
        MeshDefinition $$0 = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition $$1 = $$0.getRoot().clearRecursively();
        PartDefinition $$2 = $$1.getChild(PartNames.HEAD);
        CubeListBuilder $$3 = CubeListBuilder.create().texOffs(24, 0).addBox(-3.0f, -6.0f, -1.0f, 6.0f, 6.0f, 1.0f, new CubeDeformation(1.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_EAR, $$3, PartPose.offset(-6.0f, -6.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_EAR, $$3, PartPose.offset(6.0f, -6.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 64);
    }
}
