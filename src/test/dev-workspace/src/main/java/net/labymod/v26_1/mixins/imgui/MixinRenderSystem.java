package net.labymod.v26_1.mixins.imgui;

import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/imgui/MixinRenderSystem.class */
@Mixin({RenderSystem.class})
public class MixinRenderSystem {
    @Inject(method = {"initRenderer"}, at = {@At("TAIL")})
    private static void labyMod$initializeImGui(GpuDevice device, CallbackInfo ci) {
    }
}
