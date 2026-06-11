package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.SprayWheelWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/SprayWheelWidgetDirectPropertyValueAccessor.class */
public class SprayWheelWidgetDirectPropertyValueAccessor extends WheelWidgetDirectPropertyValueAccessor {
    LssPropertyResetter SprayWheelWidgetResetter = new SprayWheelWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.WheelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        return super.getPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.WheelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        return super.hasPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.WheelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.SprayWheelWidgetResetter;
    }
}
