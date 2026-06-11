package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.EmoteModelWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/EmoteModelWidgetDirectPropertyValueAccessor.class */
public class EmoteModelWidgetDirectPropertyValueAccessor extends CosmeticModelWidgetDirectPropertyValueAccessor {
    LssPropertyResetter EmoteModelWidgetResetter = new EmoteModelWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.CosmeticModelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ModelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        return super.getPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.CosmeticModelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ModelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        return super.hasPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.CosmeticModelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ModelWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.EmoteModelWidgetResetter;
    }
}
