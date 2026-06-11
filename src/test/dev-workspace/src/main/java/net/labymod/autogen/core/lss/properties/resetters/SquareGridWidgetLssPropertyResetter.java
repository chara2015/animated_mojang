package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SquareGridWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/SquareGridWidgetLssPropertyResetter.class */
public class SquareGridWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof SquareGridWidget) {
            if (((SquareGridWidget) widget).preferredSquareHeight() != null) {
                ((SquareGridWidget) widget).preferredSquareHeight().reset();
            }
            if (((SquareGridWidget) widget).spaceBetweenEntries() != null) {
                ((SquareGridWidget) widget).spaceBetweenEntries().reset();
            }
        }
        super.reset(widget);
    }
}
