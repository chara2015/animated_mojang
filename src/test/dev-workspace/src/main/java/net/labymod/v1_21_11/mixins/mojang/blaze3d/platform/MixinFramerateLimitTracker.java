package net.labymod.v1_21_11.mixins.mojang.blaze3d.platform;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.event.client.lifecycle.GameFpsLimitEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/mojang/blaze3d/platform/MixinFramerateLimitTracker.class */
@Mixin({fya.class})
public class MixinFramerateLimitTracker {
    @Insert(method = {"getFramerateLimit"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$getFramerateLimit(InsertInfoReturnable<Integer> iir) {
        GameFpsLimitEventCaller.callEvent(iir);
    }
}
