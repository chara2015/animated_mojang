package net.labymod.v1_21_1.mixins.client.window;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/window/MixinWindow_Lwjgl341Compat.class */
@Mixin({fam.class})
public class MixinWindow_Lwjgl341Compat {
    @WrapOperation(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwDefaultWindowHints()V")})
    private void labyMod$applyLwjgl341Compat(Operation<Void> original) {
        GLFWUtil.applyDefaultWindowHints();
    }
}
