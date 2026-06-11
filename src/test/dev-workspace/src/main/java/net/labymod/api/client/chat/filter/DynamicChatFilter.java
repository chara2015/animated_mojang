package net.labymod.api.client.chat.filter;

import java.util.UUID;
import java.util.regex.Pattern;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/filter/DynamicChatFilter.class */
public class DynamicChatFilter {
    private final UUID identifier;
    private final String name;
    private final byte priority;
    private final Icon largeIcon;
    private final Icon smallIcon;
    private final Pattern[] incomingMessagePatterns;
    private final String outgoingMessageFormat;

    public DynamicChatFilter(UUID identifier, String name, byte priority, Icon largeIcon, Icon smallIcon, Pattern[] incomingMessagePatterns, String outgoingMessageFormat) {
        this.identifier = identifier;
        this.name = name;
        this.priority = priority;
        this.largeIcon = largeIcon;
        this.smallIcon = smallIcon;
        this.incomingMessagePatterns = incomingMessagePatterns;
        this.outgoingMessageFormat = outgoingMessageFormat;
    }

    public UUID identifier() {
        return this.identifier;
    }

    public String name() {
        return this.name;
    }

    public byte priority() {
        return this.priority;
    }

    public Icon largeIcon() {
        return this.largeIcon;
    }

    public Icon smallIcon() {
        return this.smallIcon;
    }

    public Pattern[] incomingMessagePatterns() {
        return this.incomingMessagePatterns;
    }

    public String outgoingMessageFormat() {
        return this.outgoingMessageFormat;
    }
}
