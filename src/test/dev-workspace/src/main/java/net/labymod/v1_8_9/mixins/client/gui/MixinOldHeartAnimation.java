package net.labymod.v1_8_9.mixins.client.gui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.HeartOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinOldHeartAnimation.class */
@Mixin({avo.class})
public class MixinOldHeartAnimation {
    @WrapOperation(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 3)})
    private void labyMod$oldHeartBackground(avo instance, int x, int y, int u, int v, int uWidth, int vHeight, Operation<Void> original) {
        labyMod$applyOldHeartAnimation(instance, x, y, u, v, uWidth, vHeight, original);
    }

    @WrapOperation(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 4)})
    private void labyMod$oldFullHeartFlash(avo instance, int x, int y, int u, int v, int uWidth, int vHeight, Operation<Void> original) {
        labyMod$applyOldHeartAnimation(instance, x, y, u, v, uWidth, vHeight, original);
    }

    @WrapOperation(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 5)})
    private void labyMod$oldHalfHeartFlash(avo instance, int x, int y, int u, int v, int uWidth, int vHeight, Operation<Void> original) {
        labyMod$applyOldHeartAnimation(instance, x, y, u, v, uWidth, vHeight, original);
    }

    private void labyMod$applyOldHeartAnimation(avo instance, int x, int y, int u, int v, int uWidth, int vHeight, Operation<Void> original) {
        OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        HeartOldAnimation animation = (HeartOldAnimation) oldAnimationRegistry.get(HeartOldAnimation.NAME);
        if (animation != null) {
            u = animation.getU(u);
        }
        original.call(new Object[]{instance, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(u), Integer.valueOf(v), Integer.valueOf(uWidth), Integer.valueOf(vHeight)});
    }
}
