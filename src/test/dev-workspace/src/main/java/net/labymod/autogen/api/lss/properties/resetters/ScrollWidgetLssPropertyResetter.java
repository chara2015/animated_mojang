package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/ScrollWidgetLssPropertyResetter.class */
public class ScrollWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ScrollWidget) {
            if (((ScrollWidget) widget).childAlign() != null) {
                ((ScrollWidget) widget).childAlign().reset();
            }
            if (((ScrollWidget) widget).enableScrollContent() != null) {
                ((ScrollWidget) widget).enableScrollContent().reset();
            }
            if (((ScrollWidget) widget).scrollSpeed() != null) {
                ((ScrollWidget) widget).scrollSpeed().reset();
            }
            if (((ScrollWidget) widget).scrollAlwaysVisible() != null) {
                ((ScrollWidget) widget).scrollAlwaysVisible().reset();
            }
            if (((ScrollWidget) widget).moveDirtBackground() != null) {
                ((ScrollWidget) widget).moveDirtBackground().reset();
            }
            if (((ScrollWidget) widget).fixedPosition() != null) {
                ((ScrollWidget) widget).fixedPosition().reset();
            }
            if (((ScrollWidget) widget).autoScroll() != null) {
                ((ScrollWidget) widget).autoScroll().reset();
            }
            if (((ScrollWidget) widget).modifyContentWidth() != null) {
                ((ScrollWidget) widget).modifyContentWidth().reset();
            }
        }
        super.reset(widget);
    }
}
