package net.labymod.v1_21_11.mixins.client.renderer.entity.entity;

import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/entity/MixinFishingHookRenderer.class */
@Mixin({FishingHookRenderer.class})
public abstract class MixinFishingHookRenderer {
    @Redirect(method = {"getPlayerHandPos"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera$NearPlane;getPointOnPlane(FF)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 labyMod$renderFishingHook(Camera.NearPlane instance, float x, float y) {
        FishingRodOldAnimation animation;
        if (((Boolean) Laby.labyAPI().config().multiplayer().classicPvP().oldFishingRod().get()).booleanValue() && (animation = LabyMod.getInstance().getOldAnimationRegistry().get("fishing_rod")) != null) {
            FloatVector3 stringVector = animation.getStringVector();
            x += stringVector.getX();
            y += stringVector.getY();
        }
        return instance.getPointOnPlane(x, y);
    }
}
