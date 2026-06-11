package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetDynamicTilesPerLinePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTileHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTileSizePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTileWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTilesPerLinePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.LazyGridWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/LazyGridWidgetDirectPropertyValueAccessor.class */
public class LazyGridWidgetDirectPropertyValueAccessor extends SessionedListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> tilesPerLine = new LazyGridWidgetTilesPerLinePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> dynamicTilesPerLine = new LazyGridWidgetDynamicTilesPerLinePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> tileHeight = new LazyGridWidgetTileHeightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> tileWidth = new LazyGridWidgetTileWidthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> tileSize = new LazyGridWidgetTileSizePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new LazyGridWidgetSpaceBetweenEntriesPropertyValueAccessor();
    LssPropertyResetter LazyGridWidgetResetter = new LazyGridWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "tilesPerLine":
                return this.tilesPerLine;
            case "dynamicTilesPerLine":
                return this.dynamicTilesPerLine;
            case "tileHeight":
                return this.tileHeight;
            case "tileWidth":
                return this.tileWidth;
            case "tileSize":
                return this.tileSize;
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "tilesPerLine":
                return true;
            case "dynamicTilesPerLine":
                return true;
            case "tileHeight":
                return true;
            case "tileWidth":
                return true;
            case "tileSize":
                return true;
            case "spaceBetweenEntries":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.LazyGridWidgetResetter;
    }
}
