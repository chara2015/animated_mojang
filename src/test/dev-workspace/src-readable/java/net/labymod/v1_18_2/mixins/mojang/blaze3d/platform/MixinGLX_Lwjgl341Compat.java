package net.labymod.v1_18_2.mixins.mojang.blaze3d.platform;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.GLX;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/mojang/blaze3d/platform/MixinGLX_Lwjgl341Compat.class */
@Mixin({GLX.class})
public class MixinGLX_Lwjgl341Compat {
    @WrapOperation(method = {"_initGlfw"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwInit()Z")})
    private static boolean labyMod$glfwInit(Operation<Boolean> original) {
        GLFWUtil.preferX11OverWayland();
        Boolean result = (Boolean) original.call(new Object[0]);
        if (result.booleanValue()) {
            GLFWUtil.applyDefaultWindowHints();
        }
        return result.booleanValue();
    }
}
