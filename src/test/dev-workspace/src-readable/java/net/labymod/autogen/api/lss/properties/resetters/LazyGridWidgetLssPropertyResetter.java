package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/LazyGridWidgetLssPropertyResetter.class */
public class LazyGridWidgetLssPropertyResetter extends SessionedListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SessionedListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof LazyGridWidget) {
            if (((LazyGridWidget) widget).tilesPerLine() != null) {
                ((LazyGridWidget) widget).tilesPerLine().reset();
            }
            if (((LazyGridWidget) widget).dynamicTilesPerLine() != null) {
                ((LazyGridWidget) widget).dynamicTilesPerLine().reset();
            }
            if (((LazyGridWidget) widget).tileHeight() != null) {
                ((LazyGridWidget) widget).tileHeight().reset();
            }
            if (((LazyGridWidget) widget).tileWidth() != null) {
                ((LazyGridWidget) widget).tileWidth().reset();
            }
            if (((LazyGridWidget) widget).tileSize() != null) {
                ((LazyGridWidget) widget).tileSize().reset();
            }
            if (((LazyGridWidget) widget).spaceBetweenEntries() != null) {
                ((LazyGridWidget) widget).spaceBetweenEntries().reset();
            }
        }
        super.reset(widget);
    }
}
