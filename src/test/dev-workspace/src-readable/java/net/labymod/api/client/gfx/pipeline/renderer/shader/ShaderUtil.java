package net.labymod.api.client.gfx.pipeline.renderer.shader;

import net.labymod.api.Laby;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/shader/ShaderUtil.class */
public final class ShaderUtil {
    public static boolean isShaderSelected() {
        return Laby.references().shaderPipeline().hasActiveShaderPack();
    }
}
