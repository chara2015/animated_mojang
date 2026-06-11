package net.minecraft.client.model.monster.zombie;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/zombie/AbstractZombieModel.class */
public abstract class AbstractZombieModel<S extends ZombieRenderState> extends HumanoidModel<S> {
    protected AbstractZombieModel(ModelPart $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.Model
    public void setupAnim(S $$0) {
        super.setupAnim($$0);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, $$0.isAggressive, $$0);
    }
}
