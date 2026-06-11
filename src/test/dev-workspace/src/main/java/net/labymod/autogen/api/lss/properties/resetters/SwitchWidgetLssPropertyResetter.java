package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/SwitchWidgetLssPropertyResetter.class */
public class SwitchWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof SwitchWidget) && ((SwitchWidget) widget).textHoverColor() != null) {
            ((SwitchWidget) widget).textHoverColor().reset();
        }
        super.reset(widget);
    }
}
