package net.labymod.api.client.chat.input;

import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatButtonWidget;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/input/ChatInputRegistry.class */
@Referenceable
public interface ChatInputRegistry extends Registry<ChatButtonWidget> {
    float getButtonWidth();
}
