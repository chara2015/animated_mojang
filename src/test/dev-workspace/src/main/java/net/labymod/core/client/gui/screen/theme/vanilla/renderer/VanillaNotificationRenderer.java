package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureSprite;
import net.labymod.core.client.gui.screen.widget.widgets.notification.NotificationWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaNotificationRenderer.class */
public class VanillaNotificationRenderer extends ThemeRenderer<NotificationWidget> {
    private static final float PROGRESS_BAR_PADDING = 3.0f;
    private static final float PROGRESS_BAR_HEIGHT = 1.0f;
    private final AbstractTextureAtlas atlas;
    private static final int PROGRESS_BAR_BACKGROUND_COLOR = ColorFormat.ARGB32.pack(0, 50, 130, 155);
    private static final int PROGRESS_BAR_FOREGROUND_COLOR = ColorFormat.ARGB32.pack(0, 150, 230, 255);
    private static final ResourceLocation NOTIFICATION = ResourceLocation.create("labymod", "notification");

    public VanillaNotificationRenderer() {
        super("Notification");
        this.atlas = new AbstractTextureAtlas(Textures.NOTIFICATION, 128, 128);
        this.atlas.register(NOTIFICATION, new DefaultTextureSprite(0.0f, 0.0f, 0.25f, 0.25f, (width, height) -> {
            return new NineSpliceScaling(32, 32, new NineSpliceScaling.Border(3, 3, 3, 3));
        }));
        Laby.references().atlasRegistry().register(this.atlas);
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(NotificationWidget widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        Notification notification = widget.notification();
        float progress = notification.progress();
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitGuiSprite(this.atlas, NOTIFICATION, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), -1);
        float x = bounds.getX() + PROGRESS_BAR_PADDING;
        float y = ((bounds.getBottom() - PROGRESS_BAR_HEIGHT) - PROGRESS_BAR_PADDING) + PROGRESS_BAR_HEIGHT;
        float width = bounds.getWidth() - 6.0f;
        screenCanvas.submitRelativeRect(x, y, width, PROGRESS_BAR_HEIGHT, PROGRESS_BAR_BACKGROUND_COLOR);
        screenCanvas.submitRelativeRect(x, y, width * progress, PROGRESS_BAR_HEIGHT, PROGRESS_BAR_FOREGROUND_COLOR);
    }
}
