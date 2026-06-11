package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/ServerInfoWidgetLssPropertyResetter.class */
public class ServerInfoWidgetLssPropertyResetter extends ServerEntryWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.ServerEntryWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ServerInfoWidget) {
        }
        super.reset(widget);
    }
}
