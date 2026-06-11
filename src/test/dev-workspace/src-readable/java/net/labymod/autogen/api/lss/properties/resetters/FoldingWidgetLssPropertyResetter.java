package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FoldingWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/FoldingWidgetLssPropertyResetter.class */
public class FoldingWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof FoldingWidget) {
            if (((FoldingWidget) widget).contentOffset() != null) {
                ((FoldingWidget) widget).contentOffset().reset();
            }
            if (((FoldingWidget) widget).previewExpand() != null) {
                ((FoldingWidget) widget).previewExpand().reset();
            }
        }
        super.reset(widget);
    }
}
