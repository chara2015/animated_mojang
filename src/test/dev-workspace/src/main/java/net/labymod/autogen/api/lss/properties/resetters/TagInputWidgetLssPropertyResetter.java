package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/TagInputWidgetLssPropertyResetter.class */
public class TagInputWidgetLssPropertyResetter extends WrappedListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.WrappedListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof TagInputWidget) {
        }
        super.reset(widget);
    }
}
