package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/PopupWidgetLssPropertyResetter.class */
public class PopupWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof PopupWidget) {
        }
        super.reset(widget);
    }
}
