package net.labymod.v1_18_2.mixins.client.player;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/player/MixinInput.class */
@Mixin({epu.class})
public class MixinInput {

    @Shadow
    public float b;

    @Insert(method = {"hasForwardImpulse()Z"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$oldSlowdown(InsertInfoReturnable<Boolean> returnable) {
        OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        SlowdownOldAnimation animation = (SlowdownOldAnimation) oldAnimationRegistry.get(SlowdownOldAnimation.NAME);
        if (animation != null && animation.isEnabled()) {
            returnable.setReturnValue(Boolean.valueOf(animation.isEnabled(this.b)));
        }
    }
}
