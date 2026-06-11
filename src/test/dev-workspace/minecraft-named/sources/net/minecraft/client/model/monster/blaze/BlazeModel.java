package net.minecraft.client.model.monster.blaze;

import java.util.Arrays;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/blaze/BlazeModel.class */
public class BlazeModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart[] upperBodyParts;
    private final ModelPart head;

    public BlazeModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.upperBodyParts = new ModelPart[12];
        Arrays.setAll(this.upperBodyParts, $$1 -> {
            return $$0.getChild(getPartName($$1));
        });
    }

    private static String getPartName(int $$0) {
        return "part" + $$0;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.ZERO);
        float $$2 = 0.0f;
        CubeListBuilder $$3 = CubeListBuilder.create().texOffs(0, 16).addBox(0.0f, 0.0f, 0.0f, 2.0f, 8.0f, 2.0f);
        for (int $$4 = 0; $$4 < 4; $$4++) {
            float $$5 = Mth.cos($$2) * 9.0f;
            float $$6 = (-2.0f) + Mth.cos($$4 * 2 * 0.25f);
            float $$7 = Mth.sin($$2) * 9.0f;
            $$1.addOrReplaceChild(getPartName($$4), $$3, PartPose.offset($$5, $$6, $$7));
            $$2 += 1.5707964f;
        }
        float $$22 = 0.7853982f;
        for (int $$8 = 4; $$8 < 8; $$8++) {
            float $$9 = Mth.cos($$22) * 7.0f;
            float $$10 = 2.0f + Mth.cos($$8 * 2 * 0.25f);
            float $$11 = Mth.sin($$22) * 7.0f;
            $$1.addOrReplaceChild(getPartName($$8), $$3, PartPose.offset($$9, $$10, $$11));
            $$22 += 1.5707964f;
        }
        float $$23 = 0.47123894f;
        for (int $$12 = 8; $$12 < 12; $$12++) {
            float $$13 = Mth.cos($$23) * 5.0f;
            float $$14 = 11.0f + Mth.cos($$12 * 1.5f * 0.5f);
            float $$15 = Mth.sin($$23) * 5.0f;
            $$1.addOrReplaceChild(getPartName($$12), $$3, PartPose.offset($$13, $$14, $$15));
            $$23 += 1.5707964f;
        }
        return LayerDefinition.create($$0, 64, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(LivingEntityRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.ageInTicks * 3.1415927f * (-0.1f);
        for (int $$2 = 0; $$2 < 4; $$2++) {
            this.upperBodyParts[$$2].y = (-2.0f) + Mth.cos((($$2 * 2) + $$0.ageInTicks) * 0.25f);
            this.upperBodyParts[$$2].x = Mth.cos($$1) * 9.0f;
            this.upperBodyParts[$$2].z = Mth.sin($$1) * 9.0f;
            $$1 += 1.5707964f;
        }
        float $$12 = 0.7853982f + ($$0.ageInTicks * 3.1415927f * 0.03f);
        for (int $$3 = 4; $$3 < 8; $$3++) {
            this.upperBodyParts[$$3].y = 2.0f + Mth.cos((($$3 * 2) + $$0.ageInTicks) * 0.25f);
            this.upperBodyParts[$$3].x = Mth.cos($$12) * 7.0f;
            this.upperBodyParts[$$3].z = Mth.sin($$12) * 7.0f;
            $$12 += 1.5707964f;
        }
        float $$13 = 0.47123894f + ($$0.ageInTicks * 3.1415927f * (-0.05f));
        for (int $$4 = 8; $$4 < 12; $$4++) {
            this.upperBodyParts[$$4].y = 11.0f + Mth.cos((($$4 * 1.5f) + $$0.ageInTicks) * 0.5f);
            this.upperBodyParts[$$4].x = Mth.cos($$13) * 5.0f;
            this.upperBodyParts[$$4].z = Mth.sin($$13) * 5.0f;
            $$13 += 1.5707964f;
        }
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.head.xRot = $$0.xRot * 0.017453292f;
    }
}
