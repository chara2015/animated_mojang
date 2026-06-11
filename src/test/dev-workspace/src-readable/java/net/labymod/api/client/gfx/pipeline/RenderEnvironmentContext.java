package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/RenderEnvironmentContext.class */
@Referenceable
public interface RenderEnvironmentContext {
    public static final int NO_OVERLAY = 655360;
    public static final int FULL_BRIGHT = 15728880;
    public static final float GAME_UNIT = 0.0625f;

    RenderAttributesStack renderAttributesStack();

    boolean isScreenContext();

    void setScreenContext(boolean z);

    ShaderPipeline shaderPipeline();

    int getPackedLight();

    void setPackedLight(int i);

    ScreenContext screenContext();

    default ShadowRenderPassContext shadowRenderPassContext() {
        return shaderPipeline().shadowRenderPassContext();
    }

    default int getPackedLightWithCondition() {
        return isScreenContext() ? FULL_BRIGHT : getPackedLight();
    }
}
