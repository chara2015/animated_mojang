package net.labymod.v1_21_1.mixins.imgui;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/imgui/MixinImGuiImplGlfw.class */
@Mixin(targets = {"imgui.glfw.ImGuiImplGlfw$CreateWindowFunction"}, remap = false)
public class MixinImGuiImplGlfw {
    @Redirect(method = {"accept"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwMakeContextCurrent(J)V"))
    private void labyMod$setIcon(long window) {
        GLFWUtil.setDebugWindowIcon(window);
        GLFW.glfwMakeContextCurrent(window);
    }
}
