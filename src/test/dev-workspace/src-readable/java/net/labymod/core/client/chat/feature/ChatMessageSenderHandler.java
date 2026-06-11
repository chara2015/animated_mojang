package net.labymod.core.client.chat.feature;

import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageGuessSenderEvent;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.ServerChat;
import net.labymod.api.labynet.models.ServerGroup;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/feature/ChatMessageSenderHandler.class */
@Singleton
public class ChatMessageSenderHandler {
    private static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
    private static final Map<Character, String> SEPARATORS = Map.of(':', "", '>', "<", (char) 187, "");
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final LabyNetController labyNetController = Laby.references().labyNetController();

    @Inject
    public ChatMessageSenderHandler() {
    }

    @Subscribe(-127)
    public void guessSender(ChatMessageGuessSenderEvent event) {
        ClientPacketListener clientPacketListener;
        String senderName;
        NetworkPlayerInfo playerInfo;
        NetworkPlayerInfo playerInfo2;
        if (event.getSenderProfile() != null || (clientPacketListener = this.labyAPI.minecraft().clientPacketListener()) == null) {
            return;
        }
        if (event.getSenderUniqueId() != null && (playerInfo2 = clientPacketListener.getNetworkPlayerInfo(event.getSenderUniqueId())) != null) {
            event.setSenderProfile(playerInfo2.profile());
            return;
        }
        ServerGroup serverGroup = this.labyNetController.getCurrentServer().orElse(null);
        if (serverGroup != null && serverGroup.getChat() != null && serverGroup.getChat().getMessageFormats() != null) {
            ServerChat.ParsedMessage parsed = serverGroup.getChat().parseMessage(event.component());
            senderName = parsed != null ? parsed.getSender() : null;
        } else {
            senderName = findSenderName(event.component(), event.getMessage());
        }
        if (senderName != null && !senderName.isEmpty() && (playerInfo = clientPacketListener.getNetworkPlayerInfo(senderName)) != null) {
            event.setSenderProfile(playerInfo.profile());
        }
    }

    private String findSenderName(Component component, String message) {
        if (component instanceof TranslatableComponent) {
            TranslatableComponent translatable = (TranslatableComponent) component;
            if (translatable.getKey().equals("chat.type.text") && translatable.getArguments().size() == 2) {
                Component nameComponent = (Component) translatable.getArguments().getFirst();
                if (nameComponent instanceof TextComponent) {
                    String name = ((TextComponent) nameComponent).getText();
                    if (isValidSenderName(name)) {
                        return name;
                    }
                }
            }
        }
        for (Map.Entry<Character, String> entry : SEPARATORS.entrySet()) {
            char separator = entry.getKey().charValue();
            String name2 = extractName(message, separator, entry.getValue());
            if (name2 != null) {
                return name2;
            }
        }
        return null;
    }

    private String extractName(String text, char separator, String ignoredChars) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (ignoredChars.indexOf(c) == -1) {
                if (c == ' ' && i < text.length() - 1 && text.charAt(i + 1) == separator) {
                    return builder.toString();
                }
                if (c == separator) {
                    return builder.toString();
                }
                if (VALID_CHARS.indexOf(c) == -1) {
                    return null;
                }
                builder.append(c);
            }
        }
        return null;
    }

    private boolean isValidSenderName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (VALID_CHARS.indexOf(name.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
}
