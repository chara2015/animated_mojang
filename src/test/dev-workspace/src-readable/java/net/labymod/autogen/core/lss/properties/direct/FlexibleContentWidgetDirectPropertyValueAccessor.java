package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.FlexibleContentWidgetListOrderPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.FlexibleContentWidgetOrientationPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.FlexibleContentWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.FlexibleContentWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/FlexibleContentWidgetDirectPropertyValueAccessor.class */
public class FlexibleContentWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new FlexibleContentWidgetSpaceBetweenEntriesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> orientation = new FlexibleContentWidgetOrientationPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> listOrder = new FlexibleContentWidgetListOrderPropertyValueAccessor();
    LssPropertyResetter FlexibleContentWidgetResetter = new FlexibleContentWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            case "orientation":
                return this.orientation;
            case "listOrder":
                return this.listOrder;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "spaceBetweenEntries":
                return true;
            case "orientation":
                return true;
            case "listOrder":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.FlexibleContentWidgetResetter;
    }
}
