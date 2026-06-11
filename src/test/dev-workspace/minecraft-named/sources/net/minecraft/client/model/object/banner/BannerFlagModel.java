package net.minecraft.client.model.object.banner;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/banner/BannerFlagModel.class */
public class BannerFlagModel extends Model<Float> {
    private final ModelPart flag;

    public BannerFlagModel(ModelPart $$0) {
        super($$0, RenderTypes::entitySolid);
        this.flag = $$0.getChild(BannerModel.FLAG);
    }

    public static LayerDefinition createFlagLayer(boolean $$0) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.getRoot();
        $$2.addOrReplaceChild(BannerModel.FLAG, CubeListBuilder.create().texOffs(0, 0).addBox(-10.0f, 0.0f, -2.0f, 20.0f, 40.0f, 1.0f), PartPose.offset(0.0f, $$0 ? -44.0f : -20.5f, $$0 ? 0.0f : 10.5f));
        return LayerDefinition.create($$1, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(Float $$0) {
        super.setupAnim($$0);
        this.flag.xRot = ((-0.0125f) + (0.01f * Mth.cos(6.2831855f * $$0.floatValue()))) * 3.1415927f;
    }
}
