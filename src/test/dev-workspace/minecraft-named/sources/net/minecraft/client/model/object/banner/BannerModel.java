package net.minecraft.client.model.object.banner;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/banner/BannerModel.class */
public class BannerModel extends Model<Unit> {
    public static final int BANNER_WIDTH = 20;
    public static final int BANNER_HEIGHT = 40;
    public static final String FLAG = "flag";
    private static final String POLE = "pole";
    private static final String BAR = "bar";

    public BannerModel(ModelPart $$0) {
        super($$0, RenderTypes::entitySolid);
    }

    public static LayerDefinition createBodyLayer(boolean $$0) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.getRoot();
        if ($$0) {
            $$2.addOrReplaceChild(POLE, CubeListBuilder.create().texOffs(44, 0).addBox(-1.0f, -42.0f, -1.0f, 2.0f, 42.0f, 2.0f), PartPose.ZERO);
        }
        $$2.addOrReplaceChild(BAR, CubeListBuilder.create().texOffs(0, 42).addBox(-10.0f, $$0 ? -44.0f : -20.5f, $$0 ? -1.0f : 9.5f, 20.0f, 2.0f, 2.0f), PartPose.ZERO);
        return LayerDefinition.create($$1, 64, 64);
    }
}
