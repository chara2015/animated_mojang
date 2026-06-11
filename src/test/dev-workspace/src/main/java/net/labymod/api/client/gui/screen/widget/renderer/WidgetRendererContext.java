package net.labymod.api.client.gui.screen.widget.renderer;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/renderer/WidgetRendererContext.class */
public class WidgetRendererContext {
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final AbstractWidget<?> widget;
    private WidgetRenderer.RenderPhase renderPhase;
    private ThemeRenderer themeRenderer;

    public WidgetRendererContext(AbstractWidget<?> widget) {
        this.widget = widget;
    }

    public void setThemeRenderer(ThemeRenderer themeRenderer) {
        this.themeRenderer = themeRenderer;
    }

    public void renderPre(ScreenContext context) {
        this.renderPhase = WidgetRenderer.RenderPhase.PRE;
        applyFilters(context);
        invokeWidgetRenderer(this.themeRenderer, context);
        invokeWidgetRenderer(this.widget.shadow, context);
    }

    public void renderWidget(ScreenContext context) {
        boolean scissorX = this.widget.stencilX().get().booleanValue();
        boolean scissorY = this.widget.stencilY().get().booleanValue();
        boolean hasScissor = scissorX || scissorY;
        Scissor scissor = context.canvas().scissor();
        if (hasScissor) {
            try {
                Window window = this.labyAPI.minecraft().minecraftWindow();
                Rectangle windowBounds = window.absoluteBounds();
                Rectangle widgetBounds = this.widget.bounds().rectangle(BoundsType.MIDDLE);
                float x = (scissorX ? widgetBounds : windowBounds).getX();
                float y = (scissorY ? widgetBounds : windowBounds).getY();
                float width = scissorX ? widgetBounds.getWidth() : window.getRawWidth();
                float height = scissorY ? widgetBounds.getHeight() : window.getRawHeight();
                scissor.push(x, y, width, height, this.widget.useFloatingPointPosition().get().booleanValue());
            } catch (Throwable th) {
                if (hasScissor) {
                    scissor.pop();
                }
                throw th;
            }
        }
        this.themeRenderer.renderWidget(this.widget, context);
        if (hasScissor) {
            scissor.pop();
        }
    }

    public void renderPost(ScreenContext context) {
        this.renderPhase = WidgetRenderer.RenderPhase.POST;
        invokeWidgetRenderer(this.themeRenderer, context);
        invokeWidgetRenderer(this.widget.shadow, context);
        applyFilters(context);
    }

    private void applyFilters(ScreenContext context) {
        Filter[] filters = this.widget.filter().get();
        if (filters == null) {
            return;
        }
        for (Filter filter : filters) {
            invokeWidgetRenderer(filter, context);
        }
    }

    private void invokeWidgetRenderer(WidgetRenderer<AbstractWidget<?>> renderer, ScreenContext context) {
        if (renderer == null) {
            return;
        }
        if (this.renderPhase == WidgetRenderer.RenderPhase.PRE) {
            renderer.renderPre(this.widget, context);
        } else {
            renderer.renderPost(this.widget, context);
        }
    }
}
