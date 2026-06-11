package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyTabRenderer.class */
public class FancyTabRenderer extends FancyBackgroundRenderer {
    private static final int TAB_COLOR = ColorFormat.ARGB32.pack(140, 140, 140, 80);

    public FancyTabRenderer() {
        this.name = "Tab";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPre(widget, context);
        if (widget.isActive()) {
            Tab tab = ((TabWidget) widget).getTab();
            long initialTimeActiveChanged = TimeUtil.getMillis() - widget.getLastActiveChangedTime();
            boolean transitionToRight = tab.isTransitionToRight();
            float transitionProgress = (float) CubicBezier.EASE_OUT.curve(1.0f - Math.min(initialTimeActiveChanged / 100, 1.0f));
            FancyThemeConfig config = (FancyThemeConfig) Laby.labyAPI().themeService().getThemeConfig(FancyThemeConfig.class);
            if (config != null && !config.activityTransitions().get().booleanValue()) {
                transitionProgress = 0.0f;
            }
            Rectangle outer = widget.bounds().rectangle(BoundsType.OUTER);
            float x = outer.getX();
            float y = outer.getY();
            float width = outer.getWidth();
            float height = outer.getHeight();
            Parent parent = widget.getParent();
            if (!(parent instanceof AbstractWidget) || ((AbstractWidget) parent).getLastInitialTime() + 100 < TimeUtil.getMillis()) {
                if (transitionToRight) {
                    x += width * transitionProgress;
                } else {
                    x -= width * transitionProgress;
                }
            }
            BorderRadius borderRadius = widget.getBorderRadius();
            if (borderRadius == null) {
                return;
            }
            context.canvas().submitRelativeRoundedRect(x, y, width, height, TAB_COLOR, RoundedData.builder().applyBorderRadius(borderRadius).build());
        }
    }
}
