package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyDirtBackgroundRenderer.class */
public class FancyDirtBackgroundRenderer extends VanillaBackgroundRenderer {
    private final FancyBackgroundRenderer backgroundRenderer;

    public FancyDirtBackgroundRenderer(Theme theme) {
        this.name = "DirtBackground";
        this.backgroundRenderer = new FancyBackgroundRenderer();
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        this.backgroundRenderer.renderPre(widget, context);
    }
}
