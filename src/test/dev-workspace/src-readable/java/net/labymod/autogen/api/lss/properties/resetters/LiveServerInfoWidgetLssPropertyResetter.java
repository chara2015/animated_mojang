package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.LiveServerInfoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/LiveServerInfoWidgetLssPropertyResetter.class */
public class LiveServerInfoWidgetLssPropertyResetter extends ServerInfoWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.ServerInfoWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ServerEntryWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof LiveServerInfoWidget) {
        }
        super.reset(widget);
    }
}
