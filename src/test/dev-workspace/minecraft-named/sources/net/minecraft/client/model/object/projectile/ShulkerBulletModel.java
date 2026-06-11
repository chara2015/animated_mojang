package net.minecraft.client.model.object.projectile;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.ShulkerBulletRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/projectile/ShulkerBulletModel.class */
public class ShulkerBulletModel extends EntityModel<ShulkerBulletRenderState> {
    private static final String MAIN = "main";
    private final ModelPart main;

    public ShulkerBulletModel(ModelPart $$0) {
        super($$0);
        this.main = $$0.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -4.0f, -1.0f, 8.0f, 8.0f, 2.0f).texOffs(0, 10).addBox(-1.0f, -4.0f, -4.0f, 2.0f, 8.0f, 8.0f).texOffs(20, 0).addBox(-4.0f, -1.0f, -4.0f, 8.0f, 2.0f, 8.0f), PartPose.ZERO);
        return LayerDefinition.create($$0, 64, 32);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(ShulkerBulletRenderState $$0) {
        super.setupAnim($$0);
        this.main.yRot = $$0.yRot * 0.017453292f;
        this.main.xRot = $$0.xRot * 0.017453292f;
    }
}
