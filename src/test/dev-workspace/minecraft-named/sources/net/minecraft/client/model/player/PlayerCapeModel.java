package net.minecraft.client.model.player;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/player/PlayerCapeModel.class */
public class PlayerCapeModel extends PlayerModel {
    private static final String CAPE = "cape";
    private final ModelPart cape;

    public PlayerCapeModel(ModelPart $$0) {
        super($$0, false);
        this.cape = this.body.getChild(CAPE);
    }

    public static LayerDefinition createCapeLayer() {
        MeshDefinition $$0 = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition $$1 = $$0.getRoot().clearRecursively();
        PartDefinition $$2 = $$1.getChild(PartNames.BODY);
        $$2.addOrReplaceChild(CAPE, CubeListBuilder.create().texOffs(0, 0).addBox(-5.0f, 0.0f, -1.0f, 10.0f, 16.0f, 1.0f, CubeDeformation.NONE, 1.0f, 0.5f), PartPose.offsetAndRotation(0.0f, 0.0f, 2.0f, 0.0f, 3.1415927f, 0.0f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.player.PlayerModel, net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.Model
    public void setupAnim(AvatarRenderState $$0) {
        super.setupAnim($$0);
        this.cape.rotateBy(new Quaternionf().rotateY(-3.1415927f).rotateX((6.0f + ($$0.capeLean / 2.0f) + $$0.capeFlap) * 0.017453292f).rotateZ(($$0.capeLean2 / 2.0f) * 0.017453292f).rotateY((180.0f - ($$0.capeLean2 / 2.0f)) * 0.017453292f));
    }
}
