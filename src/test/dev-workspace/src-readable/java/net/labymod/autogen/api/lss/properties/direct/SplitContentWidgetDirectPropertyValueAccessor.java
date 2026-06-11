package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetInitialPercentagePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetMaxPercentagePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetMinPercentagePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetSplitButtonWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetSplitGapWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.SplitContentWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/SplitContentWidgetDirectPropertyValueAccessor.class */
public class SplitContentWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> splitButtonWidth = new SplitContentWidgetSplitButtonWidthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> splitGapWidth = new SplitContentWidgetSplitGapWidthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> initialPercentage = new SplitContentWidgetInitialPercentagePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> minPercentage = new SplitContentWidgetMinPercentagePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> maxPercentage = new SplitContentWidgetMaxPercentagePropertyValueAccessor();
    LssPropertyResetter SplitContentWidgetResetter = new SplitContentWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "splitButtonWidth":
                return this.splitButtonWidth;
            case "splitGapWidth":
                return this.splitGapWidth;
            case "initialPercentage":
                return this.initialPercentage;
            case "minPercentage":
                return this.minPercentage;
            case "maxPercentage":
                return this.maxPercentage;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "splitButtonWidth":
                return true;
            case "splitGapWidth":
                return true;
            case "initialPercentage":
                return true;
            case "minPercentage":
                return true;
            case "maxPercentage":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.SplitContentWidgetResetter;
    }
}
