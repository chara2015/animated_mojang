package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.ButtonWidgetAlignmentPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.ButtonWidgetDisabledColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.ButtonWidgetIconPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.ButtonWidgetScaleToFitPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.ButtonWidgetTextPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.ButtonWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/ButtonWidgetDirectPropertyValueAccessor.class */
public class ButtonWidgetDirectPropertyValueAccessor extends HorizontalListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> scaleToFit = new ButtonWidgetScaleToFitPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> disabledColor = new ButtonWidgetDisabledColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> icon = new ButtonWidgetIconPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> text = new ButtonWidgetTextPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> alignment = new ButtonWidgetAlignmentPropertyValueAccessor();
    LssPropertyResetter ButtonWidgetResetter = new ButtonWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.HorizontalListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "scaleToFit":
                return this.scaleToFit;
            case "disabledColor":
                return this.disabledColor;
            case "icon":
                return this.icon;
            case "text":
                return this.text;
            case "alignment":
                return this.alignment;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.HorizontalListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "scaleToFit":
                return true;
            case "disabledColor":
                return true;
            case "icon":
                return true;
            case "text":
                return true;
            case "alignment":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.HorizontalListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.ButtonWidgetResetter;
    }
}
