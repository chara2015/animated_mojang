package net.labymod.v26_1.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/MixinMinecraftRenderContext.class */
@Mixin({Minecraft.class})
public class MixinMinecraftRenderContext {
    @Inject(method = {"runTick"}, at = {@At("HEAD")})
    private void labyMod$beginFrame(boolean param0, CallbackInfo ci) {
        labyMod$frameContextRegistry().beginFrame();
    }

    @Inject(method = {"runTick"}, at = {@At("RETURN")})
    private void labyMod$endFrame(boolean param0, CallbackInfo ci) {
        labyMod$frameContextRegistry().endFrame();
    }

    private FrameContextRegistry labyMod$frameContextRegistry() {
        return Laby.labyAPI().gfxRenderPipeline().frameContextRegistry();
    }
}
