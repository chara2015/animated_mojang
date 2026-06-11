package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/OverlayWidgetLssPropertyResetter.class */
public class OverlayWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof OverlayWidget) {
            if (((OverlayWidget) widget).strategyX() != null) {
                ((OverlayWidget) widget).strategyX().reset();
            }
            if (((OverlayWidget) widget).strategyY() != null) {
                ((OverlayWidget) widget).strategyY().reset();
            }
            if (((OverlayWidget) widget).playInteractSound() != null) {
                ((OverlayWidget) widget).playInteractSound().reset();
            }
        }
        super.reset(widget);
    }
}
