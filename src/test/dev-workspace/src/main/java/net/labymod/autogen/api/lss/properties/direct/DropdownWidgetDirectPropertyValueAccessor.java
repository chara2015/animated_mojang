package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.DropdownWidgetArrowWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.DropdownWidgetWrapperPaddingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.DropdownWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/DropdownWidgetDirectPropertyValueAccessor.class */
public class DropdownWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> arrowWidth = new DropdownWidgetArrowWidthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> wrapperPadding = new DropdownWidgetWrapperPaddingPropertyValueAccessor();
    LssPropertyResetter DropdownWidgetResetter = new DropdownWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "arrowWidth":
                return this.arrowWidth;
            case "wrapperPadding":
                return this.wrapperPadding;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "arrowWidth":
                return true;
            case "wrapperPadding":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.DropdownWidgetResetter;
    }
}
