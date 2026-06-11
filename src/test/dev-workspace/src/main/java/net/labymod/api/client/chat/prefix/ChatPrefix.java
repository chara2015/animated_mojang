package net.labymod.api.client.chat.prefix;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/prefix/ChatPrefix.class */
public interface ChatPrefix {
    void render(ScreenContext screenContext, float f, float f2, AdvancedChatMessage advancedChatMessage, RenderableComponent[] renderableComponentArr, int i, int i2, int i3, float f3, double d, int i4);

    boolean isVisible(ChatTab chatTab, AdvancedChatMessage advancedChatMessage);

    int getWidth(AdvancedChatMessage advancedChatMessage, double d);
}
