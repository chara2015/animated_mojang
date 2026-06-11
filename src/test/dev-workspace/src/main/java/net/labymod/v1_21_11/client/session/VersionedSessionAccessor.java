package net.labymod.v1_21_11.client.session;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.models.Implements;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.core.client.session.DefaultSession;
import net.labymod.core.client.session.SessionUtil;
import net.labymod.core.client.session.UserFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/session/VersionedSessionAccessor.class */
@Singleton
@Implements(SessionAccessor.class)
public class VersionedSessionAccessor extends DefaultAbstractSessionAccessor<gfx> {
    private final UserFactory userFactory;

    @Inject
    public VersionedSessionAccessor(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override // net.labymod.api.client.session.SessionAccessor
    public Session createSession(String username, UUID uuid, String accessToken) {
        return new DefaultSession(username, uuid, accessToken, SessionUtil.isPremium(accessToken) ? Session.Type.MICROSOFT : Session.Type.LEGACY);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.core.client.session.DefaultAbstractSessionAccessor
    public gfx toGameSession(Session session) {
        return (gfx) this.userFactory.createUser(session);
    }

    @Override // net.labymod.core.client.session.DefaultAbstractSessionAccessor
    public Session fromGameSession(gfx gameSession) {
        if (gameSession == null) {
            return null;
        }
        UUID newUuidObject = UUID.fromString(gameSession.b().toString());
        return createSession(gameSession.c(), newUuidObject, gameSession.d());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.core.client.session.DefaultAbstractSessionAccessor
    public gfx getGameSession() {
        return gfj.V().ac();
    }
}
