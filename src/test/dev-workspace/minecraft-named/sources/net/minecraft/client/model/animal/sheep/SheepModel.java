package net.minecraft.client.model.animal.sheep;

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
import net.minecraft.client.renderer.entity.state.SheepRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/sheep/SheepModel.class */
public class SheepModel extends QuadrupedModel<SheepRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(false, 8.0f, 4.0f, 2.0f, 2.0f, 24.0f, Set.of(PartNames.HEAD));

    public SheepModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = QuadrupedModel.createBodyMesh(12, false, true, CubeDeformation.NONE);
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0f, -4.0f, -6.0f, 6.0f, 6.0f, 8.0f), PartPose.offset(0.0f, 6.0f, -8.0f));
        $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(28, 8).addBox(-4.0f, -10.0f, -7.0f, 8.0f, 16.0f, 6.0f), PartPose.offsetAndRotation(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        return LayerDefinition.create($$0, 64, 32);
    }

    @Override // net.minecraft.client.model.QuadrupedModel, net.minecraft.client.model.Model
    public void setupAnim(SheepRenderState $$0) {
        super.setupAnim($$0);
        this.head.y += $$0.headEatPositionScale * 9.0f * $$0.ageScale;
        this.head.xRot = $$0.headEatAngleScale;
    }
}
