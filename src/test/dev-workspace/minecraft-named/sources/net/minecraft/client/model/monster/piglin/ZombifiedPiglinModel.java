package net.minecraft.client.model.monster.piglin;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.ZombifiedPiglinRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/piglin/ZombifiedPiglinModel.class */
public class ZombifiedPiglinModel extends AbstractPiglinModel<ZombifiedPiglinRenderState> {
    public ZombifiedPiglinModel(ModelPart $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.model.monster.piglin.AbstractPiglinModel, net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.Model
    public void setupAnim(ZombifiedPiglinRenderState $$0) {
        super.setupAnim($$0);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, $$0.isAggressive, $$0);
    }

    @Override // net.minecraft.client.model.monster.piglin.AbstractPiglinModel, net.minecraft.client.model.HumanoidModel
    public void setAllVisible(boolean $$0) {
        super.setAllVisible($$0);
        this.leftSleeve.visible = $$0;
        this.rightSleeve.visible = $$0;
        this.leftPants.visible = $$0;
        this.rightPants.visible = $$0;
        this.jacket.visible = $$0;
    }
}
