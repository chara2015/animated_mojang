package net.labymod.core.client.chat.prefix;

import net.labymod.api.client.chat.prefix.ChatPrefix;
import net.labymod.api.client.chat.prefix.ChatPrefixRegistry;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.core.client.chat.prefix.prefix.ChatPlayerHeadPrefix;
import net.labymod.core.client.chat.prefix.prefix.ChatTrustIndicatorPrefix;
import net.labymod.core.client.chat.prefix.prefix.ServerBadgePrefix;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/prefix/DefaultChatPrefixRegistry.class */
public class DefaultChatPrefixRegistry extends DefaultRegistry<ChatPrefix> implements ChatPrefixRegistry {
    public DefaultChatPrefixRegistry() {
        register("trust_indicator", new ChatTrustIndicatorPrefix());
        register("player_head", new ChatPlayerHeadPrefix());
        register("server_badge", new ServerBadgePrefix());
    }
}
