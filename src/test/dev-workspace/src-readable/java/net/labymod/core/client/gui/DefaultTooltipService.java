package net.labymod.core.client.gui;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.TooltipService;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffectRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.bounds.Point;
import net.labymod.core.configuration.labymod.LabyConfigProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/DefaultTooltipService.class */
@Singleton
@Implements(TooltipService.class)
public class DefaultTooltipService implements TooltipService {
    private static final RenderPipeline RENDER_PIPELINE = Laby.labyAPI().renderPipeline();
    private final HoverBackgroundEffectRenderer hoverEffectRenderer;
    private Object reference;
    private Mouse mouse;

    @Inject
    public DefaultTooltipService(HoverBackgroundEffectRenderer hoverEffectRenderer) {
        this.hoverEffectRenderer = hoverEffectRenderer;
    }

    @Override // net.labymod.api.client.gui.TooltipService
    public void renderTooltip(ScreenContext context, Widget widget, RenderableComponent component) {
        if (!LabyConfigProvider.INSTANCE.get().appearance().fixedTooltips().get().booleanValue()) {
            render(context, component);
            return;
        }
        if (this.reference == widget && this.mouse != null) {
            MutableMouse prevMouse = context.mouse();
            context.setMouse(this.mouse.mutable());
            render(context, component);
            context.setMouse(prevMouse);
            return;
        }
        this.reference = widget;
        this.mouse = context.mouse().copy();
    }

    @Override // net.labymod.api.client.gui.TooltipService
    public void renderFixedTooltip(ScreenContext context, Point point, RenderableComponent component, boolean centered) {
        Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
        HoverBackgroundEffect effect = currentTheme.getHoverBackgroundRenderer();
        float scale = effect.getScale();
        float padding = effect.getPadding();
        float width = component.getWidth() * scale;
        float height = component.getCeilHeight() * scale;
        float x = centered ? point.getX() - (width / 2.0f) : point.getX();
        float y = (point.getY() - height) - padding;
        Bounds bounds = Laby.labyAPI().minecraft().minecraftWindow().absoluteBounds();
        if (x + width + padding > bounds.getWidth()) {
            x = (bounds.getWidth() - width) - padding;
        }
        if (x < 0.0f) {
            x = padding;
        }
        if (y + height + padding > bounds.getHeight()) {
            y = (bounds.getHeight() - height) - padding;
        }
        if (y < 0.0f) {
            y = padding;
        }
        context.canvas().nextLayer();
        this.hoverEffectRenderer.component(component).hoverEffect(effect).pos(x, y, component.getWidth() * scale, component.getCeilHeight() * scale).render(context);
    }

    @Override // net.labymod.api.client.gui.TooltipService
    public void unhover(Widget widget) {
        if (this.reference == widget) {
            this.reference = null;
            this.mouse = null;
        }
    }

    private void render(ScreenContext context, RenderableComponent component) {
        context.canvas().nextLayer();
        RENDER_PIPELINE.componentRenderer().renderHoverComponent(context, component, -1, true);
    }
}
