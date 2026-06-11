package net.minecraft.client.model.object.boat;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/boat/AbstractBoatModel.class */
public abstract class AbstractBoatModel extends EntityModel<BoatRenderState> {
    private final ModelPart leftPaddle;
    private final ModelPart rightPaddle;

    public AbstractBoatModel(ModelPart $$0) {
        super($$0);
        this.leftPaddle = $$0.getChild(PartNames.LEFT_PADDLE);
        this.rightPaddle = $$0.getChild(PartNames.RIGHT_PADDLE);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(BoatRenderState $$0) {
        super.setupAnim($$0);
        animatePaddle($$0.rowingTimeLeft, 0, this.leftPaddle);
        animatePaddle($$0.rowingTimeRight, 1, this.rightPaddle);
    }

    private static void animatePaddle(float $$0, int $$1, ModelPart $$2) {
        $$2.xRot = Mth.clampedLerp((Mth.sin(-$$0) + 1.0f) / 2.0f, -1.0471976f, -0.2617994f);
        $$2.yRot = Mth.clampedLerp((Mth.sin((-$$0) + 1.0f) + 1.0f) / 2.0f, -0.7853982f, 0.7853982f);
        if ($$1 == 1) {
            $$2.yRot = 3.1415927f - $$2.yRot;
        }
    }
}
