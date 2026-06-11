package net.minecraft.client.model.animal.equine;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.DonkeyRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/equine/DonkeyModel.class */
public class DonkeyModel extends AbstractEquineModel<DonkeyRenderState> {
    public static final float DONKEY_SCALE = 0.87f;
    public static final float MULE_SCALE = 0.92f;
    private static final MeshTransformer DONKEY_TRANSFORMER = $$0 -> {
        modifyMesh($$0.getRoot());
        return $$0;
    };
    private final ModelPart leftChest;
    private final ModelPart rightChest;

    public DonkeyModel(ModelPart $$0) {
        super($$0);
        this.leftChest = this.body.getChild(PartNames.LEFT_CHEST);
        this.rightChest = this.body.getChild(PartNames.RIGHT_CHEST);
    }

    public static LayerDefinition createBodyLayer(float $$0) {
        return LayerDefinition.create(AbstractEquineModel.createBodyMesh(CubeDeformation.NONE), 64, 64).apply(DONKEY_TRANSFORMER).apply(MeshTransformer.scaling($$0));
    }

    public static LayerDefinition createBabyLayer(float $$0) {
        return LayerDefinition.create(AbstractEquineModel.createFullScaleBabyMesh(CubeDeformation.NONE), 64, 64).apply(DONKEY_TRANSFORMER).apply(BABY_TRANSFORMER).apply(MeshTransformer.scaling($$0));
    }

    public static LayerDefinition createSaddleLayer(float $$0, boolean $$1) {
        return EquineSaddleModel.createFullScaleSaddleLayer($$1).apply(DONKEY_TRANSFORMER).apply($$1 ? AbstractEquineModel.BABY_TRANSFORMER : MeshTransformer.IDENTITY).apply(MeshTransformer.scaling($$0));
    }

    private static void modifyMesh(PartDefinition $$0) {
        PartDefinition $$1 = $$0.getChild(PartNames.BODY);
        CubeListBuilder $$2 = CubeListBuilder.create().texOffs(26, 21).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 8.0f, 3.0f);
        $$1.addOrReplaceChild(PartNames.LEFT_CHEST, $$2, PartPose.offsetAndRotation(6.0f, -8.0f, 0.0f, 0.0f, -1.5707964f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_CHEST, $$2, PartPose.offsetAndRotation(-6.0f, -8.0f, 0.0f, 0.0f, 1.5707964f, 0.0f));
        PartDefinition $$3 = $$0.getChild("head_parts").getChild(PartNames.HEAD);
        CubeListBuilder $$4 = CubeListBuilder.create().texOffs(0, 12).addBox(-1.0f, -7.0f, 0.0f, 2.0f, 7.0f, 1.0f);
        $$3.addOrReplaceChild(PartNames.LEFT_EAR, $$4, PartPose.offsetAndRotation(1.25f, -10.0f, 4.0f, 0.2617994f, 0.0f, 0.2617994f));
        $$3.addOrReplaceChild(PartNames.RIGHT_EAR, $$4, PartPose.offsetAndRotation(-1.25f, -10.0f, 4.0f, 0.2617994f, 0.0f, -0.2617994f));
    }

    @Override // net.minecraft.client.model.animal.equine.AbstractEquineModel, net.minecraft.client.model.Model
    public void setupAnim(DonkeyRenderState $$0) {
        super.setupAnim($$0);
        this.leftChest.visible = $$0.hasChest;
        this.rightChest.visible = $$0.hasChest;
    }
}
