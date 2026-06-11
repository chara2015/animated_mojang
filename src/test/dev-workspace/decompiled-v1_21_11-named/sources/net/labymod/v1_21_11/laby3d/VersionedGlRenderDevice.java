package net.labymod.v1_21_11.laby3d;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;
import net.labymod.api.laby3d.LabyModResourceProvider;
import net.labymod.core.laby3d.opengl.OptimizedGlStateFactory;
import net.labymod.core.laby3d.pipeline.pass.ContextAwareCommandBuffer;
import net.labymod.laby3d.api.opengl.GlFunctions;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import net.labymod.laby3d.api.opengl.state.track.GlStateStack;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.shaders.ShaderConfiguration;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_21_11.client.gfx.pipeline.CommandEncoderPipelineExt;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/VersionedGlRenderDevice.class */
public class VersionedGlRenderDevice extends GlRenderDevice {
    public VersionedGlRenderDevice(ShaderConfiguration shaderConfiguration, int debuggerFlags) {
        super(new LabyModResourceProvider(), shaderConfiguration, debuggerFlags);
    }

    @NotNull
    protected GlFunctions createGlFunctions(@NotNull GLCapabilities capabilities, @NotNull Set<String> enabledExtensions) {
        return new VersionedGlFunctions(capabilities, enabledExtensions);
    }

    @NotNull
    public CommandBuffer createCommandBuffer(int flags) {
        return new ContextAwareCommandBuffer(this, super.createCommandBuffer(flags), () -> {
            CommandEncoderPipelineExt.invalidate(RenderSystem.getDevice().createCommandEncoder());
        }, flags);
    }

    @NotNull
    protected GlStateStack createGlStateStack() {
        return new GlStateStack(this, new OptimizedGlStateFactory());
    }

    public void copyTextureToTexture(@NotNull DeviceTextureView source, @NotNull DeviceTextureView destination) {
        storeState();
        super.copyTextureToTexture(source, destination);
        restoreState();
    }
}
