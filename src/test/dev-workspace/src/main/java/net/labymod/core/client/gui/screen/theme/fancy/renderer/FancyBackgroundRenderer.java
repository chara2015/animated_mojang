package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyBackgroundRenderer.class */
public class FancyBackgroundRenderer extends VanillaBackgroundRenderer {
    public FancyBackgroundRenderer() {
        this.name = "Background";
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
    }
}
