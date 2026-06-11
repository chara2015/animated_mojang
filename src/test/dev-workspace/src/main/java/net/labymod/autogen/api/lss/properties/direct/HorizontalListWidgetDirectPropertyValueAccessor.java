package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetLayoutPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetSpaceLeftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetSpaceRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.HorizontalListWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/HorizontalListWidgetDirectPropertyValueAccessor.class */
public class HorizontalListWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> spaceLeft = new HorizontalListWidgetSpaceLeftPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> spaceRight = new HorizontalListWidgetSpaceRightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new HorizontalListWidgetSpaceBetweenEntriesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> layout = new HorizontalListWidgetLayoutPropertyValueAccessor();
    LssPropertyResetter HorizontalListWidgetResetter = new HorizontalListWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceLeft":
                return this.spaceLeft;
            case "spaceRight":
                return this.spaceRight;
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            case "layout":
                return this.layout;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceLeft":
                return true;
            case "spaceRight":
                return true;
            case "spaceBetweenEntries":
                return true;
            case "layout":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.HorizontalListWidgetResetter;
    }
}
