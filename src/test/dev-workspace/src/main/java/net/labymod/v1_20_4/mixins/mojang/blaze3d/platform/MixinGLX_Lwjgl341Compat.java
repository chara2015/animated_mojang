package net.labymod.v1_20_4.mixins.mojang.blaze3d.platform;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.GLX;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import org.lwjgl.system.FunctionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/mojang/blaze3d/platform/MixinGLX_Lwjgl341Compat.class */
@Mixin({GLX.class})
public class MixinGLX_Lwjgl341Compat {
    @WrapOperation(method = {"_initGlfw"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL;getFunctionProvider()Lorg/lwjgl/system/FunctionProvider;")})
    private static FunctionProvider labyMod$disableGlxGetProcAddress(Operation<FunctionProvider> original) {
        return null;
    }

    @WrapOperation(method = {"_initGlfw"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFWNativeGLX;setPath(Lorg/lwjgl/system/FunctionProvider;)V")})
    private static void labyMod$disableGlxPath(FunctionProvider sharedLibrary, Operation<Void> original) {
        if (sharedLibrary != null) {
            original.call(new Object[]{sharedLibrary});
        }
    }

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
