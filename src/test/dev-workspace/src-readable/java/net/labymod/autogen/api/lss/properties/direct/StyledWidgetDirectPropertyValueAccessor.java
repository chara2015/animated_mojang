package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/StyledWidgetDirectPropertyValueAccessor.class */
public class StyledWidgetDirectPropertyValueAccessor implements DirectPropertyValueAccessor {
    LssPropertyResetter StyledWidgetResetter = new StyledWidgetLssPropertyResetter();

    @Override // net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        return null;
    }

    @Override // net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        return false;
    }

    @Override // net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.StyledWidgetResetter;
    }
}
