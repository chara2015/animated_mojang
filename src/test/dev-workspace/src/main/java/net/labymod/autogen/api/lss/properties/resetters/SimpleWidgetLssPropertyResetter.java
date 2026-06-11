package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/SimpleWidgetLssPropertyResetter.class */
public class SimpleWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof SimpleWidget) {
        }
        super.reset(widget);
    }
}
