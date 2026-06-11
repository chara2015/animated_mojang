package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VariableIconWidgetIconHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VariableIconWidgetIconWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.VariableIconWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/VariableIconWidgetDirectPropertyValueAccessor.class */
public class VariableIconWidgetDirectPropertyValueAccessor extends IconWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> iconWidth = new VariableIconWidgetIconWidthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> iconHeight = new VariableIconWidgetIconHeightPropertyValueAccessor();
    LssPropertyResetter VariableIconWidgetResetter = new VariableIconWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.IconWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "iconWidth":
                return this.iconWidth;
            case "iconHeight":
                return this.iconHeight;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.IconWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "iconWidth":
                return true;
            case "iconHeight":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.IconWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.VariableIconWidgetResetter;
    }
}
