package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.widget.widgets.notification.NotificationWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyNotificationRenderer.class */
public class FancyNotificationRenderer extends FancyBackgroundRenderer {
    private static final int BACKGROUND_COLOR = ColorFormat.ARGB32.pack(0, 50, 130, 155);
    private static final int FOREGROUND_COLOR = ColorFormat.ARGB32.pack(0, 150, 230, 255);

    public FancyNotificationRenderer() {
        this.name = "Notification";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPre(widget, context);
        Bounds bounds = widget.bounds();
        Notification notification = ((NotificationWidget) widget).notification();
        float progress = notification.progress();
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitRelativeRoundedRect(bounds.getX() + 5.0f, (bounds.getBottom() - 2.0f) - 2.0f, bounds.getWidth() - (5.0f * 2.0f), 2.0f, BACKGROUND_COLOR, RoundedData.builder().setRadius(1.0f).build());
        screenCanvas.submitRelativeRoundedRect(bounds.getX() + 5.0f, (bounds.getBottom() - 2.0f) - 2.0f, (bounds.getWidth() - (5.0f * 2.0f)) * progress, 2.0f, FOREGROUND_COLOR, RoundedData.builder().setRadius(1.0f).build());
    }
}
