package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaColorPreviewRenderer.class */
public class VanillaColorPreviewRenderer extends VanillaBackgroundRenderer {
    private final Icon icon;

    public VanillaColorPreviewRenderer() {
        this.name = "ColorPreview";
        this.icon = Icon.texture(Textures.TRANSPARENT);
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        int backgroundColor = widget.backgroundColor().get().intValue();
        if (backgroundColor != 0 && ((backgroundColor & (-16777216)) >>> 24) == 255) {
            super.renderPre(widget, context);
            return;
        }
        ReasonableMutableRectangle bounds = widget.bounds().rectangle(BoundsType.MIDDLE);
        this.icon.spriteSize((int) ((256.0f * bounds.getWidth()) / 6.0f), (int) ((256.0f * bounds.getHeight()) / 6.0f));
        BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius != null && borderRadius.isSet()) {
            this.icon.setBorderRadius(borderRadius);
        }
        context.canvas().submitIcon(this.icon, bounds);
        super.renderPre(widget, context);
    }
}
