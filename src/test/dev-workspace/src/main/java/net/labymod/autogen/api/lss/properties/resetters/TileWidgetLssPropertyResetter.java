package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.TileWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/TileWidgetLssPropertyResetter.class */
public class TileWidgetLssPropertyResetter extends WrappedWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.WrappedWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof TileWidget) {
        }
        super.reset(widget);
    }
}
