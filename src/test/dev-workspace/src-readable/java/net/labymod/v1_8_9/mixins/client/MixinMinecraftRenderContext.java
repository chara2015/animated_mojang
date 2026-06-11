package net.labymod.v1_8_9.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MixinMinecraftRenderContext.class */
@Mixin({ave.class})
public class MixinMinecraftRenderContext {
    @Inject(method = {"runGameLoop"}, at = {@At("HEAD")})
    private void labyMod$beginFrame(CallbackInfo ci) {
        labyMod$frameContextRegistry().beginFrame();
    }

    @Inject(method = {"runGameLoop"}, at = {@At("RETURN")})
    private void labyMod$endFrame(CallbackInfo ci) {
        labyMod$frameContextRegistry().endFrame();
    }

    private FrameContextRegistry labyMod$frameContextRegistry() {
        return Laby.labyAPI().gfxRenderPipeline().frameContextRegistry();
    }
}
