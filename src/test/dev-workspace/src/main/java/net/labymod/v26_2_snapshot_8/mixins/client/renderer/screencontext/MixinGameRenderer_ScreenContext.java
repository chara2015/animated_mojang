package net.labymod.v26_2_snapshot_8.mixins.client.renderer.screencontext;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/screencontext/MixinGameRenderer_ScreenContext.class */
@Mixin({GameRenderer.class})
public class MixinGameRenderer_ScreenContext {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/CommandEncoder;clearDepthTexture(Lcom/mojang/blaze3d/textures/GpuTexture;D)V", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$enableScreenContext(DeltaTracker $$0, boolean $$1, CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
