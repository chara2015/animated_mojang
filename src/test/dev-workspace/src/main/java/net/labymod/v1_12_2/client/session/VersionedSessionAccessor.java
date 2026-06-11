package net.labymod.v1_12_2.client.session;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.models.Implements;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.core.client.session.DefaultSession;
import net.labymod.core.client.session.SessionUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/session/VersionedSessionAccessor.class */
@Singleton
@Implements(SessionAccessor.class)
public class VersionedSessionAccessor extends DefaultAbstractSessionAccessor<bii> {
    @Inject
    public VersionedSessionAccessor() {
    }

    @Override // net.labymod.api.client.session.SessionAccessor
    public Session createSession(String username, UUID uuid, String accessToken) {
        return new DefaultSession(username, uuid, accessToken, SessionUtil.isPremium(accessToken) ? Session.Type.MICROSOFT : Session.Type.LEGACY);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.core.client.session.DefaultAbstractSessionAccessor
    public bii toGameSession(Session session) {
        Session.Type type = session.getType();
        if (type == Session.Type.MICROSOFT) {
            type = Session.Type.MOJANG;
        }
        return new bii(session.getUsername(), session.getUniqueId().toString(), session.getAccessToken(), type.toString());
    }

    @Override // net.labymod.core.client.session.DefaultAbstractSessionAccessor
    public Session fromGameSession(bii gameSession) {
        if (gameSession == null) {
            return null;
        }
        return createSession(gameSession.c(), gameSession.e().getId(), gameSession.d());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.core.client.session.DefaultAbstractSessionAccessor
    public bii getGameSession() {
        return bib.z().K();
    }
}
