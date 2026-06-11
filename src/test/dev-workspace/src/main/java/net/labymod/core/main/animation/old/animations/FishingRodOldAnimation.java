package net.labymod.core.main.animation.old.animations;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/FishingRodOldAnimation.class */
public class FishingRodOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "fishing_rod";
    public static final boolean LEGACY_PVP = MinecraftVersions.V1_8_9.orOlder();
    private static final FloatVector3 STRING_VECTOR = new FloatVector3(-0.25625f, 0.13f, 0.0f);
    private static final FloatVector3 V1_16_5_STRING_VECTOR = new FloatVector3(0.089375f, 0.03f, 0.0f);
    private static final FloatVector3 V1_12_2_STRING_VECTOR = new FloatVector3(0.14625f, 0.055f, 0.0f);
    private static final FloatVector3 V1_8_9_STRING_VECTOR = new FloatVector3(0.11125f, -0.015625f, 0.0f);

    public FishingRodOldAnimation() {
        super(NAME);
    }

    @Subscribe
    public void onRenderItemInHand(RenderFirstPersonItemInHandEvent event) {
        if (!event.itemStack().isFishingTool() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM || !isEnabled()) {
            return;
        }
        Stack stack = event.stack();
        if (LEGACY_PVP) {
            stack.scale(0.78f, 0.78f, 0.78f);
            stack.rotate(6.0f, 0.0f, 1.0f, 0.0f);
            stack.translate(0.0f, 0.325f, -0.45f);
        } else {
            int position = event.hand() == LivingEntity.Hand.MAIN_HAND ? 1 : -1;
            stack.scale(0.88f, 0.88f, 0.88f);
            stack.rotate(position * 6.0f, 0.0f, 1.0f, 0.0f);
            stack.rotate(position * 1.0f, -1.0f, 0.0f, 0.0f);
            stack.translate(0.009375f, 0.16875f, -0.16875f);
        }
    }

    public FloatVector3 getStringVector() {
        if (MinecraftVersions.V1_16_5.isCurrent()) {
            return V1_16_5_STRING_VECTOR;
        }
        if (MinecraftVersions.V1_12_2.isCurrent()) {
            return V1_12_2_STRING_VECTOR;
        }
        if (MinecraftVersions.V1_8_9.isCurrent()) {
            return V1_8_9_STRING_VECTOR;
        }
        return STRING_VECTOR;
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldFishingRod());
    }
}
