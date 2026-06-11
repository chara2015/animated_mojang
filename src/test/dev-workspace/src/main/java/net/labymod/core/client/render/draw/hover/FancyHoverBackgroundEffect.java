package net.labymod.core.client.render.draw.hover;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.InjectionNames;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/hover/FancyHoverBackgroundEffect.class */
@Singleton
@Implements(key = InjectionNames.FANCY_HOVER_EFFECT, value = HoverBackgroundEffect.class)
public class FancyHoverBackgroundEffect extends DefaultHoverBackgroundEffect {
    private static final float PADDING = 3.0f;
    private static final int BORDER_COLOR = ColorFormat.ARGB32.pack(180, 180, 180, 255);
    private static final int DARK_BACKGROUND = ColorFormat.ARGB32.pack(-267386864, 240);

    @Inject
    public FancyHoverBackgroundEffect() {
    }

    @Override // net.labymod.api.client.render.draw.hover.HoverBackgroundEffect
    public float getPadding() {
        return PADDING;
    }

    @Override // net.labymod.api.client.render.draw.hover.HoverBackgroundEffect
    public float getScale() {
        return 0.75f;
    }

    @Override // net.labymod.api.client.render.draw.hover.HoverBackgroundEffect
    public void render(ScreenContext context, float x, float y, float width, float height, RenderableComponent component) {
        ScreenCanvas renderState = context.canvas();
        renderState.submitAbsoluteRoundedRect(x - PADDING, y - PADDING, x + width + 1.5f, y + height + 1.5f, ColorFormat.ARGB32.pack(DARK_BACKGROUND, 240), RoundedData.builder().setRadius((PADDING * getScale()) + 1.0f).setLowerEdgeSoftness(-0.2f).setBorderThickness(2.0f).setBorderSoftness(4.0f).setBorderColor(BORDER_COLOR).build());
        if (component != null) {
            renderState.submitRenderableComponent(component, x, y, -1, getScale(), 5);
        }
    }
}
