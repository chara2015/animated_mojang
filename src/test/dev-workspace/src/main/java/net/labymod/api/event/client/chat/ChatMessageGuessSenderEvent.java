package net.labymod.api.event.client.chat;

import java.util.UUID;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Event;
import net.labymod.api.mojang.GameProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/ChatMessageGuessSenderEvent.class */
public class ChatMessageGuessSenderEvent implements Event {
    private final Component component;
    private final String message;
    private UUID senderUniqueId;
    private GameProfile senderProfile;

    public ChatMessageGuessSenderEvent(@NotNull Component component, String message, UUID senderUniqueId) {
        this.component = component;
        this.message = message;
        this.senderUniqueId = senderUniqueId;
    }

    @NotNull
    public Component component() {
        return this.component;
    }

    @NotNull
    public String getMessage() {
        return this.message;
    }

    @Nullable
    public UUID getSenderUniqueId() {
        return this.senderUniqueId;
    }

    @Nullable
    public GameProfile getSenderProfile() {
        return this.senderProfile;
    }

    public void setSenderProfile(GameProfile senderProfile) {
        this.senderProfile = senderProfile;
        this.senderUniqueId = senderProfile.getUniqueId();
    }
}
