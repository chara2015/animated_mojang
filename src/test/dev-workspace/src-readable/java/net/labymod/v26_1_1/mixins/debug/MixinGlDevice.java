package net.labymod.v26_1_1.mixins.debug;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.shaders.GpuDebugOptions;
import com.mojang.blaze3d.shaders.ShaderSource;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.ImGuiPipeline;
import net.labymod.util.property.SystemProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/debug/MixinGlDevice.class */
@Mixin({GlDevice.class})
public class MixinGlDevice {
    @WrapOperation(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/shaders/GpuDebugOptions;useLabels()Z")})
    private boolean labyMod$initializeDebugLabels(GpuDebugOptions instance, Operation<Boolean> original) {
        boolean useLabels = ((Boolean) original.call(new Object[]{instance})).booleanValue();
        if (useLabels) {
            return useLabels;
        }
        return SystemProperties.RENDER_DEVICE_DEBUG_CONTEXT.get().booleanValue() || SystemProperties.RENDER_DEVICE_USER_DEBUG.get().booleanValue();
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$initializeImGui(long windowHandle, ShaderSource defaultShaderSource, GpuDebugOptions debugOptions, CallbackInfo ci) {
        ImGuiPipeline.getInstance().initialize(windowHandle);
    }
}
