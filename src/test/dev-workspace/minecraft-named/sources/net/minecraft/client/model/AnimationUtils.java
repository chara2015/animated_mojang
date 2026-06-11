package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.UndeadRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.SwingAnimationType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/AnimationUtils.class */
public class AnimationUtils {
    public static void animateCrossbowHold(ModelPart $$0, ModelPart $$1, ModelPart $$2, boolean $$3) {
        ModelPart $$4 = $$3 ? $$0 : $$1;
        ModelPart $$5 = $$3 ? $$1 : $$0;
        $$4.yRot = ($$3 ? -0.3f : 0.3f) + $$2.yRot;
        $$5.yRot = ($$3 ? 0.6f : -0.6f) + $$2.yRot;
        $$4.xRot = (-1.5707964f) + $$2.xRot + 0.1f;
        $$5.xRot = (-1.5f) + $$2.xRot;
    }

    public static void animateCrossbowCharge(ModelPart $$0, ModelPart $$1, float $$2, float $$3, boolean $$4) {
        ModelPart $$5 = $$4 ? $$0 : $$1;
        ModelPart $$6 = $$4 ? $$1 : $$0;
        $$5.yRot = $$4 ? -0.8f : 0.8f;
        $$5.xRot = -0.97079635f;
        $$6.xRot = $$5.xRot;
        float $$7 = Mth.clamp($$3, 0.0f, $$2);
        float $$8 = $$7 / $$2;
        $$6.yRot = Mth.lerp($$8, 0.4f, 0.85f) * ($$4 ? 1 : -1);
        $$6.xRot = Mth.lerp($$8, $$6.xRot, -1.5707964f);
    }

    public static void swingWeaponDown(ModelPart $$0, ModelPart $$1, HumanoidArm $$2, float $$3, float $$4) {
        float $$5 = Mth.sin($$3 * 3.1415927f);
        float $$6 = Mth.sin((1.0f - ((1.0f - $$3) * (1.0f - $$3))) * 3.1415927f);
        $$0.zRot = 0.0f;
        $$1.zRot = 0.0f;
        $$0.yRot = 0.15707964f;
        $$1.yRot = -0.15707964f;
        if ($$2 == HumanoidArm.RIGHT) {
            $$0.xRot = (-1.8849558f) + (Mth.cos($$4 * 0.09f) * 0.15f);
            $$1.xRot = (-0.0f) + (Mth.cos($$4 * 0.19f) * 0.5f);
            $$0.xRot += ($$5 * 2.2f) - ($$6 * 0.4f);
            $$1.xRot += ($$5 * 1.2f) - ($$6 * 0.4f);
        } else {
            $$0.xRot = (-0.0f) + (Mth.cos($$4 * 0.19f) * 0.5f);
            $$1.xRot = (-1.8849558f) + (Mth.cos($$4 * 0.09f) * 0.15f);
            $$0.xRot += ($$5 * 1.2f) - ($$6 * 0.4f);
            $$1.xRot += ($$5 * 2.2f) - ($$6 * 0.4f);
        }
        bobArms($$0, $$1, $$4);
    }

    public static void bobModelPart(ModelPart $$0, float $$1, float $$2) {
        $$0.zRot += $$2 * ((Mth.cos($$1 * 0.09f) * 0.05f) + 0.05f);
        $$0.xRot += $$2 * Mth.sin($$1 * 0.067f) * 0.05f;
    }

    public static void bobArms(ModelPart $$0, ModelPart $$1, float $$2) {
        bobModelPart($$0, $$2, 1.0f);
        bobModelPart($$1, $$2, -1.0f);
    }

    public static <T extends UndeadRenderState> void animateZombieArms(ModelPart $$0, ModelPart $$1, boolean $$2, T $$3) {
        boolean $$4 = $$3.swingAnimationType != SwingAnimationType.STAB;
        if ($$4) {
            float $$5 = $$3.attackTime;
            float $$6 = (-3.1415927f) / ($$2 ? 1.5f : 2.25f);
            float $$7 = Mth.sin($$5 * 3.1415927f);
            float $$8 = Mth.sin((1.0f - ((1.0f - $$5) * (1.0f - $$5))) * 3.1415927f);
            $$1.zRot = 0.0f;
            $$1.yRot = -(0.1f - ($$7 * 0.6f));
            $$1.xRot = $$6;
            $$1.xRot += ($$7 * 1.2f) - ($$8 * 0.4f);
            $$0.zRot = 0.0f;
            $$0.yRot = 0.1f - ($$7 * 0.6f);
            $$0.xRot = $$6;
            $$0.xRot += ($$7 * 1.2f) - ($$8 * 0.4f);
        }
        bobArms($$1, $$0, $$3.ageInTicks);
    }
}
