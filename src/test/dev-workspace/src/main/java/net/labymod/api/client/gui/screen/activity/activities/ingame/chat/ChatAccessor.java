package net.labymod.api.client.gui.screen.activity.activities.ingame.chat;

import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/activities/ingame/chat/ChatAccessor.class */
@Referenceable
public interface ChatAccessor {
    ChatProvider getProvider();

    boolean isChatOpen();

    void reload();
}
