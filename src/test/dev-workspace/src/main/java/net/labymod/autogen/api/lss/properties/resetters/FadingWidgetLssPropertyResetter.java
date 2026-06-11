package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.FadingWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/FadingWidgetLssPropertyResetter.class */
public class FadingWidgetLssPropertyResetter extends WrappedWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.WrappedWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof FadingWidget) {
        }
        super.reset(widget);
    }
}
