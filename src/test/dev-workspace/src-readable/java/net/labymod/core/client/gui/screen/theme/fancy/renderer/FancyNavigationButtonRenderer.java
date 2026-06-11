package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.navigation.DefaultNavigationRegistry;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyNavigationButtonRenderer.class */
public class FancyNavigationButtonRenderer extends FancyButtonRenderer {
    public FancyNavigationButtonRenderer() {
        this.name = "NavigationButton";
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        float x;
        super.renderPost(widget, context);
        if (widget.isActive()) {
            DefaultNavigationRegistry registry = (DefaultNavigationRegistry) this.labyAPI.navigationService();
            long timePassed = (long) (registry.getTimePassedSinceActiveChanged() + TimeUtil.convertDeltaToMilliseconds(context.getTickDelta()));
            registry.setTimePassedSinceActiveChanged(timePassed);
            boolean transitionToRight = registry.getPreviousActiveIndex() > registry.getActiveIndex();
            float transitionProgress = (float) CubicBezier.EASE_OUT.curve(1.0f - Math.min(timePassed / 150, 1.0f));
            FancyThemeConfig config = (FancyThemeConfig) Laby.labyAPI().themeService().getThemeConfig(FancyThemeConfig.class);
            if (config != null && !config.activityTransitions().get().booleanValue()) {
                transitionProgress = 0.0f;
            }
            Rectangle outer = widget.bounds().rectangle(BoundsType.OUTER);
            float x2 = outer.getX();
            float y = (outer.getY() + outer.getHeight()) - 1.475f;
            float width = outer.getWidth();
            if (transitionToRight) {
                x = x2 + (width * transitionProgress);
            } else {
                x = x2 - (width * transitionProgress);
            }
            float padding = ((width / 100.0f) * 45.0f) / 2.0f;
            int color = ColorFormat.ARGB32.mul(config.accentColor().get().get(), 3.5833f, 1.48f, 1.3125f, 1.0f);
            context.canvas().submitAbsoluteRect(x + padding, y, (x + width) - padding, y + 1.475f, color);
        }
    }
}
