package net.minecraft.client.model.object.skull;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.monster.piglin.PiglinModel;
import net.minecraft.client.model.object.skull.SkullModelBase;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/skull/PiglinHeadModel.class */
public class PiglinHeadModel extends SkullModelBase {
    private final ModelPart head;
    private final ModelPart leftEar;
    private final ModelPart rightEar;

    public PiglinHeadModel(ModelPart $$0) {
        super($$0);
        this.head = $$0.getChild(PartNames.HEAD);
        this.leftEar = this.head.getChild(PartNames.LEFT_EAR);
        this.rightEar = this.head.getChild(PartNames.RIGHT_EAR);
    }

    public static MeshDefinition createHeadModel() {
        MeshDefinition $$0 = new MeshDefinition();
        PiglinModel.addHead(CubeDeformation.NONE, $$0);
        return $$0;
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(SkullModelBase.State $$0) {
        super.setupAnim($$0);
        this.head.yRot = $$0.yRot * 0.017453292f;
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.leftEar.zRot = ((float) (-(Math.cos($$0.animationPos * 3.1415927f * 0.2f * 1.2f) + 2.5d))) * 0.2f;
        this.rightEar.zRot = ((float) (Math.cos($$0.animationPos * 3.1415927f * 0.2f) + 2.5d)) * 0.2f;
    }
}
