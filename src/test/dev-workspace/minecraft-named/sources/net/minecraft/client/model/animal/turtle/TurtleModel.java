package net.minecraft.client.model.animal.turtle;

import java.util.Set;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.TurtleRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/turtle/TurtleModel.class */
public class TurtleModel extends QuadrupedModel<TurtleRenderState> {
    private static final String EGG_BELLY = "egg_belly";
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 120.0f, 0.0f, 9.0f, 6.0f, 120.0f, Set.of(PartNames.HEAD));
    private final ModelPart eggBelly;

    public TurtleModel(ModelPart $$0) {
        super($$0);
        this.eggBelly = $$0.getChild(EGG_BELLY);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(3, 0).addBox(-3.0f, -1.0f, -3.0f, 6.0f, 5.0f, 6.0f), PartPose.offset(0.0f, 19.0f, -10.0f));
        $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(7, 37).addBox(PartNames.SHELL, -9.5f, 3.0f, -10.0f, 19.0f, 20.0f, 6.0f).texOffs(31, 1).addBox("belly", -5.5f, 3.0f, -13.0f, 11.0f, 18.0f, 3.0f), PartPose.offsetAndRotation(0.0f, 11.0f, -10.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.addOrReplaceChild(EGG_BELLY, CubeListBuilder.create().texOffs(70, 33).addBox(-4.5f, 3.0f, -14.0f, 9.0f, 18.0f, 1.0f), PartPose.offsetAndRotation(0.0f, 11.0f, -10.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(1, 23).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 1.0f, 10.0f), PartPose.offset(-3.5f, 22.0f, 11.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(1, 12).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 1.0f, 10.0f), PartPose.offset(3.5f, 22.0f, 11.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(27, 30).addBox(-13.0f, 0.0f, -2.0f, 13.0f, 1.0f, 5.0f), PartPose.offset(-5.0f, 21.0f, -4.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(27, 24).addBox(0.0f, 0.0f, -2.0f, 13.0f, 1.0f, 5.0f), PartPose.offset(5.0f, 21.0f, -4.0f));
        return LayerDefinition.create($$0, 128, 64);
    }

    @Override // net.minecraft.client.model.QuadrupedModel, net.minecraft.client.model.Model
    public void setupAnim(TurtleRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.walkAnimationPos;
        float $$2 = $$0.walkAnimationSpeed;
        if ($$0.isOnLand) {
            float $$3 = $$0.isLayingEgg ? 4.0f : 1.0f;
            float $$4 = $$0.isLayingEgg ? 2.0f : 1.0f;
            float $$5 = $$1 * 5.0f;
            float $$6 = Mth.cos($$3 * $$5);
            float $$7 = Mth.cos($$5);
            this.rightFrontLeg.yRot = (-$$6) * 8.0f * $$2 * $$4;
            this.leftFrontLeg.yRot = $$6 * 8.0f * $$2 * $$4;
            this.rightHindLeg.yRot = (-$$7) * 3.0f * $$2;
            this.leftHindLeg.yRot = $$7 * 3.0f * $$2;
        } else {
            float $$8 = 0.5f * $$2;
            float $$9 = Mth.cos($$1 * 0.6662f * 0.6f) * $$8;
            this.rightHindLeg.xRot = $$9;
            this.leftHindLeg.xRot = -$$9;
            this.rightFrontLeg.zRot = -$$9;
            this.leftFrontLeg.zRot = $$9;
        }
        this.eggBelly.visible = $$0.hasEgg;
        if (this.eggBelly.visible) {
            this.root.y -= 1.0f;
        }
    }
}
