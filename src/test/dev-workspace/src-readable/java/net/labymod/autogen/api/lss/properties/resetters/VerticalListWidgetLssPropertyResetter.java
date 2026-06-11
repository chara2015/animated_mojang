package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/VerticalListWidgetLssPropertyResetter.class */
public class VerticalListWidgetLssPropertyResetter extends SessionedListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SessionedListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof VerticalListWidget) {
            if (((VerticalListWidget) widget).overwriteWidth() != null) {
                ((VerticalListWidget) widget).overwriteWidth().reset();
            }
            if (((VerticalListWidget) widget).squeezeHeight() != null) {
                ((VerticalListWidget) widget).squeezeHeight().reset();
            }
            if (((VerticalListWidget) widget).selectable() != null) {
                ((VerticalListWidget) widget).selectable().reset();
            }
            if (((VerticalListWidget) widget).spaceBetweenEntries() != null) {
                ((VerticalListWidget) widget).spaceBetweenEntries().reset();
            }
            if (((VerticalListWidget) widget).renderOutOfBounds() != null) {
                ((VerticalListWidget) widget).renderOutOfBounds().reset();
            }
            if (((VerticalListWidget) widget).listAlignment() != null) {
                ((VerticalListWidget) widget).listAlignment().reset();
            }
            if (((VerticalListWidget) widget).listOrder() != null) {
                ((VerticalListWidget) widget).listOrder().reset();
            }
        }
        super.reset(widget);
    }
}
