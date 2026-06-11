package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/SectionWidgetLssPropertyResetter.class */
public class SectionWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof SectionedListWidget.SectionWidget) {
            if (((SectionedListWidget.SectionWidget) widget).lineHeight() != null) {
                ((SectionedListWidget.SectionWidget) widget).lineHeight().reset();
            }
            if (((SectionedListWidget.SectionWidget) widget).lineColor() != null) {
                ((SectionedListWidget.SectionWidget) widget).lineColor().reset();
            }
            if (((SectionedListWidget.SectionWidget) widget).textColor() != null) {
                ((SectionedListWidget.SectionWidget) widget).textColor().reset();
            }
        }
        super.reset(widget);
    }
}
