package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/ColorPickerWidgetLssPropertyResetter.class */
public class ColorPickerWidgetLssPropertyResetter extends OverlayWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.OverlayWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ColorPickerWidget) {
        }
        super.reset(widget);
    }
}
