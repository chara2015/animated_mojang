package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridFeedWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/TilesGridFeedWidgetLssPropertyResetter.class */
public class TilesGridFeedWidgetLssPropertyResetter extends TilesGridWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.TilesGridWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof TilesGridFeedWidget) && ((TilesGridFeedWidget) widget).refreshRadius() != null) {
            ((TilesGridFeedWidget) widget).refreshRadius().reset();
        }
        super.reset(widget);
    }
}
