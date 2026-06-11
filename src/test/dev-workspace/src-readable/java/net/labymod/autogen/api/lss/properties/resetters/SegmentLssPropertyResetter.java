package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/SegmentLssPropertyResetter.class */
public class SegmentLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof WheelWidget.Segment) && ((WheelWidget.Segment) widget).segmentColor() != null) {
            ((WheelWidget.Segment) widget).segmentColor().reset();
        }
        super.reset(widget);
    }
}
