package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyAddonProfileButtonRenderer.class */
public class FancyAddonProfileButtonRenderer extends FancyButtonRenderer {
    public FancyAddonProfileButtonRenderer() {
        this.name = "AddonProfileButton";
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPost(widget, context);
        if (!widget.isActive()) {
            return;
        }
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.OUTER);
        float x = rectangle.getX();
        float y = ((rectangle.getY() + rectangle.getHeight()) - 2.0f) - 0.75f;
        float width = rectangle.getWidth();
        context.canvas().submitRelativeRect(x, y, width, 0.75f, ColorFormat.ARGB32.pack(2857703, 255));
    }
}
