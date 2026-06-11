package net.minecraft.client.model.monster.endermite;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/endermite/EndermiteModel.class */
public class EndermiteModel extends EntityModel<EntityRenderState> {
    private static final int BODY_COUNT = 4;
    private static final int[][] BODY_SIZES = {new int[]{4, 3, 2}, new int[]{6, 4, 5}, new int[]{3, 3, 1}, new int[]{1, 2, 1}};
    private static final int[][] BODY_TEXS = {new int[]{0, 0}, new int[]{0, 5}, new int[]{0, 14}, new int[]{0, 18}};
    private final ModelPart[] bodyParts;

    public EndermiteModel(ModelPart $$0) {
        super($$0);
        this.bodyParts = new ModelPart[4];
        for (int $$1 = 0; $$1 < 4; $$1++) {
            this.bodyParts[$$1] = $$0.getChild(createSegmentName($$1));
        }
    }

    private static String createSegmentName(int $$0) {
        return "segment" + $$0;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        float $$2 = -3.5f;
        for (int $$3 = 0; $$3 < 4; $$3++) {
            $$1.addOrReplaceChild(createSegmentName($$3), CubeListBuilder.create().texOffs(BODY_TEXS[$$3][0], BODY_TEXS[$$3][1]).addBox(BODY_SIZES[$$3][0] * (-0.5f), 0.0f, BODY_SIZES[$$3][2] * (-0.5f), BODY_SIZES[$$3][0], BODY_SIZES[$$3][1], BODY_SIZES[$$3][2]), PartPose.offset(0.0f, 24 - BODY_SIZES[$$3][1], $$2));
            if ($$3 < 3) {
                $$2 += (BODY_SIZES[$$3][2] + BODY_SIZES[$$3 + 1][2]) * 0.5f;
            }
        }
        return LayerDefinition.create($$0, 64, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(EntityRenderState $$0) {
        super.setupAnim($$0);
        for (int $$1 = 0; $$1 < this.bodyParts.length; $$1++) {
            this.bodyParts[$$1].yRot = Mth.cos(($$0.ageInTicks * 0.9f) + ($$1 * 0.15f * 3.1415927f)) * 3.1415927f * 0.01f * (1 + Math.abs($$1 - 2));
            this.bodyParts[$$1].x = Mth.sin(($$0.ageInTicks * 0.9f) + ($$1 * 0.15f * 3.1415927f)) * 3.1415927f * 0.1f * Math.abs($$1 - 2);
        }
    }
}
