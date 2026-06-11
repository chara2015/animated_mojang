package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/InterpolateWidgetLssPropertyResetter.class */
public class InterpolateWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof InterpolateWidget) {
        }
        super.reset(widget);
    }
}
