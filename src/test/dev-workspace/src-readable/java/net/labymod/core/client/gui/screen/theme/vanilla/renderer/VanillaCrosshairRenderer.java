package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaCrosshairRenderer.class */
public class VanillaCrosshairRenderer extends ThemeRenderer<Widget> {
    private static final int WIDTH = 1;
    private static final int HEIGHT = 9;

    public VanillaCrosshairRenderer() {
        super("Crosshair");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(Widget widget, ScreenContext context) {
        MutableMouse mouse = context.mouse();
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitRelativeRect(mouse.getX() - 0.5f, mouse.getY() - 4.5f, 1.0f, 9.0f, Integer.MAX_VALUE);
        screenCanvas.submitRelativeRect(mouse.getX() - 4.5f, mouse.getY() - 0.5f, 9.0f, 1.0f, Integer.MAX_VALUE);
    }
}
