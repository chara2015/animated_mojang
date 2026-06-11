package net.minecraft.client.model.effects;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/effects/SpinAttackEffectModel.class */
public class SpinAttackEffectModel extends EntityModel<AvatarRenderState> {
    private static final int BOX_COUNT = 2;
    private final ModelPart[] boxes;

    public SpinAttackEffectModel(ModelPart $$0) {
        super($$0);
        this.boxes = new ModelPart[2];
        for (int $$1 = 0; $$1 < 2; $$1++) {
            this.boxes[$$1] = $$0.getChild(boxName($$1));
        }
    }

    private static String boxName(int $$0) {
        return "box" + $$0;
    }

    public static LayerDefinition createLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        for (int $$2 = 0; $$2 < 2; $$2++) {
            float $$3 = (-3.2f) + (9.6f * ($$2 + 1));
            float $$4 = 0.75f * ($$2 + 1);
            $$1.addOrReplaceChild(boxName($$2), CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, (-16.0f) + $$3, -8.0f, 16.0f, 32.0f, 16.0f), PartPose.ZERO.withScale($$4));
        }
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(AvatarRenderState $$0) {
        super.setupAnim($$0);
        for (int $$1 = 0; $$1 < this.boxes.length; $$1++) {
            float $$2 = $$0.ageInTicks * (-(45 + (($$1 + 1) * 5)));
            this.boxes[$$1].yRot = Mth.wrapDegrees($$2) * 0.017453292f;
        }
    }
}
