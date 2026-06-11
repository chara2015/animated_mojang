package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenContext;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenStrategy;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/ModelWidget.class */
@AutoWidget
public class ModelWidget extends SimpleWidget {
    private static final float MODEL_SCALE = 16.0f;
    protected final AnimationController animationController;
    protected final float modelWidth;
    protected final float modelHeight;

    @ApiStatus.Internal
    private final ResourceLocation id;
    protected Model model;
    protected ModelBuffer modelBuffer;
    private boolean immediate;
    protected final LssProperty<Integer> modelColor = new LssProperty<>(-1);
    protected final LssProperty<Float> baseScale = new LssProperty<>(Float.valueOf(1.0f));
    protected final LssProperty<Float> modelOffset = new LssProperty<>(Float.valueOf(0.0f));
    protected final LssProperty<OffscreenStrategy> submissionStrategy = new LssProperty<>(OffscreenStrategy.DYNAMIC);
    protected ResourceLocation baseTextureLocation = Textures.EMPTY;
    protected final FloatVector3 translation = new FloatVector3();
    protected final FloatVector3 rotation = new FloatVector3();
    protected final FloatVector3 scale = new FloatVector3(1.0f);
    protected final FloatVector3 pivotPoint = new FloatVector3();

    public ModelWidget(ResourceLocation id, Model model, AnimationController animationController, float modelWidth, float modelHeight) {
        this.id = id;
        this.animationController = animationController;
        this.modelWidth = modelWidth;
        this.modelHeight = modelHeight;
        setModel(model);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        int modelColor = ColorUtil.lerpedColor(backgroundColorTransitionDuration().get().longValue(), context.getTickDelta(), this.modelColor);
        renderModel(context, ColorUtil.combineAlpha(modelColor), context.getTickDelta());
    }

    public void renderModel(ScreenContext context, final int color, final float tickDelta) {
        this.animationController.applyAnimation(this.model, new String[0]);
        Bounds bounds = bounds();
        ScreenCanvas canvas = context.canvas();
        float scale = baseScale().get().floatValue();
        DynamicOffscreenRenderState.DynamicRenderer renderer = new DynamicOffscreenRenderState.DynamicRenderer(this) { // from class: net.labymod.api.client.gui.screen.widget.widgets.ModelWidget.1
            final /* synthetic */ ModelWidget this$0;

            {
                this.this$0 = this;
            }

            @Override // net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState.DynamicRenderer
            public void render(DynamicOffscreenRenderState renderState, OffscreenContext ctx, Stack stack) {
                this.this$0.renderModel(ctx, stack, color, tickDelta);
            }

            @Override // net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState.DynamicRenderer
            public float getTranslateY(int height, int guiScale) {
                return height + this.this$0.modelOffset().get().floatValue();
            }
        };
        canvas.submitModelRender(this.submissionStrategy.get(), bounds, scale, null, renderer);
    }

    protected void renderModel(OffscreenContext context, Stack stack, int color, float tickDelta) {
        this.modelBuffer.setARGB(color);
        stack.push();
        stack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
        stack.rotateRadians(0.15f, 1.0f, 0.0f, 0.0f);
        FloatVector3 pivot = pivotPoint();
        stack.translate(pivot.getX() / MODEL_SCALE, pivot.getY() / MODEL_SCALE, pivot.getZ() / MODEL_SCALE);
        FloatVector3 rotation = rotation();
        stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
        stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
        stack.rotateRadians(rotation.getX(), 1.0f, 0.0f, 0.0f);
        FloatVector3 scale = scale();
        stack.scale(scale.getX(), scale.getY(), scale.getZ());
        FloatVector3 translation = translation();
        stack.translate(((-pivot.getX()) + translation.getX()) / MODEL_SCALE, ((-pivot.getY()) + translation.getY()) / MODEL_SCALE, ((-pivot.getZ()) + translation.getZ()) / MODEL_SCALE);
        this.modelBuffer.render(context.commandBuffer(), stack);
        ConfigProperty<RenderMode> renderMode = this.labyAPI.config().ingame().cosmetics().renderMode();
        RenderMode prevRenderMode = renderMode.get();
        try {
            renderMode.set(RenderMode.IMMEDIATE);
            renderModelAttachments(context.commandBuffer(), stack, RenderEnvironmentContext.FULL_BRIGHT, tickDelta);
            renderMode.set(prevRenderMode);
            stack.pop();
        } catch (Throwable th) {
            renderMode.set(prevRenderMode);
            throw th;
        }
    }

    protected void renderModelAttachments(CommandBuffer cmd, Stack stack, int color, float tickDelta) {
    }

    public FloatVector3 translation() {
        return this.translation;
    }

    public void setTranslation(FloatVector3 translation) {
        this.translation.set(translation);
    }

    public FloatVector3 rotation() {
        return this.rotation;
    }

    public void setRotation(FloatVector3 rotation) {
        this.rotation.set(rotation);
    }

    public FloatVector3 scale() {
        return this.scale;
    }

    public void setScale(FloatVector3 scale) {
        this.scale.set(scale);
    }

    public FloatVector3 pivotPoint() {
        return this.pivotPoint;
    }

    public void setPivotPoint(FloatVector3 pivotPoint) {
        this.pivotPoint.set(pivotPoint);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
        if (this.modelBuffer != null) {
            this.modelBuffer.dispose();
        }
    }

    public void rebuildModel() {
        ThreadSafe.ensureRenderThread();
        this.modelBuffer.rebuildModel();
    }

    public Model model() {
        return this.model;
    }

    public void setModel(Model model) {
        ThreadSafe.ensureRenderThread();
        if (this.modelBuffer != null) {
            this.modelBuffer.dispose();
        }
        this.model = model;
        this.modelBuffer = new DefaultModelBuffer(model, true);
        this.modelBuffer.setImmediate(this.immediate);
        this.modelBuffer.setResourceLocation(this.baseTextureLocation);
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.baseTextureLocation = resourceLocation;
        if (this.modelBuffer != null) {
            this.modelBuffer.setResourceLocation(resourceLocation);
        }
    }

    public void setModelAndTexture(Model modelAndTexture, ResourceLocation textureLocation) {
        setModel(modelAndTexture);
        setResourceLocation(textureLocation);
    }

    public AnimationController animationController() {
        return this.animationController;
    }

    public boolean isImmediate() {
        return this.immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
        if (this.modelBuffer != null) {
            this.modelBuffer.setImmediate(immediate);
        }
    }

    public LssProperty<Integer> modelColor() {
        return this.modelColor;
    }

    public LssProperty<Float> baseScale() {
        return this.baseScale;
    }

    public LssProperty<Float> modelOffset() {
        return this.modelOffset;
    }

    public LssProperty<OffscreenStrategy> submissionStrategy() {
        return this.submissionStrategy;
    }
}
