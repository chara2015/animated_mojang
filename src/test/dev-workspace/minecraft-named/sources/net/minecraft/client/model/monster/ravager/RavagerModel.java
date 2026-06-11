package net.minecraft.client.model.monster.ravager;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.RavagerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/ravager/RavagerModel.class */
public class RavagerModel extends EntityModel<RavagerRenderState> {
    private final ModelPart head;
    private final ModelPart mouth;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart neck;

    public RavagerModel(ModelPart $$0) {
        super($$0);
        this.neck = $$0.getChild(PartNames.NECK);
        this.head = this.neck.getChild(PartNames.HEAD);
        this.mouth = this.head.getChild(PartNames.MOUTH);
        this.rightHindLeg = $$0.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = $$0.getChild(PartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = $$0.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = $$0.getChild(PartNames.LEFT_FRONT_LEG);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$3 = $$1.addOrReplaceChild(PartNames.NECK, CubeListBuilder.create().texOffs(68, 73).addBox(-5.0f, -1.0f, -18.0f, 10.0f, 10.0f, 18.0f), PartPose.offset(0.0f, -7.0f, 5.5f));
        PartDefinition $$4 = $$3.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -20.0f, -14.0f, 16.0f, 20.0f, 16.0f).texOffs(0, 0).addBox(-2.0f, -6.0f, -18.0f, 4.0f, 8.0f, 4.0f), PartPose.offset(0.0f, 16.0f, -17.0f));
        $$4.addOrReplaceChild(PartNames.RIGHT_HORN, CubeListBuilder.create().texOffs(74, 55).addBox(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f), PartPose.offsetAndRotation(-10.0f, -14.0f, -8.0f, 1.0995574f, 0.0f, 0.0f));
        $$4.addOrReplaceChild(PartNames.LEFT_HORN, CubeListBuilder.create().texOffs(74, 55).mirror().addBox(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f), PartPose.offsetAndRotation(8.0f, -14.0f, -8.0f, 1.0995574f, 0.0f, 0.0f));
        $$4.addOrReplaceChild(PartNames.MOUTH, CubeListBuilder.create().texOffs(0, 36).addBox(-8.0f, 0.0f, -16.0f, 16.0f, 3.0f, 16.0f), PartPose.offset(0.0f, -2.0f, 2.0f));
        $$1.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 55).addBox(-7.0f, -10.0f, -7.0f, 14.0f, 16.0f, 20.0f).texOffs(0, 91).addBox(-6.0f, 6.0f, -7.0f, 12.0f, 13.0f, 18.0f), PartPose.offsetAndRotation(0.0f, 1.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(96, 0).addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.offset(-8.0f, -13.0f, 18.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.offset(8.0f, -13.0f, 18.0f));
        $$1.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create().texOffs(64, 0).addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.offset(-8.0f, -13.0f, -5.0f));
        $$1.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f), PartPose.offset(8.0f, -13.0f, -5.0f));
        return LayerDefinition.create($$0, 128, 128);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(RavagerRenderState $$0) {
        super.setupAnim($$0);
        float $$1 = $$0.stunnedTicksRemaining;
        float $$2 = $$0.attackTicksRemaining;
        if ($$2 > 0.0f) {
            float $$4 = Mth.triangleWave($$2, 10.0f);
            float $$5 = (1.0f + $$4) * 0.5f;
            float $$6 = $$5 * $$5 * $$5 * 12.0f;
            float $$7 = $$6 * Mth.sin(this.neck.xRot);
            this.neck.z = (-6.5f) + $$6;
            this.neck.y = (-7.0f) - $$7;
            if ($$2 > 5.0f) {
                this.mouth.xRot = Mth.sin(((-4.0f) + $$2) / 4.0f) * 3.1415927f * 0.4f;
            } else {
                this.mouth.xRot = 0.15707964f * Mth.sin((3.1415927f * $$2) / 10.0f);
            }
        } else {
            float $$9 = (-1.0f) * Mth.sin(this.neck.xRot);
            this.neck.x = 0.0f;
            this.neck.y = (-7.0f) - $$9;
            this.neck.z = 5.5f;
            boolean $$10 = $$1 > 0.0f;
            this.neck.xRot = $$10 ? 0.21991149f : 0.0f;
            this.mouth.xRot = 3.1415927f * ($$10 ? 0.05f : 0.01f);
            if ($$10) {
                double $$11 = ((double) $$1) / 40.0d;
                this.neck.x = ((float) Math.sin($$11 * 10.0d)) * 3.0f;
            } else if ($$0.roarAnimation > Density.SURFACE) {
                float $$12 = Mth.sin($$0.roarAnimation * 3.1415927f * 0.25f);
                this.mouth.xRot = 1.5707964f * $$12;
            }
        }
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = $$0.yRot * 0.017453292f;
        float $$13 = $$0.walkAnimationPos;
        float $$14 = 0.4f * $$0.walkAnimationSpeed;
        this.rightHindLeg.xRot = Mth.cos($$13 * 0.6662f) * $$14;
        this.leftHindLeg.xRot = Mth.cos(($$13 * 0.6662f) + 3.1415927f) * $$14;
        this.rightFrontLeg.xRot = Mth.cos(($$13 * 0.6662f) + 3.1415927f) * $$14;
        this.leftFrontLeg.xRot = Mth.cos($$13 * 0.6662f) * $$14;
    }
}
