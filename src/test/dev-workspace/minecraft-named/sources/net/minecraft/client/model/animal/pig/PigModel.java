package net.minecraft.client.model.animal.pig;

import java.util.Set;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/pig/PigModel.class */
public class PigModel extends QuadrupedModel<LivingEntityRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(false, 4.0f, 4.0f, Set.of(PartNames.HEAD));

    public PigModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
        return LayerDefinition.create(createBasePigModel($$0), 64, 64);
    }

    protected static MeshDefinition createBasePigModel(CubeDeformation $$0) {
        MeshDefinition $$1 = QuadrupedModel.createBodyMesh(6, true, false, $$0);
        PartDefinition $$2 = $$1.getRoot();
        $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f, $$0).texOffs(16, 16).addBox(-2.0f, 0.0f, -9.0f, 4.0f, 3.0f, 1.0f, $$0), PartPose.offset(0.0f, 12.0f, -6.0f));
        return $$1;
    }
}
