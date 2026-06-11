package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.StorageServerInfoWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/StorageServerInfoWidgetDirectPropertyValueAccessor.class */
public class StorageServerInfoWidgetDirectPropertyValueAccessor extends LabyNetServerInfoWidgetDirectPropertyValueAccessor {
    LssPropertyResetter StorageServerInfoWidgetResetter = new StorageServerInfoWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.LabyNetServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ServerEntryWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        return super.getPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.LabyNetServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ServerEntryWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        return super.hasPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.LabyNetServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ServerInfoWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ServerEntryWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.StorageServerInfoWidgetResetter;
    }
}
