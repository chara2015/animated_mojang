package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ProgressableProgressBarWidgetProgressBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ProgressableProgressBarWidgetProgressForegroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.ProgressableProgressBarWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/ProgressableProgressBarWidgetDirectPropertyValueAccessor.class */
public class ProgressableProgressBarWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> progressForegroundColor = new ProgressableProgressBarWidgetProgressForegroundColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> progressBackgroundColor = new ProgressableProgressBarWidgetProgressBackgroundColorPropertyValueAccessor();
    LssPropertyResetter ProgressableProgressBarWidgetResetter = new ProgressableProgressBarWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "progressForegroundColor":
                return this.progressForegroundColor;
            case "progressBackgroundColor":
                return this.progressBackgroundColor;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "progressForegroundColor":
                return true;
            case "progressBackgroundColor":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.ProgressableProgressBarWidgetResetter;
    }
}
