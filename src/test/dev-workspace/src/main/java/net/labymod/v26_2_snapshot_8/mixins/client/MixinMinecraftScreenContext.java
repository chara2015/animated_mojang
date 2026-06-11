package net.labymod.v26_2_snapshot_8.mixins.client;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/MixinMinecraftScreenContext.class */
@Mixin({Minecraft.class})
public class MixinMinecraftScreenContext {
    @Inject(method = {"renderFrame"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;extract(Lnet/minecraft/client/DeltaTracker;Z)V", shift = At.Shift.BEFORE)})
    private void labyMod$enableScreenContext(CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }

    @Inject(method = {"renderFrame"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/GpuSurface;isAcquired()Z", ordinal = 1, shift = At.Shift.BEFORE)})
    private void labyMod$disableScreenContext(CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
}
