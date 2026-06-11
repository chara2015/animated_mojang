package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/button/VanillaIngameButtonRenderer.class */
public class VanillaIngameButtonRenderer extends VanillaBackgroundRenderer {
    public VanillaIngameButtonRenderer() {
        super("ButtonInGame");
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        int iPack;
        int backgroundColor = widget.backgroundColor().get().intValue();
        if (backgroundColor == 0) {
            LssProperty<Integer> lssPropertyBackgroundColor = widget.backgroundColor();
            if (widget.isAttributeStateEnabled(AttributeState.ENABLED)) {
                if (widget.isHovered()) {
                    iPack = ColorFormat.ARGB32.pack(20, 20, 20, 220);
                } else {
                    iPack = ColorFormat.ARGB32.pack(0, 0, 0, 170);
                }
            } else {
                iPack = ColorFormat.ARGB32.pack(0, 0, 0, 180);
            }
            lssPropertyBackgroundColor.set(Integer.valueOf(iPack));
        }
        super.renderPre(widget, context);
        widget.backgroundColor().set(Integer.valueOf(backgroundColor));
    }
}
