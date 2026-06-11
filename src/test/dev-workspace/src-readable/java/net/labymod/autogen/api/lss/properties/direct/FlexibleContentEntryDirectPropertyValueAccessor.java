package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.FlexibleContentEntryIgnoreBoundsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.FlexibleContentEntryLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/FlexibleContentEntryDirectPropertyValueAccessor.class */
public class FlexibleContentEntryDirectPropertyValueAccessor extends WrappedWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> ignoreBounds = new FlexibleContentEntryIgnoreBoundsPropertyValueAccessor();
    LssPropertyResetter FlexibleContentEntryResetter = new FlexibleContentEntryLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.WrappedWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "ignoreBounds":
                return this.ignoreBounds;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.WrappedWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "ignoreBounds":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.WrappedWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.FlexibleContentEntryResetter;
    }
}
