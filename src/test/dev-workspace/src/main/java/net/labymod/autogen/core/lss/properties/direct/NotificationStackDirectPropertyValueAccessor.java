package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.NotificationStackLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/NotificationStackDirectPropertyValueAccessor.class */
public class NotificationStackDirectPropertyValueAccessor extends VerticalListWidgetDirectPropertyValueAccessor {
    LssPropertyResetter NotificationStackResetter = new NotificationStackLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.VerticalListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        return super.getPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.VerticalListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        return super.hasPropertyValueAccessor(key);
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.VerticalListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.NotificationStackResetter;
    }
}
