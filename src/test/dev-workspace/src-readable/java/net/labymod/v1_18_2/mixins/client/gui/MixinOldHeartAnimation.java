package net.labymod.v1_18_2.mixins.client.gui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.HeartOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/MixinOldHeartAnimation.class */
@Mixin({dzq.class})
public class MixinOldHeartAnimation {
    @WrapOperation(method = {"renderHeart"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V")})
    private void labyMod$applyOldHeartAnimation(dzq instance, dtm poseStack, int i0, int i1, int i2, int i3, int i4, int i5, Operation<Void> original) {
        OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        HeartOldAnimation animation = (HeartOldAnimation) oldAnimationRegistry.get(HeartOldAnimation.NAME);
        if (animation != null) {
            i2 = animation.getU(i2);
        }
        original.call(new Object[]{instance, poseStack, Integer.valueOf(i0), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)});
    }
}
