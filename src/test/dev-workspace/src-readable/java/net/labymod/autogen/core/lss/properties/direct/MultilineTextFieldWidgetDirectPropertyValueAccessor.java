package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.MultilineTextFieldWidgetTextColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.MultilineTextFieldWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/MultilineTextFieldWidgetDirectPropertyValueAccessor.class */
public class MultilineTextFieldWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> textColor = new MultilineTextFieldWidgetTextColorPropertyValueAccessor();
    LssPropertyResetter MultilineTextFieldWidgetResetter = new MultilineTextFieldWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "textColor":
                return this.textColor;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "textColor":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.MultilineTextFieldWidgetResetter;
    }
}
