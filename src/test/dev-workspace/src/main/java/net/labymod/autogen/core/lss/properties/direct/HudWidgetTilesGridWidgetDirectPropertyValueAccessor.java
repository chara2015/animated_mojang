package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.HudWidgetTilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.HudWidgetTilesGridWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/HudWidgetTilesGridWidgetDirectPropertyValueAccessor.class */
public class HudWidgetTilesGridWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new HudWidgetTilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor();
    LssPropertyResetter HudWidgetTilesGridWidgetResetter = new HudWidgetTilesGridWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.HudWidgetTilesGridWidgetResetter;
    }
}
