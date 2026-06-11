package net.labymod.v1_21_4.mixins.client;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/MixinMinecraftScreenContext.class */
@Mixin({flk.class})
public class MixinMinecraftScreenContext {
    @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableCull()V", shift = At.Shift.AFTER)})
    private void labyMod$enableScreenContext(boolean running, CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }

    @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;unbindWrite()V", shift = At.Shift.BEFORE)})
    private void labyMod$disableScreenContext(boolean running, CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
}
