package net.labymod.core.client.chat.prefix.prefix;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.prefix.ChatPrefix;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/prefix/prefix/ChatPlayerHeadPrefix.class */
public class ChatPlayerHeadPrefix implements ChatPrefix {
    private static final int HEAD_SIZE = 8;
    private final LabyAPI api = Laby.labyAPI();

    @Override // net.labymod.api.client.chat.prefix.ChatPrefix
    public void render(ScreenContext context, float x, float y, AdvancedChatMessage message, RenderableComponent[] renderableComponents, int index, int subIndex, int lineHeight, float textOffset, double scale, int alpha) {
        GameProfile gameProfile;
        if (index != 0 || subIndex != 0 || (gameProfile = message.chatMessage().getSenderProfile()) == null) {
            return;
        }
        float headSize = (float) (8.0d * scale);
        context.canvas().submitPlayerFace(gameProfile, x + 1, y + textOffset, headSize, headSize, ColorFormat.ARGB32.pack(255, 255, 255, alpha), true);
    }

    @Override // net.labymod.api.client.chat.prefix.ChatPrefix
    public boolean isVisible(ChatTab chatTab, AdvancedChatMessage message) {
        return this.api.config().ingame().advancedChat().showPlayerHeads().get().booleanValue() && message.chatMessage().getSenderProfile() != null;
    }

    @Override // net.labymod.api.client.chat.prefix.ChatPrefix
    public int getWidth(AdvancedChatMessage message, double scale) {
        double headSize = 8.0d * scale;
        return MathHelper.ceil(headSize + ((double) 1));
    }
}
