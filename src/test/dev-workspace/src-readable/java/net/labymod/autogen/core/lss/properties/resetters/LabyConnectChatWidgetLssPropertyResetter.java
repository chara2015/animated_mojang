package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/LabyConnectChatWidgetLssPropertyResetter.class */
public class LabyConnectChatWidgetLssPropertyResetter extends ChatHistoryWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.ChatHistoryWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof LabyConnectChatWidget) {
        }
        super.reset(widget);
    }
}
