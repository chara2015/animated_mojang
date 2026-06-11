package net.labymod.core.client.render.draw.hover;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.InjectionNames;
import net.labymod.api.util.color.GradientDirection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/hover/VanillaHoverBackgroundEffect.class */
@Singleton
@Implements(key = InjectionNames.VANILLA_HOVER_EFFECT, value = HoverBackgroundEffect.class)
public class VanillaHoverBackgroundEffect extends DefaultHoverBackgroundEffect {
    private static final float PADDING = 3.0f;
    private static final float LINE_WIDTH = 1.0f;
    private static final int DARK_BACKGROUND = -267386864;
    private static final int LIGHT_BACKGROUND = 1347420415;
    private static final int MEDIUM_BACKGROUND = 1344798847;

    @Inject
    public VanillaHoverBackgroundEffect() {
    }

    @Override // net.labymod.api.client.render.draw.hover.HoverBackgroundEffect
    public float getPadding() {
        return 4.0f;
    }

    private void submitVerticalGradient(ScreenCanvas renderState, float left, float top, float right, float bottom, int argb0, int argb1) {
        renderState.submitAbsoluteGradientRect(GradientDirection.TOP_TO_BOTTOM, left, top, right, bottom, argb0, argb1);
    }

    @Override // net.labymod.api.client.render.draw.hover.HoverBackgroundEffect
    public void render(ScreenContext context, float x, float y, float width, float height, RenderableComponent component) {
        ScreenCanvas renderState = context.canvas();
        float left = x - PADDING;
        float top = y - PADDING;
        float right = x + width + 1.5f;
        float bottom = y + height + 1.5f;
        submitVerticalGradient(renderState, left, top - LINE_WIDTH, right, top, DARK_BACKGROUND, DARK_BACKGROUND);
        submitVerticalGradient(renderState, left, bottom, right, bottom + LINE_WIDTH, DARK_BACKGROUND, DARK_BACKGROUND);
        submitVerticalGradient(renderState, left, top, right, bottom, DARK_BACKGROUND, DARK_BACKGROUND);
        submitVerticalGradient(renderState, left - LINE_WIDTH, top, left, bottom, DARK_BACKGROUND, DARK_BACKGROUND);
        submitVerticalGradient(renderState, right, top, right + LINE_WIDTH, bottom, DARK_BACKGROUND, DARK_BACKGROUND);
        submitVerticalGradient(renderState, left, top + LINE_WIDTH, left + LINE_WIDTH, bottom - LINE_WIDTH, LIGHT_BACKGROUND, MEDIUM_BACKGROUND);
        submitVerticalGradient(renderState, right - LINE_WIDTH, top + LINE_WIDTH, right, bottom - LINE_WIDTH, LIGHT_BACKGROUND, MEDIUM_BACKGROUND);
        submitVerticalGradient(renderState, left, top, right, top + LINE_WIDTH, LIGHT_BACKGROUND, LIGHT_BACKGROUND);
        submitVerticalGradient(renderState, left, bottom - LINE_WIDTH, right, bottom, MEDIUM_BACKGROUND, MEDIUM_BACKGROUND);
        if (component != null) {
            renderState.submitRenderableComponent(component, x, y, -1, 1);
        }
    }
}
