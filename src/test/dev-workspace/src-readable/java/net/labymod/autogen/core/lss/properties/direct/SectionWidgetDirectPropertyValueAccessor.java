package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.SectionWidgetLineColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.SectionWidgetLineHeightPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.SectionWidgetTextColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.SectionWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/SectionWidgetDirectPropertyValueAccessor.class */
public class SectionWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> lineHeight = new SectionWidgetLineHeightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> lineColor = new SectionWidgetLineColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> textColor = new SectionWidgetTextColorPropertyValueAccessor();
    LssPropertyResetter SectionWidgetResetter = new SectionWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "lineHeight":
                return this.lineHeight;
            case "lineColor":
                return this.lineColor;
            case "textColor":
                return this.textColor;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "lineHeight":
                return true;
            case "lineColor":
                return true;
            case "textColor":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.SectionWidgetResetter;
    }
}
