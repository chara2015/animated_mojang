package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.SectionedListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.SectionedListWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/SectionedListWidgetDirectPropertyValueAccessor.class */
public class SectionedListWidgetDirectPropertyValueAccessor extends SessionedListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new SectionedListWidgetSpaceBetweenEntriesPropertyValueAccessor();
    LssPropertyResetter SectionedListWidgetResetter = new SectionedListWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.SectionedListWidgetResetter;
    }
}
