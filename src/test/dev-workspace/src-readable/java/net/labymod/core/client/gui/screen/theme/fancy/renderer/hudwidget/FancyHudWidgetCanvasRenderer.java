package net.labymod.core.client.gui.screen.theme.fancy.renderer.hudwidget;

import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureSprite;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget.VanillaHudWidgetCanvasRenderer;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/hudwidget/FancyHudWidgetCanvasRenderer.class */
public class FancyHudWidgetCanvasRenderer extends VanillaHudWidgetCanvasRenderer {
    private final TextureSprite sprite = new DefaultTextureSprite(0.0f, 0.0f, 1.0f, 1.0f, (width, height) -> {
        return NineSpliceScaling.of(width, height, new NineSpliceScaling.Border(32));
    });

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/hudwidget/FancyHudWidgetCanvasRenderer$TransformationType.class */
    private enum TransformationType {
        CANVAS,
        STRING
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget.VanillaHudWidgetCanvasRenderer, net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        if (DynamicBackgroundController.isEnabled()) {
            Bounds bounds = widget.bounds();
            Stack stack = context.stack();
            stack.push();
            applyTransformation(stack, widget, TransformationType.STRING);
            renderString(context, bounds);
            stack.pop();
            applyTransformation(stack, widget, TransformationType.CANVAS);
            super.renderPre(widget, context);
            renderFrame(context, bounds);
            return;
        }
        super.renderPre(widget, context);
    }

    private void applyTransformation(Stack stack, AbstractWidget<?> widget, TransformationType type) {
        Bounds bounds = widget.bounds();
        float progress = getProgress(widget);
        float factor = getFactor(progress);
        float distance = bounds.getBottom(BoundsType.INNER);
        stack.translate(bounds.getCenterX(), bounds.getCenterY(), 0.0f);
        stack.translate(0.0f, (-distance) * factor, 0.0f);
        if (type == TransformationType.CANVAS) {
            stack.rotate(25.0f * factor, 0.0f, 0.0f, 1.0f);
        }
        float scale = 1.0f - ((float) Math.pow(2.718281828459045d, -progress));
        stack.scale(scale);
        stack.translate(-bounds.getCenterX(), -bounds.getCenterY(), 0.0f);
    }

    private void renderFrame(ScreenContext context, Bounds bounds) {
        float x = bounds.getX(BoundsType.INNER);
        float y = bounds.getY(BoundsType.INNER);
        float width = bounds.getWidth(BoundsType.INNER);
        float height = bounds.getHeight(BoundsType.INNER);
        ScreenCanvas renderState = context.canvas();
        renderState.submitGuiSprite(Textures.Hud.FRAME, this.sprite, x - 9.0f, y - 9.0f, width + (9.0f * 2.0f), height + (9.0f * 2.0f), -1);
    }

    private void renderString(ScreenContext context, Bounds bounds) {
        float x = bounds.getX(BoundsType.INNER);
        float y = bounds.getY(BoundsType.INNER);
        float width = bounds.getWidth(BoundsType.INNER);
        ScreenCanvas renderState = context.canvas();
        renderState.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, Textures.Hud.STRING), (x + (width / 2.0f)) - (80.0f / 2.0f), y - (80.0f * 2.0f), 80.0f, 80.0f * 2.0f, UVCoordinates.of(0, 0, 128, 256, 128, 256), -1);
    }

    private float getProgress(AbstractWidget<?> widget) {
        FancyThemeConfig config = (FancyThemeConfig) this.labyAPI.themeService().getThemeConfig(FancyThemeConfig.class);
        if (config != null && !config.activityTransitions().get().booleanValue()) {
            return 2.1474836E9f;
        }
        long timePassed = TimeUtil.getMillis() - widget.getLastInitialTime();
        if (widget instanceof HudWidgetInteractionWidget) {
            timePassed = ((HudWidgetInteractionWidget) widget).getRenderTimePassed();
        }
        return timePassed / 150.0f;
    }

    private float getFactor(float progress) {
        return ((float) (((Math.pow(2.718281828459045d, 1.0f - (2.0f * (progress - 5.0f))) / 28.0d) - (Math.cos(2.0f * (progress - 5.0f)) * Math.pow(2.718281828459045d, 1.0f - (progress - 5.0f)))) / 10.0d)) / 20.0f;
    }
}
