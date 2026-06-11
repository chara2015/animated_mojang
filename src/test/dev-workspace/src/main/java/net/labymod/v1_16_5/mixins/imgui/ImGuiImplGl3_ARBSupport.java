package net.labymod.v1_16_5.mixins.imgui;

import imgui.gl3.ImGuiImplGl3;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLVertexArrayObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/imgui/ImGuiImplGl3_ARBSupport.class */
@Mixin(value = {ImGuiImplGl3.class}, remap = false)
public class ImGuiImplGl3_ARBSupport {
    @Redirect(method = {"createDeviceObjects", "restoreModifiedGlState", "bind"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL32;glBindVertexArray(I)V"))
    private void labyMod$glBindVertexArray(int id) {
        OpenGLVertexArrayObject.bind(id);
    }

    @Redirect(method = {"unbind"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL32;glDeleteVertexArrays(I)V"))
    private void labyMod$glDeleteVertexArrays(int id) {
        OpenGLVertexArrayObject.delete(id);
    }

    @Redirect(method = {"bind"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL32;glGenVertexArrays()I"))
    private int labyMod$glGenVertexArrays() {
        return OpenGLVertexArrayObject.generate();
    }
}
