package net.minecraft.client.model.animal.rabbit;

import java.util.Set;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.RabbitRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/rabbit/RabbitModel.class */
public class RabbitModel extends EntityModel<RabbitRenderState> {
    private static final float REAR_JUMP_ANGLE = 50.0f;
    private static final float FRONT_JUMP_ANGLE = -40.0f;
    private static final float NEW_SCALE = 0.6f;
    private static final MeshTransformer ADULT_TRANSFORMER = MeshTransformer.scaling(0.6f);
    private static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 22.0f, 2.0f, 2.65f, 2.5f, 36.0f, Set.of(PartNames.HEAD, PartNames.LEFT_EAR, PartNames.RIGHT_EAR, PartNames.NOSE));
    private static final String LEFT_HAUNCH = "left_haunch";
    private static final String RIGHT_HAUNCH = "right_haunch";
    private final ModelPart leftHaunch;
    private final ModelPart rightHaunch;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart head;

    public RabbitModel(ModelPart $$0) {
        super($$0);
        this.leftHaunch = $$0.getChild(LEFT_HAUNCH);
        this.rightHaunch = $$0.getChild(RIGHT_HAUNCH);
        this.leftFrontLeg = $$0.getChild(PartNames.LEFT_FRONT_LEG);
        this.rightFrontLeg = $$0.getChild(PartNames.RIGHT_FRONT_LEG);
        this.head = $$0.getChild(PartNames.HEAD);
    }

    public static LayerDefinition createBodyLayer(boolean $$0) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.getRoot();
        PartDefinition $$3 = $$2.addOrReplaceChild(LEFT_HAUNCH, CubeListBuilder.create().texOffs(30, 15).addBox(-1.0f, 0.0f, 0.0f, 2.0f, 4.0f, 5.0f), PartPose.offsetAndRotation(3.0f, 17.5f, 3.7f, -0.36651915f, 0.0f, 0.0f));
        PartDefinition $$4 = $$2.addOrReplaceChild(RIGHT_HAUNCH, CubeListBuilder.create().texOffs(16, 15).addBox(-1.0f, 0.0f, 0.0f, 2.0f, 4.0f, 5.0f), PartPose.offsetAndRotation(-3.0f, 17.5f, 3.7f, -0.36651915f, 0.0f, 0.0f));
        $$3.addOrReplaceChild(PartNames.LEFT_HIND_FOOT, CubeListBuilder.create().texOffs(26, 24).addBox(-1.0f, 5.5f, -3.7f, 2.0f, 1.0f, 7.0f), PartPose.rotation(0.36651915f, 0.0f, 0.0f));
        $$4.addOrReplaceChild(PartNames.RIGHT_HIND_FOOT, CubeListBuilder.create().texOffs(8, 24).addBox(-1.0f, 5.5f, -3.7f, 2.0f, 1.0f, 7.0f), PartPose.rotation(0.36651915f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0f, -2.0f, -10.0f, 6.0f, 5.0f, 10.0f), PartPose.offsetAndRotation(0.0f, 19.0f, 8.0f, -0.34906584f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(8, 15).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 7.0f, 2.0f), PartPose.offsetAndRotation(3.0f, 17.0f, -1.0f, -0.19198622f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(0, 15).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 7.0f, 2.0f), PartPose.offsetAndRotation(-3.0f, 17.0f, -1.0f, -0.19198622f, 0.0f, 0.0f));
        PartDefinition $$5 = $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(32, 0).addBox(-2.5f, -4.0f, -5.0f, 5.0f, 4.0f, 5.0f), PartPose.offset(0.0f, 16.0f, -1.0f));
        $$5.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create().texOffs(52, 0).addBox(-2.5f, -9.0f, -1.0f, 2.0f, 5.0f, 1.0f), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, -0.2617994f, 0.0f));
        $$5.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create().texOffs(58, 0).addBox(0.5f, -9.0f, -1.0f, 2.0f, 5.0f, 1.0f), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.2617994f, 0.0f));
        $$2.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create().texOffs(52, 6).addBox(-1.5f, -1.5f, 0.0f, 3.0f, 3.0f, 2.0f), PartPose.offsetAndRotation(0.0f, 20.0f, 7.0f, -0.3490659f, 0.0f, 0.0f));
        $$5.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(32, 9).addBox(-0.5f, -2.5f, -5.5f, 1.0f, 1.0f, 1.0f), PartPose.ZERO);
        return LayerDefinition.create($$1, 64, 32).apply($$0 ? BABY_TRANSFORMER : ADULT_TRANSFORMER);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(RabbitRenderState $$0) {
        super.setupAnim($$0);
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = $$0.yRot * 0.017453292f;
        float $$1 = Mth.sin($$0.jumpCompletion * 3.1415927f);
        this.leftHaunch.xRot += $$1 * REAR_JUMP_ANGLE * 0.017453292f;
        this.rightHaunch.xRot += $$1 * REAR_JUMP_ANGLE * 0.017453292f;
        this.leftFrontLeg.xRot += $$1 * FRONT_JUMP_ANGLE * 0.017453292f;
        this.rightFrontLeg.xRot += $$1 * FRONT_JUMP_ANGLE * 0.017453292f;
    }
}
