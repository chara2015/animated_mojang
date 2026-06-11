package net.labymod.v26_1_1.mixins.client.player;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;
import net.minecraft.client.player.ClientInput;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/player/MixinInput.class */
@Mixin({ClientInput.class})
public abstract class MixinInput {
    @Shadow
    public abstract Vec2 getMoveVector();

    @Insert(method = {"hasForwardImpulse()Z"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$oldSlowdown(InsertInfoReturnable<Boolean> returnable) {
        OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        SlowdownOldAnimation animation = (SlowdownOldAnimation) oldAnimationRegistry.get(SlowdownOldAnimation.NAME);
        if (animation != null && animation.isEnabled()) {
            returnable.setReturnValue(Boolean.valueOf(animation.isEnabled(getMoveVector().y)));
        }
    }
}
