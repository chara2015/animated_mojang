package net.minecraft.client.model.animal.llama;

import java.util.Map;
import java.util.function.UnaryOperator;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/llama/LlamaModel.class */
public class LlamaModel extends EntityModel<LlamaRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = LlamaModel::transformToBaby;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightChest;
    private final ModelPart leftChest;

    public LlamaModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.rightChest = $$0.getChild(PartNames.RIGHT_CHEST);
        this.leftChest = $$0.getChild(PartNames.LEFT_CHEST);
        this.rightHindLeg = $$0.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = $$0.getChild(PartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = $$0.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = $$0.getChild(PartNames.LEFT_FRONT_LEG);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.getRoot();
        $$2.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0f, -14.0f, -10.0f, 4.0f, 4.0f, 9.0f, $$0).texOffs(0, 14).addBox(PartNames.NECK, -4.0f, -16.0f, -6.0f, 8.0f, 18.0f, 6.0f, $$0).texOffs(17, 0).addBox("ear", -4.0f, -19.0f, -4.0f, 3.0f, 3.0f, 2.0f, $$0).texOffs(17, 0).addBox("ear", 1.0f, -19.0f, -4.0f, 3.0f, 3.0f, 2.0f, $$0), PartPose.offset(0.0f, 7.0f, -6.0f));
        $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(29, 0).addBox(-6.0f, -10.0f, -7.0f, 12.0f, 18.0f, 10.0f, $$0), PartPose.offsetAndRotation(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_CHEST, CubeListBuilder.create().texOffs(45, 28).addBox(-3.0f, 0.0f, 0.0f, 8.0f, 8.0f, 3.0f, $$0), PartPose.offsetAndRotation(-8.5f, 3.0f, 3.0f, 0.0f, 1.5707964f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_CHEST, CubeListBuilder.create().texOffs(45, 41).addBox(-3.0f, 0.0f, 0.0f, 8.0f, 8.0f, 3.0f, $$0), PartPose.offsetAndRotation(5.5f, 3.0f, 3.0f, 0.0f, 1.5707964f, 0.0f));
        CubeListBuilder $$5 = CubeListBuilder.create().texOffs(29, 29).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 14.0f, 4.0f, $$0);
        $$2.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, $$5, PartPose.offset(-3.5f, 10.0f, 6.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_HIND_LEG, $$5, PartPose.offset(3.5f, 10.0f, 6.0f));
        $$2.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, $$5, PartPose.offset(-3.5f, 10.0f, -5.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, $$5, PartPose.offset(3.5f, 10.0f, -5.0f));
        return LayerDefinition.create($$1, 128, 64);
    }

    private static MeshDefinition transformToBaby(MeshDefinition $$0) {
        UnaryOperator<PartPose> unaryOperator;
        UnaryOperator<PartPose> $$4 = $$02 -> {
            return $$02.translated(0.0f, 21.0f, 3.52f).scaled(0.71428573f, 0.64935064f, 0.7936508f);
        };
        UnaryOperator<PartPose> $$5 = $$03 -> {
            return $$03.translated(0.0f, 33.0f, 0.0f).scaled(0.625f, 0.45454544f, 0.45454544f);
        };
        UnaryOperator<PartPose> $$6 = $$04 -> {
            return $$04.translated(0.0f, 33.0f, 0.0f).scaled(0.45454544f, 0.41322312f, 0.45454544f);
        };
        MeshDefinition $$7 = new MeshDefinition();
        for (Map.Entry<String, PartDefinition> $$8 : $$0.getRoot().getChildren()) {
            String $$9 = $$8.getKey();
            PartDefinition $$10 = $$8.getValue();
            switch ($$9) {
                case "head":
                    unaryOperator = $$4;
                    break;
                case "body":
                    unaryOperator = $$5;
                    break;
                default:
                    unaryOperator = $$6;
                    break;
            }
            UnaryOperator<PartPose> $$11 = unaryOperator;
            $$7.getRoot().addOrReplaceChild($$9, $$10.transformed($$11));
        }
        return $$7;
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(LlamaRenderState $$0) {
        super.setupAnim($$0);
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = $$0.yRot * 0.017453292f;
        float $$1 = $$0.walkAnimationSpeed;
        float $$2 = $$0.walkAnimationPos;
        this.rightHindLeg.xRot = Mth.cos($$2 * 0.6662f) * 1.4f * $$1;
        this.leftHindLeg.xRot = Mth.cos(($$2 * 0.6662f) + 3.1415927f) * 1.4f * $$1;
        this.rightFrontLeg.xRot = Mth.cos(($$2 * 0.6662f) + 3.1415927f) * 1.4f * $$1;
        this.leftFrontLeg.xRot = Mth.cos($$2 * 0.6662f) * 1.4f * $$1;
        this.rightChest.visible = $$0.hasChest;
        this.leftChest.visible = $$0.hasChest;
    }
}
