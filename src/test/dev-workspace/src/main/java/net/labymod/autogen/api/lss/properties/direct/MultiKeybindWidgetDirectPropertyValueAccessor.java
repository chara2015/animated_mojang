package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.MultiKeybindWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/MultiKeybindWidgetDirectPropertyValueAccessor.class */
public class MultiKeybindWidgetDirectPropertyValueAccessor extends TextFieldWidgetDirectPropertyValueAccessor {
    LssPropertyResetter MultiKeybindWidgetResetter = new MultiKeybindWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.TextFieldWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        return super.getPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.TextFieldWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        return super.hasPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.TextFieldWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.MultiKeybindWidgetResetter;
    }
}
