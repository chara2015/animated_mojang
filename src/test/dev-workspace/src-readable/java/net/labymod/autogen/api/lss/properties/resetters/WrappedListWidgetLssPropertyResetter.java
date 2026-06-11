package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/WrappedListWidgetLssPropertyResetter.class */
public class WrappedListWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof WrappedListWidget) {
            if (((WrappedListWidget) widget).spaceBetweenEntries() != null) {
                ((WrappedListWidget) widget).spaceBetweenEntries().reset();
            }
            if (((WrappedListWidget) widget).verticalSpaceBetweenEntries() != null) {
                ((WrappedListWidget) widget).verticalSpaceBetweenEntries().reset();
            }
            if (((WrappedListWidget) widget).horizontalSpaceBetweenEntries() != null) {
                ((WrappedListWidget) widget).horizontalSpaceBetweenEntries().reset();
            }
            if (((WrappedListWidget) widget).maxLines() != null) {
                ((WrappedListWidget) widget).maxLines().reset();
            }
        }
        super.reset(widget);
    }
}
