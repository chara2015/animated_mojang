package net.labymod.v1_21_11.mixins.mojang.blaze3d.platform;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharModsCallback;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/mojang/blaze3d/platform/MixinInputConstants_PreeditSupport.class */
@Mixin({fyc.class})
public class MixinInputConstants_PreeditSupport {
    @WrapOperation(method = {"setupKeyboardCallbacks"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetCharModsCallback(JLorg/lwjgl/glfw/GLFWCharModsCallbackI;)Lorg/lwjgl/glfw/GLFWCharModsCallback;")})
    private static GLFWCharModsCallback labyMod$changeToCharCallback(long window, GLFWCharModsCallbackI cbfun, Operation<GLFWCharModsCallback> original) {
        GLFW.glfwSetCharCallback(window, (handle, codepoint) -> {
            cbfun.invoke(handle, codepoint, 0);
        });
        return null;
    }
}
