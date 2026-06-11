package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TilesGridWidgetTileHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TilesGridWidgetTilesPerLinePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.TilesGridWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/TilesGridWidgetDirectPropertyValueAccessor.class */
public class TilesGridWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new TilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> tilesPerLine = new TilesGridWidgetTilesPerLinePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> tileHeight = new TilesGridWidgetTileHeightPropertyValueAccessor();
    LssPropertyResetter TilesGridWidgetResetter = new TilesGridWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            case "tilesPerLine":
                return this.tilesPerLine;
            case "tileHeight":
                return this.tileHeight;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return true;
            case "tilesPerLine":
                return true;
            case "tileHeight":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.TilesGridWidgetResetter;
    }
}
