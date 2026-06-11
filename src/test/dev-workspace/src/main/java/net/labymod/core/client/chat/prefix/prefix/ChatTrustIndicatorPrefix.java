package net.labymod.core.client.chat.prefix.prefix;

import net.labymod.api.client.chat.ChatData;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.chat.prefix.ChatPrefix;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/prefix/prefix/ChatTrustIndicatorPrefix.class */
public class ChatTrustIndicatorPrefix implements ChatPrefix {
    @Override // net.labymod.api.client.chat.prefix.ChatPrefix
    public void render(ScreenContext context, float x, float y, AdvancedChatMessage message, RenderableComponent[] renderableComponents, int index, int subIndex, int lineHeight, float textOffset, double scale, int alpha) {
        ChatTrustLevel chatTrustLevel = message.trustLevel();
        context.canvas().submitRelativeRect(x - 2.0f, y, 2.0f, lineHeight, ColorFormat.ARGB32.pack(chatTrustLevel.getHexColor(), alpha));
    }

    @Override // net.labymod.api.client.chat.prefix.ChatPrefix
    public boolean isVisible(ChatTab chatTab, AdvancedChatMessage message) {
        return ChatData.isChatTrustEnabled() && chatTab.config().chatTrust().get().booleanValue();
    }

    @Override // net.labymod.api.client.chat.prefix.ChatPrefix
    public int getWidth(AdvancedChatMessage message, double scale) {
        return 0;
    }
}
