package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/HorizontalListWidgetLssPropertyResetter.class */
public class HorizontalListWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof HorizontalListWidget) {
            if (((HorizontalListWidget) widget).spaceLeft() != null) {
                ((HorizontalListWidget) widget).spaceLeft().reset();
            }
            if (((HorizontalListWidget) widget).spaceRight() != null) {
                ((HorizontalListWidget) widget).spaceRight().reset();
            }
            if (((HorizontalListWidget) widget).spaceBetweenEntries() != null) {
                ((HorizontalListWidget) widget).spaceBetweenEntries().reset();
            }
            if (((HorizontalListWidget) widget).layout() != null) {
                ((HorizontalListWidget) widget).layout().reset();
            }
        }
        super.reset(widget);
    }
}
