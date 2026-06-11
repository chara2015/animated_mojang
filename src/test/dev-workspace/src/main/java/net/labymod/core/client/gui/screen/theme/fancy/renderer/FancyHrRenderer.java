package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.HrWidget;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyHrRenderer.class */
public class FancyHrRenderer extends ThemeRenderer<HrWidget> {
    private static final int COLOR = ColorFormat.ARGB32.pack(255, 255, 255, 20);

    public FancyHrRenderer() {
        super("Hr");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(HrWidget widget, ScreenContext context) {
        context.canvas().submitRect(widget.bounds(), COLOR);
    }
}
