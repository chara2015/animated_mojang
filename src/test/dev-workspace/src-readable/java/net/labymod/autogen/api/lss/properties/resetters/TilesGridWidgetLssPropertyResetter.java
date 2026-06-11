package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/TilesGridWidgetLssPropertyResetter.class */
public class TilesGridWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof TilesGridWidget) {
            if (((TilesGridWidget) widget).spaceBetweenEntries() != null) {
                ((TilesGridWidget) widget).spaceBetweenEntries().reset();
            }
            if (((TilesGridWidget) widget).tilesPerLine() != null) {
                ((TilesGridWidget) widget).tilesPerLine().reset();
            }
            if (((TilesGridWidget) widget).tileHeight() != null) {
                ((TilesGridWidget) widget).tileHeight().reset();
            }
        }
        super.reset(widget);
    }
}
