package net.labymod.api.client.session;

import java.util.UUID;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/SessionAccessor.class */
@Referenceable
public interface SessionAccessor {
    @Nullable
    Session getSession();

    void updateSession(Session session);

    Session createSession(String str, UUID uuid, String str2);

    boolean isPremium();

    @Deprecated
    @Nullable
    default Session session() {
        return getSession();
    }
}
