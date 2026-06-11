package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.ImDrawData;
import imgui.gl3.ImGuiImplGl3;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.OperatingSystem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/ImGuiGlPipeline.class */
public class ImGuiGlPipeline {
    private final LabyImGuiImplGl2 gl2 = new LabyImGuiImplGl2();
    private final ImGuiImplGl3 gl3 = new ImGuiImplGl3();

    public void init(String glslVersion) {
        if (PlatformEnvironment.isNoShader() && OperatingSystem.isOSX()) {
            this.gl2.init();
        } else {
            this.gl3.init(glslVersion);
        }
    }

    public void renderData(ImDrawData data) {
        if (PlatformEnvironment.isNoShader() && OperatingSystem.isOSX()) {
            this.gl2.renderDrawData(data);
        } else {
            this.gl3.renderDrawData(data);
        }
    }
}
