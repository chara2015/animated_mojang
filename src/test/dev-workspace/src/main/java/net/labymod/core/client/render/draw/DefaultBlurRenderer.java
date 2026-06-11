package net.labymod.core.client.render.draw;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.draw.BlurRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.labymod.model.HighQuality;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.shaders.block.BlurDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.BlurDataUniformBlockData;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlockData;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.models.Implements;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.pipeline.target.RenderTargetDescription;
import net.labymod.laby3d.api.pipeline.target.attachment.AttachmentType;
import net.labymod.laby3d.api.pipeline.target.attachment.ClearValue;
import net.labymod.laby3d.api.pipeline.target.attachment.RenderTargetAttachmentDescription;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/DefaultBlurRenderer.class */
@Singleton
@Implements(BlurRenderer.class)
public class DefaultBlurRenderer implements BlurRenderer {
    private static final CachedOrthoProjectionMatrix<Matrix4f> BLUR_PROJECTION_MATRIX = CachedOrthoProjectionMatrix.simple(0.1f, 1000.0f, false);
    private static final int MAX_RENDER_TARGETS = 4;
    private static final int MAX_WIDTH = 1280;
    private static final int MAX_HEIGHT = 720;
    private final RenderTarget destinationTarget;
    private final RenderTarget[] renderTargets;
    private RenderTarget cachedBlurSource;
    private RenderTarget cachedBlurResult;
    private int cachedBlurRadius;
    private final BorderRadius borderRadius = new BorderRadius();
    private final Laby3D laby3D = Laby.references().laby3D();

    @Inject
    public DefaultBlurRenderer(EventBus eventBus) {
        RenderTargetDescription renderTargetDescription = RenderTargetDescription.builder().addColorAttachment(RenderTargetAttachmentDescription.builder().setName(VertexDescriptions.COLOR_NAME).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).setType(AttachmentType.COLOR).setClearValue(ClearValue.color(0.0f, 0.0f, 0.0f, 0.0f)).setSamplerDescription(builder -> {
            builder.setFilter(SamplerDescription.Filter.NEAREST);
        }).build()).setSize(1280, 720).build();
        this.destinationTarget = this.laby3D.renderDevice().createTarget("Blur Destination", renderTargetDescription);
        this.renderTargets = createRenderTargets();
        resizeRenderTargets();
        eventBus.registerListener(this);
    }

    private RenderTarget[] createRenderTargets() {
        RenderTarget[] targets = new RenderTarget[4];
        for (int index = 0; index < 4; index++) {
            RenderTargetDescription renderTargetDescription = RenderTargetDescription.builder().addColorAttachment(RenderTargetAttachmentDescription.builder().setName(VertexDescriptions.COLOR_NAME).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).setType(AttachmentType.COLOR).setClearValue(ClearValue.color(0.0f, 0.0f, 0.0f, 0.0f)).setSamplerDescription(builder -> {
                builder.setFilter(SamplerDescription.Filter.NEAREST);
            }).build()).setSize(1280 >> index, 720 >> index).build();
            int finalIndex = index;
            RenderTarget newTarget = this.laby3D.renderDevice().createTarget("RT(" + finalIndex + ")", renderTargetDescription);
            targets[index] = newTarget;
        }
        return targets;
    }

    @Subscribe
    public void onWindowResize(WindowResizeEvent event) {
        resizeRenderTargets();
    }

    @Override // net.labymod.api.client.render.draw.BlurRenderer
    public void invalidateCache() {
        this.cachedBlurSource = null;
        this.cachedBlurResult = null;
        this.cachedBlurRadius = 0;
    }

    @Override // net.labymod.api.client.render.draw.BlurRenderer
    public void renderRectangle(@NotNull ScreenContext context, @NotNull AbstractWidget<?> widget, int radius) {
        HighQuality quality;
        RenderTarget sourceTarget;
        ThemeService themeService = Laby.references().themeService();
        FancyThemeConfig config = (FancyThemeConfig) themeService.getThemeConfig(FancyThemeConfig.class);
        if (config == null || (quality = config.blurQuality().get()) == HighQuality.NONE) {
            return;
        }
        RenderTarget activityRenderTarget = Laby.labyAPI().minecraft().mainTarget();
        if (this.cachedBlurSource == activityRenderTarget && this.cachedBlurRadius == radius && this.cachedBlurResult != null) {
            sourceTarget = this.cachedBlurResult;
        } else {
            RenderTarget[] usedRenderTargets = (RenderTarget[]) Arrays.copyOf(this.renderTargets, this.renderTargets.length);
            float offset = radius * 0.5f;
            if (quality == HighQuality.MEDIUM) {
                offset *= 0.5f;
            } else if (quality == HighQuality.LOW) {
                offset *= 0.25f;
            }
            processKawaseBlurDown(context, activityRenderTarget, offset, usedRenderTargets);
            processKawaseBlurUp(context, offset, usedRenderTargets);
            sourceTarget = usedRenderTargets[0];
            this.cachedBlurSource = activityRenderTarget;
            this.cachedBlurResult = sourceTarget;
            this.cachedBlurRadius = radius;
        }
        BorderRadius borderRadius = widget.getBorderRadius();
        this.borderRadius.set(borderRadius == null ? BorderRadius.EMPTY : borderRadius);
        RenderTarget destinationTarget = this.laby3D.renderTargetPool().acquire(sourceTarget);
        renderBlur(context, widget.bounds().rectangle(BoundsType.MIDDLE), sourceTarget, destinationTarget);
    }

    private void processKawaseBlurDown(ScreenContext context, RenderTarget baseTarget, float offset, RenderTarget[] renderTargets) {
        int index = 0;
        while (index < 4) {
            RenderTarget sourceTarget = index == 0 ? baseTarget : renderTargets[index - 1];
            RenderTarget destination = this.laby3D.renderTargetPool().acquire(renderTargets[index]);
            renderTargets[index] = destination;
            render(context, sourceTarget, destination, BlurRenderer.BlurAlgorithm.KAWASE_DOWN, offset);
            index++;
        }
    }

    private void processKawaseBlurUp(ScreenContext context, float offset, RenderTarget[] renderTargets) {
        int index = 3;
        while (index >= 0) {
            RenderTarget destination = renderTargets[index];
            RenderTarget sourceTarget = index == 3 ? destination : renderTargets[index + 1];
            render(context, sourceTarget, destination, BlurRenderer.BlurAlgorithm.KAWASE_UP, offset);
            index--;
        }
    }

    private void renderBlur(ScreenContext context, Rectangle rectangle, RenderTarget sourceTarget, RenderTarget destinationTarget) {
        float alpha = Laby.references().renderPipeline().getAlpha();
        int argb = ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, alpha);
        ScreenCanvas canvas = context.canvas();
        canvas.submitBlitRenderTarget(0.0f, 0.0f, sourceTarget.width(), sourceTarget.height(), destinationTarget, sourceTarget);
        canvas.submitGuiBlit(GuiMaterial.textured(RenderStates.POST_PROCESSING_ROUNDED, destinationTarget.findColorTexture(0)), rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), UVCoordinates.DEFAULT, argb, RoundedData.builder().applyBorderRadius(this.borderRadius).setBorderThickness(0.0f).build());
        this.borderRadius.set(BorderRadius.EMPTY);
    }

    private void render(ScreenContext context, RenderTarget source, RenderTarget destination, BlurRenderer.BlurAlgorithm algorithm, float offset) {
        Stack stack = context.stack();
        stack.push();
        stack.identity();
        ScreenCanvas canvas = context.canvas();
        canvas.submitPostProcessing(RenderStates.getGuiBlur(algorithm), source, destination, 0.0f, 0.0f, destination.width(), destination.height(), command -> {
            BlurDataUniformBlockData blurData = new BlurDataUniformBlockData();
            blurData.offset().set(offset);
            command.bindUniformBlock(BlurDataUniformBlock.NAME, this.laby3D.blurData());
            command.bindUniformBlockData(BlurDataUniformBlock.NAME, blurData);
            ProjectionUniformBlockData projectionData = new ProjectionUniformBlockData();
            projectionData.projectionMatrix().set(BLUR_PROJECTION_MATRIX.getCached(destination.width(), destination.height()));
            command.bindUniformBlockData("Projection", projectionData);
        });
        stack.pop();
    }

    private void resizeRenderTargets() {
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        resizeRenderTargets(window.getRawWidth(), window.getRawHeight());
    }

    private void resizeRenderTargets(int width, int height) {
        this.destinationTarget.resize(width, height);
        if (width > 1280) {
            width = 1280;
        }
        if (height > 720) {
            height = 720;
        }
        int finalWidth = width;
        int finalHeight = height;
        resizeTargets(this.renderTargets, finalWidth, finalHeight);
    }

    private void resizeTargets(RenderTarget[] renderTargets, int width, int height) {
        for (int index = 0; index < 4; index++) {
            if (index == 0) {
                renderTargets[index].resize(width, height);
            } else {
                int downSample = index + 1;
                RenderTarget renderTarget = renderTargets[index];
                renderTarget.resize(width / downSample, height / downSample);
            }
        }
    }
}
