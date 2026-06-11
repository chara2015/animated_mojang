package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/FlexibleContentWidgetLssPropertyResetter.class */
public class FlexibleContentWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof FlexibleContentWidget) {
            if (((FlexibleContentWidget) widget).spaceBetweenEntries() != null) {
                ((FlexibleContentWidget) widget).spaceBetweenEntries().reset();
            }
            if (((FlexibleContentWidget) widget).orientation() != null) {
                ((FlexibleContentWidget) widget).orientation().reset();
            }
            if (((FlexibleContentWidget) widget).listOrder() != null) {
                ((FlexibleContentWidget) widget).listOrder().reset();
            }
        }
        super.reset(widget);
    }
}
