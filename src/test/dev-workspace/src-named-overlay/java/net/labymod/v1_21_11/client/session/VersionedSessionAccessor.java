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
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/session/VersionedSessionAccessor.class */
@Singleton
@Implements(SessionAccessor.class)
public class VersionedSessionAccessor extends DefaultAbstractSessionAccessor<User> {
    private final UserFactory userFactory;

    @Inject
    public VersionedSessionAccessor(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public Session createSession(String username, UUID uuid, String accessToken) {
        return new DefaultSession(username, uuid, accessToken, SessionUtil.isPremium(accessToken) ? Session.Type.MICROSOFT : Session.Type.LEGACY);
    }

    /* JADX INFO: renamed from: toGameSession, reason: merged with bridge method [inline-methods] */
    public User m35toGameSession(Session session) {
        return (User) this.userFactory.createUser(session);
    }

    public Session fromGameSession(User gameSession) {
        if (gameSession == null) {
            return null;
        }
        UUID newUuidObject = UUID.fromString(gameSession.getProfileId().toString());
        return createSession(gameSession.getName(), newUuidObject, gameSession.getAccessToken());
    }

    /* JADX INFO: renamed from: getGameSession, reason: merged with bridge method [inline-methods] */
    public User m34getGameSession() {
        return Minecraft.getInstance().getUser();
    }
}
