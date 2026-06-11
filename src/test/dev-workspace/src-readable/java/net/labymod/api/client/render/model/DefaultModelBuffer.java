package net.labymod.api.client.render.model;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.compiler.ModelCompiler;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/DefaultModelBuffer.class */
public class DefaultModelBuffer implements ModelBuffer {
    private final Model model;
    private boolean screenContext;
    private boolean immediate;
    private ResourceLocation resourceLocation = Textures.WHITE;
    private int argb = -1;
    private final ModelCompiler modelCompiler = new ModelCompiler(false);

    public DefaultModelBuffer(@NotNull Model model, boolean screenContext) {
        this.model = model;
        this.screenContext = screenContext;
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    @NotNull
    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public void setResourceLocation(@Nullable ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            resourceLocation = Textures.WHITE;
        }
        this.resourceLocation = resourceLocation;
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public void render(@NotNull CommandBuffer cmd, @NotNull Stack stack) {
        renderImmediate(cmd, stack);
        setARGB(-1);
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public void submitToCanvas(@NotNull ScreenContext context, float left, float top, float right, float bottom, float scale, @Nullable Consumer<Stack> offscreenStackConsumer) {
        ScreenCanvas canvas = context.canvas();
        canvas.submitDynamicOffscreen(left, top, right, bottom, scale, null, new CanvasDynamicRenderer(this, offscreenStackConsumer));
    }

    private void renderImmediate(@Nullable CommandBuffer cmd, Stack stack) {
        Laby3D laby3D = Laby.references().laby3D();
        VertexConsumer vertexConsumerBegin = laby3D.begin(DrawingMode.QUADS, VertexDescriptions.MODEL);
        ColorFormat colorFormat = ColorFormat.ARGB32;
        float red = colorFormat.normalizedRed(this.argb);
        float green = colorFormat.normalizedGreen(this.argb);
        float blue = colorFormat.normalizedBlue(this.argb);
        float alpha = colorFormat.normalizedAlpha(this.argb);
        RenderEnvironmentContext envContext = Laby.references().renderEnvironmentContext();
        int lightCoords = envContext.getPackedLight();
        for (ModelPart child : this.model.getChildren().values()) {
            this.modelCompiler.compile(stack, vertexConsumerBegin, child, red, green, blue, alpha, lightCoords, RenderEnvironmentContext.NO_OVERLAY);
        }
        ShaderTextures.setShaderTexture(0, this.resourceLocation);
        if (this.screenContext) {
            ShaderTextures.setShaderTexture(1, Textures.WHITE);
            ShaderTextures.setShaderTexture(2, Textures.WHITE);
        } else {
            laby3D.setupOverlayAndLightingTextures();
        }
        if (cmd == null) {
            MeshRenderer.drawImmediate(vertexConsumerBegin.build(), RenderStates.TRANSLUCENT_EMOTES);
        } else {
            MeshRenderer.drawImmediate(cmd, vertexConsumerBegin.build(), RenderStates.TRANSLUCENT_EMOTES, c -> {
            });
        }
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public void rebuildModel() {
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public boolean isImmediate() {
        return this.immediate;
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public int getARGB() {
        return this.argb;
    }

    @Override // net.labymod.api.client.render.model.ModelBuffer
    public void setARGB(int argb) {
        this.argb = argb;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/DefaultModelBuffer$CanvasDynamicRenderer.class */
    static class CanvasDynamicRenderer implements DynamicOffscreenRenderState.DynamicRenderer {
        private final DefaultModelBuffer buffer;

        @Nullable
        private final Consumer<Stack> offscreenConsumer;

        CanvasDynamicRenderer(DefaultModelBuffer buffer, @Nullable Consumer<Stack> offscreenConsumer) {
            this.buffer = buffer;
            this.offscreenConsumer = offscreenConsumer;
        }

        @Override // net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState.DynamicRenderer
        public void render(DynamicOffscreenRenderState renderState, OffscreenContext context, Stack stack) {
            if (this.offscreenConsumer != null) {
                this.offscreenConsumer.accept(stack);
            }
            this.buffer.render(context.commandBuffer(), stack);
        }
    }
}
