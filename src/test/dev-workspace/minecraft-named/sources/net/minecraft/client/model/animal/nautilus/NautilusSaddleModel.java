package net.minecraft.client.model.animal.nautilus;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/nautilus/NautilusSaddleModel.class */
public class NautilusSaddleModel extends NautilusModel {
    private final ModelPart nautilus;
    private final ModelPart shell;

    public NautilusSaddleModel(ModelPart $$0) {
        super($$0);
        this.nautilus = $$0.getChild("root");
        this.shell = this.nautilus.getChild(PartNames.SHELL);
    }

    public static LayerDefinition createSaddleLayer() {
        MeshDefinition $$0 = createBodyMesh();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0f, 29.0f, -6.0f));
        $$2.addOrReplaceChild(PartNames.SHELL, CubeListBuilder.create().texOffs(0, 0).addBox(-7.0f, -10.0f, -7.0f, 14.0f, 10.0f, 16.0f, new CubeDeformation(0.2f)), PartPose.offset(0.0f, -13.0f, 5.0f));
        return LayerDefinition.create($$0, 128, 128);
    }
}
