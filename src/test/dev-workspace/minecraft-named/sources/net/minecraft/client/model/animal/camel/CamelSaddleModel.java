package net.minecraft.client.model.animal.camel;

import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.CamelRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/camel/CamelSaddleModel.class */
public class CamelSaddleModel extends CamelModel {
    private static final String SADDLE = "saddle";
    private static final String BRIDLE = "bridle";
    private static final String REINS = "reins";
    private final ModelPart reins;

    public CamelSaddleModel(ModelPart $$0) {
        super($$0);
        this.reins = this.head.getChild(REINS);
    }

    public static LayerDefinition createSaddleLayer() {
        MeshDefinition $$0 = createBodyMesh();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.getChild(PartNames.BODY);
        PartDefinition $$3 = $$2.getChild(PartNames.HEAD);
        CubeDeformation $$4 = new CubeDeformation(0.05f);
        $$2.addOrReplaceChild(SADDLE, CubeListBuilder.create().texOffs(74, 64).addBox(-4.5f, -17.0f, -15.5f, 9.0f, 5.0f, 11.0f, $$4).texOffs(92, BookEditScreen.TEXT_WIDTH).addBox(-3.5f, -20.0f, -15.5f, 7.0f, 3.0f, 11.0f, $$4).texOffs(0, 89).addBox(-7.5f, -12.0f, -23.5f, 15.0f, 12.0f, 27.0f, $$4), PartPose.offset(0.0f, 0.0f, 0.0f));
        $$3.addOrReplaceChild(REINS, CubeListBuilder.create().texOffs(98, 42).addBox(3.51f, -18.0f, -17.0f, 0.0f, 7.0f, 15.0f).texOffs(84, 57).addBox(-3.5f, -18.0f, -2.0f, 7.0f, 7.0f, 0.0f).texOffs(98, 42).addBox(-3.51f, -18.0f, -17.0f, 0.0f, 7.0f, 15.0f), PartPose.offset(0.0f, 0.0f, 0.0f));
        $$3.addOrReplaceChild(BRIDLE, CubeListBuilder.create().texOffs(60, 87).addBox(-3.5f, -7.0f, -15.0f, 7.0f, 8.0f, 19.0f, $$4).texOffs(21, 64).addBox(-3.5f, -21.0f, -15.0f, 7.0f, 14.0f, 7.0f, $$4).texOffs(50, 64).addBox(-2.5f, -21.0f, -21.0f, 5.0f, 5.0f, 6.0f, $$4).texOffs(74, 70).addBox(2.5f, -19.0f, -18.0f, 1.0f, 2.0f, 2.0f).texOffs(74, 70).mirror().addBox(-3.5f, -19.0f, -18.0f, 1.0f, 2.0f, 2.0f), PartPose.offset(0.0f, 0.0f, 0.0f));
        return LayerDefinition.create($$0, 128, 128);
    }

    @Override // net.minecraft.client.model.animal.camel.CamelModel, net.minecraft.client.model.Model
    public void setupAnim(CamelRenderState $$0) {
        super.setupAnim($$0);
        this.reins.visible = $$0.isRidden;
    }
}
