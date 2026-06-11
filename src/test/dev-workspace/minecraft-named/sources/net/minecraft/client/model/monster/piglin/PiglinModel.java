package net.minecraft.client.model.monster.piglin;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/piglin/PiglinModel.class */
public class PiglinModel extends AbstractPiglinModel<PiglinRenderState> {
    public PiglinModel(ModelPart $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.model.monster.piglin.AbstractPiglinModel, net.minecraft.client.model.HumanoidModel, net.minecraft.client.model.Model
    public void setupAnim(PiglinRenderState $$0) {
        super.setupAnim($$0);
        float $$2 = $$0.attackTime;
        PiglinArmPose $$3 = $$0.armPose;
        if ($$3 == PiglinArmPose.DANCING) {
            float $$4 = $$0.ageInTicks / 60.0f;
            this.rightEar.zRot = 0.5235988f + (0.017453292f * Mth.sin($$4 * 30.0f) * 10.0f);
            this.leftEar.zRot = (-0.5235988f) - ((0.017453292f * Mth.cos($$4 * 30.0f)) * 10.0f);
            this.head.x += Mth.sin($$4 * 10.0f);
            this.head.y += Mth.sin($$4 * 40.0f) + 0.4f;
            this.rightArm.zRot = 0.017453292f * (70.0f + (Mth.cos($$4 * 40.0f) * 10.0f));
            this.leftArm.zRot = this.rightArm.zRot * (-1.0f);
            this.rightArm.y += (Mth.sin($$4 * 40.0f) * 0.5f) - 0.5f;
            this.leftArm.y += (Mth.sin($$4 * 40.0f) * 0.5f) + 0.5f;
            this.body.y += Mth.sin($$4 * 40.0f) * 0.35f;
            return;
        }
        if ($$3 == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON && $$2 == 0.0f) {
            holdWeaponHigh($$0);
            return;
        }
        if ($$3 == PiglinArmPose.CROSSBOW_HOLD) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, $$0.mainArm == HumanoidArm.RIGHT);
            return;
        }
        if ($$3 == PiglinArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, $$0.maxCrossbowChageDuration, $$0.ticksUsingItem, $$0.mainArm == HumanoidArm.RIGHT);
            return;
        }
        if ($$3 == PiglinArmPose.ADMIRING_ITEM) {
            this.head.xRot = 0.5f;
            this.head.yRot = 0.0f;
            if ($$0.mainArm == HumanoidArm.LEFT) {
                this.rightArm.yRot = -0.5f;
                this.rightArm.xRot = -0.9f;
            } else {
                this.leftArm.yRot = 0.5f;
                this.leftArm.xRot = -0.9f;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.model.HumanoidModel
    public void setupAttackAnimation(PiglinRenderState $$0) {
        float $$1 = $$0.attackTime;
        if ($$1 > 0.0f && $$0.armPose == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON) {
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, $$0.mainArm, $$1, $$0.ageInTicks);
        } else {
            super.setupAttackAnimation($$0);
        }
    }

    private void holdWeaponHigh(PiglinRenderState $$0) {
        if ($$0.mainArm == HumanoidArm.LEFT) {
            this.leftArm.xRot = -1.8f;
        } else {
            this.rightArm.xRot = -1.8f;
        }
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
