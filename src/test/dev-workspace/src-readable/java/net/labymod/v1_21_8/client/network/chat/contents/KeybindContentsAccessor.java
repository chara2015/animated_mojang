package net.labymod.v1_21_8.client.network.chat.contents;

import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/network/chat/contents/KeybindContentsAccessor.class */
public interface KeybindContentsAccessor {
    void setKeybind(String str);

    Component resolveKeybind();
}
