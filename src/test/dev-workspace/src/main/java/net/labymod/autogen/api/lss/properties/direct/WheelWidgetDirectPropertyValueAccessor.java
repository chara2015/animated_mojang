package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetInnerBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetInnerBorderColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetInnerRadiusPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetSegmentBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetSegmentBorderColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetSegmentDistanceDegreesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetSegmentHighlightColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetSegmentSelectedColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WheelWidgetSelectablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.WheelWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/WheelWidgetDirectPropertyValueAccessor.class */
public class WheelWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> selectable = new WheelWidgetSelectablePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> innerRadius = new WheelWidgetInnerRadiusPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> segmentDistanceDegrees = new WheelWidgetSegmentDistanceDegreesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> segmentBackgroundColor = new WheelWidgetSegmentBackgroundColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> segmentHighlightColor = new WheelWidgetSegmentHighlightColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> segmentSelectedColor = new WheelWidgetSegmentSelectedColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> segmentBorderColor = new WheelWidgetSegmentBorderColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> innerBackgroundColor = new WheelWidgetInnerBackgroundColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> innerBorderColor = new WheelWidgetInnerBorderColorPropertyValueAccessor();
    LssPropertyResetter WheelWidgetResetter = new WheelWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "selectable":
                return this.selectable;
            case "innerRadius":
                return this.innerRadius;
            case "segmentDistanceDegrees":
                return this.segmentDistanceDegrees;
            case "segmentBackgroundColor":
                return this.segmentBackgroundColor;
            case "segmentHighlightColor":
                return this.segmentHighlightColor;
            case "segmentSelectedColor":
                return this.segmentSelectedColor;
            case "segmentBorderColor":
                return this.segmentBorderColor;
            case "innerBackgroundColor":
                return this.innerBackgroundColor;
            case "innerBorderColor":
                return this.innerBorderColor;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "selectable":
                return true;
            case "innerRadius":
                return true;
            case "segmentDistanceDegrees":
                return true;
            case "segmentBackgroundColor":
                return true;
            case "segmentHighlightColor":
                return true;
            case "segmentSelectedColor":
                return true;
            case "segmentBorderColor":
                return true;
            case "innerBackgroundColor":
                return true;
            case "innerBorderColor":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.WheelWidgetResetter;
    }
}
