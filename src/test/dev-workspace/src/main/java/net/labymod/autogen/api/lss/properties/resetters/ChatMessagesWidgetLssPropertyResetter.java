package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.chat.advanced.ChatMessagesWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/ChatMessagesWidgetLssPropertyResetter.class */
public class ChatMessagesWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ChatMessagesWidget) {
        }
        super.reset(widget);
    }
}
