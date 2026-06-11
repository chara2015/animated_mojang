package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetHorizontalSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetMaxLinesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetVerticalSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.WrappedListWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/WrappedListWidgetDirectPropertyValueAccessor.class */
public class WrappedListWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new WrappedListWidgetSpaceBetweenEntriesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> verticalSpaceBetweenEntries = new WrappedListWidgetVerticalSpaceBetweenEntriesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> horizontalSpaceBetweenEntries = new WrappedListWidgetHorizontalSpaceBetweenEntriesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> maxLines = new WrappedListWidgetMaxLinesPropertyValueAccessor();
    LssPropertyResetter WrappedListWidgetResetter = new WrappedListWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            case "verticalSpaceBetweenEntries":
                return this.verticalSpaceBetweenEntries;
            case "horizontalSpaceBetweenEntries":
                return this.horizontalSpaceBetweenEntries;
            case "maxLines":
                return this.maxLines;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return true;
            case "verticalSpaceBetweenEntries":
                return true;
            case "horizontalSpaceBetweenEntries":
                return true;
            case "maxLines":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.WrappedListWidgetResetter;
    }
}
