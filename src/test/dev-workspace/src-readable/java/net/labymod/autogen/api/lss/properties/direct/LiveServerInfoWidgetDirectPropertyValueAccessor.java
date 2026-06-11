package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.LiveServerInfoWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/LiveServerInfoWidgetDirectPropertyValueAccessor.class */
public class LiveServerInfoWidgetDirectPropertyValueAccessor extends ServerInfoWidgetDirectPropertyValueAccessor {
    LssPropertyResetter LiveServerInfoWidgetResetter = new LiveServerInfoWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.ServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ServerEntryWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        return super.getPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ServerEntryWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        return super.hasPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.ServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ServerEntryWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.LiveServerInfoWidgetResetter;
    }
}
