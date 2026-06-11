package net.labymod.v26_2_snapshot_8.mixins.client.renderer.screencontext;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/screencontext/MixinGui_ScreenContext.class */
@Mixin({Gui.class})
public class MixinGui_ScreenContext {
    @Inject(method = {"extractRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;getScaledXPos(Lcom/mojang/blaze3d/platform/Window;)D", shift = At.Shift.BEFORE)})
    private void labyMod$disableScreenContext(DeltaTracker deltaTracker, boolean shouldRenderLevel, boolean resourcesLoaded, CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
}
