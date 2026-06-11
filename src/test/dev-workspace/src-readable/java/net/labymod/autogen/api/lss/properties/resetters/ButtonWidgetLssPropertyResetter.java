package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/ButtonWidgetLssPropertyResetter.class */
public class ButtonWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.HorizontalListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ButtonWidget) {
            if (((ButtonWidget) widget).scaleToFit() != null) {
                ((ButtonWidget) widget).scaleToFit().reset();
            }
            if (((ButtonWidget) widget).disabledColor() != null) {
                ((ButtonWidget) widget).disabledColor().reset();
            }
            if (((ButtonWidget) widget).icon() != null) {
                ((ButtonWidget) widget).icon().reset();
            }
            if (((ButtonWidget) widget).text() != null) {
                ((ButtonWidget) widget).text().reset();
            }
            if (((ButtonWidget) widget).alignment() != null) {
                ((ButtonWidget) widget).alignment().reset();
            }
        }
        super.reset(widget);
    }
}
