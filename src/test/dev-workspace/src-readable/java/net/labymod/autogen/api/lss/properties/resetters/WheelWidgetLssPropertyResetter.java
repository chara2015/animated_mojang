package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/WheelWidgetLssPropertyResetter.class */
public class WheelWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof WheelWidget) {
            if (((WheelWidget) widget).selectable() != null) {
                ((WheelWidget) widget).selectable().reset();
            }
            if (((WheelWidget) widget).innerRadius() != null) {
                ((WheelWidget) widget).innerRadius().reset();
            }
            if (((WheelWidget) widget).segmentDistanceDegrees() != null) {
                ((WheelWidget) widget).segmentDistanceDegrees().reset();
            }
            if (((WheelWidget) widget).segmentBackgroundColor() != null) {
                ((WheelWidget) widget).segmentBackgroundColor().reset();
            }
            if (((WheelWidget) widget).segmentHighlightColor() != null) {
                ((WheelWidget) widget).segmentHighlightColor().reset();
            }
            if (((WheelWidget) widget).segmentSelectedColor() != null) {
                ((WheelWidget) widget).segmentSelectedColor().reset();
            }
            if (((WheelWidget) widget).segmentBorderColor() != null) {
                ((WheelWidget) widget).segmentBorderColor().reset();
            }
            if (((WheelWidget) widget).innerBackgroundColor() != null) {
                ((WheelWidget) widget).innerBackgroundColor().reset();
            }
            if (((WheelWidget) widget).innerBorderColor() != null) {
                ((WheelWidget) widget).innerBorderColor().reset();
            }
        }
        super.reset(widget);
    }
}
