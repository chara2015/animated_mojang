package net.labymod.v1_16_5.mixins.client.renderer.entity.entity;

import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/entity/entity/MixinFishingHookRenderer.class */
@Mixin({efa.class})
public abstract class MixinFishingHookRenderer {
    @Redirect(method = {"render(Lnet/minecraft/world/entity/projectile/FishingHook;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = @At(value = "NEW", target = "net/minecraft/world/phys/Vec3"))
    private dcn labyMod$renderFishingHook(double x, double y, double z) {
        FishingRodOldAnimation animation;
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldFishingRod().get().booleanValue() && (animation = (FishingRodOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(FishingRodOldAnimation.NAME)) != null) {
            FloatVector3 stringVector = animation.getStringVector();
            x += (double) stringVector.getX();
            y += (double) stringVector.getY();
        }
        return new dcn(x, y, z);
    }
}
