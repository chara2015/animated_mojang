package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/WrappedWidgetLssPropertyResetter.class */
public class WrappedWidgetLssPropertyResetter extends StyledWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof WrappedWidget) {
            if (((WrappedWidget) widget).priorityLayer() != null) {
                ((WrappedWidget) widget).priorityLayer().reset();
            }
            if (((WrappedWidget) widget).alignmentX() != null) {
                ((WrappedWidget) widget).alignmentX().reset();
            }
            if (((WrappedWidget) widget).alignmentY() != null) {
                ((WrappedWidget) widget).alignmentY().reset();
            }
            if (((WrappedWidget) widget).renderer() != null) {
                ((WrappedWidget) widget).renderer().reset();
            }
            if (((WrappedWidget) widget).boxSizing() != null) {
                ((WrappedWidget) widget).boxSizing().reset();
            }
        }
        super.reset(widget);
    }
}
