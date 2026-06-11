package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.LabeledWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/LabeledWidgetLssPropertyResetter.class */
public class LabeledWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.HorizontalListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof LabeledWidget) && ((LabeledWidget) widget).labelAlignment() != null) {
            ((LabeledWidget) widget).labelAlignment().reset();
        }
        super.reset(widget);
    }
}
