package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/ScrollbarWidgetLssPropertyResetter.class */
public class ScrollbarWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ScrollbarWidget) {
            if (((ScrollbarWidget) widget).scrollButtonClickOffset() != null) {
                ((ScrollbarWidget) widget).scrollButtonClickOffset().reset();
            }
            if (((ScrollbarWidget) widget).useLssPosition() != null) {
                ((ScrollbarWidget) widget).useLssPosition().reset();
            }
            if (((ScrollbarWidget) widget).scrollColor() != null) {
                ((ScrollbarWidget) widget).scrollColor().reset();
            }
            if (((ScrollbarWidget) widget).scrollHoverColor() != null) {
                ((ScrollbarWidget) widget).scrollHoverColor().reset();
            }
            if (((ScrollbarWidget) widget).scrollBackgroundColor() != null) {
                ((ScrollbarWidget) widget).scrollBackgroundColor().reset();
            }
            if (((ScrollbarWidget) widget).minScrollHeight() != null) {
                ((ScrollbarWidget) widget).minScrollHeight().reset();
            }
        }
        super.reset(widget);
    }
}
