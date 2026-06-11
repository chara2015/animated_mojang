package net.minecraft.client.model.object.cart;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/cart/MinecartModel.class */
public class MinecartModel extends EntityModel<MinecartRenderState> {
    public MinecartModel(ModelPart $$0) {
        super($$0);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild(PartNames.BOTTOM, CubeListBuilder.create().texOffs(0, 10).addBox(-10.0f, -8.0f, -1.0f, 20.0f, 16.0f, 2.0f), PartPose.offsetAndRotation(0.0f, 4.0f, 0.0f, 1.5707964f, 0.0f, 0.0f));
        $$1.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.offsetAndRotation(-9.0f, 4.0f, 0.0f, 0.0f, 4.712389f, 0.0f));
        $$1.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.offsetAndRotation(9.0f, 4.0f, 0.0f, 0.0f, 1.5707964f, 0.0f));
        $$1.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.offsetAndRotation(0.0f, 4.0f, -7.0f, 0.0f, 3.1415927f, 0.0f));
        $$1.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f), PartPose.offset(0.0f, 4.0f, 7.0f));
        return LayerDefinition.create($$0, 64, 32);
    }
}
