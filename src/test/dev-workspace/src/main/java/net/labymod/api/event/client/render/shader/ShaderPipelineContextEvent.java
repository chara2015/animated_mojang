package net.labymod.api.event.client.render.shader;

import java.util.function.BooleanSupplier;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.event.Event;
import net.labymod.api.event.ReplayableEvent;
import net.labymod.api.util.debug.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/shader/ShaderPipelineContextEvent.class */
@ReplayableEvent
public class ShaderPipelineContextEvent implements Event {

    @Nullable
    private ShadowRenderPassContext shadowRenderPassContext;

    @NotNull
    private BooleanSupplier activeShaderPackSupplier = () -> {
        return false;
    };

    @Nullable
    public ShadowRenderPassContext getShadowRenderPassContext() {
        return this.shadowRenderPassContext;
    }

    public void setShadowRenderPassContext(@Nullable ShadowRenderPassContext shadowRenderPassContext) {
        this.shadowRenderPassContext = shadowRenderPassContext;
    }

    @NotNull
    public BooleanSupplier getActiveShaderPackSupplier() {
        return this.activeShaderPackSupplier;
    }

    public void setActiveShaderPackSupplier(BooleanSupplier activeShaderPackSupplier) {
        Preconditions.notNull(activeShaderPackSupplier, "activeShaderPackSupplier");
        this.activeShaderPackSupplier = activeShaderPackSupplier;
    }
}
