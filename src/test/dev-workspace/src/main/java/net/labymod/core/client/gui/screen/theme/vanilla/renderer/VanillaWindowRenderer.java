package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.draw.builder.VanillaWindowBuilder;
import net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaWindowRenderer.class */
public class VanillaWindowRenderer extends ThemeRenderer<AbstractWidget<?>> {
    private final VanillaWindowBuilder windowBuilder;

    public VanillaWindowRenderer() {
        super("Window");
        this.windowBuilder = Laby.references().vanillaWindowBuilder();
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        boolean window = widget instanceof NewWindowWidget;
        boolean topLarge = (window && ((NewWindowWidget) widget).titleBarWidget() == null) ? false : true;
        this.windowBuilder.top(topLarge).bottom(false).rescalable(window).position(bounds.rectangle(BoundsType.OUTER)).render(context);
    }
}
