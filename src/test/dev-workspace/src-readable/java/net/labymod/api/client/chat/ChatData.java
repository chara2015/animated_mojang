package net.labymod.api.client.chat;

import net.labymod.api.Laby;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.version.VersionMultiRange;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatData.class */
public final class ChatData {
    public static final String CHAT_TRUST_VERSIONS = "1.19<*";
    private static final VersionCompatibility CHAT_TRUST_COMPATIBILITY = new VersionMultiRange(CHAT_TRUST_VERSIONS);
    private static final Lazy<LabyModLoader> LAZY_LOADER = Lazy.of(() -> {
        return Laby.labyAPI().labyModLoader();
    });

    private ChatData() {
    }

    public static boolean isChatTrustEnabled() {
        return CHAT_TRUST_COMPATIBILITY.isCompatible(LAZY_LOADER.get().version());
    }
}
