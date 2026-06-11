package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.GridWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/GridWidgetLssPropertyResetter.class */
public class GridWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof GridWidget) {
            if (((GridWidget) widget).outlineThickness() != null) {
                ((GridWidget) widget).outlineThickness().reset();
            }
            if (((GridWidget) widget).columns() != null) {
                ((GridWidget) widget).columns().reset();
            }
            if (((GridWidget) widget).rows() != null) {
                ((GridWidget) widget).rows().reset();
            }
            if (((GridWidget) widget).maxColumnWidth() != null) {
                ((GridWidget) widget).maxColumnWidth().reset();
            }
            if (((GridWidget) widget).maxRowHeight() != null) {
                ((GridWidget) widget).maxRowHeight().reset();
            }
            if (((GridWidget) widget).iterate() != null) {
                ((GridWidget) widget).iterate().reset();
            }
            if (((GridWidget) widget).auto() != null) {
                ((GridWidget) widget).auto().reset();
            }
        }
        super.reset(widget);
    }
}
