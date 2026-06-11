package net.labymod.v1_8_9.mixins.client.renderer.entity.entity;

import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/entity/MixinRenderFish.class */
@Mixin({biz.class})
public abstract class MixinRenderFish {
    @Redirect(method = {"doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V"}, at = @At(value = "NEW", target = "Lnet/minecraft/util/Vec3;<init>"))
    private aui labyMod$renderFishingHook(double x, double y, double z) {
        FishingRodOldAnimation animation;
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldFishingRod().get().booleanValue() && (animation = (FishingRodOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(FishingRodOldAnimation.NAME)) != null) {
            FloatVector3 stringVector = animation.getStringVector();
            x += (double) stringVector.getX();
            y += (double) stringVector.getY();
        }
        return new aui(x, y, z);
    }
}
