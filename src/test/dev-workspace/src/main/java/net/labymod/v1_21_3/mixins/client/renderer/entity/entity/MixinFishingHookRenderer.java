package net.labymod.v1_21_3.mixins.client.renderer.entity.entity;

import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/entity/entity/MixinFishingHookRenderer.class */
@Mixin({grx.class})
public abstract class MixinFishingHookRenderer {
    @Redirect(method = {"getPlayerHandPos"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera$NearPlane;getPointOnPlane(FF)Lnet/minecraft/world/phys/Vec3;"))
    private fby labyMod$renderFishingHook(a instance, float x, float y) {
        FishingRodOldAnimation animation;
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldFishingRod().get().booleanValue() && (animation = (FishingRodOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(FishingRodOldAnimation.NAME)) != null) {
            FloatVector3 stringVector = animation.getStringVector();
            x += stringVector.getX();
            y += stringVector.getY();
        }
        return instance.a(x, y);
    }
}
